package fr.sekaijin.jberet.listener;

import javax.batch.api.listener.AbstractJobListener;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

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
