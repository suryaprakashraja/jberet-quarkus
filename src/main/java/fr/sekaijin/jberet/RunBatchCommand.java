package fr.sekaijin.jberet;

import java.util.Properties;

import javax.inject.Inject;

import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
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
//, QuarkusApplication 
{
//    @Inject
//    CommandLine.IFactory factory; 

    Logger LOG = Logger.getLogger(RunBatchCommand.class);

    @CommandLine.Parameters
    (paramLabel = "<name>", defaultValue = "picocli",
        description = "Your view name.")
    String name;
	@Inject
	QuarkusJobOperator jobOperator;

	long start() {
		Job job = new JobBuilder("programmatic")
				.step(
				new StepBuilder("programmaticStep")
				.itemCount(5)
				.reader("myItemReader", new Properties())
				.processor("myItemProcessor", new Properties())
				.writer("myItemWriter", new Properties())
				.listener("myStepListener", new Properties())
				.nextOn("*").to("programmaticStep2")
				.build()
				)
				.step(new StepBuilder("programmaticStep2")
						.batchlet("programmaticBatchlet", new Properties())
						.listener("myStepListener", new Properties())
						.build()
				)
//				.step(
//						new StepBuilder("programmaticStep2")
//						.reader("CamelItemReader", new Properties())
//						.writer("myItemWriter", new Properties())
//						.listener("myStepListener", new Properties())
//						.build()
//						)
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

//	@Override
//	public int run(String... args) throws Exception {
//		System.out.println("args" + args);
//		return new CommandLine(this, factory).execute(args);
//	}
//
//    public static void main(String[] args) {
//        System.setProperty("quarkus.banner.enabled", "false");
//        Quarkus.run(RunBatchCommand.class, args);
//    }
 }
