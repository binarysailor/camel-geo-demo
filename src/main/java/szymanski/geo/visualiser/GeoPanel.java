package szymanski.geo.visualiser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class GeoPanel extends JPanel {
	private List<Point> points = new LinkedList<>();
	private Stencil stencil;
	
	GeoPanel(Stencil stencil) {
		setSize(500, 400);
		this.stencil = stencil;
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
				stencil.draw(g, p);
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