package exercises;

import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetAnimation;
import mars.drawingx.gadgets.annotations.GadgetDouble;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.geometry.Vector;
import mars.utils.Numeric;


public class RotatingPolygon implements Drawing {
	
	@GadgetAnimation(start = true)
	double time = 0.0;

	@GadgetDouble(min = -1, max = 1)
	double speed = 0.2;
	
	@GadgetDouble(min = 0, max = 300)
	double r = 200.0;

	@GadgetInteger
	int n = 3;
	
	

	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.hsb(0, 0, 0.1));		
		
		double alpha = speed * time; 
		
		Vector[] p = new Vector[n];
		for (int i = 0; i < n; i++) {
			double phi = alpha + 1.0 * i / n;
			p[i] = Vector.polar(r, phi);
		}
		
		view.setFill(Color.hsb(240, 0.7, 0.8));
		view.fillPolygon(p);

		view.setFill(Color.hsb(0, 0.7, 0.8));
		
		for (int i = 0; i < n; i++) {
			double phi = alpha + 1.0 * i / n;
			Vector c = Vector.polar(r * Numeric.sinT(alpha), phi);
			view.fillCircleCentered(c, r/8);
		}		
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(800, 800);
	}
	
}
