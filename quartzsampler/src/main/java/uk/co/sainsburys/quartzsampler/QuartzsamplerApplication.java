package uk.co.sainsburys.quartzsampler;

import java.beans.BeanProperty;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class QuartzsamplerApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuartzsamplerApplication.class, args);
  }

  @Service
  public class SampleService {
    public void performTask(String value) {
      System.out.println("Service task performed! " + value);
    }
  }

  @Component
  public class SampleJob implements Job {
    private final SampleService sampleService;

    public SampleJob(SampleService sampleService) {
      this.sampleService = sampleService;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
      sampleService.performTask(context.getMergedJobDataMap().getString("exampleKey"));
    }
  }

  @Bean
  public JobDetail jobDetail() {
    return JobBuilder.newJob(SampleJob.class)
        .withIdentity("sampleJob")
        .usingJobData("exampleKey", "exampleValue")
        .storeDurably()
        .build();
  }

  @Bean
  public JobDetail jobDetail2() {
    return JobBuilder.newJob(SampleJob.class)
        .withIdentity("sampleJob2")
        .usingJobData("exampleKey", "exampleValue1")
        .storeDurably()
        .build();
  }

  @Bean
  public Trigger trigger(@Qualifier("jobDetail") JobDetail jobDetail) {
    return org.quartz.TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity("sampleTrigger")
        .withSchedule(org.quartz.SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(10)
            .repeatForever())
        .build();
  }

  @Bean
  public Trigger trigger2(@Qualifier("jobDetail2") JobDetail jobDetail) {
    return org.quartz.TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity("sampleTrigger2")
        .withSchedule(org.quartz.SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(10)
            .repeatForever())
        .build();
  }
}

