package sensors.widgets;

import processing.core.*;


/**
 * 
 * @author TOM
 * 
 * NOTE: do I also need a super class for alot of this stuff that needs to  be done on all thermmeters
 * draw major/minor divisions
 * draw bulb and tube
 * draw alcohol (with highlight or layer highlight)
 * mark highs and lows 
 *
 */



public class AlcoholThermometer {

	
	String comPortString;
	int B=3975;
	int thermometerwidth = 100;
	int thermometerHeight = 480;
	int y0 = 50;
	int bulb_width = 30;
	int bulb_height = thermometerHeight - 2*y0;
	int x0 = thermometerwidth/2 - bulb_width/2;
	int r = bulb_width/2;
	float temperature = 0;
	float major = 10;
	float minor = 1;
	
	PImage bg;
	PApplet p;
	
	
			
	
	public AlcoholThermometer( PApplet applet, int height) {
		this(applet, height, "C");
	}
	
	public AlcoholThermometer(PApplet applet, int height, String scale) {
		
		Thermometer thermometer = new Thermometer(scale);
		thermometerHeight = height;
		p = applet;
		drawThermometer();
		
			
	}
	
	private void drawThermometer() {
		bg = p.loadImage("thermometer-bg.png");
		int x0 = (p.getWidth()-thermometerwidth)/2;
		int y0 = (p.getHeight()-thermometerHeight)/2;
		
		p.strokeWeight(12);
		p.stroke(128);
		p.fill(128);
		p.rect(x0-6, y0+6, thermometerwidth, thermometerHeight, 30,30,30,30);
		p.filter(p.BLUR, 8);
		p.stroke(0);
		p.strokeWeight(1);
		p.noFill();
		p.rect(0,0,50,100);
		p.image(bg, x0, y0) ;	
	}
	
	private void drawGraduations() {
		PApplet.print("sjhgsdfh");
	}
	
	private void drawBulb() {
		
		
	}

}
