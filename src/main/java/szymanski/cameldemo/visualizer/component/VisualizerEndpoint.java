package szymanski.cameldemo.visualizer.component;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

import szymanski.cameldemo.visualizer.api.Visualizer;

public class VisualizerEndpoint extends DefaultEndpoint {

	private String remaining;
	private int panelIndex;
	
	public VisualizerEndpoint(Component component, String uri, String remaining) {
		super(uri, component);
		this.remaining = remaining;
		parsePanelIndex();
	}

	private void parsePanelIndex() {
		if (remaining.matches("panel[0-3]")) {
			this.panelIndex = Integer.parseInt(remaining.substring(remaining.length() - 1));
		} else {
			throw new IllegalArgumentException("Invalid visualizer endpoint URI");
		}
	}

	@Override
	public Producer createProducer() throws Exception {
		return new VisualizerProducer(this);
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		throw new UnsupportedOperationException("Visualizer does not support consumers");
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public Visualizer getVisualizer() {
		VisualizerComponent component = (VisualizerComponent)getComponent();
		return component.getVisualizer();
	}

	public int getPanelIndex() {
		return panelIndex;
	}

}
