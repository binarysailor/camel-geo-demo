package szymanski.cameldemo.visualizer.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import szymanski.cameldemo.visualizer.api.Stoppable;
import szymanski.cameldemo.visualizer.api.Visualizer;
import szymanski.cameldemo.visualizer.api.VisualizerListener;

@SuppressWarnings("serial")
class GeoFrame extends JFrame implements Visualizer {
	private static final int PANELS_X = 2;
	private static final int PANELS_Y = 2;
	private GeoPanel[] panels;
	private List<VisualizerListener> listeners = new LinkedList<>();
	
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
	public void showPoint(int panelIndex, int x, int y, Color color) {
		panels[panelIndex].addPoint(new Dot(x, y, color));
		panels[panelIndex].repaint();
		for (VisualizerListener listener : listeners) {
			listener.onPointShown(panelIndex, x, y, color);
		}
	}
	
	@Override
	public void registerStoppable(final Stoppable stoppable) {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stoppable.stop();
			}
		});
	}
	
	@Override
	public void addListener(VisualizerListener listener) {
		listeners.add(listener);
	}
}
