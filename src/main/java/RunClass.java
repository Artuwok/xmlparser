import java.io.File;

public class RunClass {

    public static void main(String[] args) {

        XmlFileProcessorService x = new XmlFileProcessorService();
        File[] xxx = x.checkForXMLFiles("/home/artemvlasenko");
        System.out.println("print file size: " + xxx.length);
        System.out.println(xxx[0].toString());

        Entry entry = x.xmlToObjectMapper(xxx[0]);
        System.out.println(entry.getCreationDate() + "  " + entry.getContent());

        // http://stackoverflow.com/questions/5509638/create-a-pool-of-jaxb-unmarshaller

    }
}

