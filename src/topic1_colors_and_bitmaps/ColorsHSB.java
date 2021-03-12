package topic1_colors_and_bitmaps;


import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetColorPicker;
import mars.drawingx.gadgets.annotations.GadgetDouble;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.geometry.Vector;

public class ColorsHSB implements Drawing {

	@GadgetColorPicker
	Color colorBackground = Color.gray(0.25);
	
	@GadgetInteger(min = 1, max = 400)
	int size = 400;
	
	@GadgetDouble
	double brightness = 1.0;
	
	
	
	public Image image() {
		WritableImage image = new WritableImage(size, size);
		PixelWriter pw = image.getPixelWriter();
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				double dx = 2 * x / image.getWidth() - 1;              // Normalizujemo na interval [-1, 1)
				double dy = 2 * y / image.getHeight() - 1;
				
				double r = Math.sqrt(dx*dx + dy*dy);                   // Udaljenost od centra.
				double phi = Math.atan2(dy, dx) * 360 / (2 * Math.PI); // Ugao (0-360)
				
				Color c = r <= 1 ? Color.hsb(phi, r, brightness) : Color.TRANSPARENT;
				pw.setColor(x, y, c);
			}
		}
		
		return image;
	}
	
	
	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, colorBackground);
		view.drawImageCentered(Vector.ZERO, image());
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(600, 600);
	}
		
}
