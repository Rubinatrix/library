package service.interfaces;

import domain.User;
import dto.UserDTO;
import utils.OnlineLibraryException;

public interface UserService {

    User findByName(String name);

    User get(Long id);
    void add(User user);
    void edit(User user);
    boolean delete(Long id);

    User registerNewUser(UserDTO accountDTO) throws OnlineLibraryException;

}
