package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.model.Entry;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.*;

public class MultiThreadXMLProcessingService {

    private static final Logger logger = LogManager.getLogger(MultiThreadXMLProcessingService.class);
    private final int THREADS_NUMBER = 10;

    public void process() {

        SessionFactory sessionFactory = HibernateService.getSessionFactory();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_NUMBER);
        executorService.scheduleAtFixedRate((new ExecutorServiceStarter
                        (sessionFactory, THREADS_NUMBER, executorService)), 0,
                PropertyLoaderService.MONITORING_INTERVAL, TimeUnit.MINUTES);
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
                    logger.info(entry + " Thread name: " + Thread.currentThread().getName());
                    xmlProcessor.moveFile(file, false);
                }
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
            } finally {
                session.close();
                logger.info("Thread # " + Thread.currentThread().getName() + " is finished");
            }
        }
    }

    private class ExecutorServiceStarter implements Runnable {
        final ExecutorService executorService;
        final SessionFactory sessionFactory;
        long threadCount;

        ExecutorServiceStarter(SessionFactory sessionFactory, long threadCount, ExecutorService executorService) {
            this.executorService = executorService;
            this.threadCount = threadCount;
            this.sessionFactory = sessionFactory;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            File[] files = new XmlFileProcessorService().checkForXMLFiles(PropertyLoaderService.INPUT_DIRECTORY);
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Read time: " + duration + " ms to read: " + files.length + " files from file system");

            if (files.length != 0) {
                BlockingQueue<File> queueOfFiles = new ArrayBlockingQueue<>(files.length, false, Arrays.asList(files));
                for (int count = 0; count < threadCount; ++count) {
                    executorService.submit(new MyRunnable(queueOfFiles, sessionFactory));
                }
            }
        }
    }
}
