package fr.sekaijin.jberet;

import static fr.sekaijin.jberet.core.PropertiesBuilder.EMPTY;
import static fr.sekaijin.jberet.core.PropertiesBuilder.properties;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.Step;
import org.jberet.job.model.StepBuilder;
import org.jberet.repository.ApplicationAndJobName;
import org.jberet.runtime.JobExecutionImpl;
import org.jboss.logging.Logger;

import fr.sekaijin.jberet.job.MockBatchlet;
import fr.sekaijin.jberet.job.MockProcessor;
import fr.sekaijin.jberet.job.MockReader;
import fr.sekaijin.jberet.job.MockWriter;
import fr.sekaijin.jberet.listener.JobListener;
import fr.sekaijin.jberet.listener.StepListener;
import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@QuarkusMain
@Command(name = "runBatch", mixinStandardHelpOptions = true, exitCodeOnSuccess = 12)
public class RunBatchCommand implements Runnable, QuarkusApplication {

	Logger LOG = Logger.getLogger(RunBatchCommand.class);
	@Inject
	CommandLine.IFactory factory;

	@CommandLine.Parameters(paramLabel = "<name>", defaultValue = "picocli", description = "Your view name.")
	String name;

	@CommandLine.Option(names = { "-e", "--end" }, defaultValue = "100")
	int end;

	@CommandLine.Option(names = "--restart")
	int restart;

	@CommandLine.Option(names = "--failed", defaultValue = "false")
	boolean failed;

	@Inject
	QuarkusJobOperator jobOperator;

	@ConfigProperty(name = "quarkus.application.name")
	String app;

	@ConfigProperty(name = "quarkus.application.version")
	String version;

	long start() {

		final Step firstStep = new StepBuilder("step1").itemCount(5)
				.reader(MockReader.NAME, properties().add("end", end).build())
				.processor(MockProcessor.NAME,
						properties()
						.add("resource", List.of("V", "Z", "Y", "X", "W"))
						.add("failed", failed)
						.add("int", 15).build())
				.writer(MockWriter.NAME, EMPTY).listener(StepListener.NAME, EMPTY)

				.failOn("FAIL").exitStatus("Step 1 fail").next("Step2").build();

		final Step secondStep = new StepBuilder("Step2").batchlet(MockBatchlet.NAME, EMPTY)
				.listener(StepListener.NAME, EMPTY).build();

		Job job = new JobBuilder("myJob").step(firstStep).step(secondStep).listener(JobListener.NAME, EMPTY).build();

		ApplicationAndJobName name = new ApplicationAndJobName(app, "myJob");
		jobOperator.getBatchEnvironment().getJobRepository().addJob(name, job);

		LOG.info(restart);

		if (0 == restart) {
			return jobOperator.start(job, EMPTY);
		} else {
			return jobOperator.restart(restart, EMPTY);
		}
		
	}

	@Override
	public void run() {

		LOG.infof("Hello %s, go go commando! %d", name, end);
		long executionId = start();
        final JobExecutionImpl jobExecution = JobExecutionImpl.class.cast(jobOperator.getJobExecution(executionId));
		try {
			jobExecution.awaitTermination(0, TimeUnit.SECONDS);  //no timeout
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Quarkus.asyncExit(jobOperator.getJobExecution(executionId).getBatchStatus().ordinal());
		Quarkus.waitForExit();
		LOG.info(app);
		LOG.info(version);
		LOG.infof("Execution # %d", executionId);
		LOG.info(jobOperator.getJobExecution(executionId).getStartTime());
		LOG.info(jobOperator.getJobExecution(executionId).getEndTime());
		LOG.info(jobOperator.getJobExecution(executionId).getExitStatus());

	}

	@Override
	public int run(String... args) throws Exception {
		return new CommandLine(this, factory).execute(args);
	}
}
