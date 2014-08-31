package szymanski.geo.visualiser;

import java.awt.Graphics;
import java.awt.Point;

class CircleStencil implements Stencil {

	private int radius = 5;
	
	
	CircleStencil(int radius) {
		this.radius = radius;
	}

	@Override
	public void draw(Graphics g, Point coords) {
		g.fillArc(coords.x, coords.y, radius, radius, 0, 360);
	}

}
