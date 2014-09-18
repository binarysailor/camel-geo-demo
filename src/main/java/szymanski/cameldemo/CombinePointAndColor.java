package szymanski.cameldemo;

import java.awt.Color;
import java.awt.Point;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

class CombineCoordsAndColor implements AggregationStrategy {

	private static CombineCoordsAndColor instance = new CombineCoordsAndColor();
	
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
		final Color color = colorExchange.getIn().getBody(Color.class);
		coordExchange.getIn().setHeader("pointColor", color);
		return coordExchange;
	}


}
