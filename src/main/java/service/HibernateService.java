package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateService {

    private static final Logger logger = LogManager.getLogger(HibernateService.class);
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            logger.info("Hibernate session was created!");
        } catch (HibernateException ex) {
            logger.error("Failed to create hibernate session. See the logs!", ex);
            System.out.print("Failed to create hibernate session! Check the hibernate.cfg.xml");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null)
            sessionFactory.close();
        logger.info("Hibernate session was closed successfully");
    }
}
