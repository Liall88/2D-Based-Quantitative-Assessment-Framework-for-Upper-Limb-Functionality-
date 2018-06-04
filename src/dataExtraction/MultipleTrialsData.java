/**
 * Author: Liall Arafa
   Imperial College London
   15 May 2018
	
	This code reads multiple .arff files from tests and finds the average 
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
	public static ArrayList<Double> npAng0Avrg = new ArrayList<Double>();
	public static ArrayList<Double> npAng1Avrg = new ArrayList<Double>();
	public static ArrayList<Double> npAng2Avrg = new ArrayList<Double>();
	public static ArrayList<Double> npAng3Avrg = new ArrayList<Double>();
	public static ArrayList<Double> npAng4Avrg = new ArrayList<Double>();
	public static ArrayList<Double> npAng5Avrg = new ArrayList<Double>();
	
	public static ArrayList<Double> pAng0Avrg = new ArrayList<Double>();
	public static ArrayList<Double> pAng1Avrg = new ArrayList<Double>();
	public static ArrayList<Double> pAng2Avrg = new ArrayList<Double>();
	public static ArrayList<Double> pAng3Avrg = new ArrayList<Double>();
	public static ArrayList<Double> pAng4Avrg = new ArrayList<Double>();
	public static ArrayList<Double> pAng5Avrg = new ArrayList<Double>();
	
	
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
	public static ArrayList<Double> disKey0XAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey0YAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey1XAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey1YAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey2XAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey2YAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey3XAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey3YAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey4XAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey4YAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey5XAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey5YAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey6XAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey6YAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey7XAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey7YAvrg= new ArrayList<Double>();
	
	//normalised distances of keypoint from reference trajectory
	public static ArrayList<Double> disKey0NormAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey1NormAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey2NormAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey3NormAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey4NormAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey5NormAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey6NormAvrg= new ArrayList<Double>();
	public static ArrayList<Double> disKey7NormAvrg= new ArrayList<Double>();
	
	
	//speed  of keypoints in terms of pixel per frame between skeletons
	public static ArrayList<Double> npKey0SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey1SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey2SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey3SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey4SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey5SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey6SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey7SpeedAvrg= new ArrayList<Double>();

	public static ArrayList<Double> pKey0SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey1SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey2SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey3SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey4SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey5SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey6SpeedAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey7SpeedAvrg= new ArrayList<Double>();

	//jerk (or smoothness) of keypoints
	public static ArrayList<Double> npKey0JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey1JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey2JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey3JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey4JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey5JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey6JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> npKey7JerkAvrg= new ArrayList<Double>();

	public static ArrayList<Double> pKey0JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey1JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey2JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey3JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey4JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey5JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey6JerkAvrg= new ArrayList<Double>();
	public static ArrayList<Double> pKey7JerkAvrg= new ArrayList<Double>();
	
	//public static void setAverages(ArrayList<Double>  )
	//get data from ARFFReader class
}
