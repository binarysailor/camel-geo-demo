package szymanski.cameldemo;

import java.awt.Color;
import java.util.Map;

import org.apache.camel.Exchange;

public class ColorMapper {
	public void mapToColor(Exchange ex) {
		@SuppressWarnings("unchecked")
		Map<String, Object> row = ex.getIn().getBody(Map.class); 
		int r = (Integer)row.get("r");
		int g = (Integer)row.get("g");
		int b = (Integer)row.get("b");
		ex.getOut().setBody(new Color(r, g, b));
	}
}
