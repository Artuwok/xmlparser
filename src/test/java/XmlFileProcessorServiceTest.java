import org.junit.Before;
import org.junit.Test;
import repository.model.Entry;
import service.XmlFileProcessorService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class XmlFileProcessorServiceTest {
    XmlFileProcessorService xmlFileProcessorService;

    @Before
    public void initialize() {
        xmlFileProcessorService = new XmlFileProcessorService();
    }


    @Test
    public void checkXmlFileProcessorServiceCreated() {
        XmlFileProcessorService xmlFileProcessorService = new XmlFileProcessorService();
        assertNotNull(xmlFileProcessorService);
    }

    @Test
    public void XMLToEntityTest() {
        File file = null;
        try {
            file = new File("testFile.xml");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("<Entry>" +
                    "<content>123</content>" +
                    "<creationDate>2014-01-01 00:00:00</creationDate>" +
                    "</Entry>");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Entry entry = xmlFileProcessorService.XMLToEntity(file);
        assertNotNull(entry);
        assertEquals("123", entry.getContent());
    }
}
