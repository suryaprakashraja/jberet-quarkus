package fr.sekaijin.jberet;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;



@Named(MockReader.NAME)
@Dependent
public class MockReader extends AbstractItemReader {
	
	static final String NAME = "myItemReader";

	Logger LOGGER = Logger.getLogger(MockReader.class);
	
	int count = 1;
	int end;
	

	@Inject
	@BatchProperty(name = "end")
	String conf;

	

	@Override
	public Object readItem() throws Exception {
		LOGGER.infof("read %d", count);
		if (end < count ) {
			return null;
		} else {
			return count ++;
		}
	}

	@PostConstruct
	public void init() {
		end = Integer.parseInt(conf);
	}
		

	public static Properties configure(int end) {
		Properties props = new Properties();
		props.setProperty("end", String.valueOf(end));
		return props;
	}

}
