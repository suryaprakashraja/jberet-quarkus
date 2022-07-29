package fr.sekaijin.jberet;

import java.util.List;

import javax.batch.api.BatchProperty;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Named(MockProcessor.NAME)
@Dependent
public class MockProcessor extends AbstracItemProcessor<Integer, String> {

	static final String NAME = "myItemProcessor";
	@Inject
	@BatchProperty(name = "resource")
	List<String> tr;

	@Inject
	@BatchProperty(name = "failed")
	Boolean failed;

	int counter=0;

	@Override
	public String process(Integer item) throws Exception {
		if(failed && 33 < counter++) {
			throw new Exception("fail on 33");
		}
		return tr.get(item % 5);
	}

}
