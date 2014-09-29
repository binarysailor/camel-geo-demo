package szymanski.cameldemo;

import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spi.Registry;

public class ErrorsApp {
	public static void main(String[] args) throws Exception {

		Registry registry = ErrorsApp.buildRegistry();
		
		DefaultCamelContext context = new DefaultCamelContext(registry);
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				errorHandler(deadLetterChannel("direct:dlc").maximumRedeliveries(10).redeliveryDelay(1000));
				onException(NumberFormatException.class).handled(true).to("file:C:/Temp/camel-out/errors");
				onException(IllegalArgumentException.class).handled(true).convertBodyTo(String.class).to("file:C:/Temp/camel-out/errors");
				
				from("direct:sqrt")
				.routeId("compute-sqrt")
				.recipientList(header("recipient"))
				.to("direct:output");
				
				from("direct:intsqrt")
				.routeId("compute-int-sqrt")
				.to("bean:sqrtCalc?method=intSqrt")
				.setHeader("name", simple("int"));
				
				from("direct:doublesqrt")
				.routeId("compute-double-sqrt")
				.to("bean:sqrtCalc?method=doubleSqrt")
				.setHeader("name", simple("double"));
				
				from("direct:dlc")
				.routeId("DLC")
				.setHeader("name", simple("DLC"))
				.to("direct:output");
				
				from("direct:output")
				.setBody(simple("${headers.name}: ${in.body}"))
				.to("stream:out");

				from("stream:in?promptMessage=Enter number: ").bean(ConvertToIntOrDouble.class).to("direct:sqrt");
			}
		});
		context.start();
		Thread.sleep(600000);
	}

	private static Registry buildRegistry() {
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("sqrtCalc", new SqrtCalculator());
		return registry;
	}
	
	public static class SqrtCalculator {
		
		private Random rnd = new Random();
		
		public int intSqrt(int input) {
			if (input < 0) {
				throw new IllegalArgumentException("Don't know how to compute square root of a negative number");
			}
			return (int)Math.sqrt(input);
		}
		
		public double doubleSqrt(double input) throws TimeoutException {
			if (input < 0) {
				throw new IllegalArgumentException("Don't know how to compute square root of a negative number");
			}
			if (input > 1000 || rnd.nextBoolean()) {
				throw new TimeoutException("Double square root calculator is busy now. Try later");
			}
			
			return Math.sqrt(input);
		}
	}
}
