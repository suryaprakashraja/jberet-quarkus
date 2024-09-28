package ltim.sample.jberetQuartz.core;

import java.io.Serializable;
import java.util.List;

import jakarta.batch.api.chunk.ItemWriter;

public abstract class AbstractItemWriter<C extends Serializable, O extends Object> implements ItemWriter {

	@SuppressWarnings("unchecked")
	@Override
	public void open(Serializable checkpoint) throws Exception {
		check((C)checkpoint);
	}

	public void check(C checkpoint) throws Exception {
		return;
	}

	@Override
	public void close() throws Exception {
		return;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void writeItems(List<Object> items) throws Exception {
		write((List<O>) items);
	}

	public abstract void write(List<O> items) throws Exception;

	@Override
	public C checkpointInfo() throws Exception {
		return null;
	}

}
