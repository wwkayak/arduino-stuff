package sensors.widgets;

import processing.core.PApplet;
import java.util.Random;

public class ArduinoThermometerProcessingSketch extends PApplet {

	AlcoholThermometer[] thermometers = new AlcoholThermometer[4] ;
	Random r = new Random();
	String message = "";
	int xOffset = 0;
	int yOffset = 0;
	
	public void setup() {
		size(800, 600);
		background(33, 123, 100);// nice green!
		for(int i=0; i < 4; i++) {
			thermometers[i] = new AlcoholThermometer(this);	
			thermometers[i].setLayer(i);
			thermometers[i].setPos(r.nextInt(width-thermometers[i].width()), r.nextInt(height-thermometers[i].height()));
		}
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
		int count=0;
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

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PApplet.main(new String[] { "--present",
				"sensors.widgets.ArduinoThermometerProcessingSketch" });

	}

}
