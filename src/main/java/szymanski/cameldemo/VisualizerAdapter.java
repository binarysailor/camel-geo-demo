package szymanski.cameldemo;

import java.awt.Color;
import java.awt.Point;

import org.apache.camel.Exchange;

import szymanski.cameldemo.visualizer.api.Visualizer;

public class VisualizerAdapter {
	private Visualizer visualizer;

	public VisualizerAdapter(Visualizer v) {
		this.visualizer = v;
	}

	public void draw(Exchange ex) {
		Object body = ex.getIn().getBody();
		
		if (body instanceof Point) {
			Point p = (Point) body;
			visualizer.showPoint(Color.BLACK, 0, p.x, p.y);
		} else {
			throw new UnsupportedOperationException("Only Points can be drawn");
		}
	}
}
