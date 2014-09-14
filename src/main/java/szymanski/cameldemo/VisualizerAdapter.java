package szymanski.cameldemo;

import java.awt.Color;
import java.awt.Point;

import org.apache.camel.Exchange;

import szymanski.cameldemo.visualizer.api.Visualizer;

public class VisualizerAdapter {
	private final static int DEFAULT_PANEL = 0;
	private final static Color DEFAULT_COLOR = Color.black;
	private Visualizer visualizer;
	private int panelIndex;

	public VisualizerAdapter(Visualizer v) {
		this.visualizer = v;
		this.panelIndex = DEFAULT_PANEL;
	}
	
	public VisualizerAdapter(Visualizer v, int panelIndex) {
		this(v);
		this.panelIndex = panelIndex;
	}

	public void draw(Exchange ex) {
		Object body = ex.getIn().getBody();
		
		if (body instanceof Point) {
			Point p = (Point) body;
			visualizer.showPoint(panelIndex, p.x, p.y, DEFAULT_COLOR);
		} else {
			throw new UnsupportedOperationException("Only Points can be drawn");
		}
	}
}
