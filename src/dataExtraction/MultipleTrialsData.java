/**
 * Author: Liall Arafa
   Imperial College London
   15 May 2018
	
 */
package dataExtraction;

import java.util.ArrayList;

/**
 * @author la2817
 *
 */
public class MultipleTrialsData {
	//number of trials 
	final int trialNum = OutputTemporalData.numOfTests;
	
	//ArrayLists Of angles of each Skeletons at each frame of both paretic and non-paretic skeletons at each frame
	public static ArrayList<ArrayList<Double>> npAngAvrgList = new ArrayList<ArrayList<Double>>();
	public static ArrayList<ArrayList<Double>> pAngAvrgList = new ArrayList<ArrayList<Double>>();

	
	
	
	//reference trajectory taken from non-paretic arm 
	public static ArrayList<Keypoint> refTrajKey0Avrg= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey1Avrg= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey2Avrg= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey3Avrg= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey4Avrg= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey5Avrg= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey6Avrg= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey7Avrg= new ArrayList<Keypoint>();
	
	//distances of keypoint in terms of x,y coordinates from reference trajectory Keypoints
	public static ArrayList<ArrayList<Double>> disXAvrgList= new ArrayList<ArrayList<Double>>(); ;
	public static ArrayList<ArrayList<Double>> disYAvrgList= new ArrayList<ArrayList<Double>>(); ;


	
	//normalised distances of keypoint from reference trajectory
	public static ArrayList<ArrayList<Double>> disNormAvrgList= new ArrayList<ArrayList<Double>>(); ;


	
	
	//speed  of keypoints in terms of pixel per frame between skeletons
	public static ArrayList<ArrayList<Double>> npSpeedAvrgList= new ArrayList<ArrayList<Double>>(); ;
	public static ArrayList<ArrayList<Double>> pSpeedAvrgList= new ArrayList<ArrayList<Double>>(); ;
	


	//jerk (or smoothness) of keypoints
	public static ArrayList<ArrayList<Double>> npJerkAvrgList= new ArrayList<ArrayList<Double>>(); ;
	public static ArrayList<ArrayList<Double>> pJerkAvrgList= new ArrayList<ArrayList<Double>>(); ;
	

	
	//public static void setAverages(ArrayList<Double>  )
	//get data from ARFFReader class
public static ArrayList<String> getAverageAcrossTrials(){
		
		return new ArrayList<String> ();
	
	}
}
