package dataExtraction;

/**
 * Author: Liall Arafa
   Imperial College London
   16 Apr 2018
	
 */

/**
 * @author la2817
 */

public class Keypoint {
	
	public Double x;
	public Double y;
	public Double con;

	
	public Keypoint (Double X, Double Y, Double Con){
		x = X;
		y = Y;
		con= Con;
	}
	public Keypoint (Double X, Double Y){
		x = X;
		y = Y;
	}
	
}
