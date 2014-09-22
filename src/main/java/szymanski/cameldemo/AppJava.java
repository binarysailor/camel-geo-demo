package szymanski.cameldemo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.CompositeRegistry;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.spi.Registry;
import org.apache.camel.spring.spi.ApplicationContextRegistry;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import szymanski.cameldemo.visualizer.component.VisualizerComponent;

public class AppJava {
	public static void main(String[] args) throws Exception {

		Registry registry = AppJava.buildRegistry();
		
		DefaultCamelContext context = new DefaultCamelContext(registry);
		context.addComponent("visualizer", new VisualizerComponent());
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				AggregationStrategy strategy = CombineCoordsAndColor.instance();
				
				from("dataset:uniqueRandom")
				.routeId("random-points-to-visualizer")
				.to("bean:text2Point")
				.enrich("direct:color", strategy)
				.to("visualizer:panel0");
				
				from("direct:color")
				.routeId("point-color-enricher")
				.setHeader("point_x").mvel("request.body.x")
				.setHeader("point_y").mvel("request.body.y")
				.transform(constant(
					"select color from pixel_colors "
					+ "where image_name = 'sampleImage' "
					+ "and x = :?point_x and y = :?point_y"))
				.to("jdbc:pointColorsDB?useHeadersAsParameters=true&outputType=SelectOne");
			}
		});
		context.start();
	}

	private static Registry buildRegistry() {
		CompositeRegistry registry = new CompositeRegistry();
	
		SimpleRegistry simpleRegistry = new SimpleRegistry();
		simpleRegistry.put("text2Point", new Text2PointProcessor());
		simpleRegistry.put("uniqueRandom", new NonRepeatingRandomPointDataSet(50, 40, 6000));
		simpleRegistry.put("map2Color", new ColorMapper());
		registry.addRegistry(simpleRegistry);
		
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/h2-context.xml");
		ApplicationContextRegistry appContextRegistry = new ApplicationContextRegistry(appContext);
		registry.addRegistry(appContextRegistry);
		
		return registry;
	}
}
