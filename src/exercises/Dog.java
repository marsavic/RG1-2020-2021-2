package exercises;

import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetAnimation;
import mars.drawingx.gadgets.annotations.GadgetDouble;
import mars.geometry.Vector;


class Rect {
	Vector p, d;
	Color c;
	
	public Rect(Vector p, Vector d, Color c) {
		this.p = p;
		this.d = d;
		this.c = c;
	}
	
	public void draw(View view) {
		view.setLineWidth(8);
		view.setStroke(c);
		view.strokeRect(p, d); 
	}
}


public class Dog implements Drawing {
	@GadgetAnimation
	double time = 0.0;
	
	@GadgetDouble(min = 0.1, max = 10)
	double tStep = 3;
	
	
	double o = 1;
	
	Rect[] rects = new Rect[] {
			new Rect(new Vector( -90,  -90), new Vector(170, 190), Color.hsb( 60, 0.8, 0.9, o)), // glava
			new Rect(new Vector(  50,   70), new Vector(100,  40), Color.hsb(  0, 0.0, 0.0, o)), // desno uvo
			new Rect(new Vector( -40,   20), new Vector( 30,  30), Color.hsb(  0, 0.0, 0.0, o)), // levo oko
			new Rect(new Vector(  20,   10), new Vector( 20,  30), Color.hsb(  0, 0.0, 0.0, o)), // desno oko
			new Rect(new Vector( -20,  -40), new Vector( 50,  20), Color.hsb(  0, 0.8, 0.9, o)), // nos
			new Rect(new Vector( -50, -100), new Vector(100,  30), Color.hsb(  0, 0.0, 0.0, o)), // usta
			new Rect(new Vector(-140,   80), new Vector(100,  40), Color.hsb(  0, 0.0, 0.0, o)), // levo uvo
	};
	
	int n = rects.length;

	
	Rect lerp(Rect a, Rect b, double t) {
		return new Rect(
				Vector.lerp(a.p, b.p, t),
				Vector.lerp(a.d, b.d, t),
				a.c.interpolate(b.c, t)
		);
	}
	
	
	double smootherstep(double x) {
		return x * x * x * (x * (6 * x - 15) + 10);
	}


	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.hsb(120, 0.4, 0.4));
		
		int k = (int) (time / tStep);
		double t = (time % tStep) / tStep;
		
		for (int l = k; l < k + n; l++) {
			int i = l % n;
			int j = (i + 1) % n;
			double ts = smootherstep(t);
			Rect rect = lerp(rects[i], rects[j], ts);
			rect.draw(view);
		}
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(600, 600);
	}
	
}
