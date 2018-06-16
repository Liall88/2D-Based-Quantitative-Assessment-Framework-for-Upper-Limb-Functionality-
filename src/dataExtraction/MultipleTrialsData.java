/**
 * Author: Liall Arafa
   Imperial College London
   15 May 2018
	
 */
package dataExtraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;

import arff.Arff;
import arff.ArffReader;

/**
 * @author la2817
 *
 */
public class MultipleTrialsData {
	//number of trials 
	final int trialNum = MakeSingleTrialArffs.numOfTests;
	
	//ArrayLists Of angles of each Skeletons at each frame of both paretic and non-paretic skeletons at each frame
	private static ArrayList<ArrayList<Double>> npAngAvrgList = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> pAngAvrgList = new ArrayList<ArrayList<Double>>();

	//distances of keypoint in terms of x,y coordinates from reference trajectory Keypoints
	private static ArrayList<ArrayList<Double>> disXAvrgList= new ArrayList<ArrayList<Double>>(); ;
	private static ArrayList<ArrayList<Double>> disYAvrgList= new ArrayList<ArrayList<Double>>(); ;

	//normalised distances of keypoint from reference trajectory
	private static ArrayList<ArrayList<Double>> disNormAvrgList= new ArrayList<ArrayList<Double>>(); ;
	
	//speed  of keypoints in terms of pixel per frame between skeletons
	private static ArrayList<ArrayList<Double>> npSpeedAvrgList= new ArrayList<ArrayList<Double>>(); ;
	private static ArrayList<ArrayList<Double>> pSpeedAvrgList= new ArrayList<ArrayList<Double>>(); ;

