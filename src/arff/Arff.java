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
	//where list.get(0) represents arraylist for keypoint 0, list.get(1) reresents arraylist for keypoint 1 so on so forth
	public ArrayList<ArrayList<Double>> multilist;
	//public ArrayList<ArrayList<Double>> pList;


	public Arff(String Path, String wolfClass, ArrayList<ArrayList<Double>> List ){
		path=Path;
		WMFTClass=wolfClass;
		multilist = List;
		
	}
	
	public Arff(String Path, int Trial, String wolfClass,ArrayList<ArrayList<Double>> List ){
	path=Path;
	trial=Trial;
	WMFTClass=wolfClass;
	multilist = List;
	
	}
	

}
