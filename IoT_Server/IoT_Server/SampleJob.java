package org.TimerTest.TimerTest;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-08-12
 */
public class SampleJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("### Hello Job is being executed11111111111!");
    }
}
