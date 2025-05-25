package util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConvertQueue {
    private static ConvertQueue instance;
    private final BlockingQueue<ConvertJob> queue = new LinkedBlockingQueue<>();
    private final ThreadPoolExecutor executor;

    private ConvertQueue() {
        int processors = Runtime.getRuntime().availableProcessors();
        executor = new ThreadPoolExecutor(
            processors,
            processors, 
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>()
        );

        new Thread(() -> {
            while (true) {
                try {
                    ConvertJob job = queue.take();
                    executor.submit(() -> {
                        try {
                            job.process();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static synchronized ConvertQueue getInstance() {
        if (instance == null) instance = new ConvertQueue();
        return instance;
    }

    public void addJob(ConvertJob job) {
        queue.add(job);
    }    
}
