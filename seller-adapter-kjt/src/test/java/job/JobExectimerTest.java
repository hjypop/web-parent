package job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.selleradapter.job.JobGetChangeProductFromKJT;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class JobExectimerTest {

//	@Autowired
//	public IJobService jobService;
//	@Test
//	public void findList(){
//		JobExectimer entity = new JobExectimer();
//		List<JobExectimer> list = jobService.listJobExectimer(entity);
//		System.out.println(list.size());
//	}
	
	@Test
	public void findList(){
		new JobGetChangeProductFromKJT().doExecute(null); 
	}
}
