package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateService {

    private static final SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger(HibernateService.class);

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            logger.info("HIBERNATE SESSION WAS CREATED SUCCESSFULLY!");
        } catch (Throwable ex) {
            logger.info("FAIL TO CREATE SESSION FACTORY!");
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null)
            sessionFactory.close();
    }
}
