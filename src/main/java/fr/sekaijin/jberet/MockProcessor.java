package fr.sekaijin.jberet;

import java.util.List;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

@Named(MockProcessor.NAME)
@Dependent
public class MockProcessor implements ItemProcessor {

	static final String NAME = "myItemProcessor";
	Logger LOGGER = Logger.getLogger(MockProcessor.class);

	@Inject
	@BatchProperty(name = "resource")
	List<String> tr;

	@Inject
	@BatchProperty(name = "failed")
	Boolean failed;

	int counter=0;
	
	@Override
	public Object processItem(Object item) throws Exception {
		if(failed && 33 < counter++) {
			throw new Exception("fail on 33");
		}
		return tr.get(Integer.class.cast(item) % 5);
	}

}
