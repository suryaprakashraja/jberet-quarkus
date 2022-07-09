package fr.sekaijin.jberet;

import java.io.Serializable;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.PollingConsumer;
import org.jboss.logging.Logger;

public abstract class AbstractCamelReader extends AbstractItemReader {


	Logger LOGGER = Logger.getLogger(CamelFileReader.class);
	
	@Inject
	CamelContext context;
	
	Endpoint file;

	private PollingConsumer consumer;

	
	@Override
	public void open(Serializable checkpoint) throws Exception { 
		String uri = getEndPointUri();
		LOGGER.info("init " + uri);
		file=context.getEndpoint(uri);
		consumer = file.createPollingConsumer();
		consumer.build();
		consumer.start();
	}

	protected abstract String getEndPointUri();

	@Override
	public void close() throws Exception {
		consumer.stop();
		consumer.close();
	}

	@Override
	public Object readItem() throws Exception {
		Exchange e = consumer.receive(300);
		if( null!= e){
			LOGGER.info(e.getIn().getHeader(Exchange.FILE_NAME_ONLY));
		}
		return e;
	}
}
