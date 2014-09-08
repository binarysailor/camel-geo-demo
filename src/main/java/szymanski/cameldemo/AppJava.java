package szymanski.cameldemo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class AppJava {
	public static void main(String[] args) throws Exception {

		DefaultCamelContext context = new DefaultCamelContext();
		CamelUtils.stopAfterWindowClosed(context);
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("stream:in")
				.filter(
					body().contains("drugs"))
				.to("log:demo?level=WARN");
			}
		});
		context.start();
	}
}
