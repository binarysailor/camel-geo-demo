package szymanski.cameldemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppSpring {
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/context.xml");
		appContext.start();
	}
}
