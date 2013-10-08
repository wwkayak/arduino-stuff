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
	
	float tempC;
	float tempF;
	float maxHighTemp;
	float maxLowTemp;
	float sensorValue;
	float upperRange;
	float lowerRange;
	String scale = "C";
	
	public Thermometer(){}
	
	public Thermometer(String scale){
		this.scale = scale;
	}
		
	public void setCelsiusTemp(float newTemp){
		tempC = newTemp;
		tempF = convertToFahrenheit(newTemp);
		checkMaxMin(tempC);
		
	}
	
	public void setFahrenheitTemp(float newTemp){
		tempF = newTemp;
		tempC = convertToCelsius(newTemp);
		checkMaxMin(tempC);
	}
	
	void checkMaxMin(float tempC){
		if(tempC > maxHighTemp){
			maxHighTemp = tempC;
		}
		if(tempC < maxLowTemp){
			maxLowTemp = tempC;
		}
	}
		
	public void setRange(float lower, float upper) {
		lowerRange = lower;
		upperRange = upper;
	}
	
	public float getCelsius(){
		return tempC;
	}
	
	public float getFahrenheit(){
		return tempF;
	}
	
	float convertToCelsius(float in){
		return 5/9*(tempF - 32);
	}
	
	float convertToFahrenheit(float in){
		return 9/5*in + 32;
	}
	
	public float getMax() {
		if(scale=="F") {
			return convertToFahrenheit(maxHighTemp);
		}else {
			return maxHighTemp;
		}
	}
	
	public float getMin() {
		if(scale=="F") {
			return convertToFahrenheit(maxLowTemp);
		}else {
			return maxLowTemp;
		}
	}
	
}
