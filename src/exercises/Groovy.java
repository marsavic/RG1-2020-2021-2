package exercises;

import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetAnimation;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.geometry.Transformation;
import mars.geometry.Vector;
import mars.random.fixed.continuous.PerlinNoise;


public class Groovy implements Drawing {
	
	@GadgetAnimation(start = true)
	double time = 0.0;

	@GadgetInteger(min = 1)
	int n = 3;
	
	PerlinNoise pn = new PerlinNoise(2748710926358208537L);
	
	
	
	@Override
	public void draw(View view) {
		view.setTransformation(Transformation.identity()
				.translate(new Vector(-0.5))
				.scale(500)
		);
		
		DrawingUtils.clear(view, Color.gray(0.125));
		
		
		Vector[] p = new Vector[n+1];
		for (int i = 0; i <= n; i++) {
			p[i] = new Vector(
					1.0 * i / n,
					pn.getValue(623.852 * i / n, time, 0.5) * 0.5 + 0.5
			);
		}
		
		Vector d = Vector.UNIT_X.div(2*n);
		
		view.beginPath();
		view.moveTo(Vector.ZERO);
		view.lineTo(p[0]);
		for (int i = 0; i < n; i++) {
			view.bezierCurveTo(p[i].add(d), p[i+1].sub(d), p[i+1]);
		}
		view.lineTo(new Vector(1, 0));
		view.closePath();
		
		view.setFill(Color.hsb(30, 0.9, 0.9));
		view.fill();
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(720, 720);
	}
	
}
