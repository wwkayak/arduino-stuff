package sensors.utilities;

public class TemperatureUtility {

/**
 * Maps the temperature to a pixel location within the widget
 * @return A value based on pixels instead of temperature scale
 * 
 */
public double mapTemperature(double temp, double oldMin, 
		       double oldRange, double newMin, double newRange) {
	
	double prcnt = (temp-oldMin) / (oldRange);
	Double d = newMin + prcnt*newRange;
	
	return d;
}

}