package fr.sekaijin.jberet;

import javax.batch.api.listener.AbstractJobListener;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.Quarkus;

@Named("myJobListener")
@Dependent
public class JobListener extends AbstractJobListener {
	

	Logger LOGGER = Logger.getLogger(JobListener.class);
	
	@Inject
	QuarkusJobOperator jobOperator;

    @Inject
    private JobContext jobContext;
	
	
	@Override
	public void afterJob() throws Exception {
		LOGGER.infof("afterJob %s", jobContext.getJobName());
		
		int code = jobOperator.getJobExecution(jobContext.getExecutionId()).getBatchStatus().ordinal();
		Quarkus.asyncExit(code);
	}

}
