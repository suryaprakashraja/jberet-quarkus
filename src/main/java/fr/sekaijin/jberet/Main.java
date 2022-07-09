package fr.sekaijin.jberet;

import java.util.Properties;

import javax.inject.Inject;

import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.StepBuilder;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main implements QuarkusApplication {
	@Inject
	QuarkusJobOperator jobOperator;

	void start() {
		Job job = new JobBuilder("programmatic")
				.step(new StepBuilder("programmaticStep")
//                      .batchlet("programmaticBatchlet")
					.reader("myItemReader", new Properties())
					.writer("myItemWriter", new Properties())
					.build())
				.build();

//		long executionId = 
				jobOperator.start(job, new Properties());
	}

	@Override
	public int run(String... args) throws Exception {
		start();
		Quarkus.waitForExit();
		return 0;
	}

}
