package szymanski.geo;

import java.awt.Point;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

import szymanski.geo.visualiser.GeoDrawingBean;
import szymanski.geo.visualiser.GeoFrame;

public class App {
	public static void main(String[] args) throws Exception {
		GeoFrame geo = new GeoFrame();
		geo.setVisible(true);

		SimpleRegistry registry = new SimpleRegistry();
		registry.put("text2Point", new Text2PointProcessor());
		registry.put("geo", new GeoDrawingBean(geo));
		
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
				from("seda:text").to("bean:text2Point");
				from("bean:text2Point").to("seda:a");
				*/
				from("direct:a").to("bean:geo");
			}
		});
		context.start();
		
		context.createProducerTemplate().sendBody("bean:text2Point", "80 50");
		context.createProducerTemplate().sendBody("seda:text", "70 34");
		context.createProducerTemplate().sendBody("seda:pretext", "100 34");
		context.createProducerTemplate().sendBody("direct:a", new Point(100, 100));
	}
}
