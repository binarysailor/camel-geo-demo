package szymanski.cameldemo.colordb.util;

class Result {
	private int width, height;
	private String outputPath;

	Result(int width, int height, String outputPath) {
		this.width = width;
		this.height = height;
		this.outputPath = outputPath;
	}
	
	void print() {
		System.out.printf("Converted an image of %d x %d = %d pixels into %s\n", 
				width, height, width*height, outputPath);
	}
}
