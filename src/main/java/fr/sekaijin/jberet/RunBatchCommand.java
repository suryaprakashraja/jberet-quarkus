package fr.sekaijin.jberet;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.Step;
import org.jberet.job.model.StepBuilder;
import org.jboss.logging.Logger;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@QuarkusMain
@Command(name = "runBatch", mixinStandardHelpOptions = true, exitCodeOnSuccess = 12)
public class RunBatchCommand implements Runnable
{

	Logger LOG = Logger.getLogger(RunBatchCommand.class);

	@CommandLine.Parameters(paramLabel = "<name>", defaultValue = "picocli", description = "Your view name.")
	String name;
	
	@CommandLine.Option(names = "-e", defaultValue = "100")
	int end;
	
	@Inject
	QuarkusJobOperator jobOperator;

	long start() {
		final Properties noConfig = new Properties();
		
		final Step firstStep = new StepBuilder("step1")
				.itemCount(5)
				.reader(MockReader.NAME, MockReader.configure(end))
				.processor(MockProcessor.NAME, MockProcessor.configure(List.of("V", "Z", "Y", "X", "W")))
				.writer(MockWriter.NAME, noConfig)
				.listener(StepListener.NAME, noConfig)
				.nextOn("*").to("Step2")
				.build();
		
		final Step secondStep = new StepBuilder("Step2")
				.batchlet(MockBatchlet.NAME, noConfig)
				.listener(StepListener.NAME, noConfig)
				.build();
		
		Job job = new JobBuilder("myJob").step(firstStep).step(secondStep).listener(JobListener.NAME, noConfig)

				.build();

		return jobOperator.start(job, noConfig);
	}

	@Override
	public void run() {

		LOG.infof("Hello %s, go go commando! %d", name, end);
		long executionId = start();
		Quarkus.waitForExit();
		LOG.info(jobOperator.getJobExecution(executionId).getStartTime());
		LOG.info(jobOperator.getJobExecution(executionId).getEndTime());
		LOG.info(jobOperator.getJobExecution(executionId).getExitStatus());

	}
}
