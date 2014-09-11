package szymanski.cameldemo;

import java.awt.Point;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class Text2PointProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String text = exchange.getIn().getBody(String.class);
		exchange.getOut().setBody(toPoint(text));
	}

	private Point toPoint(String text) {
		String[] coordinates = text.split("\\s");
		int x = Integer.parseInt(coordinates[0]);
		int y = Integer.parseInt(coordinates[1]);
		return new Point(x, y);
	}

}
