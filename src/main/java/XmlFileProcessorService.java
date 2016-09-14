import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlFileProcessorService {

    private static final Logger logger = LogManager.getLogger(XmlFileProcessorService.class);

    public File[] checkForXMLFiles(String path) {
        File directory = new File(path);
        if (directory.isDirectory()) {
            File[] xmlFiles = directory.listFiles((folder, name) -> name.toLowerCase().endsWith(".xml"));
            logger.error("Number is xml files is: " + xmlFiles.length);
            return xmlFiles;
        }
        return new File[0];
    }

    public Entry mapXmlToEntity(File file) {
        Entry entry = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Entry.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            entry = (Entry) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            logger.error("ERROR PARSING XML FILE: " + file.getName());
        }
        return entry;
    }
}
