package fr.sekaijin.jberet;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.jboss.logging.Logger;

@Named("myItemWriter")
@Dependent
public class MockWriter extends AbstractItemWriter {
	
	Logger LOGGER = Logger.getLogger(MockReader.class);

	@Override
	public void writeItems(List<Object> items) throws Exception {
		LOGGER.info("write");
		
	}

}

