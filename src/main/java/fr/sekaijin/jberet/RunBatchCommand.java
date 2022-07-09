package fr.sekaijin.jberet;

import java.util.Properties;

import javax.inject.Inject;

import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.StepBuilder;
import org.jboss.logging.Logger;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.Quarkus;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "runBatch", mixinStandardHelpOptions = true)
public class RunBatchCommand implements Runnable {

    Logger LOG = Logger.getLogger(RunBatchCommand.class);

    @Parameters(paramLabel = "<name>", defaultValue = "picocli",
        description = "Your view name.")
    String name;
	@Inject
	QuarkusJobOperator jobOperator;

	long start() {
		Job job = new JobBuilder("programmatic")
//				.step(
//				new StepBuilder("programmaticStep")
//				.reader("myItemReader", new Properties())
//				.writer("myItemWriter", new Properties())
//				.listener("myStepListener", new Properties())
//				.build()
//				)
				.step(
						new StepBuilder("programmaticStep2")
						.reader("CamelItemReader", new Properties())
						.writer("myItemWriter", new Properties())
						.listener("myStepListener", new Properties())
						.build()
						)
				.listener("myJobListener", new Properties())

			.build();

		return jobOperator.start(job, new Properties());
	}

    @Override
    public void run() {

        LOG.infof("Hello %s, go go commando!", name);
		long executionId = start();
		Quarkus.waitForExit();
		LOG.info(jobOperator.getJobExecution(executionId).getStartTime());
		LOG.info(jobOperator.getJobExecution(executionId).getEndTime());
		LOG.info(jobOperator.getJobExecution(executionId).getExitStatus());
//
//		return jobOperator.getJobExecution(executionId).getBatchStatus().ordinal();


    }

}
