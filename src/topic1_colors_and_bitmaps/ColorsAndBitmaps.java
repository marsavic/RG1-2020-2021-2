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
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.geometry.Vector;

public class ColorsAndBitmaps implements Drawing {

	public Image imgSolidColor() {
		// Svi pikseli su ljubicasti.
		
		WritableImage image = new WritableImage(400, 400);
		PixelWriter pw = image.getPixelWriter();
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				pw.setColor(x, y, Color.PURPLE);
			}
		}
		
		return image;
	}

	

	@GadgetColorPicker
	Color colorBackground = new Color(0.2, 0.2, 0.2, 1);
	
	@GadgetInteger(min = 0, max = 1)
	int imageIndex = 0;
	
	Image[] images;
	
	
	@Override
	public void init(View view) {
		images = new Image[] {
			imgSolidColor()
		};
		
		view.setImageSmoothing(false);
	}

	
	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, colorBackground);
		view.drawImageCentered(Vector.ZERO, images[imageIndex]);
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(600, 600);
	}
	
}
