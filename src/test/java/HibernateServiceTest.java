import org.junit.Before;
import org.junit.Test;
import service.HibernateService;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HibernateServiceTest {

    @Before
    public void createHibernateService() {
        HibernateService hibernateService = new HibernateService();
    }


    @Test
    public void testSessionFactoryIsCreated() {
        assertNotNull(HibernateService.getSessionFactory());
    }

    @Test
    public void hibernateSessionFactoryIsClosed() {
        HibernateService.closeSessionFactory();
        assertTrue(HibernateService.getSessionFactory().isClosed());

    }
}


