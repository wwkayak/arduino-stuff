package sensors.widgets;

public class SonarStore {

	int angle = 0;
	int distance = 0;
	float alpha = 255;
	float alphaDecrement = .75f;

	public SonarStore() {
		this(0, 0);
	}

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

	public void setAlpha(float newAlpha) {
		alpha = newAlpha;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlphaDecrement(float newDec){
		alphaDecrement = newDec;
	}
	
	public void dim() {
		if (alpha > 0) {
			alpha -= alphaDecrement;
		}
	}
}
