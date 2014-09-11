package szymanski.cameldemo.visualizer.impl;

import java.awt.Color;

class Dot {
	private int x, y;
	private Color color;
	
	Dot(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		this.color = c;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	Color getColor() {
		return color;
	}

}
