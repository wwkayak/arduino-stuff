package sensors.widgets;

/**
 * A Generic Super class for any thermometer.
 * The Thermometer class is the model for the graphical view.
 * 
 */
public class Thermometer  {
	
	double tempC=0;
	double tempF=0;
	double maxHighTemp=-1000;
	double maxLowTemp=1000;
	double sensorValue=-1;
	double upperRange=0;
	double lowerRange=0;
	String scale = "C";
	
	/**
	 * Default Class constructor. Assumes Celsius
	 */
	public Thermometer(){
		this("C");
	}
	
	/**
	 * Class Constructor
	 * @param scale A string indicating whether Celsius("C"), or Fahrenheit("F") should be used.
	 * Also assumes a temperature range of 03 to 120 degrees Celsius.
	 */
	public Thermometer(String scale){
		this.scale = scale;
		lowerRange = -30;
		upperRange = 120;
		
	}
	
	/**
	 * Class Constructor
	 * @param scale A string indicating whether Celsius("C"), or Fahrenheit("F") should be used.
	 * @param lower lower temperature range
	 * @param upper upper temperature range
	 */
	public Thermometer(String scale, double lower, double upper){
		this.scale = scale;
		lowerRange = lower;
		upperRange = upper;
	}
	
	/**
	 * 
	 * @return "C" or "F" to indicate the temperature scale being used.
	 */
	public String getScale() {
		return scale;
	}
	
	/**
	 * 
	 * @param newTemp Sets the current temperature in Celsius, and Fahrenheit, 
	 * based on the Celsius value passed in. 
	 */
	public void setCelsiusTemp(double newTemp){
		tempC = newTemp;
		tempF = convertToFahrenheit(newTemp);
		checkMaxMin(tempC);
	}
	
	/**
	 * 
	 * @param newTemp Sets the current temperature in Celsius, and Fahrenheit, 
	 * based on the Fahrenheit value passed in. 
	 */
	public void setFahrenheitTemp(double newTemp){
		tempF = newTemp;
		tempC = convertToCelsius(newTemp);
		checkMaxMin(tempC);
	}
	
	/**
	 * 
	 * @return the current temperature in Celsius
	 */
	public double getCelsius(){
		return tempC;
	}
	
	/**
	 * 
	 * @return the current temperature in Fahrenheit
	 */
	public double getFahrenheit(){
		return tempF;
	}
	
	/**
	 * 
	 * @param lower lower temperature range
	 * @param upper upper temperature range
	 */
	public void setRange(double lower, double upper) {
		lowerRange = lower;
		upperRange = upper;
	}
	
	/**
	 * 
	 * @return lower display range
	 */
	public double getLower() {
		return lowerRange;
	}
	
	/**
	 * 
	 * @return upper display range
	 */
	public double getUpper() {
		return upperRange;
	}
	
	/**
	 * 
	 * @return The maximum temperature "spread". Useful for mapping to pixles.
	 */
	public double getRange() {
		return upperRange - lowerRange;
	}
	
	/**
	 * Utility to convert between scales
	 * @param in Fahrenheit value to be converted
	 * @return
	 */
	public double convertToCelsius(double in){
		return 5*(tempF - 32)/9;
	}
	
	/**
	 * Utility to convert between scales
	 * @param in Celsius value to be converted
	 * @return
	 */
	public double convertToFahrenheit(double in){
		return 9*in/5 + 32;
	}
	
	/**
	 * Utility to track max and min temperatures seen over the life of the widget.
	 * @param tempC the current temperature, in Celsius
	 */
	private void checkMaxMin(double tempC){
		if(tempC > maxHighTemp){
			maxHighTemp = tempC;
		}
		if(tempC < maxLowTemp){
			maxLowTemp = tempC;
		}
	}
	
	/**
	 * 
	 * @return The highest temperature seen so far
	 */
	public double getMax() {
		if(scale=="F") {
			return convertToFahrenheit(maxHighTemp);
		}else {
			return maxHighTemp;
		}
	}
	
	/**
	 * 
	 * @return The lowest temperature seen so far
	 */
	public double getMin() {
		if(scale=="F") {
			return convertToFahrenheit(maxLowTemp);
		}else {
			return maxLowTemp;
		}
	}
	
}
