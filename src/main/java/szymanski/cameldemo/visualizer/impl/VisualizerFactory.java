package szymanski.cameldemo.visualizer.impl;

import szymanski.cameldemo.visualizer.api.Visualizer;

public class VisualizerFactory {
	public static Visualizer create() {
		GeoFrame frame = new GeoFrame();
		frame.setVisible(true);
		return frame;
	}
}
