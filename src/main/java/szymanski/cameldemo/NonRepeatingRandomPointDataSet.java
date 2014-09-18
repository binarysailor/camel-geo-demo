package szymanski.cameldemo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.camel.component.dataset.SimpleDataSet;

public class NonRepeatingRandomPointDataSet extends SimpleDataSet {

	private Point[] points;

	public NonRepeatingRandomPointDataSet(int xRange, int yRange, int count) {
		points = new Point[count];
		List<Point> allPoints = new ArrayList<>(4*xRange*yRange);
		for (int x = -xRange; x<xRange; x++) {
			for (int y = -yRange; y < yRange; y++) {
				allPoints.add(new Point(x, y));
			}
		}
		Collections.shuffle(allPoints);
		allPoints.subList(0, count).toArray(points);
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
