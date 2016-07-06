package job;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.pojo.entity.system.JobExectimer;
import com.hjy.service.IJobService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class JobExectimerTest {

	@Autowired
	public IJobService jobService;
	@Test
	public void findList(){
		JobExectimer entity = new JobExectimer();
		List<JobExectimer> list = jobService.listJobExectimer(entity);
		System.out.println(list.size());
	}
}
