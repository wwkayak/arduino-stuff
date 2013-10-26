package sensors.widgets;

import processing.core.*;
import processing.serial.*;
import org.apache.commons.collections4.iterators.*;
import org.apache.commons.collections4.queue.CircularFifoQueue;

public class Sonar extends SensorWidget {
	Serial comPort;
	SonarStore scan, nextScan;
		
	int pivotX;
	int pivotY;
	float theta, vX, vY, vX2, vY2, r;
	PGraphics pg;
	CircularFifoQueue<SonarStore> scans = new CircularFifoQueue<SonarStore>(128);
	PeekingIterator<SonarStore> iter; 
	
	public Sonar(PApplet applet) {
		super(applet);
		width = 500;
		height = 300;
		pivotX = width / 2;
		pivotY = height;
		pg = parent.createGraphics(width, height);
	}
	
	public void draw() {
		this.draw(x0,y0);
	}
	
	public void draw(int x, int y) {
		x0 = x;
		y0 = y;
		pg.beginDraw();
		pg.background(0);
		pg.strokeWeight(1.5f); 
		iter = PeekingIterator.peekingIterator(scans.iterator());		
		while (iter.hasNext()) {
			scan = iter.next();
			scan.dim();
			r = PApplet.map(scan.getDistance(), 0, 450, 0,	height);//non-linear map might be nice?
			theta = PApplet.radians(scan.getAngle());
			vX = r * PApplet.cos(theta);
			vY = r * PApplet.sin(theta);
			pg.stroke(0, 128, 0, scan.getAlpha());
			pg.line(pivotX, pivotY, pivotX - vX, pivotY - vY);
			if (iter.hasNext()) {
				nextScan = iter.peek();
				r = PApplet.map(nextScan.getDistance(), 0, 450, 0,	height);
				theta = PApplet.radians(nextScan.getAngle());
				vX2 = r * PApplet.cos(theta);
				vY2 = r * PApplet.sin(theta);
				pg.line(pivotX - vX2, pivotY - vY2, pivotX - vX, pivotY - vY);
			}
		}
		pg.endDraw();
		parent.image(pg, x0, y0);
	}
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
