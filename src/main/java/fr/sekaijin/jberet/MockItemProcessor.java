package fr.sekaijin.jberet;

import java.util.List;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Named("myItemProcessor")
@Dependent
public class MockItemProcessor implements ItemProcessor {
	
	List<String> tr = List.of("A", "B", "C", "D", "E"); 

	@Override
	public Object processItem(Object item) throws Exception {
		return tr.get(Integer.class.cast(item) % 5);
	}

}
