package ltim.sample.jberetQuartz.listener;

import jakarta.batch.api.listener.AbstractJobListener;
import jakarta.batch.runtime.context.JobContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.jboss.logging.Logger;

@Named(JobListener.NAME)
@Dependent
public class JobListener extends AbstractJobListener {

	public static final String NAME = "myJobListener";

	Logger LOGGER = Logger.getLogger(JobListener.class);

	@Inject
	JobContext jobContext;

	@Override
	public void afterJob() throws Exception {
		LOGGER.infof("afterJob %s", jobContext.getJobName());
	}

}
