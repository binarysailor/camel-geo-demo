package szymanski.cameldemo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Message;

public class PointRecipientList {
	public void calculate(Message message) {
		Point point = message.getBody(Point.class);
		List<Integer> indexes = new ArrayList<>();
		indexes.add(0);
		if (inCircle(30, point.x, point.y)) {
			indexes.add(1);
		}
		if (inSquare(50, point.x, point.y)) {
			indexes.add(2);
		}
		if (nearIdentityLine(5, point.x, point.y)) {
			indexes.add(3);
		}
		message.setHeader("adapterIndexes", indexes.toArray(new Integer[0]));
	}

	private boolean inCircle(int r, int x, int y) {
		return x*x + y*y <= r*r;
	}
	
	private boolean inSquare(int side, int x, int y) {
		int halfSide = side/2;
		return x > -halfSide & x <= halfSide & y > -halfSide & y <= halfSide;
	}
	
	private boolean nearIdentityLine(int delta, int x, int y) {
		return Math.abs(x - y) <= delta;
	}

}
