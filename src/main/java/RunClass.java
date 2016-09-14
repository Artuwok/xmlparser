import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.File;

public class RunClass {

    public static void main(String[] args) {

        XmlFileProcessorService x = new XmlFileProcessorService();
        File[] xxx = x.checkForXMLFiles("/home/artemvlasenko");
        Entry entry = x.xmlToObjectMapper(xxx[0]);


        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long id = (Long) session.save(entry);
        session.getTransaction().commit();
        // Entry entry1= (Entry) session.get(Entry.class, id);
        System.out.println(entry);
        session.close();
        HibernateUtil.closeSessionFactory();

    }
}

