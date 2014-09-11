package szymanski.cameldemo.visualizer.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;

import szymanski.cameldemo.visualizer.api.Stoppable;
import szymanski.cameldemo.visualizer.api.Visualizer;

public class SpringContextStoppable implements Stoppable, ApplicationContextAware {

	private Lifecycle context;
	private Visualizer visualizer;
	
	public SpringContextStoppable(Visualizer visualizer) {
		this.visualizer = visualizer;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = (Lifecycle)applicationContext;
		this.visualizer.registerStoppable(this);
	}

	@Override
	public void stop() {
		context.stop();
	}
}
