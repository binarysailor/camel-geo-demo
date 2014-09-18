package szymanski.cameldemo.colordb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseInitializer {

	private JdbcTemplate jdbc;
	private String imageName;
	private String filePath;

	public DatabaseInitializer(DataSource datasource, String imageName, String filePath) {
		this.jdbc = new JdbcTemplate(datasource);
		this.imageName = imageName;
		this.filePath = filePath;
	}

	public void init() throws SQLException, IOException {
		jdbc.execute("create table pixel_colors(image_name varchar(30), x int, y int, color varchar(6))");
		InsertPixelStatementSetter pixelData = new InsertPixelStatementSetter(imageName);
		loadPixelDataFromFile(pixelData);
		jdbc.batchUpdate("insert into pixel_colors(image_name, x, y, color) values (?, ?, ?, ?)", pixelData);
	}

	private void loadPixelDataFromFile(InsertPixelStatementSetter pixelData) throws IOException {
		try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null) {
				pixelData.addPixel(line);
			}
		}
	}
}
