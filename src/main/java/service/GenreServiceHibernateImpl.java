package service;

import domain.Genre;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.GenreService;
import utils.OnlineLibraryErrorType;
import utils.OnlineLibraryException;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class GenreServiceHibernateImpl implements GenreService, InitializingBean{

    protected static Logger logger = Logger.getLogger("org/service");

    // store genre list to avoid getting it from database for each request
    private Map<Long, Genre> allGenres;

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Collection<Genre> getAll() {
        logger.debug("Retrieving all genres");
        return allGenres.values();
    }

    @Override
    public Genre get(Long id) {
        return allGenres.get(id);
    }

    @Override
    public void add(Genre genre) {
        logger.debug("Adding new genre");
        Session session = sessionFactory.getCurrentSession();
        session.save(genre);
    }

    @Override
    public void delete(Long id) throws OnlineLibraryException{
        logger.debug("Deleting existing genre");
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT b FROM Book as b WHERE b.genre.id = :genreId");
        query.setParameter("genreId", id);
        if (query.list().size() == 0) {
            Genre genre = (Genre) session.get(Genre.class, id);
            session.delete(genre);
        } else {
            throw new OnlineLibraryException(OnlineLibraryErrorType.OPERATION_NOT_ALLOWED);
        }
    }

    @Override
    public void edit(Genre genre) {
        logger.debug("Editing existing genre");
        Session session = sessionFactory.getCurrentSession();
        Genre existingGenre = (Genre) session.get(Genre.class, genre.getId());
        existingGenre.setName(genre.getName());
        session.save(existingGenre);
    }

    private Map<Long, Genre> cacheGenres() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Genre ORDER BY name");
        Map<Long, Genre> genresCache = new LinkedHashMap<>();
        for (Object genre: query.list()){
            genresCache.put(((Genre)genre).getId(), (Genre) genre);
        }
        session.close();
        return genresCache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        allGenres = cacheGenres();
    }
}