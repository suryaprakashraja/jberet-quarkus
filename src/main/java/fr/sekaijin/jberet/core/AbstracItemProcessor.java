package fr.sekaijin.jberet.core;

import javax.batch.api.chunk.ItemProcessor;

import org.jboss.logging.Logger;

import fr.sekaijin.jberet.job.MockProcessor;

public abstract class AbstracItemProcessor<I extends Object, O extends Object> implements ItemProcessor {

	Logger LOGGER = Logger.getLogger(MockProcessor.class);

	public AbstracItemProcessor() {
		super();
	}

	public abstract O process(I item) throws Exception;

	@SuppressWarnings("unchecked")
	@Override
	public Object processItem(Object item) throws Exception {
		return process((I)item);
	}

}