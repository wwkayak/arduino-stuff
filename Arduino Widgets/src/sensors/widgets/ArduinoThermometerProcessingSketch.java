package sensors.widgets;

import processing.core.PApplet;
import processing.serial.*;


import java.util.Random;

@SuppressWarnings("serial")
public class ArduinoThermometerProcessingSketch extends PApplet {

	AlcoholThermometer[] thermometers = new AlcoholThermometer[4] ;
	Random r = new Random();
	String message = "";
	int xOffset = 0;
	int yOffset = 0;
	Serial comPort;
	int B=3975; // temp conversion constant
	
	public void setup() {
		size(800, 600);
		background(33, 123, 100);// nice green!
		for(int i=0; i < 4; i++) {
			thermometers[i] = new AlcoholThermometer(this);	
			thermometers[i].setLayer(i);
			thermometers[i].setPos(r.nextInt(width-thermometers[i].width()), r.nextInt(height-thermometers[i].height()));
		}
		
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
		background(33, 123, 100);// nice green!
		
		for(int i=0; i < thermometers.length; i++) {
			thermometers[i].draw();
		}
		text(message, 50, 50);
	}

	public void mousePressed() {
		message="mouse pressed";
		int layer = -1;
		for(int i=0; i < thermometers.length; i++) {
			if(thermometers[i].isMouseOver()) {
				if( thermometers[i].layer() > layer){
					layer = thermometers[i].layer();
				}
			}
		}
		if(layer != -1) {
			thermometers[layer].setDragging(true);
			xOffset = mouseX - thermometers[layer].x();
			yOffset = mouseY - thermometers[layer].y();
		}else{
			message = "no clicky";
		}
	}
	
	public void mouseDragged() {
		message = "mouse dragging";
		for(int i=0; i < thermometers.length; i++) {
			if( thermometers[i].isDragging()){
				int th = thermometers[i].height();
				int tw = thermometers[i].width();
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
				thermometers[i].setPos(x, y);
			}
		}
	}

	public void mouseReleased() {
		message = "mouse released";
		for(int i=0; i < thermometers.length; i++) {
			thermometers[i].setDragging(false);
		}
	}

	
	public void serialEvent(Serial cPort){
		
	  String str = cPort.readStringUntil('\n');
	  if(str != null) {
	    int sensorVal = Integer.parseInt(trim(str));
	    double resistance=(1023-sensorVal)*10000/sensorVal; 
	    double currTemp = 1/(log((float)resistance/10000)/B+1/298.15)-273.15;
	    message = "TEMP: " + currTemp;
	    thermometers[3].setCelsius(currTemp);
	    
	  } 
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PApplet.main(new String[] {
				"sensors.widgets.ArduinoThermometerProcessingSketch" });

	}

}
