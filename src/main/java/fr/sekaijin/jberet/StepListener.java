package fr.sekaijin.jberet;

import javax.batch.api.listener.AbstractStepListener;
import javax.batch.runtime.context.StepContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

@Named("myStepListener")
@Dependent
public class StepListener extends AbstractStepListener {
	

	Logger LOGGER = Logger.getLogger(StepListener.class);

    @Inject
    private StepContext stepContext;

	
	@Override
	public void afterStep() throws Exception {
		LOGGER.infof("afterStep %s", stepContext.getStepName());
	}

}
