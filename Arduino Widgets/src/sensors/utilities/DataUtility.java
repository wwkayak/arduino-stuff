/**Similar to Arduino’s map function, this value remapping allows a simple adaption of nonlinear input or output devices.
If you have a device like a sensor with a behavior that follows the red line for example draw some joined lines through the red curve and make note of  the five node points which are occurring here.
{{    0,10  }  , {    10,50  }  , {    20,150  }  , {    40,200  }  ,{    50,200  }};
Put these values in the “nodepoints” array where the reMap() function calculates the linear equations (y=mx+b) and according to that the resulting output value.
If you need more nodes simply enlarge the “nodepoints” Array. The node values can be positive or negative .

*Martin Nawrath / KHM 2010 http://interface.khm.de/index.php/lab/experiments/nonlinear-mapping/
*/

package sensors.utilities;

/** I don't like this version, use a lookup table / formula
 * 
 *
 *
 */
public class DataUtility {

	public int nonlinearMap(float pts[][], int input) {
		int output=0;
	
	
		return output;
	}
	
}

