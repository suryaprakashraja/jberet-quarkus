package fr.sekaijin.jberet.job;

import javax.batch.api.BatchProperty;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import fr.sekaijin.jberet.core.AbstractItemReader;

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
