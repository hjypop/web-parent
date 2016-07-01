package job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.selleradapter.job.JobForOrderStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class JobTest {

	private Scheduler createScheduler() throws SchedulerException {// 创建调度器
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		return schedulerFactory.getScheduler();
	}

	private void scheduleJob(Scheduler scheduler) throws SchedulerException {
		// Create a job detail for the job
		JobDetail jobDetail = JobBuilder.newJob(JobForOrderStatus.class)
				.withIdentity("ScanDirectory", "jobDetail-group").withDescription("ScanDirectory from tomcat conf")
				.build();
		System.out.println("jobDetail--------->" + jobDetail);
		// Configure the directory to scan
		jobDetail.getJobDataMap().put("SCAN_DIR", "E:\\myself\\tomcat\\apache-tomcat-7.0.67\\conf");

		// Create a trigger that fires every 10 seconds,forever
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("scanTrigger", "trigger-group").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60 * 5)) // 每5分钟触发一次
				.build();

		// Associate the trigger with the job in the schedule
		scheduler.scheduleJob(jobDetail, trigger);

	}

	@Test
	public void TestJob() {
		JobTest simpleSchedulerTests = new JobTest();
		try {
			Scheduler scheduler = simpleSchedulerTests.createScheduler();
			// Start the scheduler running
			scheduler.start();
			simpleSchedulerTests.scheduleJob(scheduler);
			// Stop the scheduler after 10 second
			Thread.sleep(10 * 60 * 1000);
			scheduler.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
