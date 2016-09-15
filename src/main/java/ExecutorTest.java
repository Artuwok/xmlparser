import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {

    public static void main(String[] args) {

        ScheduledExecutorService execService = Executors.newScheduledThreadPool(5);

        execService.scheduleAtFixedRate(() -> {
            //The repetitive task, say to update Database
            System.out.println("hi there at: " + new java.util.Date());
        }, 0, 1000L, TimeUnit.MILLISECONDS);

    }
}
