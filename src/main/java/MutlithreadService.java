import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.*;

public class MutlithreadService {

    public void myltyJob(File[] fileArray) {

        SessionFactory sessionFactory = HibernateService.getSessionFactory();
        BlockingQueue<File> queueOfFiles = new ArrayBlockingQueue<>(fileArray.length, false, Arrays.asList(fileArray));
        ExecutorService executorService = Executors.newFixedThreadPool(20); // TODO numver of threads

        for (int count = 0; count < 20; ++count) {
            executorService.submit(new MyRunnable(queueOfFiles, sessionFactory));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {

        } finally {
            HibernateService.closeSessionFactory();
        }
    }

    private class MyRunnable implements Runnable {
        final BlockingQueue<File> queueOfFiles;
        final SessionFactory sessionFactory;

        MyRunnable(BlockingQueue<File> queueOfFiles, SessionFactory sessionFactory) {
            this.queueOfFiles = queueOfFiles;
            this.sessionFactory = sessionFactory;
        }

        @Override
        public void run() {
            XmlFileProcessorService xmlFileProcessorService = new XmlFileProcessorService();
            File file = null;
            Entry entry = null;
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            try {
                while ((file = queueOfFiles.poll()) != null) {
                    entry = xmlFileProcessorService.mapXmlToEntity(file);
                    session.save(entry);
                    session.flush();
                    session.clear();
                    System.out.println(entry);
                }
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
            } finally {
                session.close();
                System.out.println("THREAD # " + Thread.currentThread().getName() + " IS FINISHED");
            }
        }
    }
}
