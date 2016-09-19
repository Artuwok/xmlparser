import org.junit.Before;
import org.junit.Test;
import service.PropertyLoaderService;

import static org.junit.Assert.assertNotNull;

public class PropertyLoaderServiceTest {

    @Before
    public void loadProperties() {
        PropertyLoaderService.getProperties();
    }

    @Test
    public void assertPropertiesAreNotEmpty() {
        assertNotNull(PropertyLoaderService.ERROR_DIRECTORY);
        assertNotNull(PropertyLoaderService.INPUT_DIRECTORY);
        assertNotNull(PropertyLoaderService.OUTPUT_DIRECTORY);
        assertNotNull(PropertyLoaderService.MONITORING_INTERVAL);
    }

    @Test
    public void checkPropertiesAreCreated(){
        assertNotNull(PropertyLoaderService.getProperties());
    }
}