	//jerk (or smoothness) of keypoints
	private static ArrayList<ArrayList<Double>> npJerkAvrgList= new ArrayList<ArrayList<Double>>(); ;
	private static ArrayList<ArrayList<Double>> pJerkAvrgList= new ArrayList<ArrayList<Double>>(); ;
	

	
	//public static void setAverages(ArrayList<Double>  )
	//get data from ARFFReader class
private static ArrayList<String> getAverageAcrossTrials(){
		
		return new ArrayList<String> ();
	
	}



//method that uses cubic spline interpolation to return an arff of the same length to a larger one 
//takes in iarff to be interpolated, otherarff interpolation is based on, and the keypoint for which list should be interpolated 
public static ArrayList<Double> listCubicSplineInterpolator(ArrayList<Double> iList, ArrayList<Double> otherList ){
																											
	
	ArrayList<Double> interpolatedList = new ArrayList<Double> ();
	SplineInterpolator cubicSp = new SplineInterpolator();
	
	int maxLim = otherList.size();
	int sampleRate = (int) maxLim/iList.size();
	System.out.println("DEBUG: sample Rate: " + sampleRate);
	
	double [] x = new double[otherList.size()];
	double [] y = new double[otherList.size()];

	for (int i =0; i < otherList.size();i++){
		x[i]=i;//each frame
		y[i]=(double)otherList.get(i);//each y value at each frame
	}
			
	PolynomialSplineFunction psf = cubicSp.interpolate(x, y);

	//DEBUG: interpolate between every 2 points
	for(int i =0; i <iList.size();i++ ){
		interpolatedList.add(psf.value(i));
		System.out.println("DEBUG interPolatedList; " + interpolatedList.get(i));
		
	}
	System.out.println("DEBUG interListsize: " +interpolatedList.size());

	return interpolatedList;
}


public static ArrayList<Double> listScale (ArrayList<Double> list, int scaleSize){
	
	System.out.println("DEBUG: listSize: " +list.size());
	System.out.println("DEBUG: scaleSize: " +scaleSize);


	ArrayList<Double> scaledList = new ArrayList<Double>();
	int step = list.size()/scaleSize;
	System.out.println("DEBUG: step: " +step);
	int stepCount=0;
	
	int i =0; 
	while (scaledList.size()<scaleSize){

		System.out.println("DEBUG: stepCount" +stepCount);
		if(stepCount==step){
			scaledList.add(list.get(i));
			System.out.println("DEBUG: Scalelist: " + list.get(i));
			stepCount=0;
		}		
		stepCount++;
		i++;

	}	
		
	
	System.out.println("DEBUG: ScalelistSize: " + scaledList.size());

	return scaledList;

}

/*public static ArrayList<Double> listLinearInterpolator(ArrayList<Double> iList, ArrayList<Double> otherList ){
																											
	double min=0;
	double max=iList.size();
	
	int norm; //(value-min)/(max-min);
	ArrayList<Double> normiList = new ArrayList<Double>(); //list containing all normalised values of iList
	ArrayList<Double> interValList = new ArrayList<Double>();//list containing all interpolated values

	double [] normiXList = new double[iList.size()];
	
	for (int i =0; i <iList.size(); i++){
		normiXList[i]= (i-min)/(max-min);
		System.out.println("DEBUG: normXlist:" + normiXList[i]);

	}
	int sampleRate= 1;
	//(otherList.size()/iList.size());
	
	//get normalised values of all i List values
	/*for (int i =0; i < iList.size(); i++){	
		normiList.add((iList.get(i)-min)/(max-min));
		//System.out.println("DEBUG: normlist:" + normiList.get(i));
		//System.out.println("DEBUG: ilist:" + iList.get(i));					
		//System.out.println("DEBUG: normlist:" + normiList.get(i));

	}
	min=0;
	max=otherList.size();
	for (int i =0; i < iList.size(); i++){
		Double interVal=(max-min)*normiXList[i]+min;
		//interValList.add(iList.get(i));
		interValList.add(interVal);			
			/*if(i%sampleRate==0){
				 System.out.println("DEBUG:adding intermediate value");
				// Double interVal=(max-min)*normiList.get(i)+min;
				 //interValList.add(interVal);
				 System.out.println("DEBUG:adding interVal" + interVal);
			}
	 //System.out.println("DEBUG: interValList: " + interValList.get(i));		
	
		}
	
	System.out.println("DEBUG: ilistSize: " + iList.size());		
	System.out.println("DEBUG: normiSize: " + normiList.size());
	System.out.println("DEBUG: otherListSize: " + otherList.size());
	System.out.println("DEBUG: interValListSize:" + interValList.size());

	return interValList;
	
	
	// max =otherList.size();
	
	//for (int i =0; i < max; i++){
		//interValuesList.add((max-min)*(normiList.get(i)+min));
		//System.out.println("DEBUG: interValuesList:" + interValuesList.get(i));
	//}//
	
	/*ArrayList<Double> interpolatedList = new ArrayList<Double> ();
	LinearInterpolator li = new LinearInterpolator();
	int maxLim = otherList.size();
	int sampleRate = (int) maxLim/iList.size();
	System.out.println("DEBUG: sample Rate: " + sampleRate);
	
	double [] x = new double[iList.size()];
	double [] y = new double[iList.size()];

	for (int i =0; i < iList.size();i++){
		x[i]=i;//each frame
		y[i]=(double)iList.get(i);//each y value at each frame
	}
			
	PolynomialSplineFunction psf = li.interpolate(x, y);
	for(int i =0; i <iList.size()-1;i++ ){
		System.out.println("DEBUG: min :"+iList.get(i));
		System.out.println("DEBUG: max : "+iList.get(i+1));
		min =iList.get(i);
		max = iList.get(i+1);
		
		//System.out.println(" DEBUG: interpolated value:"+ psf.value(iList.get(i)+(iList.get(i+1) - iList.get(i))) );		
		System.out.println(" DEBUG:  value:"+ ((iList.get(i)+ (iList.get(i+1) - iList.get(i)))));		

	}
		//between points depending on sample rate 
		//interpolatedList.add(); //gets each value of the multilist in the iArff and interpolates it using psf	
	
}*/


public static void main (String[] args) throws IOException{
	String folderName="longExercisesSegmented";
	//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
	String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName +"/";
	
	ArffReader.readAllARFFs(INPUTFOLDER);
	
	ArrayList<Arff> npSArffs=ArffReader.getNPSpeedArffs(INPUTFOLDER);	
	System.out.println("DEBUG npsArffs.size() "+ npSArffs.size());

	ArrayList<Arff> npAArffs=ArffReader.getNPAngArffs(INPUTFOLDER);	
	System.out.println("DEBUG npAArffs.size() "+ npAArffs.size());
	
	ArrayList<Arff> xDisArffs=ArffReader.getXDisArffs(INPUTFOLDER);	
	System.out.println("DEBUG xDisArffs.size() "+ xDisArffs.size());
	
	ArrayList<Arff> npJArffs=ArffReader.getNPJerkArffs(INPUTFOLDER);	
	System.out.println("DEBUG npJArffs.size() "+ npJArffs.size());

	ArrayList<Arff> pSArffs=ArffReader.getPSpeedArffs(INPUTFOLDER);	
	System.out.println("DEBUG psArffs.size() "+ pSArffs.size());
	
	ArrayList<Arff> pAArffs=ArffReader.getPAngArffs(INPUTFOLDER);	
	System.out.println("DEBUG pAArffs.size() "+ pAArffs.size());
	
	ArrayList<Arff> yDisArffs=ArffReader.getYDisArffs(INPUTFOLDER);	
	System.out.println("DEBUG yDisArffs.size() "+ yDisArffs.size());
	
	ArrayList<Arff> pJArffs=ArffReader.getPJerkArffs(INPUTFOLDER);	
	System.out.println("DEBUG pJArffs.size() "+ pJArffs.size());
	
	ArrayList<Arff> NPxArffs=ArffReader.getNPXArffs(INPUTFOLDER);	
	System.out.println("DEBUG NPxArffs.size() "+ NPxArffs.size());
	
	ArrayList<Arff> NPyArffs=ArffReader.getNPYArffs(INPUTFOLDER);	
	System.out.println("DEBUG NPyArffs.size() "+ NPyArffs.size());
	
	ArrayList<Arff> PxArffs=ArffReader.getPXArffs(INPUTFOLDER);	
	System.out.println("DEBUG PxArffs.size() "+ PxArffs.size());

	ArrayList<Arff> PyArffs=ArffReader.getPYArffs(INPUTFOLDER);	
	System.out.println("DEBUG PyArffs.size() "+ PyArffs.size());
	
	//ArrayList<Double> iList = listCubicSplineInterpolator(npAArffs.get(0).multilist.get(4),pAArffs.get(0).multilist.get(1));
	//ArrayList<Double> iList = listLinearInterpolator(npAArffs.get(0).multilist.get(4),pAArffs.get(0).multilist.get(1));
	ArrayList<Double> l = listScale(npAArffs.get(0).multilist.get(4), 100);


	//arffListInterpolator(npSArffs,200);
	//Arff intPShoAngArff = new Arff("/example/path", "?", )	
}	
	  
}
