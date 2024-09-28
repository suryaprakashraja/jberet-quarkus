package ltim.sample.jberetQuartz.job;

import jakarta.batch.api.BatchProperty;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.jboss.logging.Logger;

import ltim.sample.jberetQuartz.core.AbstractItemReader;

@Named(MockReader.NAME)
@Dependent
public class MockReader extends AbstractItemReader<Integer, Integer> {

	public static final String NAME = "myItemReader";

	Logger LOGGER = Logger.getLogger(MockReader.class);

	int count = 1;

	@Inject
	@BatchProperty(name = "end")
	Integer end;

	@Override
	public void check(Integer checkpoint) throws Exception {
		LOGGER.infof("checkpoint %d", checkpoint);
		count = (null == checkpoint) ? count : checkpoint;
	}

	@Override
	public Integer readItem() throws Exception {
		if (end < count) {
			return null;
		} else {
			LOGGER.infof("read %d", count);
			return count++;
		}
	}

	@Override
	public Integer checkpointInfo() throws Exception {
		return count;
	}

}
