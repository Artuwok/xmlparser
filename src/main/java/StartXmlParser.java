import service.MultiThreadXMLProcessingService;


public class StartXmlParser {

    public static void main(String[] args) {
        MultiThreadXMLProcessingService processingService = new MultiThreadXMLProcessingService();
        processingService.process();
    }
}

