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
        File[] xxx = x.checkForXMLFiles("/home/artemvlasenko");
        Entry entry = x.mapXmlToEntity(xxx[0]);


        SessionFactory sessionFactory = HibernateService.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long id = (Long) session.save(entry);
        session.getTransaction().commit();
        // Entry entry1= (Entry) session.get(Entry.class, id);
        System.out.println(entry);
        session.close();
        HibernateService.closeSessionFactory();

    }
}

