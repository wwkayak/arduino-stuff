package sensors.widgets;
import processing.core.*;

public class GuageThermometer extends SensorWidget {
		
	int center = 120;
	int lowerLimit=0;
	int upperLimit=50;
	int tickMajor = 10;
	int tickMinor = 1;
	Double temp = 0.0;
	double scaleAngle = 300;
	double startAngle =(float)((360-scaleAngle)/2 + 90);
	double range = upperLimit - lowerLimit;
	Double scale = scaleAngle/(upperLimit - lowerLimit);
	int trigDegsPerTempDeg = scale.intValue();
	
	Thermometer thrmtr;
	PImage bg;
	PGraphics pg;
	PGraphics pgNeedle;
	PFont LCDFont;
	PFont plainFont;
	
	public GuageThermometer(PApplet app) {
		this(app, "C");
	}
	
	public GuageThermometer(PApplet app, String scale) {
		super(app);
		width=240;
		height= 240;
		thrmtr = new Thermometer(scale, lowerLimit, upperLimit);
		pg = parent.createGraphics(width, height);
		pgNeedle = parent.createGraphics(width, height);
		initialize();
	}

	private void initialize() {
		bg = parent.loadImage("gauge-thermometer.png");
		LCDFont = parent.createFont("DS-DIGIB.TTF",20);
		plainFont = parent.createFont("Arial",11);
		drawNeedle();
	}
	
	public void draw() {
		this.draw(x0, y0);
	}
	
	@Override
	public void draw(int x, int y) {
		x0 = x;
		y0 = y;
		
		pg.beginDraw();
		temp = thrmtr.getCelsius();
		parent.tint(255, 255);
		if(isMouseOver()) {
			if(isDragging()){
				parent.tint(255, 125);
			}
		}
		pg.strokeWeight(1.5f);
		pg.background(bg);
		drawLCD();
		drawScale();
		pg.pushMatrix();
		pg.translate(center, center);
		pg.rotate(PApplet.radians((float)(startAngle+90+(temp*trigDegsPerTempDeg))));
		pg.image(pgNeedle, -center, -center);
		pg.popMatrix();
		pg.endDraw();
				
		parent.image(pg, x0, y0);

	}
	
	private void drawScale() {
		
		float r = 95;
		float theta =(float)startAngle;
		float x1, y1;
		float x2, y2;
		int i=0;
		
		pg.ellipseMode(PConstants.CENTER);
		pg.pushMatrix();
		pg.translate(center, center);
		while( i <= scaleAngle){
			pg.strokeWeight(1.5f);
			theta = PApplet.radians(i+(float)startAngle);
			x1 = r * PApplet.cos(theta);
			y1 = r * PApplet.sin(theta);
			x2 = (r-10) * PApplet.cos(theta);
			y2 = (r-10) * PApplet.sin(theta);			
			pg.line(x1, y1, x2, y2);
			i+=tickMinor*trigDegsPerTempDeg;	
			pg.strokeWeight(1);
			for(int k=1; k < tickMajor && i <= scaleAngle; k++, i+=tickMinor*trigDegsPerTempDeg){
				theta = PApplet.radians(i+(float)startAngle);
				x1 = r * PApplet.cos(theta);
				y1 = r * PApplet.sin(theta);
				pg.ellipse(x1,y1,2,2);
			}
		}
		pg.popMatrix();
	}
		
	//needle is a separate object so we can rotate it, instead redrawing it every time
	private void drawNeedle() {
		
		pgNeedle.beginDraw();

		pgNeedle.pushMatrix();
		pgNeedle.translate(center,center);
		//pivot circle
		pgNeedle.noFill();
		pgNeedle.strokeWeight(5);
		pgNeedle.ellipseMode(PConstants.RADIUS);
		pgNeedle.ellipse(0,0,12,12);
		
		//needle shadow
		pgNeedle.noStroke();
		pgNeedle.fill(150);
		pgNeedle.beginShape(PConstants.TRIANGLES);
		pgNeedle.vertex( -9, -13);
		pgNeedle.vertex( -4, -76);
		pgNeedle.vertex( 1, -13);
		pgNeedle.endShape();
		
		//needle
		pgNeedle.fill(0);
		pgNeedle.beginShape(PConstants.TRIANGLES);
		pgNeedle.vertex( -5, -10);
		pgNeedle.vertex( 0, -80);
		pgNeedle.vertex( 5, -10);
		pgNeedle.endShape();
		
		//needle highlight
		pgNeedle.strokeWeight(1);
		pgNeedle.stroke(128);
		pgNeedle.line(6, -14, 0, -80);
				
		pgNeedle.popMatrix();
		pgNeedle.endDraw();
		
	}
	
	private void drawLCD(){
				
		pg.beginDraw();
		pg.textFont(LCDFont);
		pg.fill(128,255,128);
		pg.stroke(0);
		pg.rect(center-35,center+30,70,20,5,5,5,5);
				
		pg.textAlign(PConstants.CENTER, PConstants.CENTER);
		pg.fill(64);
		pg.text(String.format("%.1f", temp.floatValue()), center , center+38);
		pg.endDraw();
	}
	
	public void setCelsius(double newTemp) {
		thrmtr.setCelsiusTemp(newTemp);
	}

	

}
