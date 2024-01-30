package soo.company.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import soo.company.batch.helloworld.HelloWorldJobConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorldJobFunctionalTests {

    @Test
    public void testLaunchJob() throws Exception {
        // given
        ApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldJobConfiguration.class);
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean(Job.class);

        // when
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());

        // then
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

}