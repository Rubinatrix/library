package service;

import domain.User;
import org.springframework.stereotype.Service;
import service.interfaces.UserService;
import utils.OnlineLibraryErrorType;
import domain.Role;
import dto.UserDTO;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import org.hibernate.Query;
import utils.OnlineLibraryException;
import java.util.List;

@Service
@Transactional
public class UserServiceHibernateImpl implements UserService {

    protected static Logger logger = Logger.getLogger("org/service");

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public User registerNewUser(UserDTO accountDTO) throws OnlineLibraryException {

        String username = accountDTO.getUsername();

        if ((username.toLowerCase().equals("guest") || username.toLowerCase().equals("admin"))) {
            throw new OnlineLibraryException(OnlineLibraryErrorType.USERNAME_NOT_ALLOWED);
        } else if (nameExist(username)) {
            throw new OnlineLibraryException(OnlineLibraryErrorType.USER_EXISTS);
        }

        User user = new User();
        user.setUsername(accountDTO.getUsername());
        user.setPassword(accountDTO.getPassword());
        user.setUserRole(Role.ROLE_USER);
        add(user);
        return user;
    }

    @Override
    public User findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT u FROM User u WHERE u.username = :username");
        query.setParameter("username", name);
        User user = (User) query.uniqueResult();
        return user;
    }

    @Override
    public User get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        return user;
    }

    @Override
    public void add(User user) {
        logger.debug("Adding new user");
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    public boolean delete(Long id) {
        logger.debug("Deleting existing user");
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT u FROM User as u WHERE u.id = :userId");
        query.setParameter("userId", id);
        if (query.list().size() == 0) {
            User user = (User) session.get(User.class, id);
            session.delete(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void edit(User user) {
        logger.debug("Editing existing user");
        Session session = sessionFactory.getCurrentSession();
        User existingUser = (User) session.get(User.class, user.getId());
        existingUser.setUsername(user.getUsername());
        session.save(existingUser);
    }

    private boolean nameExist(String name) {
        User user = findByName(name);
        if (user != null) {
            return true;
        }
        return false;
    }

}
