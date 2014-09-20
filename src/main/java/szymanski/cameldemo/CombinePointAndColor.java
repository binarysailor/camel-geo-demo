package szymanski.cameldemo;

import java.awt.Color;
import java.awt.Point;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

class CombineCoordsAndColor implements AggregationStrategy {

	private static CombineCoordsAndColor instance = new CombineCoordsAndColor();
	private ColorMapper colorMapper = new ColorMapper();
	
	private CombineCoordsAndColor() {
	}
	
	public static AggregationStrategy instance() {
		return instance;
	}
	
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		if (oldExchange == null) {
			return newExchange;
		} 
		
		if (oldExchange.getIn().getBody() instanceof Point) {
			return combine(oldExchange, newExchange);
		} else {
			return combine(newExchange, oldExchange);
		}
	}

	private Exchange combine(Exchange coordExchange, Exchange colorExchange) {
		final String hexColor = colorExchange.getIn().getBody(String.class);
		final Color color = colorMapper.hexToColor(hexColor);
		coordExchange.getIn().setHeader("pointColor", color);
		return coordExchange;
	}


}
