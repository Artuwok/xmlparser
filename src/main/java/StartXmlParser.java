import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StartXmlParser {

    public static void main(String[] args) {

        /*PART 1 LOAD PROPERTIES*/
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            System.out.println(prop.getProperty("database"));
            System.out.println(prop.getProperty("dbuser"));
            System.out.println(prop.getProperty("dbpassword"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        /*END OF PART 1*/

        XmlFileProcessorService x = new XmlFileProcessorService();
        File[] xxx = x.checkForXMLFiles("/home/artemvlasenko/xmltest");

        SessionFactory sessionFactory = HibernateService.getSessionFactory();
        long processTime = System.currentTimeMillis();
        for (File zz : xxx) {
            Session session = sessionFactory.openSession();
            Entry entry = x.mapXmlToEntity(zz);
            session.beginTransaction();
            long id = (Long) session.save(entry);
            session.getTransaction().commit();
            // Entry entry1= (Entry) session.get(Entry.class, id);
            System.out.println(entry);
            session.close();
        }
        HibernateService.closeSessionFactory();
        long duration = System.currentTimeMillis() - processTime;
        System.out.println(duration);
        //http://stackoverflow.com/questions/1442720/how-to-use-multiple-threads-to-process-large-number-of-files-stored-in-the-local
    }
}

