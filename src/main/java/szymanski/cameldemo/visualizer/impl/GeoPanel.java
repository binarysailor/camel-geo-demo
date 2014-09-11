package szymanski.cameldemo.visualizer.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class GeoPanel extends JPanel {
	private final static int CIRCLE_RADIUS = 6;
	private final static int SIZE_X = 500;
	private final static int SIZE_Y = 400;
	private final static int STEP = SIZE_X / 100;
	private final static Color BG_COLOR = new Color(40, 40, 40);
	private List<Dot> dots = new LinkedList<>();
	
	GeoPanel() {
		setSize(SIZE_X, SIZE_Y);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		paintFrame(g2);
		paintAxes(g2);
		paintDots(g2);
	}

	private void paintFrame(Graphics g) {
		g.setColor(BG_COLOR);
		g.fillRect(2, 2, getSize().width - 2, getSize().height- 2);
		g.setColor(Color.BLACK);
		g.drawRect(2, 2, getSize().width - 3, getSize().height- 3);
	}
	
	private void paintAxes(Graphics2D g) {
		BasicStroke stroke = new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1f, new float[] {2f, 2f}, 0f);
		g.setStroke(stroke);
		g.setColor(Color.gray);
		Dimension dim = new Dimension();
		getSize(dim);
		int width = dim.width;
		int height = dim.height;
		g.drawLine(width/2, 15, width/2, height-15);
		g.drawLine(15, height/2, width-15, height/2);
	}

	private void paintDots(Graphics g) {
		synchronized (dots) {
			for (Dot d : dots) {
				g.setColor(d.getColor());				
				g.fillArc(x(d), y(d), CIRCLE_RADIUS, CIRCLE_RADIUS, 0, 360);
			}
		}
	}

	private int y(Dot d) {
		return getSize().height/2 - d.getY() * STEP;
	}

	private int x(Dot d) {
		return getSize().width/2 + d.getX() * STEP;
	}

	public void addPoint(Dot dot) {
		synchronized (dots) {
			dots.add(dot);
		}
		invalidate();
	}
}
