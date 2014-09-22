package szymanski.cameldemo.visualizer.api;

import java.awt.Color;

public interface VisualizerListener {
	void onPointShown(int panelIndex, int x, int y, Color color);
}
