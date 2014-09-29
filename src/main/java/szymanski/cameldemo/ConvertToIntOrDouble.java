package szymanski.cameldemo;

import org.apache.camel.Exchange;

public class ConvertToIntOrDouble {
	public void convert(Exchange ex, String input) {
		if (input.indexOf(".") >= 0) {
			Double d = Double.valueOf(input);
			ex.getOut().setBody(d);
			ex.getOut().setHeader("recipient", "direct:doublesqrt");
		} else {
			Integer i = Integer.valueOf(input);
			ex.getOut().setBody(i);
			ex.getOut().setHeader("recipient", "direct:intsqrt");
		}
	}
}
