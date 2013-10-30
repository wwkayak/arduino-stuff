package sensors.widgets;

import processing.core.*;
import processing.serial.*;
import org.apache.commons.collections4.iterators.*;
import org.apache.commons.collections4.queue.CircularFifoQueue;


/**
 * A widget for visualizing the ultrasonic / ping style sensors. 
 * The widget has a similar interface to and old-school green radar screen.
 * It stores a range of "pings" in a collection of class SonarStore. Each scan(SonarStore)
 * contains a distance, an angle, and alpha value to provide a fade effect.
 * The Processing Serial class reads, and updates, the Arduino sensor information
 * when it's available.
 * The pings are stored in a CircularFifoQueue from the Apache Commons library 
 *
 * @todo reword this rambling description'
 * @todo create a non-linear map of distance to provide better visual detail of closer objects
 * 
 */
public class Sonar extends SensorWidget {
	Serial comPort;
	SonarStore scan, nextScan;
	int pivotX;
	int pivotY;
	int range; 
	int borderWidth = 12;
	float theta, vX, vY, vX2, vY2, r;
	PGraphics pg;
	CircularFifoQueue<SonarStore> scans = new CircularFifoQueue<SonarStore>(128);
	PeekingIterator<SonarStore> iter; 
	
	/**
	 * Default Constructor
	 * @param applet The parent PApplet class which is responsible for all drawing.
	 * 
	 */
	public Sonar(PApplet applet) {
		this(applet, 300);
	}
	
	/**
	 * 
	 * @param applet The parent PApplet class which is responsible for all drawing.
	 * @param range The max scan range in cm, (based on what the Arduino "server" code is sending
	 * 
	 * @todo don't assume cm's
	 */
	public Sonar(PApplet applet, int range) {
		super(applet);
		width = 500;
		height = 300;
		this.range = range;
		pivotX = width / 2;
		pivotY = height-borderWidth;
		pg = parent.createGraphics(width, height);
	}
	
	/** default draw implementation from SensorWidget
	 * This is called by the Processing Thread.
	 * 
	 */
	public void draw() {
		this.draw(x0,y0);
	}
	
	/**
	 * The main method this widget uses to draw itself. 
	 * 
	 * @param x y, represent the location of this widget on the main PApplet screen (parent).
	 */
	public void draw(int x, int y) {
		x0 = x;
		y0 = y;
		pg.beginDraw();
		pg.background(0);
		drawRanges();
		drawPings();
		drawBorder();
		pg.endDraw();
		parent.image(pg, x0, y0);
	}
	
	/**
	 * Draw each of the pings returned from the Arduino as a collection of vectors
	 * And connects their endpoints.
	 * 
	 * @todo make line drawing optional
	 */
	private void drawPings() {
		pg.strokeWeight(1.5f); 
		iter = PeekingIterator.peekingIterator(scans.iterator());	
		while (iter.hasNext()) {
			scan = iter.next();
			scan.dim();
			r = PApplet.map(scan.getDistance(), 0, range, 0, height);//non-linear map might be nice?
			theta = PApplet.radians(scan.getAngle());
			vX = r * PApplet.cos(theta);
			vY = r * PApplet.sin(theta);
			pg.stroke(0, 128, 0, scan.getAlpha());
			//Connect vector endpoints
			pg.line(pivotX, pivotY, pivotX - vX, pivotY - vY);
			if (iter.hasNext()) {
				nextScan = iter.peek();
				r = PApplet.map(nextScan.getDistance(), 0, range, 0,	height);
				theta = PApplet.radians(nextScan.getAngle());
				vX2 = r * PApplet.cos(theta);
				vY2 = r * PApplet.sin(theta);
				pg.line(pivotX - vX2, pivotY - vY2, pivotX - vX, pivotY - vY);
			}
		}	
	}
	
	/**
	 * Draws a window border around the "radar screen"
	 */
	private void drawBorder() {
		pg.noFill();
		pg.stroke(128);
		pg.strokeWeight(10);
		pg.rectMode(PConstants.CORNER);
		pg.rect(6,6,width-borderWidth,height-borderWidth);
		pg.stroke(255);
		pg.strokeWeight(1);
		pg.line(0,height-1, width, height-1);
		pg.line(11, height-11, width-11, height-11);
		pg.line(width-1, height-1, width-1, 2);
		pg.line(width-11, height-borderWidth, width-11, borderWidth);
	}
	
	/*
	 * Draws a series of rings to indicate the range of each ping
	 */
	public void drawRanges() {
		int div = range/10;
		pg.ellipseMode(PConstants.RADIUS);
		pg.stroke(0, 128, 0, 90);
		for(int r=div; r < range; r+=div){
			float dist = PApplet.map(r, 0, range, 0, width/2);
			pg.arc(pivotX, pivotY,dist, dist, PConstants.PI/2, 2*PConstants.PI);
			pg.textAlign(PConstants.CENTER);
			pg.text(r, pivotX, height-dist);
		}
		
	}
	
	/*
	 * Sets the maximum range that the arduino is scanning. This is used to map
	 * the actual Ping distance to windows pixels
	 */
	public void setRange(int newRange) {
		range = newRange;
	}
	
	public int getRange() {
		return range;
	}
	
	/**
	 * Add the most recent Ping from the Arduino to the queue. This is a circular queue
	 * so the oldest pings will automatically be removed. In fact they will literally 
	 * fade away because of the SonarStore's ever decreasing alpha value.
	 * @param buffer A string buffer containing comma separated range and angle values.
	 */
	public void addScan(String buffer){
		 String[] values = PApplet.split(buffer, ',');
		    try {
		      int angle = Integer.parseInt(PApplet.trim(values[0])); //deg
		      int distance = Integer.parseInt(PApplet.trim(values[1])); //cm 
		      scans.add(new SonarStore(angle, distance));
		   } catch (Exception e) {
		      e.printStackTrace();
		    }	
	}

}
