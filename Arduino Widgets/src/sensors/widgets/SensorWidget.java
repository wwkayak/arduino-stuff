package sensors.widgets;

public interface SensorWidget {
	
	public void draw(int x, int y);
	public boolean isMouseOver();
	public void setDragging(boolean isDragging);
	public boolean isDragging();
	public void setPos(int x, int y);
	public void setLayer(int l);
	public int layer();
	

}
