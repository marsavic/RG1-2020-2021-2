package exams;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetAnimation;
import mars.drawingx.gadgets.annotations.GadgetDouble;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.geometry.Vector;
import mars.utils.Graphics;
import mars.utils.Numeric;


public class Sun implements Drawing {
	@GadgetAnimation(start = true)
	double time = 0.0;
	
	double tPeriod = 1.0;
	
	@GadgetDouble(min = 0, max = 400)
	double r = 75;
	
	@GadgetDouble(min = 0, max = 400)
	double rayLengthMin = 150;

	@GadgetDouble(min = 0, max = 400)
	double rayLengthMax = 200;
	
	@GadgetInteger
	int nRaysDiv2 = 7;
	
	Color color = Color.hsb(50, 0.6, 1.0);
	
	
	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.gray(0.125));
		
		view.setLineCap(StrokeLineCap.ROUND);
		view.setLineWidth(12);
		
		for (int i = 0; i < 2 * nRaysDiv2; i++) {
			double t = 0.5 * (Numeric.sinT(time / tPeriod) + 1);
			if (i % 2 == 1) {
				t = 1 - t;
			}
			double l = rayLengthMin + t * (rayLengthMax - rayLengthMin);
			
			view.setStroke(Graphics.scaleOpacity(color, l / rayLengthMax));
			view.strokeLine(Vector.ZERO, Vector.polar(l, 0.5 * i / nRaysDiv2));
		}
		
		view.setFill(color);
		view.fillCircleCentered(Vector.ZERO, r);
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(800, 800);
	}
	
}
