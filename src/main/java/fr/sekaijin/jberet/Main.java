package fr.sekaijin.jberet;

import java.util.Properties;

import javax.inject.Inject;

import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.StepBuilder;
import org.jboss.logging.Logger;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main implements QuarkusApplication {
	@Inject
	QuarkusJobOperator jobOperator;
	
	Logger LOGGER = Logger.getLogger(Main.class);

	long start() {
		Job job = new JobBuilder("programmatic")
				.step(new StepBuilder("programmaticStep")
//                      .batchlet("programmaticBatchlet")
					.reader("myItemReader", new Properties())
					.writer("myItemWriter", new Properties())
					.build())
				.build();

//		long executionId = 
			return	jobOperator.start(job, new Properties());
	}

	@Override
	public int run(String... args) throws Exception {
		long executionId = start();
		Quarkus.waitForExit();
		LOGGER.info(jobOperator.getJobExecution(executionId).getStartTime());
		LOGGER.info(jobOperator.getJobExecution(executionId).getEndTime());
		LOGGER.info(jobOperator.getJobExecution(executionId).getExitStatus());
		return 0;
	}

}
