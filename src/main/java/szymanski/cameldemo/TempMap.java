package szymanski.cameldemo;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;

public class TempMap {
	public void transform(Exchange e) {
		Point point = e.getIn().getBody(Point.class);
		Map<String, Object> map = new HashMap<String, Object>();
		int c = 5 * (int)Math.sqrt(point.x*point.x + point.y*point.y);
		c = Math.max(c, 0);
		map.put("r", 255);
		map.put("g", c);
		map.put("b", c);
		e.getOut().setBody(map);
	}
}
