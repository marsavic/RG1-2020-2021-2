package exercises;

import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.geometry.Transformation;
import mars.geometry.Vector;


public class IFSSmiley implements Drawing {
	
	@GadgetInteger(min = 0)
	int nLevels = 6;
	
	
	double r = 100;                       // Poluprecnik glave.
	double xEye = 35;                     // Udaljenost oka od centra po x-osi.
	double yEye = 35;                     // Udaljenost oka od centra po y-osi.
	double rEye = 15;                     // Poluprecnik oka.
	double rMouth = 65;                   // Poluprecnik luka usta.
	double phiMouth = 1.0/3;              // Ugao luka usta.
	
	
	private void drawFace(View view) {
		// Glava
		view.setFill(Color.hsb(60, 0.9, 0.9));
		view.fillCircleCentered(new Vector(0, 0), r);
		
		// Oci
		view.setFill(Color.hsb(0, 0, 0));
		view.fillCircleCentered(new Vector(-xEye, yEye), rEye);
		view.fillCircleCentered(new Vector( xEye, yEye), rEye);
		
		// Usta
		view.setLineWidth(7.5);
		view.setStroke(Color.hsb(0, 0, 0));
		view.strokeArcCentered(new Vector(0, 0), new Vector(rMouth), 0.75 - phiMouth/2, phiMouth);
	}
	
	
	Vector[] shifts = {
			new Vector(-200, -200),
			new Vector(-200,  200),
			new Vector( 200, -200),
			new Vector( 200,  200)
	};

	void drawIFS(View view, int level) {
		if (level == 0) return;
		
		for (Vector s : shifts) {
			view.stateStore();
			view.addTransformation(Transformation.scaling(0.5).translate(s));
			drawIFS(view, level - 1);
			view.stateRestore();
		}
		
		drawFace(view);
	}
	
	
	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.gray(0.125));
		drawIFS(view, nLevels);
	}
	
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(800, 800);
	}
	
}
