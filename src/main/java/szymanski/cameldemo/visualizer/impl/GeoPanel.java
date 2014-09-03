package szymanski.cameldemo.visualizer.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class GeoPanel extends JPanel {
	private final static int CIRCLE_RADIUS = 3;
	private List<Point> points = new LinkedList<>();
	
	GeoPanel() {
		setSize(500, 400);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, getSize().width, getSize().height);
		g.setColor(Color.black);
		g.drawRect(2, 2, getSize().width - 8, getSize().height- 8);
		synchronized (points) {
			for (Point p : points) {
				g.fillArc(p.x + getWidth()/2, p.y + getHeight()/2, CIRCLE_RADIUS, CIRCLE_RADIUS, 0, 360);
			}
		}
	}
	
	public void addPoint(int x, int y) {
		synchronized (points) {
			points.add(new Point(x, y));
		}
		invalidate();
	}
}
