package fr.sekaijin.jberet;

import javax.batch.api.chunk.AbstractItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.jboss.logging.Logger;



@Named("myItemReader")
@Dependent
public class MockReader extends AbstractItemReader {
	
	Logger LOGGER = Logger.getLogger(MockReader.class);

	@Override
	public Object readItem() throws Exception {
		LOGGER.info("read");
		return 1;
	}


}
