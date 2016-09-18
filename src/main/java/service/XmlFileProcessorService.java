package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.model.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class XmlFileProcessorService {

    private static final Logger logger = LogManager.getLogger(XmlFileProcessorService.class);
    private Unmarshaller jaxbUnmarshaller;

    public XmlFileProcessorService() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Entry.class);
            this.jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public File[] checkForXMLFiles(String path) {
        File directory = new File(path);
        if (directory.isDirectory()) {
            File[] xmlFiles = directory.listFiles((folder, name) -> name.toLowerCase().endsWith(".xml"));
            logger.info("Number is xml files is: " + xmlFiles.length);
            return xmlFiles;
        }
        return new File[0];
    }

    public Entry XMLToEntity(File file) {
        Entry entry = null;
        try {
            entry = (Entry) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            logger.error("ERROR PARSING XML FILE: " + file.getName());
            moveFile(file, true);
        }
        return entry;
    }

    public boolean moveFile(File file, boolean isBrokenFile) {

        Path movePath = isBrokenFile ?
                new File(PropertyLoaderService.ERROR_DIRECTORY + file.getName()).toPath() :
                new File(PropertyLoaderService.OUTPUT_DIRECTOTY + file.getName()).toPath();
        try {
            Files.move(file.toPath(), movePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}

