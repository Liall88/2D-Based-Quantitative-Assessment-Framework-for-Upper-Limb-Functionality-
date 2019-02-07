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
import java.nio.file.Files;
import arff.Arff;
import arff.ArffReader;
import arff.ArffWriter;

/**
 * @author la2817
 *
  // Calculates the averages of the differences (WMFT5-WMFTx)at each frame for angle, speed, jerk over multiple trials and outputs in an arff file for each metric
 */
public class MultipleTrialsData {
	//number of trials 
	final int trialNum = MakeSingleTrialArffs.numOfTests;
	
	
	// private static ArrayList<ArrayList<Double>> allMetricsAvrgList
	
	
	//public static void setAverages(ArrayList<Double>  )
	//get data from ARFFReader class


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


//must send scaled lists of same size to this method
public static ArrayList<Double> averageSingleList(ArrayList<ArrayList<Double>> scaledTrials){
	System.out.println("DEBUG: scaledTrials size" +scaledTrials.get(0).size());

	int scaledSize = scaledTrials.get(0).size();
	ArrayList<Double> avrglist = new ArrayList<Double>();
	Double sum=0.0;
	for(int i =0; i<scaledSize;i++){//length of trial		
		sum = 0.0;
		for(int j=0;j<scaledTrials.size();j++){//number of trials
		 Double val = scaledTrials.get(j).get(i); 
		// System.out.println("DEBUG val : " + val);

		 sum+=val;
		}	
		//System.out.println("DEBUG: avrgVal: " + sum/scaledTrials.size());
		avrglist.add(sum/scaledTrials.size()); //sum of each line in the file 
	
	}	
	return avrglist;
}

public static ArrayList<Double> findAverage(ArrayList<Double> test1,ArrayList<Double> test2 ){

	ArrayList<Double> avrglist = new ArrayList<Double>();
	Double sum=0.0;
	for(int i =0; i<test1.size();i++){//length of trial		
		
		avrglist.add((test1.get(i)+test2.get(i))/2); //sum of each line in the file 
	
	}	
	return avrglist;
}

	
public static ArrayList<Double> getDiff (ArrayList<Double> npList, ArrayList<Double> pList ){
	ArrayList<Double> distList = new ArrayList<Double>();
	for(int i =0; i<npList.size(); i++){
		distList.add((Math.abs(npList.get(i)- pList.get(i))));
		//System.out.println("debug diff value:" + distList.get(i));
		}
	
	return distList;
}


public static void main (String[] args) throws IOException{
	String folderName="simulatedExercises/ActualCOPY";
	
	 String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/trainingData/"+folderName +"/";
	 //FILEEXT=".csv";
	// FILEEXT=".arff";
	//String folderName="Actual";
	//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
	//String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/trainingData/simulatedExercises/"+folderName+"/";
	//String INPUTFOLDER="/homes/la2817/Desktop/Outputs/arff_Outputs/trainingData/realExercisesArff/earlyExperiment/";
	////String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/trainingData/longExercises/";
	String OUTPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/trainingData/simulatedExercises/ActualDiff/";
	
	//Lists made up of averages from all trials of a single test 
	
	//read all Arffs in a folder
	/*Path path = ...;

	if (Files.exists(path)) {
	    // ...
	}*/
	ArffReader.readAllARFFs(INPUTFOLDER);
	//Each ArrayList<Arff> each arraylist holds an arff representing the metric file of a single trial

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
	
	int numOfArffs= PyArffs.size();
	
	
	//get the differences between np and p for each test, and store with arraylists for keypoints 0-7
	/*ArrayList<ArrayList<Double>> diffSpeedTest0 = new ArrayList<ArrayList<Double>>();
	ArrayList<ArrayList<Double>> diffSpeedTest1 = new ArrayList<ArrayList<Double>>();
	
	ArrayList<ArrayList<Double>> diffAngTest0 = new ArrayList<ArrayList<Double>>();
	ArrayList<ArrayList<Double>> diffAngTest1 = new ArrayList<ArrayList<Double>>();
	
	ArrayList<ArrayList<Double>> diffJerkTest0 = new ArrayList<ArrayList<Double>>();
	ArrayList<ArrayList<Double>> diffJerkTest1 = new ArrayList<ArrayList<Double>>();

	//get difference in speed keypoint 1
	diffSpeedTest0.add(getDiff(npSArffs.get(0).multilist.get(0),pSArffs.get(0).multilist.get(0)));
	diffSpeedTest0.add(getDiff(npSArffs.get(0).multilist.get(1),pSArffs.get(0).multilist.get(1)));
	diffSpeedTest0.add(getDiff(npSArffs.get(0).multilist.get(2),pSArffs.get(0).multilist.get(2)));
	diffSpeedTest0.add(getDiff(npSArffs.get(0).multilist.get(3),pSArffs.get(0).multilist.get(3)));
	diffSpeedTest0.add(getDiff(npSArffs.get(0).multilist.get(4),pSArffs.get(0).multilist.get(4)));
	diffSpeedTest0.add(getDiff(npSArffs.get(0).multilist.get(5),pSArffs.get(0).multilist.get(5)));
	diffSpeedTest0.add(getDiff(npSArffs.get(0).multilist.get(6),pSArffs.get(0).multilist.get(6)));
	diffSpeedTest0.add(getDiff(npSArffs.get(0).multilist.get(7),pSArffs.get(0).multilist.get(7)));
	
	diffAngTest0.add(getDiff(npAArffs.get(0).multilist.get(0),pAArffs.get(0).multilist.get(0)));
	diffAngTest0.add(getDiff(npAArffs.get(0).multilist.get(1),pAArffs.get(0).multilist.get(1)));
	diffAngTest0.add(getDiff(npAArffs.get(0).multilist.get(2),pAArffs.get(0).multilist.get(2)));
	diffAngTest0.add(getDiff(npAArffs.get(0).multilist.get(3),pAArffs.get(0).multilist.get(3)));
	diffAngTest0.add(getDiff(npAArffs.get(0).multilist.get(4),pAArffs.get(0).multilist.get(4)));
	diffAngTest0.add(getDiff(npAArffs.get(0).multilist.get(5),pAArffs.get(0).multilist.get(5)));
	
	
	diffJerkTest0.add(getDiff(npJArffs.get(0).multilist.get(0),pJArffs.get(0).multilist.get(0)));
	diffJerkTest0.add(getDiff(npJArffs.get(0).multilist.get(1),pJArffs.get(0).multilist.get(1)));
	diffJerkTest0.add(getDiff(npJArffs.get(0).multilist.get(2),pJArffs.get(0).multilist.get(2)));
	diffJerkTest0.add(getDiff(npJArffs.get(0).multilist.get(3),pJArffs.get(0).multilist.get(3)));
	diffJerkTest0.add(getDiff(npJArffs.get(0).multilist.get(4),pJArffs.get(0).multilist.get(4)));
	diffJerkTest0.add(getDiff(npJArffs.get(0).multilist.get(5),pJArffs.get(0).multilist.get(5)));
	diffJerkTest0.add(getDiff(npJArffs.get(0).multilist.get(6),pJArffs.get(0).multilist.get(6)));
	diffJerkTest0.add(getDiff(npJArffs.get(0).multilist.get(7),pJArffs.get(0).multilist.get(7)));
	
	diffSpeedTest1.add(getDiff(npSArffs.get(1).multilist.get(0),pSArffs.get(1).multilist.get(0)));
	diffSpeedTest1.add(getDiff(npSArffs.get(1).multilist.get(1),pSArffs.get(1).multilist.get(1)));
	diffSpeedTest1.add(getDiff(npSArffs.get(1).multilist.get(2),pSArffs.get(1).multilist.get(2)));
	diffSpeedTest1.add(getDiff(npSArffs.get(1).multilist.get(3),pSArffs.get(1).multilist.get(3)));
	diffSpeedTest1.add(getDiff(npSArffs.get(1).multilist.get(4),pSArffs.get(1).multilist.get(4)));
	diffSpeedTest1.add(getDiff(npSArffs.get(1).multilist.get(5),pSArffs.get(1).multilist.get(5)));
	diffSpeedTest1.add(getDiff(npSArffs.get(1).multilist.get(6),pSArffs.get(1).multilist.get(6)));
	diffSpeedTest1.add(getDiff(npSArffs.get(1).multilist.get(7),pSArffs.get(1).multilist.get(7)));
	
	diffAngTest1.add(getDiff(npAArffs.get(1).multilist.get(0),pAArffs.get(1).multilist.get(0)));
	diffAngTest1.add(getDiff(npAArffs.get(1).multilist.get(1),pAArffs.get(1).multilist.get(1)));
	diffAngTest1.add(getDiff(npAArffs.get(1).multilist.get(2),pAArffs.get(1).multilist.get(2)));
	diffAngTest1.add(getDiff(npAArffs.get(1).multilist.get(3),pAArffs.get(1).multilist.get(3)));
	diffAngTest1.add(getDiff(npAArffs.get(1).multilist.get(4),pAArffs.get(1).multilist.get(4)));
	diffAngTest1.add(getDiff(npAArffs.get(1).multilist.get(5),pAArffs.get(1).multilist.get(5)));
	
	diffJerkTest1.add(getDiff(npJArffs.get(1).multilist.get(0),pJArffs.get(1).multilist.get(0)));
	diffJerkTest1.add(getDiff(npJArffs.get(1).multilist.get(1),pJArffs.get(1).multilist.get(1)));
	diffJerkTest1.add(getDiff(npJArffs.get(1).multilist.get(2),pJArffs.get(1).multilist.get(2)));
	diffJerkTest1.add(getDiff(npJArffs.get(1).multilist.get(3),pJArffs.get(1).multilist.get(3)));
	diffJerkTest1.add(getDiff(npJArffs.get(1).multilist.get(4),pJArffs.get(1).multilist.get(4)));
	diffJerkTest1.add(getDiff(npJArffs.get(1).multilist.get(5),pJArffs.get(1).multilist.get(5)));
	diffJerkTest1.add(getDiff(npJArffs.get(1).multilist.get(6),pJArffs.get(1).multilist.get(6)));
	diffJerkTest1.add(getDiff(npJArffs.get(1).multilist.get(7),pJArffs.get(1).multilist.get(7)));
	
	
	String OUTPUTTRIAL0 = OUTPUTFOLDER+"/difftest0.arff";
	String OUTPUTTRIAL1 = OUTPUTFOLDER+"/difftest1.arff";

	
	//make arffs and write arffs
	Arff difftest0 = new Arff(OUTPUTFOLDER,0,diffSpeedTest0,diffAngTest0,diffJerkTest0);
	Arff difftest1 = new Arff(OUTPUTFOLDER,0,diffSpeedTest1,diffAngTest1,diffJerkTest1);
	
	ArffWriter.writeDiffArff(OUTPUTFOLDER, difftest0, "difftest0");
	ArffWriter.writeDiffArff(OUTPUTFOLDER, difftest1, "difftest1");
	*/

	
	//write into Arff
	
	/*/for every arff
	//for each keypoint in each arff multilist
	//avverage together
	multiAvrgList.add(npSArffs.get(0).multilist.get(0));
	multiAvrgList.add(npSArffs.get(1).multilist.get(0));
	keyAvrgList= averageSingleList(multiAvrgList);
	
	
	
	//for(int i =0; i <npSArffs.size();i++){
		//for(int j=0;j<npSArffs.get(0).multilist.size();j++){
				//	keyAvrgList.add(averageSingleList(npSArffs.get(i).multilist));

		//}
		//System.out.println("npSpeedAvrgList");
		//System.out.println(npSpeedAvrgList.size());
		//System.out.println(npSpeedAvrgList.get(i));	
	//}
	for(int i =0; i <npSArffs.size();i++){
		pSpeedAvrgList.add(averageSingleList(pSArffs.get(i).multilist));
		//System.out.println("pSpeedAvrgList");
		System.out.println(pSpeedAvrgList.size());
		System.out.println(pSpeedAvrgList.get(i));	
	}
	for(int i =0; i <npAArffs.size();i++){
		npAngAvrgList.add(averageSingleList(npAArffs.get(i).multilist));
		//System.out.println("npAngAvrgList");
		System.out.println(npAngAvrgList.size());
		System.out.println(npAngAvrgList.get(i));	
	}
	for(int i =0; i <npAArffs.size();i++){
		pAngAvrgList.add(averageSingleList(pAArffs.get(i).multilist));
		//System.out.println("pAngAvrgList");
		System.out.println(pAngAvrgList.size());
		System.out.println(pAngAvrgList.get(i));	
	}
	for(int i =0; i <npJArffs.size();i++){
		npJerkAvrgList.add(averageSingleList(npJArffs.get(i).multilist));
		//System.out.println("npJerkAvrgList");
		System.out.println(npJerkAvrgList.size());
		System.out.println(npJerkAvrgList.get(i));	
	}
	for(int i =0; i <npJArffs.size();i++){
		pJerkAvrgList.add(averageSingleList(pJArffs.get(i).multilist));
		//System.out.println("pJerkAvrgList");
		System.out.println(pJerkAvrgList.size());

		 System.out.println(pJerkAvrgList.get(i));	
	}
	
	
	
	
		//ArrayLists Of angles of each Skeletons at each frame of both paretic and non-paretic skeletons at each frame
	ArrayList<ArrayList<Double>> npAngAvrgList = new ArrayList<ArrayList<Double>>();
	ArrayList<ArrayList<Double>> pAngAvrgList = new ArrayList<ArrayList<Double>>();

	//distances of keypoint in terms of x,y coordinates from reference trajectory Keypoints
	ArrayList<ArrayList<Double>> disXAvrgList= new ArrayList<ArrayList<Double>>(); ;
	ArrayList<ArrayList<Double>> disYAvrgList= new ArrayList<ArrayList<Double>>(); ;

	//normalised distances of keypoint from reference trajectory
	ArrayList<ArrayList<Double>> disNormAvrgList= new ArrayList<ArrayList<Double>>(); ;
	
	//speed  of keypoints in terms of pixel per frame between skeletons
	ArrayList<ArrayList<Double>> npSpeedAvrgList= new ArrayList<ArrayList<Double>>(); ;
	ArrayList<ArrayList<Double>> pSpeedAvrgList= new ArrayList<ArrayList<Double>>(); ;

	//jerk (or smoothness) of keypoints
	ArrayList<ArrayList<Double>> npJerkAvrgList= new ArrayList<ArrayList<Double>>(); ;
	ArrayList<ArrayList<Double>> pJerkAvrgList= new ArrayList<ArrayList<Double>>(); ;
	
	
	
	diffTest0.add(getDiff(npAngAvrgList.get(0),pAngAvrgList.get(0)));
	diffTest0.add(getDiff(npJerkAvrgList.get(0),pJerkAvrgList.get(0)));

	diffTest1.add(getDiff(npSpeedAvrgList.get(1),pSpeedAvrgList.get(1)));
	diffTest1.add(getDiff(npAngAvrgList.get(1),pAngAvrgList.get(1)));
	diffTest1.add(getDiff(npJerkAvrgList.get(1),pJerkAvrgList.get(1)));
		
	*/
	
}	
	  
}
