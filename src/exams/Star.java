package exams;


import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.geometry.Transformation;
import mars.geometry.Vector;


public class Star implements Drawing {
	@GadgetInteger
	int n = 5;

	double r = 200.0;


	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.hsb(320, 0.6, 0.25));
		
		Vector[] vertices = new Vector[2*n];
		for (int i = 0; i < 2*n; i++) {
			vertices[i] = Vector.polar(r * (i % 2 == 0 ? 1 : 0.5), i / (2.0*n));
		}
		
		view.stateStore();

		view.setStroke(Color.BLACK);
		view.setLineWidth(10);
		
		view.setFill(Color.hsb(40, 0.8, 0.7));
		view.fillPolygon(vertices);
		view.strokePolygon(vertices);
		
		view.addTransformation(Transformation.rotation(0.5 / n));
		
		view.setFill(Color.hsb(40, 0.8, 0.9));
		view.fillPolygon(vertices);
		view.strokePolygon(vertices);
		
		view.stateRestore();
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(600, 600);
	}
}
