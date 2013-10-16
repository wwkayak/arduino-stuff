package sensors.widgets;
/**
 *Super class for all Arduino graphical widgets. By implementing all of these 
 *methods, any subclass can be drawn properly, no matter what type of 
 *sensor is being represented.
 */
public interface SensorWidget {
	
	/** Override this method to provide a graphical representation of you sensor 
	 *
	 * @param x Distance from the left of the Processing canvas to draw widget
	 * @param y Distance from the top of the Processing canvas to draw widget
	 */
	public void draw(int x, int y);
		
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
	 */
	public boolean isMouseOver();
	
	/**
	 * 
	 * @param isDragging Can simply indicate that the Processing "applet.mousePressed" has been detected.
	 */
	public void setDragging(boolean isDragging);
	
	/**
	 * 
	 * @return A boolean value to indicate if the mouse button is in a "pressed" state.
	 */
	public boolean isDragging();
	
	/**
	 * 
	 *@param x Distance from the left of the Processing canvas to draw widget
	 *@param y Distance from the top of the Processing canvas to draw widget
	 */
	public void setPos(int x, int y);
	
	/**
	 * 
	 * @param layer Indicates the order in which the widgets are drawn. This allows 
	 * us to select a widget, even if they are stacked on top of each other.
	 */
	public void setLayer(int layer);
	
	/**
	 * 
	 * @return The current layer of this widget.
	 */
	public int layer();
	

}
