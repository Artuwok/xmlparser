package service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.model.Entry;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.*;

public class MultiThreadService {

    private final int THREADS_NUMBER = 1;

    public void multiJob2() {

        SessionFactory sessionFactory = HibernateService.getSessionFactory();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);
        ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate((new XRunnable(sessionFactory, THREADS_NUMBER, executorService)), 0, 20L, TimeUnit.SECONDS);

      /*  boolean isJobDone = scheduledFuture.isDone();

        while (!isJobDone) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isJobDone = scheduledFuture.isDone();
        }

        if (isJobDone) {
            HibernateService.closeSessionFactory();
            System.exit(0);
        }*/

    }

    private class MyRunnable implements Runnable {
        private final BlockingQueue<File> queueOfFiles;
        private final SessionFactory sessionFactory;


        MyRunnable(BlockingQueue<File> queueOfFiles, SessionFactory sessionFactory) {
            this.queueOfFiles = queueOfFiles;
            this.sessionFactory = sessionFactory;
        }

        @Override
        public void run() {
            XmlFileProcessorService xmlProcessor = new XmlFileProcessorService();
            File file = null;
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            try {
                while ((file = queueOfFiles.poll()) != null) {
                    Entry entry = xmlProcessor.XMLToEntity(file);
                    session.save(entry);
                    session.flush();
                    session.clear();
                    System.out.println(entry + " Thread name: " + Thread.currentThread().getName());
                    xmlProcessor.moveFile(file, false);
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

    private class XRunnable implements Runnable {
        final ExecutorService executorService;
        final SessionFactory sessionFactory;
        long threadCount;

        XRunnable(SessionFactory sessionFactory, long threadCount, ExecutorService executorService) {
            this.executorService = executorService;
            this.threadCount = threadCount;
            this.sessionFactory = sessionFactory;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            File[] files = new XmlFileProcessorService().checkForXMLFiles(PropertyLoaderService.INPUT_DIRECTORY);
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("Read time: " + duration + " ms to read: " + files.length + " files");

            if (files.length != 0) {
                BlockingQueue<File> queueOfFiles = new ArrayBlockingQueue<>(files.length, false, Arrays.asList(files));
                for (int count = 0; count < threadCount; ++count) {
                    executorService.submit(new MyRunnable(queueOfFiles, sessionFactory));
                }
            }
        }
    }
}
