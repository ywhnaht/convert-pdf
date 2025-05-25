package util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConvertQueue {
    private static ConvertQueue instance;
    private final BlockingQueue<ConvertJob> queue = new LinkedBlockingQueue<>();

    private ConvertQueue() {
        new Thread(() -> {
            while (true) {
                try {
                    ConvertJob job = queue.take();
                    job.process();
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
