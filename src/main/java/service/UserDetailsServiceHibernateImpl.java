package service;

import domain.User;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserDetailsServiceHibernateImpl implements UserDetailsService  {

    protected static Logger logger = Logger.getLogger("org/service");

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.username = :username");
        query.setParameter("username", username);
        User result = (User) query.uniqueResult();
        if (result == null) throw new UsernameNotFoundException("username: " + username + " not found!");
        return result;
    }

}
