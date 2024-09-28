package ltim.sample.jberetQuartz.core;

import java.io.Serializable;

import jakarta.batch.api.chunk.ItemReader;

public abstract class AbstractItemReader<C extends Serializable, O extends Object> implements ItemReader {

	@SuppressWarnings("unchecked")
	@Override
	public void open(Serializable checkpoint) throws Exception {
		check((C)checkpoint);
	}

	@Override
	public void close() throws Exception {
		return;
	}

	@Override
	public abstract O readItem() throws Exception;

	@Override
	public C checkpointInfo() throws Exception {
		return null;
	}

	public void check(C checkpoint) throws Exception {
		return;
	}

}
