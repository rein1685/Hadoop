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
        
        HBase testhb = App.hb;
        
        /*Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);*/
        
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int day = localDate.getDateValue();
        
        for(HashMap<String , String> data : datas)
        {
            String cmpDate = data.get("created_at");
            
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date checkDate = transFormat.parse(cmpDate);
            LocalDate checkLocalDate = checkdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            
            checkMonth = checkLocalDate.getMonthValue();
            checkDay = checkLocalDate.getMonthValue();
            
            BufferedWriter fw = new BufferedWriter(new FileWriter(checkLocalDate.getYearValue()+"/"+month+"/"+day-1"+".csv" , true));
            
            if(month == checkMonth && day -1 == checkDay)
            {
                String temp = data.get("temp");
                String humidity = data.get("humidity");
                String cds = data.get("cds");
                String vol_bat = data.get("vol_bat");
                
                String formatString = cmpDate + ", " + temp + ", " + humidity + ", " + cds + ", " + vol_bat";
                
                System.out.println(cmpDate);
                System.out.println("----------File-------------");
                fw.write(formatString);
                fw.newLine();
            }
            
            fw.flush();
            fw.close();
        }
    }
}
