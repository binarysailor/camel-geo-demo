package szymanski.cameldemo;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.language.MvelExpression;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import szymanski.cameldemo.visualizer.api.Visualizer;
import szymanski.cameldemo.visualizer.impl.VisualizerFactory;

public class AppJava {
	public static void main(String[] args) throws Exception {

		Visualizer visualizer = VisualizerFactory.create();
		SimpleRegistry registry = AppJava.buildRegistry(visualizer);
		
		DefaultCamelContext context = new DefaultCamelContext(registry);

		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				AggregationStrategy strategy = CombineCoordsAndColor.instance();
				
				from("dataset:random?produceDelay=20")
				.multicast().to("bean:text2Point", "seda:e")
				.aggregationStrategy(strategy).end()
				.to("bean:adapter0");
				
				from("seda:e").setExchangePattern(ExchangePattern.InOut)
				.beanRef("text2Point")
				.setHeader("point_x", simple("body.x"))
				.setHeader("point_y", simple("body.y"))
				/*
				.transform(constant("select r, g, b from colors where x = :body_x and y = :body_y"))
				.to("jdbc:pointColorsDB?outputType=SelectOne")
				*/
				.to("bean:tempMap")
				//.transform().mvel("new java.awt.Color((int)body.get('r'), (int)body.get('g'), (int)body.get('b'))");
				.to("bean:map2Color");
			}
		});
		context.start();
		visualizer.registerStoppable(CamelUtils.stoppableCamel(context));
	}

	private static SimpleRegistry buildRegistry(Visualizer visualizer) {
		
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("text2Point", new Text2PointProcessor());
		registry.put("adapter0", new VisualizerAdapter(visualizer, 0));
		registry.put("adapter1", new VisualizerAdapter(visualizer, 1));
		registry.put("adapter2", new VisualizerAdapter(visualizer, 2));
		registry.put("adapter3", new VisualizerAdapter(visualizer, 3));
		registry.put("random", new RandomPointDataSet(40, 30, 500));
		registry.put("map2Color", new ColorMapper());
		registry.put("tempMap", new TempMap());
		return registry;
	}
}
