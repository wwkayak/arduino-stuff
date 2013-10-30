package sensors.widgets;

/**
 * 
 * SonarStore is a simple class to hold the results of an Utrasonic Ping Sensor.
 * It contains a Distance, an Angle, and an alpha value for visual effect.
 *
 */
public class SonarStore {

	int angle = 0;
	int distance = 0;
	float alpha = 255;
	float alphaDecrement = .75f;

	public SonarStore() {
		this(0, 0);
	}

	/**
	 * 
	 * @param deg the angle the bot/sensor/servo was facing when the ping was sent/received
	 * @param dist The resulting distance of the ping
	 */
	public SonarStore(int deg, int dist) {
		angle = deg;
		distance = dist;
		alpha = 255;
	}

	
	public int getAngle() {
		return angle;
	}

	public void setAngle(int deg) {
		angle = deg;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int dist) {
		distance = dist;
	}

	/**
	 * 
	 * @param newAlpha Ttypically this will be set to 255 upon creation, and slowly decay
	 * as the ping "ages"
	 */
	public void setAlpha(float newAlpha) {
		alpha = newAlpha;
	}

	public float getAlpha() {
		return alpha;
	}

	/** Sets the "rate" at which the alpha value decays
	 * 
	 * @param newDec The amount to subtract from the alpha valu on each iteration
	 */
	public void setAlphaDecrement(float newDec){
		alphaDecrement = newDec;
	}
	
	/**
	 * decrement the alpha value by the amount set above.
	 */
	public void dim() {
		if (alpha > 0) {
			alpha -= alphaDecrement;
		}
	}
}
