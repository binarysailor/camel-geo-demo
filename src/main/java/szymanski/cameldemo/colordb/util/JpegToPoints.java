package szymanski.cameldemo.colordb.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;


public class JpegToPoints {
	
	private static final Pattern FILE_NAME = Pattern.compile("(.*)\\.\\w{3,4}$");
	private static final String EXTENSION = ".img";
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: JpegToPoints <jpegpath> [<outputpath>]");
			return;
		}
		String imagePath = args[0];
		String outputPath = null;
		if (args.length > 1) {
			outputPath = args[1];
		}
		try {
			Result result = new JpegToPoints().convert(imagePath, outputPath);
			result.print();
		} catch (IOException e) {
			System.err.print("Error converting the image: " + e.getMessage());
		}
	}

	Result convert(String imagePath, String outputPath) throws IOException {
		String computedOutputPath = getOutputPath(imagePath, outputPath);
		PrintWriter out = new PrintWriter(computedOutputPath);
		Raster raster = getRaster(imagePath);
		Rectangle size = raster.getBounds();
		int[] color = new int[3];
		int yStart = size.height / 2;
		int xStart = - size.width / 2;
		for (int y = 0; y < size.height; y++) {
			for (int x = 0; x < size.width; x++) {
				raster.getPixel(x, y, color);
				out.printf("%d %d %d %d %d\n", xStart + x, yStart - y, color[0], color[1], color[2]);
			}
		}
		out.close();
		return new Result(size.width, size.height, computedOutputPath);
	}

	private String getOutputPath(String imagePath, String explicitOutputPath) {
		if (explicitOutputPath != null) {
			return explicitOutputPath;
		} else {
			Matcher matcher = FILE_NAME.matcher(imagePath);
			if (matcher.matches()) {
				return matcher.group(1) + EXTENSION;
			} else {
				return imagePath + EXTENSION;
			}
		}
	}

	private Raster getRaster(String imagePath) throws IOException {
		BufferedImage image = ImageIO.read(new File(imagePath));
		return image.getData();
	}
}
