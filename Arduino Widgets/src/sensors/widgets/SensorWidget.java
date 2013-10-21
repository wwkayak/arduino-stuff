package sensors.widgets;
import processing.core.*;
/**
 *Super class for all Arduino graphical widgets. By implementing all of these 
 *methods, any subclass can be drawn properly, no matter what type of 
 *sensor is being represented.
 */
public abstract class SensorWidget {
	
	int width=100;
	int height=100;
	int x0=0;
	int y0=0;
	int layer=1;
	boolean dragging = false;
	PApplet parent;
	
	public SensorWidget(PApplet parent){
		this.parent = parent;
	}
	
	public void draw() {
		this.draw(0,0);
	}
	/** Override this method to provide a graphical representation of you sensor 
	 *
	 * @param x Distance from the left of the Processing canvas to draw widget
	 * @param y Distance from the top of the Processing canvas to draw widget
	 */
	abstract void draw(int x, int y);
		
	/** 
	 * 
	 * @return A boolean value indicating if the mouse is over the widget.
	 * The isOver variable can be set with a simple test using 
	 * the draw() coordinates:
	 * <pre>
	 * if (applet.mouseX > x && applet.mouseX < x + widgetWidth && 
	 *                 applet.mouseY > y && applet.mouseY < y  widgetHeight) {
	 *    isOver = true;
	 *}
	 *</pre>
	 * 
	 *  This assumes rectangular widgets. If you have round, or custom, widgets
	 *  this method should be overridden.
	 */
	public boolean isMouseOver(){
		if (parent.mouseX > x0 && parent.mouseX < x0 + width && parent.mouseY > y0
					&& parent.mouseY < y0 + height) {
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param isDragging Can simply indicate that the Processing "applet.mousePressed" has been detected.
	 */
	public void setDragging(boolean isDragging){
		dragging = isDragging;
	}
	
	/**
	 * 
	 * @return A boolean value to indicate if the mouse button is in a "pressed" state.
	 */
	public boolean isDragging() {
		return dragging;
	}
	
	/**
	 * 
	 *@param x Distance from the left of the Processing canvas to draw widget
	 *@param y Distance from the top of the Processing canvas to draw widget
	 */
	public void setPos(int x, int y){
		x0 = x;
		y0 = y;
	}
	
	/**
	 * 
	 * @param layer Indicates the order in which the widgets are drawn. This allows 
	 * us to select a widget, even if they are stacked on top of each other.
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	/**
	 * 
	 * @return The current layer of this widget.
	 */
	public int getLayer() {
		return layer;
	}
	
	public int x() {
		return x0;
	}
	
	public int y() {
		return y0;
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	

}
