package szymanski.cameldemo;

import org.apache.camel.Exchange;

public class ConvertToIntOrDouble {
	public void convert(Exchange ex, String input) {
		if (input.indexOf(".") >= 0) {
			ex.getOut().setBody(Double.valueOf(input));
			ex.getOut().setHeader("recipient", "direct:doublesqrt");
		} else {
			ex.getOut().setBody(Integer.valueOf(input));
			ex.getOut().setHeader("recipient", "direct:intsqrt");
		}
	}
}
