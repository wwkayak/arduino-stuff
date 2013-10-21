package sensors.widgets;

import sensors.utilities.*;
import processing.core.*;

/**
 * A typical red alcohol style outdoor window thermometer.
 * It contains a Temperature instance to actually store values,
 * and implements the SensorWidget in order to graphically display 
 * the information.
 *
 * @see Thermometer
 * @see SensorWidget
 * @see PGraphics
 * 
 */
public class AlcoholThermometer extends SensorWidget {

	int midy = height/2;
	int midx = width/2;
	int bulbx = midx - 5; // bulbx , y top of the bulb, bottom of glass tube
	int bulby = 400;
	int glassTop = 30;
	int pixelRange = bulby - glassTop - 5;
	int alcoholRange = 380;
	int layer = 0;
	int tickMajor = 10;
	int tickMinor = 1;
	double temp;
	double lowerLimit = 0;
	double upperLimit = 50;
	double range = upperLimit - lowerLimit;
	Double scale = pixelRange/(upperLimit - lowerLimit);
	int pixPerDeg = scale.intValue();
	
	Thermometer thrmtr;
	TemperatureUtility util = new TemperatureUtility();
	PGraphics pg;
	PGraphics pg2;
	PImage bg;
	PFont LCDFont;
	PFont plainFont;
	
	/**
	 * Class Constructor
	 * @param applet The parent class of all Processing "sketches"
	 * Using the Processing library outside of it's IDE requires that we pass
	 * the main Applet widow reference to "draw" on.
	 */
	public AlcoholThermometer(PApplet applet) {
		this(applet, "C");
	}

	/**
	 * Class Constructor
	 * @param applet The parent Processing Applet class to "draw" on
	 * @param scale "C" Celsius or "F" Fahrenheit
	 */
	public AlcoholThermometer(PApplet applet, String scale) {

		super(applet);
		
		width=100;
		height= 480;
		thrmtr = new Thermometer(scale, lowerLimit, upperLimit);
		thrmtr.setCelsiusTemp(lowerLimit+1);
		pg = parent.createGraphics(width, height);
		pg2 = parent.createGraphics(width, height);
		initialize();
	}

	
	
	public void initialize() {
		
		bg = parent.loadImage("thermometer-bg.png");
		LCDFont = parent.createFont("DS-DIGIB.TTF",20);
		plainFont = parent.createFont("Arial",11);
	}

	/**
	 * The implementation of the SensorWidget's draw function.
	 * This is where we actually draw the widget. This is called continually from
	 * the Processing Thread, so we need to remember everything redrawn all the time.
	 * 
	 * We use a PGraphics to draw on. That way we don't need to refer to the main 
	 * PApplet class until we're done.
	 */
	public void draw(int px0, int py0) {
		x0 = px0;
		y0 = py0;
		
		pg.beginDraw();
		parent.tint(255, 255);
		if(isMouseOver()) {
			if(isDragging()) {
				parent.tint(255, 125);
			}
		}
		pg.background(bg);
		drawShadow();	// A separate shadow to mach the amount of alcohol being displayed
		pg.image(pg2,0,0);
		drawGlass(); 	//draws an outline an highlight og a glass bulb
		drawAlcohol();	// draws the actual  red "level"
		drawScale();	// draws the gradient ticks and LCD
		drawLCD();
		pg.endDraw();
				
		parent.image(pg, x0, y0);
	}
	
	public void draw() {
		this.draw(x0, y0);
	}
	
	/**
	 * I originally had a separate shadow method because I was using a BLUR filter
	 * for a drop shadow, but it's sloow, but it also makes more maintainable.
	 */
	private void drawShadow() {
		int pixPos  = mapTemp2Pixels(thrmtr.getCelsius());
		pg2.beginDraw();
		pg2.noFill();
		pg2.clear();
		pg2.strokeWeight(6);
		pg2.stroke(150);
		pg2.line(midx+7, bulby-pixPos, midx+7, 398);
		pg2.endDraw();
	}
	
	private void drawGlass() {
		pg.rectMode(PConstants.CORNERS);
		pg.fill(180);
		pg.stroke(128);
		pg.rect(midx-5, bulby-1, midx+4, glassTop, 5, 5, 0, 0 );
		pg.stroke(255);
		pg.rect(midx-2, bulby-1, midx-1, glassTop+5 );
	}
	
	private void drawAlcohol() {
		int pixPos  = mapTemp2Pixels(thrmtr.getCelsius());
		pg.rectMode(PConstants.CORNERS);
		pg.noStroke();
		pg.fill(180,0,0);
		pg.rect(midx-5, bulby, midx+5, bulby-pixPos);
		pg.fill(255,0,0);
		pg.rect(midx-3, bulby, midx+4, bulby-pixPos);
		pg.fill(220,220,220);
		pg.rect(midx-2, bulby, midx-1, bulby-pixPos);
		pg.fill(255,150,150);
		pg.rect(midx-1, bulby, midx, bulby-pixPos);
		pg.fill(255,99,99);
		pg.rect(midx, bulby, midx+1,  bulby-pixPos);
		
	}
	
	/**
	 * 
	 */
	private int mapTemp2Pixels(double temp) {
		
		Double location = util.mapTemperature( temp, lowerLimit, range, 0, pixelRange);
		return location.intValue();
		 
	}
	
	/**
	 *  Draws the actual gradient marks and LCD
	 */
	public void drawScale() {
				
		Double d;
		 
		pg.beginDraw();
		pg.textFont(plainFont);
		pg.stroke(0);
		pg.strokeWeight(1);
		pg.textSize(11);
		pg.textAlign(PConstants.LEFT, PConstants.CENTER);
		
		// Draw the minor temperature grasient lines
		for(int i=pixPerDeg; i < pixelRange; i+=tickMinor*pixPerDeg){
			pg.line(bulbx-10, (bulby - i), bulbx-15, (bulby - i));
			pg.line(bulbx+20, (bulby - i), bulbx+25, (bulby - i));
			
		}
		// Draw the Minor temperature gradient lines and Celsius and Fahrenheit  numbers
		pg.strokeWeight(1.5f);
		for(int i=pixPerDeg, j=(int)lowerLimit; i <= pixelRange; i+=tickMajor*pixPerDeg, j+=tickMajor){
			pg.stroke(255,0,0);
			pg.line(bulbx-10, (bulby - i), bulbx-17, (bulby - i));
			pg.line(bulbx+20, (bulby - i), bulbx+27, (bulby - i));
			pg.stroke(0);
			pg.textAlign(PConstants.LEFT, PConstants.CENTER);
			pg.fill(0);
			pg.text(""+ j, bulbx+30, (bulby - i));
			d = thrmtr.convertToFahrenheit(j);
			pg.textAlign(PConstants.RIGHT, PConstants.CENTER);
			pg.text(""+ d.intValue(), bulbx-18, (bulby - i));
		}
		pg.endDraw();
	}
	
	private void drawLCD(){
		Double d;
		
		pg.beginDraw();
		pg.textFont(LCDFont);
		pg.fill(128,255,128);
		pg.stroke(0);
		pg.rect(15,height-12,width-15,height-32,5,5,5,5);
		pg.textAlign(PConstants.CENTER, PConstants.CENTER);
		
		d = thrmtr.getCelsius();
		pg.fill(64);
		pg.text(String.format("%.1f", d.floatValue()), midx , height-24);
		pg.endDraw();
	}
	
	public void setCelsius(double newTemp) {
		thrmtr.setCelsiusTemp(newTemp);
	}
			
}
