package szymanski.cameldemo.visualizer.component;

import java.awt.Color;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

import szymanski.cameldemo.visualizer.api.VisualizerListener;

public class VisualizerConsumer extends DefaultConsumer implements VisualizerListener {

	public VisualizerConsumer(Endpoint endpoint, Processor processor) {
		super(endpoint, processor);
	}

	@Override
	public void start() throws Exception {
		super.start();
		VisualizerEndpoint endpoint = (VisualizerEndpoint)getEndpoint();
		endpoint.getVisualizer().addListener(this);
	}
	
	@Override
	public void onPointShown(int panelIndex, int x, int y, Color color) {
		if (panelIndex != ((VisualizerEndpoint)getEndpoint()).getPanelIndex()) {
			return;
		}
		VisualizerPoint point = new VisualizerPoint();
		point.panelIndex = panelIndex;
		point.x = x;
		point.y = y;
		point.r = color.getRed();
		point.g = color.getGreen();
		point.b = color.getBlue();
		try {
			String body = toXml(point);
			Exchange exchange = getEndpoint().createExchange();
			exchange.getIn().setBody(body);
			getProcessor().process(exchange);
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}

	private String toXml(VisualizerPoint point) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(VisualizerPoint.class);
		StringWriter writer = new StringWriter();
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(point, writer);
		return writer.getBuffer().toString();
	}
}
