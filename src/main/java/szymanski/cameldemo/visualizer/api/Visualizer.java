package szymanski.cameldemo.visualizer.api;

import java.awt.Color;

public interface Visualizer {
	void showPoint(int panelIndex, int x, int y, Color color);
	void registerStoppable(Stoppable stoppable);
}
