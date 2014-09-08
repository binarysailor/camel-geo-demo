package szymanski.cameldemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppSpring {
	public static void main(String[] args) throws Exception {

		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/context.xml");
		appContext.start();
		CamelUtils.stopAfterWindowClosed(appContext);
	}
}
