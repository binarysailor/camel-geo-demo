package szymanski.cameldemo.visualizer.api;

import java.awt.Color;

public interface Visualizer {
	void showPoint(Color color, int panelIndex, int x, int y);
	void registerStoppable(Stoppable stoppable);
}
