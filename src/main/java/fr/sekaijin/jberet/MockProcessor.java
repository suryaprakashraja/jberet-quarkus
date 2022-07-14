package fr.sekaijin.jberet;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Named(MockProcessor.NAME)
@Dependent
public class MockProcessor implements ItemProcessor {

	static final String NAME = "myItemProcessor";

	List<String> tr;

	@Inject
	@BatchProperty(name = "resource")
	String conf;

	@Override
	public Object processItem(Object item) throws Exception {
		return tr.get(Integer.class.cast(item) % 5);
	}

	@PostConstruct
	public void init() {
		tr = List.of(conf.split(", "));
	}
		

	public static Properties configure(List<String> conf) {
		Properties props = new Properties();
		props.setProperty("resource", conf.toString().replaceAll("^.|.$", ""));
		return props;
	}

}
