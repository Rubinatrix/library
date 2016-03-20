package service.interfaces;

import domain.Book;
import domain.BookContent;
import domain.User;
import org.springframework.web.multipart.MultipartFile;
import utils.OnlineLibraryException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface BookService {

    List<Book> getAll();
    List<Book> getAllByGenreId(Long genreId);
    List<Book> getBySearchString(String searchString);
    List<Book> getFavoritesByUser(User user);

    void addToFavorites(User user, Long bookId);
    void deleteFromFavorites(User user, Long bookId);

    Book get(Long id);
    void add(Book book);
    void edit(Book book);
    void delete(Long id);

    BookContent getContentByBookId(Long bookId);
    void updateBookContent(Book book, MultipartFile file) throws OnlineLibraryException;

}