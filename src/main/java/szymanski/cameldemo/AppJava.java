package szymanski.cameldemo;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.CompositeRegistry;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.spi.Registry;
import org.apache.camel.spring.spi.ApplicationContextRegistry;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import szymanski.cameldemo.visualizer.api.Visualizer;
import szymanski.cameldemo.visualizer.impl.VisualizerFactory;

public class AppJava {
	public static void main(String[] args) throws Exception {

		Visualizer visualizer = VisualizerFactory.create();
		Registry registry = AppJava.buildRegistry(visualizer);
		
		DefaultCamelContext context = new DefaultCamelContext(registry);

		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				AggregationStrategy strategy = CombineCoordsAndColor.instance();
				
				from("dataset:uniqueRandom")
				.multicast().to(ExchangePattern.InOut, "bean:text2Point", "direct:enrich")
				.aggregationStrategy(strategy).end()
				.to("bean:adapter0");
				
				from("direct:enrich")
				.setHeader("point_x").mvel("request.body.split(' ')[0]")
				.setHeader("point_y").mvel("request.body.split(' ')[1]")
				.transform(constant(
					"select color from pixel_colors "
					+ "where image_name = 'sampleImage' "
					+ "and x = :?point_x and y = :?point_y"))
				.to("jdbc:pointColorsDB?useHeadersAsParameters=true&outputType=SelectOne")
				.to("bean:map2Color");
			}
		});
		context.start();
		visualizer.registerStoppable(CamelUtils.stoppableCamel(context));
	}

	private static Registry buildRegistry(Visualizer visualizer) {
		CompositeRegistry registry = new CompositeRegistry();
	
		SimpleRegistry simpleRegistry = new SimpleRegistry();
		simpleRegistry.put("text2Point", new Text2PointProcessor());
		simpleRegistry.put("adapter0", new VisualizerAdapter(visualizer, 0));
		simpleRegistry.put("uniqueRandom", new NonRepeatingRandomPointDataSet(50, 40, 6000));
		simpleRegistry.put("map2Color", new ColorMapper());
		registry.addRegistry(simpleRegistry);
		
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/h2-context.xml");
		ApplicationContextRegistry appContextRegistry = new ApplicationContextRegistry(appContext);
		registry.addRegistry(appContextRegistry);
		
		return registry;
	}
}
