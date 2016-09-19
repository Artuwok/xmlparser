package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class PropertyLoaderService {

    private static final Logger logger = LogManager.getLogger(PropertyLoaderService.class);
    private static final Properties props = PropertyLoaderService.loadProperties();
    public static final String INPUT_DIRECTORY = props.getProperty("input_directory");
    public static final String OUTPUT_DIRECTORY = props.getProperty("output_directory");
    public static final String ERROR_DIRECTORY = props.getProperty("unprocessed_directory");
    public static final int MONITORING_INTERVAL = Integer.parseInt(props.getProperty("monitoring_interval"));

    private static Properties loadProperties() {
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("config.properties"));
            logger.info(prop.getProperty("input_directory"));
            logger.info(prop.getProperty("output_directory"));
            logger.info(prop.getProperty("unprocessed_directory"));
            logger.info(prop.getProperty("monitoring_interval"));
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("Error loading properties file. Check config.properties file", ex);
            System.out.println("Error loading properties file. Check config.properties file");
        }
        logger.info("Properties are loaded");
        return prop;
    }

    public static Properties getProperties() {
        return props;
    }
}
