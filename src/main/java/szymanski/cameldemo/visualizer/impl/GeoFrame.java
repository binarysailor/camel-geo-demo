package szymanski.cameldemo.visualizer.impl;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;

import szymanski.cameldemo.visualizer.api.Visualizer;

@SuppressWarnings("serial")
class GeoFrame extends JFrame implements Visualizer {
	private static final int PANELS_X = 2;
	private static final int PANELS_Y = 2;
	private GeoPanel[] panels;
	
	public GeoFrame() {
		setTitle("Visualiser");
		setSize(1200, 800);
		setLayout(new GridLayout(2, 2));
		addPanels();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void addPanels() {
		panels = new GeoPanel[PANELS_X * PANELS_Y];
		for (int i = 0; i < PANELS_X; i++) {
			for (int j = 0; j < PANELS_Y; j++) {
				GeoPanel panel = new GeoPanel();
				add(panel);
				panels[i*PANELS_Y + j] = panel;
			}
		}
	}

	@Override
	public void showPoint(Color color, int panelIndex, int x, int y) {
		panels[panelIndex].addPoint(x, y);
		panels[panelIndex].repaint();		
	}
}
