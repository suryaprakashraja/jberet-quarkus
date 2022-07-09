package fr.sekaijin.jberet;

import javax.batch.api.listener.AbstractStepListener;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.jboss.logging.Logger;

@Named("myStepListener")
@Dependent
public class StepListener extends AbstractStepListener {
	

	Logger LOGGER = Logger.getLogger(StepListener.class);

	
	@Override
	public void afterStep() throws Exception {
		LOGGER.info("afterStep");
	}

}
