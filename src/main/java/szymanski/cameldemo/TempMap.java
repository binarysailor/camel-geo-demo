package szymanski.cameldemo;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;

public class TempMap {
	public void transform(Exchange e) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("r", 138);
		map.put("g", 220);
		map.put("b", 242);
		e.getOut().setBody(map);
	}
}
