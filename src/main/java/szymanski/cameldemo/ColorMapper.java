package szymanski.cameldemo;

import java.awt.Color;

import org.apache.camel.Exchange;

public class ColorMapper {
	public void mapToColor(Exchange ex) {
		String hexColor = ex.getIn().getBody(String.class);
		final Color color;
		if (hexColor == null) {
			color = Color.black;
		} else {
			int r = Integer.parseInt(hexColor.substring(0, 2), 16);
			int g = Integer.parseInt(hexColor.substring(2, 4), 16);
			int b = Integer.parseInt(hexColor.substring(4), 16);
			color = new Color(r, g, b);
		}
		ex.getOut().setBody(color);
	}
}
