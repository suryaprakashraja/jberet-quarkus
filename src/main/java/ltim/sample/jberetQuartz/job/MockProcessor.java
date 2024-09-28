package ltim.sample.jberetQuartz.job;

import java.util.List;

import jakarta.batch.api.BatchProperty;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import ltim.sample.jberetQuartz.core.AbstracItemProcessor;

@Named(MockProcessor.NAME)
@Dependent
public class MockProcessor extends AbstracItemProcessor<Integer, String> {

	public static final String NAME = "myItemProcessor";
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
