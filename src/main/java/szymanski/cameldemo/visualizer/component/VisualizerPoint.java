package szymanski.cameldemo.visualizer.component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="point")
public class VisualizerPoint {
	@XmlElement
	int panelIndex, x, y, r, g, b;
}
