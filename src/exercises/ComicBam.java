package exercises;

import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetAnimation;
import mars.geometry.Transformation;
import mars.geometry.Vector;


public class ComicBam implements Drawing {
	// Font se mora ucitati sa zadatom fiksnom velicinom.
	// Za prikaz u razlicitim velicinama koristiti transformaciju skaliranja.
	Font font = Font.font("Comic Sans MS", FontWeight.BLACK, 0.6);

	
	@GadgetAnimation(min = 0, max = 1)
	double time = 0;
	
	int n = 11;
	double[] r;  // Udaljenosti temena od centra
	
	{
		r = new double[n];
		for (int i = 0; i < n; i++) {
			r[i] = 0.8 + 0.4 * Math.random();
		}
	}
	

	// Animaciona kriva koju treba koristiti za animaciju skaliranja.
	double f(double x) {
		if (x < 0.00) return 0.00;
		if (x < 1.00 / 2.75) return 7.5625 * x * x;
		if (x < 2.00 / 2.75) return 7.5625 * (x -= (1.5   / 2.75)) * x + 0.75;
		if (x < 2.50 / 2.75) return 7.5625 * (x -= (2.25  / 2.75)) * x + 0.9375;
		if (x < 2.75 / 2.75) return 7.5625 * (x -= (2.625 / 2.75)) * x + 0.984375;
		return 1.00;
	}
	
	// Polozaj i-tog temena
	Vector p(int i) {
		return Vector.polar(r[i], 1.0 * i / n);
	}
	
	
	// Iscrtava "mehuric" 
	void bubble(View view) {
		view.beginPath();
		view.moveTo(p(0));
		
		for (int i = 0; i < n; i++) {
			int j = (i+1) % n;
			view.quadraticCurveTo(Vector.ZERO, p(j));
		}
		
		view.closePath();
	}
	
	
	// Iscrtava "mehuric" sa tekstom, sve u zadatoj velicini
	void drawBam(View view, double size) {
		view.stateStore();
		
		view.setLineWidth(0.03);
		
		view.addTransformation(Transformation.scaling(size)); // Skaliramo na zadatu velicinu, dalje iscrtavamo sve u "jediničnoj" veličini. 
		bubble(view);
		view.setFill(Color.RED);
		view.fill();
		view.setStroke(Color.DARKRED);
		view.stroke();
		
		view.setTextAlign(TextAlignment.CENTER);
		view.setTextBaseline(VPos.CENTER);
		view.setFont(font);

		view.setFill(Color.YELLOW);
		view.fillText("BAM!", Vector.ZERO);
		view.setStroke(Color.BLACK);
		view.strokeText("BAM!", Vector.ZERO);
		
		view.stateRestore();
	}
	
	
	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.WHITE);
		drawBam(view, 300*f(time)); // Zadatu velicinu namestamo proporcionalno vrednosti animacione krive u trenutnom momentu.
	}

	
	public static void main(String[] args) {
		DrawingApplication.launch(800, 800);
	}
	
}
