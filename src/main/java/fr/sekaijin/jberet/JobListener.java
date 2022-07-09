package fr.sekaijin.jberet;

import javax.batch.api.listener.AbstractJobListener;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.jboss.logging.Logger;

import io.quarkus.runtime.Quarkus;

@Named("myJobListener")
@Dependent
public class JobListener extends AbstractJobListener {
	

	Logger LOGGER = Logger.getLogger(JobListener.class);

	
	@Override
	public void afterJob() throws Exception {
		LOGGER.info("afterJob");
		Quarkus.asyncExit(0);
	}

}
