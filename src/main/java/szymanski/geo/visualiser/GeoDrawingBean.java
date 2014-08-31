package szymanski.geo.visualiser;

import java.awt.Point;

import org.apache.camel.Exchange;

public class GeoDrawingBean {
	private GeoFrame frame;

	public GeoDrawingBean(GeoFrame frame) {
		this.frame = frame;
	}
	
	public void draw(Exchange ex) {
		Point p = (Point)ex.getIn().getBody();
		frame.addPoint(0, p.x, p.y);
	}
	
}
