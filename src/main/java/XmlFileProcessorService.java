import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FilenameFilter;

public class XmlFileProcessorService {


    public File[] checkForXMLFiles(String path) {
        File directory = new File(path);
        if (directory.isDirectory()) {
            File[] xmlFiles = directory.listFiles(new FilenameFilter() {
                public boolean accept(File folder, String name) {
                    return name.toLowerCase().endsWith(".xml");
                }
            });
            return xmlFiles;
        }
        return new File[0];
    }

    public Entry xmlToObjectMapper(File file) {
        Entry entry = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Entry.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            entry = (Entry) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return entry;
    }
}
