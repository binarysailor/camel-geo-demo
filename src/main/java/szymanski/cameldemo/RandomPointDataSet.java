package szymanski.cameldemo;

import java.awt.Point;
import java.util.Random;

import org.apache.camel.component.dataset.SimpleDataSet;

public class RandomPointDataSet extends SimpleDataSet {

	private Point[] points;

	public RandomPointDataSet(int xRange, int yRange, int count) {
		points = new Point[count];
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < count; i++) {
			points[i] = new Point(random.nextInt(2*xRange) - xRange, random.nextInt(2*yRange) - yRange);
		}
	}
	
	@Override
	public long getSize() {
		return points.length;
	}
	
	@Override
	protected Object createMessageBody(long messageIndex) {
		return String.format("%d %d", points[(int) messageIndex].x, points[(int) messageIndex].y);
	}
}
