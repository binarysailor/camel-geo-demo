package szymanski.cameldemo.colordb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

class InsertPixelStatementSetter implements BatchPreparedStatementSetter {

	private String imageName;
	private List<Pixel> pixels = new ArrayList<>(10000);
	
	InsertPixelStatementSetter(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public void setValues(PreparedStatement ps, int i) throws SQLException {
		Pixel pixel = pixels.get(i);
		ps.setString(1, imageName);
		ps.setInt(2, pixel.x);
		ps.setInt(3, pixel.y);
		ps.setString(4, pixel.color);
	}

	@Override
	public int getBatchSize() {
		return pixels.size();
	}

	void addPixel(String pixelAsText) {
		try (Scanner scanner = new Scanner(pixelAsText)) {
			Pixel pixel = new Pixel();
			pixel.x = scanner.nextInt();
			pixel.y = scanner.nextInt();
			int r = scanner.nextInt();
			int g = scanner.nextInt();
			int b = scanner.nextInt();
			pixel.color = String.format("%02X%02X%02X", r, g, b);
			pixels.add(pixel);
		}
	}

	private static class Pixel {
		int x, y;
		String color;
	}
}
