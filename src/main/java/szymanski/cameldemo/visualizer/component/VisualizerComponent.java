package szymanski.cameldemo.visualizer.component;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import szymanski.cameldemo.CamelUtils;
import szymanski.cameldemo.visualizer.api.Visualizer;
import szymanski.cameldemo.visualizer.impl.VisualizerFactory;

public class VisualizerComponent extends DefaultComponent {

	private Visualizer visualizer;
	
	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		return new VisualizerEndpoint(this, uri, remaining);
	}
	
	@Override
	public void start() throws Exception {
		super.start();
		visualizer = VisualizerFactory.create();
		visualizer.registerStoppable(CamelUtils.stoppableCamel(getCamelContext()));
	}

	public Visualizer getVisualizer() {
		return visualizer;
	}

}
