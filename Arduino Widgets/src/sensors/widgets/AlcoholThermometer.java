package sensors.widgets;

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
public class AlcoholThermometer implements SensorWidget {

	int tw = 100;
	int th = 480;
	int x0 = 0;
	int y0 = 0;
	int midy = th/2;
	int midx = tw/2;
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
	Double d = pixelRange/(upperLimit - lowerLimit);
	int tickFactor = d.intValue();
	
	Thermometer thrmtr;
	PGraphics pg;
	PGraphics pg2;
	PImage bg;
	PFont LCDFont;
	PFont plainFont;
	PApplet p;
	

	boolean over = false;
	boolean dragging = false;

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

		thrmtr = new Thermometer(scale, lowerLimit, upperLimit);
		thrmtr.setCelsiusTemp(lowerLimit+1);
		p = applet;
		pg = p.createGraphics(tw, th);
		pg2 = p.createGraphics(tw, th);
		initialize();
	}

	/**
	 * Maps the temperature to a pixel location within the widget
	 * @return A value based on pixels instead of temperature scale
	 */
	private int mapTemp2Pixles() {
		if(thrmtr.getScale().equals("C")) {
			temp = thrmtr.getCelsius();
		}else {
			temp = thrmtr.getFahrenheit();
		}
		double prcnt = (temp-lowerLimit) / (upperLimit-lowerLimit);
		Double d = prcnt*pixelRange;
		
		return bulby-d.intValue();
	}
	
	public void initialize() {
		bg = p.loadImage("thermometer-bg.png");
		LCDFont = p.createFont("DS-DIGIB.TTF",20);
		plainFont = p.createFont("Arial",11);
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
		over = false;
		
		pg.beginDraw();
		pg.background(bg);
		drawShadow();	// A separate shadow to mach the amount of alcohol being displayed
		pg.image(pg2,0,0);
		drawGlass(); 	//draws an outline an highlight og a glass bulb
		drawAlcohol();	// draws the actual  red "level"
		drawScale();	// draws the gradient ticks and LCD
		pg.endDraw();
				
		p.tint(255, 255);
		// Check if mouse is over our widget
		if (p.mouseX > px0 && p.mouseX < px0 + tw && p.mouseY > py0
				&& p.mouseY < py0 + th) {
			over = true;
			p.tint(255, 200);
		} 
		p.image(pg, x0, y0);
	}
	
	public void draw() {
		this.draw(x0, y0);
	}
	
	private void drawShadow() {
		pg2.beginDraw();
		pg2.noFill();
		pg2.clear();
		pg2.strokeWeight(6);
		pg2.stroke(150);
		pg2.line(midx+7, mapTemp2Pixles(), midx+7, 398);
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
		pg.rectMode(PConstants.CORNERS);
		pg.noStroke();
		pg.fill(180,0,0);
		pg.rect(midx-5, bulby, midx+5, mapTemp2Pixles() );
		pg.fill(255,0,0);
		pg.rect(midx-3, bulby, midx+4, mapTemp2Pixles() );
		pg.fill(220,220,220);
		pg.rect(midx-2, bulby, midx-1, mapTemp2Pixles() );
		pg.fill(255,150,150);
		pg.rect(midx-1, bulby, midx, mapTemp2Pixles() );
		pg.fill(255,99,99);
		pg.rect(midx, bulby, midx+1, mapTemp2Pixles() );
		
	}
	/**
	 *  Draws the actual gradient marks and LCD
	 */
	public void drawScale() {
		
		//get the range(pixles and temp)
		//factor of tickMinor(deg) to pixels
		//draw minors, then majors
		Double d;
		 
		pg.beginDraw();
		pg.textFont(plainFont);
		pg.stroke(0);
		pg.strokeWeight(1);
		pg.textSize(11);
		pg.textAlign(PConstants.LEFT, PConstants.CENTER);
		
		// Draw the minor temperature grasient lines
		for(int i=tickFactor; i < pixelRange; i+=tickMinor*tickFactor){
			pg.line(bulbx-10, (bulby - i), bulbx-15, (bulby - i));
			pg.line(bulbx+20, (bulby - i), bulbx+25, (bulby - i));
			
		}
		// Draw the Minor temperature gradient lines and Celsius and Fahrenheit  numbers
		pg.strokeWeight(1.5f);
		for(int i=tickFactor, j=(int)lowerLimit; i <= pixelRange; i+=tickMajor*tickFactor, j+=tickMajor){
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
		
		pg.textFont(LCDFont);
		pg.fill(128,255,128);
		pg.stroke(0);
		pg.rect(15,th-12,tw-15,th-32,5,5,5,5);
		pg.textAlign(PConstants.CENTER, PConstants.CENTER);
		
		d = thrmtr.getCelsius();
		pg.fill(64);
		pg.text(String.format("%.1f", d.floatValue()), midx , th-24);
		pg.endDraw();
		
	}
	
	public void setPos(int px, int py) {
		x0 = px;
		y0 = py;
	}

	/**
	 * mouse pressed
	 */
	public void setDragging(boolean isDragging) {
		dragging = isDragging;
	}
	
	/**
	 * mouse still pressed
	 */
	public boolean isDragging() {
		return dragging;
	}	
	
	/**
	 * mouse is over our widget
	 */
	public boolean isMouseOver() {
		return over;
	}
	
	public int width() {
		return tw;
	}
	
	/**
	 * Implementation of the SensorWidget's method
	 * @param newTemp
	 */
	public void setCelsius(double newTemp) {
		thrmtr.setCelsiusTemp(newTemp);
	}

	public int height() {
		return th;
	}

	public int x() {
		return x0;
	}

	public int y() {
		return y0;
	}

	/**
	 * Implementation of the SensorWidget's method
	 * @return true / false
	 */
	public boolean over() {
		return over;
	}

	/**
	 * Implementation of the SensorWidget's method
	 * Allows us to move widgets stacked on top of each other
	 */
	public int layer() {
		return layer;
	}
	
	public void setLayer(int l) {
		layer = l;
	}
		
}
