package topic2_image_processing.filters.demos;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.application.Options;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetBoolean;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.geometry.Vector;
import topic2_image_processing.filters.ConvolutionFilter;
import topic2_image_processing.filters.Filter;
import topic2_image_processing.filters.color.Grayscale;
import topic2_image_processing.filters.displacement.FlipHorizontal;
import topic2_image_processing.filters.misc.Sobel;


public class DemoFilters implements Drawing {
	@GadgetInteger(min = 0, max = 13)
	Integer imageIndex = 0;
	
	@GadgetInteger(min = 0, max = 21)
	Integer filterIndex = 0;
	
	@GadgetBoolean
	Boolean applyFilter = false;
	
	
	Filter[] filters = {			
			new Grayscale(),

			new FlipHorizontal(),
			
			new ConvolutionFilter(ConvolutionFilter.BOX_BLUR_3x3),
			new ConvolutionFilter(ConvolutionFilter.BLUR_5x5),
			new ConvolutionFilter(ConvolutionFilter.SHARPEN),
			new ConvolutionFilter(ConvolutionFilter.DETECT_EDGES),
			new Sobel(),
	};

	String[] fileNames = {
			"Mona Lisa.jpg",
			"building.jpg",
			"catparty.jpg",
			"christmas.jpg",
			"couple.jpg",
			"dive.jpg",
			"doggo.jpg",
			"fall.jpg",
			"forecast.jpg",
			"kitchen.jpg",
			"meterologist.jpg",
			"office.jpg",
			"skirts.jpg",
			"waiting.jpg",
	};
	

	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.hsb(0, 0, 0.2));
		
		Filter filter = filters[filterIndex];
		Image originalImage = new Image("images/" + fileNames[imageIndex]);
		Image filteredImage = filter.process(originalImage);
		view.drawImageCentered(Vector.ZERO, applyFilter ? filteredImage : originalImage);
		
		DrawingUtils.drawInfoText(view, "Image: " + fileNames[imageIndex] + "   Filter: " + filter.getClass().getSimpleName());
	}
	
	
	public static void main(String[] args) {
		Options options = Options.redrawOnEvents();
		options.drawingSize = new Vector(800, 700);
		DrawingApplication.launch(options);
	}
}
