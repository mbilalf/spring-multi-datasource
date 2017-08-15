package bilal.multids.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WowReportScheduler {

    //@Scheduled(cron = "*/10 * * * * *")
    public void testJob(){
        System.out.println(".. testJob " + System.currentTimeMillis());
    }
}
