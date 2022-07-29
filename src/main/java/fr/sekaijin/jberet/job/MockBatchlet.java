package fr.sekaijin.jberet.job;


import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchStatus;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

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
