package szymanski.cameldemo.visualizer.component;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

import szymanski.cameldemo.VisualizerAdapter;

public class VisualizerProducer extends DefaultProducer {

	private VisualizerAdapter adapter;

	public VisualizerProducer(Endpoint endpoint) {
		super(endpoint);
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		adapter.draw(exchange);
	}
	
	@Override
	public void start() throws Exception {
		VisualizerEndpoint endpoint = (VisualizerEndpoint)getEndpoint();
		adapter = new VisualizerAdapter(endpoint.getVisualizer(), endpoint.getPanelIndex());
		super.start();
	}
}
