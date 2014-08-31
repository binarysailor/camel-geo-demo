package szymanski.geo.visualiser;

import java.awt.GridLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GeoFrame extends JFrame {
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
				GeoPanel panel = new GeoPanel(new CircleStencil(10));
				add(panel);
				panels[i*PANELS_Y + j] = panel;
			}
		}
	}
	
	public void addPoint(int panelIndex, int x, int y) {
		panels[panelIndex].addPoint(x, y);
		panels[panelIndex].repaint();
	}
}
