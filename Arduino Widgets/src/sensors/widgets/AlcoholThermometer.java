package sensors.widgets;

import processing.core.*;

/**
 * 
 * @author TOM
 * 
 * 
 */

public class AlcoholThermometer implements SensorWidget {

	int tw = 100;
	int th = 480;
	int x0 = 0;
	int y0 = 0;
	int midy = th/2;
	int midx = tw/2;
	int bulbx = midx - 5;
	int bulby = 400;
	int glassTop = 30;
	int pixelRange = bulby - glassTop - 5;
	int alcoholRange = 380;
	int layer = 0;
	double tickMajor = 10;
	double tickMinor = 1;
	double temp;
	double lowerLimit = 0;
	double upperLimit = 100;
	
	Thermometer thrmtr;
	PGraphics pg;
	PGraphics pg2;
	PImage bg;
	PApplet p;

	boolean over = false;
	boolean dragging = false;

	public AlcoholThermometer(PApplet applet) {
		this(applet, "C");
	}

	public AlcoholThermometer(PApplet applet, String scale) {

		thrmtr = new Thermometer(scale);
		thrmtr.setRange(lowerLimit, upperLimit);
		thrmtr.setCelsiusTemp(78.1);
		p = applet;
		pg = p.createGraphics(tw, th);
		pg2 = p.createGraphics(tw, th);
		initialize();
	}

	private int mapTemp2Pixles() {
		if(thrmtr.getScale().equals("C")) {
			temp = thrmtr.getCelsius();
		}else {
			temp = thrmtr.getFahrenheit();
		}
		double prcnt = temp / (upperLimit-lowerLimit);
		Double d = prcnt*pixelRange;
		
		return bulby-d.intValue();
	}
	
	
	public void initialize() {
		bg = p.loadImage("thermometer-bg.png");
	}

	public void draw(int px0, int py0) {
		x0 = px0;
		y0 = py0;
		over = false;
		
		pg.beginDraw();
		pg.background(bg);
		drawShadow();
		pg.image(pg2,0,0);
		drawGlass();
		drawAlcohol();
		pg.endDraw();
				
		p.tint(255, 255);
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
		pg.rectMode(pg.CORNERS);
		pg.fill(180);
		pg.stroke(128);
		pg.rect(midx-5, bulby-1, midx+4, glassTop, 5, 5, 0, 0 );
		pg.stroke(255);
		pg.rect(midx-2, bulby-1, midx-1, glassTop+5 );
	}
	
	private void drawAlcohol() {
		pg.rectMode(pg.CORNERS);
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
	
	public void setPos(int px, int py) {
		x0 = px;
		y0 = py;
	}

	public void setDragging(boolean isDragging) {
		dragging = isDragging;
	}
	
	public boolean isDragging() {
		return dragging;
	}	
		
	public boolean isMouseOver() {
		return over;
	}
	
	public int width() {
		return tw;
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

	public boolean over() {
		return over;
	}

	public int layer() {
		return layer;
	}
	
	public void setLayer(int l) {
		layer = l;
	}
	
	private void drawGraduations() {

	}

	private void drawBulb() {

	}
}
