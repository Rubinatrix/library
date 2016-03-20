package controller;

import controller.property_editor.GenrePropertyEditor;
import domain.Book;
import domain.BookContent;
import domain.Genre;
import domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.interfaces.BookService;
import service.interfaces.GenreService;
import utils.OnlineLibraryErrorType;
import utils.OnlineLibraryException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    protected static Logger logger = Logger.getLogger("org/controller");

    @Value("${guest_name}")
    private String guestName;

    @Value("${buffer_size}")
    private int bufferSize;

    @Resource
    @Autowired
    private BookService bookService;

    @Resource
    @Autowired
    private GenreService genreService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getLibraryPage(@RequestParam(name = "gid", required = false) Long genreId, @RequestParam(name = "search", required = false) String searchString, Model model, Principal principal) {
        logger.debug("Received request to show books page");
        List<Book> books;
        if (searchString != null) {
            books = bookService.getBySearchString(searchString);
        } else if (genreId != null) {
            books = bookService.getAllByGenreId(genreId);
        } else {
            books = bookService.getAll();
        }
        Collection<Genre> genres = genreService.getAll();
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", guestName);
        }
        model.addAttribute("books", books);
        model.addAttribute("booksQuantity", books.size());
        model.addAttribute("genres", genres);
        return "library";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddBookPage(Model model) {
        logger.debug("Received request to show add books page");

        Book book = new Book();
        Collection<Genre> genres = genreService.getAll();

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("type", "add");

        return "book";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBook(@RequestParam("file") MultipartFile file, @ModelAttribute("book") Book book) throws OnlineLibraryException {
        logger.debug("Received request to add new book");

        bookService.add(book);
        bookService.updateBookContent(book, file);

        return "redirect:list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteBook(@RequestParam("id") Long bookId) {
        logger.debug("Received request to delete book");
        bookService.delete(bookId);
        return "redirect:list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditBookPage(@RequestParam("id") Long bookId, Model model, Principal principal) {
        logger.debug("Received request to show edit book page");

        Book existingBook = bookService.get(bookId);
        Collection<Genre> genres = genreService.getAll();

        model.addAttribute("book", existingBook);
        model.addAttribute("genres", genres);
        model.addAttribute("type", "edit");

        if (principal != null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Book> userBooks = bookService.getFavoritesByUser(user);
            model.addAttribute("isFavorite", userBooks.contains(existingBook));
        }

        return "book";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateBook(@RequestParam("id") Long bookId, @RequestParam("file") MultipartFile file, @ModelAttribute("book") Book book) throws OnlineLibraryException {
        logger.debug("Received request to add new book");

        bookService.edit(book);
        bookService.updateBookContent(book, file);

        return "redirect:list";
    }

    // pass type = "read" if we only need to read book, not to download
    @RequestMapping(value = "/content/{type}", method = RequestMethod.GET)
    public String readBook(@PathVariable("type") String type, @RequestParam("id") Long bookId, HttpServletResponse response, Model model) throws OnlineLibraryException {

        String filename = "book_" + bookService.get(bookId).getId().toString() + ".pdf";
        BookContent content = bookService.getContentByBookId(bookId);

        if (content == null) {
            model.addAttribute("contentError", OnlineLibraryErrorType.LACK_OF_CONTENT.getName());
            return "redirect:/book/edit?id=" + bookId;
        }

        response.setContentType("application/pdf");
        response.setContentLength(content.getContent().length);
        String contentDispositionType = type.equals("read") ? "inline" : "attachment";
        response.setHeader("Content-Disposition", String.format(contentDispositionType + "; filename=\"" + filename + "\""));

        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getContent());
                OutputStream outputStream = response.getOutputStream();
        ) {
            byte[] buffer = new byte[bufferSize];
            int bytesRead = -1;

            while ((bytesRead = byteArrayInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new OnlineLibraryException(e.getMessage());
        }

        return null;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean checkContent() {
        return false;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(Genre.class, "genre", new GenrePropertyEditor(genreService));
    }

}