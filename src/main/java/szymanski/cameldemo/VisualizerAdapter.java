package szymanski.cameldemo;

import java.awt.Color;
import java.awt.Point;

import org.apache.camel.Exchange;

import szymanski.cameldemo.visualizer.api.Visualizer;

public class VisualizerAdapter {
	private final static int DEFAULT_PANEL = 0;
	private final static Color DEFAULT_COLOR = Color.WHITE;
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
			final Color messageColor = (Color)ex.getIn().getHeader("pointColor");
			final Color color = messageColor != null ? messageColor : DEFAULT_COLOR;
			visualizer.showPoint(panelIndex, p.x, p.y, color);
		} else {
			throw new UnsupportedOperationException("Only Points can be drawn");
		}
	}
}
