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
 *Arff file for each metric for each limb
 *Each Arff File represents a kin.feature extracted from one trial
 */
public class Arff {
	public String path;
	public String WMFTClass;
	public  int trial;
	//2D array containing each column of arff file in an arraylist 
	public ArrayList<ArrayList<Double>> list;
	//public ArrayList<ArrayList<Double>> pList;


	//constructor for Arff when writing speed,jerk,distances, angle
	/*public Arff(String Path, int Trial, String wolfClass,ArrayList<ArrayList<Double>> NPList, ArrayList<ArrayList<Double>> PList ){
		path=Path;
		trial=Trial;
		WMFTClass=wolfClass;
		
		npList = NPList;
		pList=PList;
		
	}*/

	public Arff(String Path, String wolfClass, ArrayList<ArrayList<Double>> List ){
		path=Path;
		WMFTClass=wolfClass;
		list = List;
		
	}
	
	public Arff(String Path, int Trial, String wolfClass,ArrayList<ArrayList<Double>> List ){
	path=Path;
	trial=Trial;
	WMFTClass=wolfClass;
	list = List;
	
}
	

}
