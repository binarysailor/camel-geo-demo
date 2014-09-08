package szymanski.cameldemo;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.Lifecycle;

class CamelUtils {
	public static void stopAfterWindowClosed(DefaultCamelContext context) {
		WindowListener listener = new StopCamelContext(context);
		CamelUtils.createWindowWithListener(listener);
	}

	public static void stopAfterWindowClosed(Lifecycle appContext) {
		WindowListener listener = new StopSpringContext(appContext);
		CamelUtils.createWindowWithListener(listener);
	}
	
	private static void createWindowWithListener(final WindowListener listener) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame f = new JFrame("Close me");
				f.setPreferredSize(new Dimension(100, 100));
				f.pack();
				f.setVisible(true);
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.addWindowListener(listener);
			}
		});
	}
	
	private static class StopCamelContext extends WindowAdapter {
		private CamelContext context;
		
		public StopCamelContext(CamelContext context) {
			this.context = context;
		}

		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			try {
				context.stop();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private static class StopSpringContext extends WindowAdapter {
		private Lifecycle context;
		
		public StopSpringContext(Lifecycle context) {
			this.context = context;
		}

		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			try {
				context.stop();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
