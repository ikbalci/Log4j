import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyTimerLoggings {

    private static final Logger logger = LogManager.getLogger(MyTimerLoggings.class);

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        scheduler.scheduleAtFixedRate(new DebugTask(), 0, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new InfoTask(), 0, 1, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(new ErrorTask(), 0, 1, TimeUnit.HOURS);

        try {
            Thread.sleep(86400000); // Run for one day
        } catch (InterruptedException e) {
            logger.error("Thread interrupted", e);
        }

        scheduler.shutdown();
    }

    static class DebugTask implements Runnable {
        @Override
        public void run() {
            LocalDateTime now = LocalDateTime.now();
            String time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logger.debug("Current time: " + time);
        }
    }

    static class InfoTask implements Runnable {
        @Override
        public void run() {
            LocalDateTime now = LocalDateTime.now();
            String time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00"));
            logger.info("Current minute: " + time);
        }
    }

    static class ErrorTask implements Runnable {
        @Override
        public void run() {
            LocalDateTime now = LocalDateTime.now();
            String time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00"));
            logger.error("Current hour: " + time);
        }
    }
}
