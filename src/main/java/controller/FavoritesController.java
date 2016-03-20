package controller;

import domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.interfaces.BookService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/favorites")
public class FavoritesController {

    protected static Logger logger = Logger.getLogger("org/controller");

    @Resource
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBookToFavoritesById(@RequestParam("id") Long bookId) {
        logger.debug("Received request to add book to favorites");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookService.addToFavorites(user, bookId);
        return "redirect:/book/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteBookFromFavoritesById(@RequestParam("id") Long bookId) {
        logger.debug("Received request to delete book from favorites");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookService.deleteFromFavorites(user, bookId);
        return "redirect:/";
    }

}