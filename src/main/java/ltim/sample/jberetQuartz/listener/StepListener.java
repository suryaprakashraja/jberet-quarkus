package ltim.sample.jberetQuartz.listener;

import jakarta.batch.api.listener.AbstractStepListener;
import jakarta.batch.runtime.context.StepContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.jboss.logging.Logger;

@Named(StepListener.NAME)
@Dependent
public class StepListener extends AbstractStepListener {


	public static final String NAME = "myStepListener";

	Logger LOGGER = Logger.getLogger(StepListener.class);

    @Inject
    StepContext stepContext;


	@Override
	public void afterStep() throws Exception {
		LOGGER.infof("afterStep %s", stepContext.getStepName());
	}

}
