package org.TimerTest.TimerTest;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.CronScheduleBuilder;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-08-12
 */
public class QuartzTest {

    public void start() throws SchedulerException, InterruptedException {

    	SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

    	  Scheduler sched = schedFact.getScheduler();

    	  sched.start();

    	  // define the job and tie it to our HelloJob class
    	  JobDetail job = newJob(SampleJob.class)
    	      .withIdentity("myJob", "group1")
    	      .build();

    	  // Trigger the job to run now, and then every 40 seconds
    	  Trigger trigger = newTrigger()
    	      .withIdentity("myTrigger", "group1")
    	      .startNow()
    	      .withSchedule(org.quartz.CronScheduleBuilder.cronSchedule("0 0 0 * * *"))
    	      .build();

    	  // Tell quartz to schedule the job using our trigger
    	  sched.scheduleJob(job, trigger);
    }
}
