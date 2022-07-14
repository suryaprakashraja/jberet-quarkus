package fr.sekaijin.jberet;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.jboss.logging.Logger;

@Named(MockWriter.NAME)
@Dependent
public class MockWriter extends AbstractItemWriter {
	
	static final String NAME = "myItemWriter";
	Logger LOGGER = Logger.getLogger(MockReader.class);

	@Override
	public void writeItems(List<Object> items) throws Exception {
		LOGGER.infof("write %s", items);
		
//		for (Object object : items) {
//			Exchange e = Exchange.class.cast(object);
//			LOGGER.infof("write %s", e.getIn().getHeaders());
//			
//		}
		
	}

}

