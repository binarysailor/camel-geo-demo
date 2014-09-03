package szymanski.cameldemo;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

class CamelUtils {
	public static void stopAfterWindowClosed(final DefaultCamelContext context) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame f = new JFrame("Close me");
				f.setPreferredSize(new Dimension(100, 100));
				f.pack();
				f.setVisible(true);
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.addWindowListener(new StopCamelContext(context));
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
}
