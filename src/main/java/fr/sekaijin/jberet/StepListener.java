package fr.sekaijin.jberet;

import javax.batch.api.listener.AbstractStepListener;
import javax.batch.runtime.context.StepContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

@Named(StepListener.NAME)
@Dependent
public class StepListener extends AbstractStepListener {
	

	static final String NAME = "myStepListener";

	Logger LOGGER = Logger.getLogger(StepListener.class);

    @Inject
    StepContext stepContext;

	
	@Override
	public void afterStep() throws Exception {
		LOGGER.infof("afterStep %s", stepContext.getStepName());
	}

}
