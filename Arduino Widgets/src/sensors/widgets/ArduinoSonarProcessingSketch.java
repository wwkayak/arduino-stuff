package sensors.widgets;

import processing.core.*;
import processing.serial.*;

@SuppressWarnings("serial")
public class ArduinoSonarProcessingSketch extends PApplet {

	SensorWidget sonar;
	SonarStore scan, nextScan;
	int xOffset = 0;
	int yOffset = 0;
	Serial comPort;
			
	public void setup() {
		frameRate(24);
		size(800, 600);
		background(33, 123, 100);// nice green!
		
		sonar = new Sonar(this) ;
		sonar.setLayer(0);
		sonar.setPos(width/2-sonar.width()/2, height/2-sonar.height()/2);
		
		try {
			comPort = new Serial(this, Serial.list()[0], 9600);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Make sure you are connected to the Arduino...");
			System.exit(0);
		}
		comPort.bufferUntil('\n');
	}

	public void draw() {
		background(33, 123, 100);
		sonar.draw();
	}

	public void mousePressed() {
		if(sonar.isMouseOver()) {
			sonar.setDragging(true);
			xOffset = mouseX - sonar.x();
			yOffset = mouseY - sonar.y();
		}
	}
	
	public void mouseDragged() {
		if( sonar.isDragging()){
			int th = sonar.height();
			int tw = sonar.width();
			int x = mouseX-xOffset;
			int y = mouseY-yOffset;
			
			if(y + th > height ) {
				y = height - th; 
			}else if(y < 0 ){
				y=0;
			}
			if(x + tw > width ) {
				x = width - tw; 
			}else if(x < 0 ){
				x=0;
			}
			sonar.setPos(x, y);
		}
	}

	public void mouseReleased() {
		sonar.setDragging(false);
	}
	
	public void serialEvent(Serial cPort){
		String buffer = cPort.readStringUntil('\n');
		  if(buffer != null) {
			  ((Sonar)sonar).addScan(buffer);
		  }
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PApplet.main(new String[] {"sensors.widgets.ArduinoSonarProcessingSketch" });

	}

}
