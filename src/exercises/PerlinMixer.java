package exercises;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.application.Options;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetDouble;
import mars.drawingx.gadgets.annotations.GadgetImageChooser;
import mars.geometry.Vector;
import mars.random.fixed.continuous.PerlinNoise;
import topic2_image_processing.filters.BinaryFilter;

class PerlinMixerFilter extends BinaryFilter {

	PerlinNoise pn = new PerlinNoise(6165190095667450829L);
	double c;
	

	public PerlinMixerFilter(double c) {
		this.c = c;
	}



	@Override
	public Image process(Image input1, Image input2) {
		final int w = (int) input1.getWidth();
		final int h = (int) input1.getHeight();
		
		if (input2.getWidth() != w || input2.getHeight() != h) {
			throw new IllegalArgumentException("Input images must have the same size.");
		}

		WritableImage output = new WritableImage(w, h);

		PixelReader pr1 = input1.getPixelReader();
		PixelReader pr2 = input2.getPixelReader();
		PixelWriter pw = output.getPixelWriter();
		
		double d = 100.0;
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				Color inputColor1 = pr1.getColor(x, y);
				Color inputColor2 = pr2.getColor(x, y);
				
				double k = c + pn.getValue(x/d, y/d, 0);
				if (k < 0) k = 0;
				if (k > 1) k = 1;
				
				Color outputColor = inputColor1.interpolate(inputColor2, k);
				pw.setColor(x, y, outputColor);
			}
		}
		
		return output;
	}
	
}

public class PerlinMixer implements Drawing {
	
	@GadgetImageChooser
	Image originalImage1, originalImage2;
	
	@GadgetDouble(min = 0, max = 1)
	double c = 0.5;

	
	
	@Override
	public void init(View view) {
		originalImage1 = new Image("images/textures/soil.jpg");
		originalImage2 = new Image("images/textures/grass.jpg");
	}
	
	
	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.gray(0.2));

		BinaryFilter filter = new PerlinMixerFilter(c);
		try {
			Image filteredImage = filter.process(originalImage1, originalImage2);
			view.drawImageCentered(Vector.ZERO, filteredImage);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			DrawingUtils.drawInfoText(view, e.toString());
		}
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(Options.redrawOnEvents());
	}
}
