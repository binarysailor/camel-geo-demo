package szymanski.cameldemo;

import static szymanski.cameldemo.CamelUtils.isPlainText;
import static szymanski.cameldemo.CamelUtils.isXml;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

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
				from("file://c:/camel-in")
					.choice()
						.when(isPlainText())
							.split(body().tokenize("\n"))
							.processRef("text2Point")
							.to("bean:adapter0")
						.endChoice()
						.when(isXml())
							.split(xpath("/points/point"))
							.setBody().xquery("concat(/point/x, ' ', /point/y)", String.class)
							.processRef("text2Point")
							.to("bean:adapter0")
						.endChoice()
					.end();
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
		return registry;
	}
}