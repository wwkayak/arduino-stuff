package sensors.widgets;
/**
 * 
 * @author TOM
 *
 *Model for any number of temperature sensors
 *
 *Scale C/F
 *Range
 *Labels
 *Divisions
 *Accuracy
 * Highs and Lows
 */
public class Thermometer  {
	
	double tempC = 78.1;
	double tempF;
	double maxHighTemp;
	double maxLowTemp;
	double sensorValue;
	double upperRange;
	double lowerRange;
	String scale = "C";
	
	public Thermometer(){}
	
	public Thermometer(String scale){
		this.scale = scale;
		lowerRange = -30;
		upperRange = 120;
	}
	
	public String getScale() {
		return scale;
	}
		
	public void setCelsiusTemp(double newTemp){
		tempC = newTemp;
		tempF = convertToFahrenheit(newTemp);
		checkMaxMin(tempC);
	}
	
	public void setFahrenheitTemp(double newTemp){
		tempF = newTemp;
		tempC = convertToCelsius(newTemp);
		checkMaxMin(tempC);
	}
	
	public double getCelsius(){
		return tempC;
	}
	
	public double getFahrenheit(){
		return tempF;
	}
	
	public void setRange(double lower, double upper) {
		lowerRange = lower;
		upperRange = upper;
	}
	
	public double getLower() {
		return lowerRange;
	}
	
	public double getUpper() {
		return upperRange;
	}
	
	public double getRange() {
		return upperRange - lowerRange;
	}
	
	double convertToCelsius(double in){
		return 5/9*(tempF - 32);
	}
	
	double convertToFahrenheit(double in){
		return 9/5*in + 32;
	}
	
	void checkMaxMin(double tempC){
		if(tempC > maxHighTemp){
			maxHighTemp = tempC;
		}
		if(tempC < maxLowTemp){
			maxLowTemp = tempC;
		}
	}
	
	public double getMax() {
		if(scale=="F") {
			return convertToFahrenheit(maxHighTemp);
		}else {
			return maxHighTemp;
		}
	}
	
	public double getMin() {
		if(scale=="F") {
			return convertToFahrenheit(maxLowTemp);
		}else {
			return maxLowTemp;
		}
	}
	
}
