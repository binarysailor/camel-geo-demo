package szymanski.cameldemo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class App {
	public static void main(String[] args) throws Exception {

		DefaultCamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:/c:/temp/camel-in").to("file:/c:/temp/camel-out");
			}
		});
		context.start();
		CamelUtils.stopAfterWindowClosed(context);
	}
}
