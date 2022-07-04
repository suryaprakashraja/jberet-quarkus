package fr.sekaijin.jberet;


import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchStatus;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.jboss.logging.Logger;

@Named
@Dependent
public class ProgrammaticBatchlet extends AbstractBatchlet {
	
	
	Logger LOGGER = Logger.getLogger(ProgrammaticBatchlet.class);

	
    @Override
    public String process() throws Exception {
    	LOGGER.info("goo");
        return BatchStatus.COMPLETED.toString();
    }
}
