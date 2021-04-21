package exams;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.application.Options;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetBoolean;
import mars.drawingx.gadgets.annotations.GadgetDouble;
import mars.geometry.Vector;
import topic2_image_processing.filters.ColorFilter;
import topic2_image_processing.filters.CombinedFilter;
import topic2_image_processing.filters.DisplacementFilter;
import topic2_image_processing.filters.Filter;


public class BlackAndWhiteSkew implements Drawing {

	static class BlackAndWhite extends ColorFilter {
		double threshold;
		
		public BlackAndWhite(double threshold) {
			this.threshold = threshold;
		}
	
		@Override
		public Color processColor(Color input) {
			return input.getBrightness() < threshold ? Color.TRANSPARENT : Color.WHITE;
		}
	}
	
	static class Skew extends DisplacementFilter {
		double k;
		
		public Skew(double k) {
			this.k = k;
		}
		
		@Override
		public Vector source(Vector dst, Vector dim) {
			return new Vector(dst.x + k * dst.y, dst.y).mod(dim);
		}
	}

	
	
	@GadgetDouble
	double threshold = 0.5;
	
	@GadgetDouble(min = -1, max = 1)
	double k = 0;
	
	@GadgetBoolean
	Boolean applyFilter = false;

	Image originalImage;

	
	
	@Override
	public void init(View view) {
		originalImage = new Image("images/building.jpg");
	}
	
	
	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.gray(0.2));

		Filter filter = new CombinedFilter(new Skew(k), new BlackAndWhite(threshold));
		Image filteredImage = filter.process(originalImage);
		view.drawImageCentered(Vector.ZERO, applyFilter ? filteredImage : originalImage);
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(Options.redrawOnEvents());
	}
}
