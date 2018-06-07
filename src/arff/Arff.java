/**
 * Author: Liall Arafa
   Imperial College London
   6 Jun 2018
2DFuglMeyer
	
 */
package arff;

import java.util.ArrayList;

/**
 * @author la2817
 *
 *Class representing an Arff File
 */
public class Arff {
	public String path;
	public String WMFTClass;
	public  int trial;
	
	public ArrayList<ArrayList<Double>> npList;
	public ArrayList<ArrayList<Double>> pList;


	//constructor for Arff when writing  speed,jerk,distances, angle
	public Arff(String Path, int Trial, String wolfClass,ArrayList<ArrayList<Double>> NPList, ArrayList<ArrayList<Double>> PList ){
		path=Path;
		trial=Trial;
		WMFTClass=wolfClass;
		
		npList = NPList;
		pList=PList;
		
	}
	//constructor for Arff when reading speed,jerk,distances, angle

	public Arff(String Path, String wolfClass,ArrayList<ArrayList<Double>> NPList, ArrayList<ArrayList<Double>> PList ){
		path=Path;
		WMFTClass=wolfClass;
		npList = NPList;
		pList=PList;
		
	}
	

}
