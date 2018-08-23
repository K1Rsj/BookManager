package net.proselyte.bookmanager.dao;

import net.proselyte.bookmanager.model.Book;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);

    private HibernateTransactionManager hibernateTransactionManager;
//    private SessionFactory sessionFactory;
//
//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Autowired
    public BookDaoImpl(HibernateTransactionManager hibernateTransactionManager) {
        this.hibernateTransactionManager = hibernateTransactionManager;
    }

    @Override
    public void addBook(Book book) {
        Session session = hibernateTransactionManager.getSessionFactory().getCurrentSession();
        session.persist(book);
        logger.info("Book successfully saved. Book details: " + book);
    }

    @Override
    public void updateBook(Book book) {
        Session session = hibernateTransactionManager.getSessionFactory().getCurrentSession();
        session.update(book);
        logger.info("Book successfully update. Book details: " + book);
    }

    @Override
    public void removeBook(int id) {
        Session session = hibernateTransactionManager.getSessionFactory().getCurrentSession();
        Book book = session.load(Book.class, id);

        if(book!=null){
            session.delete(book);
        }
        logger.info("Book successfully removed. Book details: " + book);
    }

    @Override
    public Book getBookById(int id) {
        Session session = hibernateTransactionManager.getSessionFactory().getCurrentSession();
        Book book = session.load(Book.class, id);
        logger.info("Book successfully loaded. Book details: " + book);

        return book;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> listBooks() {
        Session session = hibernateTransactionManager.getSessionFactory().getCurrentSession();
        List<Book> bookList = session.createQuery("from Book").list();

        for(Book book: bookList){
            logger.info("Book list: " + book);
        }

        return bookList;
    }
}
