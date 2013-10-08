package sensors.widgets;

import processing.core.PApplet;

public class ArduinoThermometerProcessingSketch extends PApplet {

	
	
	public void setup() {
	    size(800,600);
	    
	}
	 

	public void draw() {
	    background(180);
	    AlcoholThermometer thermometer = new AlcoholThermometer(this, 480);
	}
	
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		PApplet.main(new String[] { "--present", "sensors.widgets.ArduinoThermometerProcessingSketch" });

	}

}
