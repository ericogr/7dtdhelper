package br.com.sdtd.helper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * @param args the command processLine arguments
     */
    public static void main(String[] args) {
        new Main();
    }
    
    
    public Main() {
        log.info("Starting...");
        
        Options options = Options.load();
        
        ExecutorSchedule vipSchedule = new ExecutorSchedule(options);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(vipSchedule, options.getCheckInterval(), options.getCheckInterval(), TimeUnit.MILLISECONDS);
    }
}
