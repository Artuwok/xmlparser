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
            System.out.println(prop.getProperty("inputdirectory"));
            System.out.println(prop.getProperty("outputdirectory"));
            System.out.println(prop.getProperty("unprocesseddirectory"));
            System.out.println(prop.getProperty("monitoringinterval"));

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

        /// https://examples.javacodegeeks.com/core-java/util/concurrent/scheduledexecutorservice/java-scheduledexecutorservice-example/
        /*PART 2 EXECUTOR SERVICE*/

        long startTime = System.currentTimeMillis();
        XmlFileProcessorService xmlFileProcessorService = new XmlFileProcessorService();
        File[] allFiles = xmlFileProcessorService.checkForXMLFiles("/home/artemvlasenko/xmltest");
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Read time: " + duration + " ms to read: " + allFiles.length + " files");
        startTime = System.currentTimeMillis();
        MutlithreadService x = new MutlithreadService();
        x.myltyJob2(allFiles);

        duration = System.currentTimeMillis() - startTime;
        System.out.println("Program takes: " + duration + " ms to process: " + allFiles.length + " files");

        //http://stackoverflow.com/questions/1442720/how-to-use-multiple-threads-to-process-large-number-of-files-stored-in-the-local
    }
}

