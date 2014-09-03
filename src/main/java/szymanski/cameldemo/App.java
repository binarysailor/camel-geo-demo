package szymanski.cameldemo;

import java.math.BigDecimal;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

import szymanski.cameldemo.visualizer.api.Visualizer;
import szymanski.cameldemo.visualizer.impl.VisualizerFactory;

public class App {
	public static void main(String[] args) throws Exception {
		Visualizer visualizer = VisualizerFactory.create();

		SimpleRegistry registry = new SimpleRegistry();
		registry.put("text2Point", new Text2PointProcessor());
		registry.put("adapter", new VisualizerAdapter(visualizer));
		
		DefaultCamelContext context = new DefaultCamelContext(registry);
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				/*
				from("file://c:/camel-in")
					.convertBodyTo(String.class)
					.processRef("text2Point")
					.to("seda:a");
				
				from("stream:in?promptMessage=Enter%20coordinates:")
					.convertBodyTo(String.class)
					.processRef("text2Point")
					.to("seda:a");
				
				//from("stream:in?promptMessage=Prompt 2:").to("stream:out");

				from("seda:a").to("bean:geo");
				
				from("seda:pretext").to("seda:text");
				from("seda:text").to("bean:text2Point").to("seda:a");
				//from("direct:a").to("bean:geo");
				 */
				from("stream:in?promptMessage=Enter text:").to("stream:out");
			}
		});
		context.start();
		
		context.createProducerTemplate().sendBody("bean:text2Point", "80 50");
		context.createProducerTemplate().sendBody("seda:text", "70 34");
		context.createProducerTemplate().sendBody("seda:pretext", "100 34");
		//context.createProducerTemplate().sendBody("direct:a", new Point(100, 100));
		BigDecimal body = (BigDecimal)context.createProducerTemplate().requestBody("seda:text", "3 4");
		System.out.println(body);
	}
}
