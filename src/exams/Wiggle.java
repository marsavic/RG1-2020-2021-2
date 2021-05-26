package exams;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.geometry.Transformation;
import mars.geometry.Vector;

public class Wiggle implements Drawing {
	
	@GadgetInteger(min = 0)
	int nLevels = 8;
	
	Vector p = new Vector(100, 10);
	Vector q = new Vector(0, 30);
	

	void drawSerpent(View view, int level) {
		if (level == nLevels) return;
		
		view.stateStore();
		view.addTransformation(Transformation.scaling(-0.9, 0.9).translate(q));
		drawSerpent(view, level + 1);
		view.stateRestore();

		view.setFill(Color.hsb(130, 0.8, 0.6, 0.5));
		view.fillRect(Vector.ZERO, new Vector(p.x, q.y));
		view.setLineWidth(6);
		view.setLineCap(StrokeLineCap.ROUND);
		view.setStroke(Color.hsb(130, 0.8, 0.6));
		
		view.beginPath();
		view.moveTo(Vector.ZERO);
		view.bezierCurveTo(p, q.add(p.mul(new Vector(1, -1))), q);
		view.stroke();
	}
	
	
	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.gray(0.125));
		
		view.stateStore();
		view.addTransformation(Transformation.translation(new Vector(0, -200)));
		drawSerpent(view, 0);
		view.stateRestore();
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(600, 600);
	}
}
