package ltim.sample.jberetQuartz.job;


import jakarta.batch.api.AbstractBatchlet;
import jakarta.batch.runtime.BatchStatus;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

import org.jboss.logging.Logger;

@Named(MockBatchlet.NAME)
@Dependent
public class MockBatchlet extends AbstractBatchlet {


	public static final String NAME = "myBatchlet";
	Logger LOGGER = Logger.getLogger(MockBatchlet.class);


    @Override
    public String process() throws Exception {
    	LOGGER.info("goo");
        return BatchStatus.COMPLETED.toString();
    }
}
