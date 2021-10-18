package main.webapp.Java.Functions;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import javax.servlet.http.HttpServlet;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;


public class mainScheduler extends HttpServlet {

    public void mainScheduling() throws SchedulerException {
        System.out.println("it is started");


        JobDetail job = newJob(setCurrency.class) //defining the job class
                .withIdentity("myJob")
                .build();

        Trigger t = newTrigger()
                .withIdentity("myTrigger")
                .forJob("myJob")
                .withSchedule(dailyAtHourAndMinute(18, 30))// execute job daily at 18:30
                .build();
        Scheduler scheduler = null;
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, t);
    }

}
