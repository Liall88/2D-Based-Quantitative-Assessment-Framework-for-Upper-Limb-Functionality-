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
	public String pClass;
	public  int trial;
	//2D array containing each column of arff file in an arraylist 
	//where list.get(0) represents arraylist for keypoint 0, list.get(1) reresents arraylist for keypoint 1 so on so forth
	//can also represent diff file where 0 would be list of speed Diff, 1 angle diff and 2 jerk diff
	public Integer trialsize;
	public ArrayList<ArrayList<Double>> multilist;
	//public ArrayList<ArrayList<Double>> pList;
	public ArrayList<ArrayList<Double>> speedDifflist;
	public ArrayList<ArrayList<Double>> angleDifflist;
	public ArrayList<ArrayList<Double>> jerkDifflist;
	public ArrayList<ArrayList<Double>> normDisList;


	//single Arffs
	
	public Arff(String Path, String wolfClass, ArrayList<ArrayList<Double>> List){
		path=Path;
		pClass=wolfClass;
		multilist = List;
		
	}
	public Arff(String Path, String wolfClass, ArrayList<ArrayList<Double>> List, int length){
		path=Path;
		pClass=wolfClass;
		multilist = List;
		trialsize=length;
		
	}
	
	public Arff(String Path, int Trial, String wolfClass,ArrayList<ArrayList<Double>> List){
	path=Path;
	trial=Trial;
	pClass=wolfClass;
	multilist = List;
	
	}
	

	public Arff(String Path, int Trial, String wolfClass,ArrayList<ArrayList<Double>> List, int length){
	path=Path;
	trial=Trial;
	pClass=wolfClass;
	multilist = List;
	trialsize=length;
	
	}
	//diffArffs
	/*public Arff(String Path, int Trial, String wolfClass, ArrayList<ArrayList<Double>> sList,ArrayList<ArrayList<Double>> aList,ArrayList<ArrayList<Double>> jList ){
		path=Path;
		trial=Trial;
		speedDifflist = sList;
		angleDifflist = aList;
		jerkDifflist = jList;
		pClass=wolfClass;
		
		}*/
	
	public Arff(String Path, int Trial, String wolfClass, ArrayList<ArrayList<Double>> sList,ArrayList<ArrayList<Double>> aList,ArrayList<ArrayList<Double>> jList,ArrayList<ArrayList<Double>> disList, int length ){
		path=Path;
		trial=Trial;
		speedDifflist = sList;
		angleDifflist = aList;
		jerkDifflist = jList;
		normDisList=disList;
		pClass=wolfClass;
		trialsize=length;
		
		}
	

}
