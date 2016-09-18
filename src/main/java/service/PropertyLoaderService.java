package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoaderService {

    private static final Properties props = PropertyLoaderService.loadProperties();
    public static final String INPUT_DIRECTORY = props.getProperty("inputdirectory");
    public static final String OUTPUT_DIRECTOTY = props.getProperty("outputdirectory");
    public static final String ERROR_DIRECTORY = props.getProperty("unprocesseddirectory");
    private static final String MONITORING_INTERVAL = props.getProperty("monitoringinterval");


    private static Properties loadProperties() {

        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            //  System.out.println(prop.getProperty("database"));
            //   System.out.println(prop.getProperty("dbuser"));
            //  System.out.println(prop.getProperty("dbpassword"));
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
        return prop;
    }

    public static Properties getProperties() {
        return props;
    }
}
