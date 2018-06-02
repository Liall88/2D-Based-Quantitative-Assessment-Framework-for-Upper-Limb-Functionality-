package dataExtraction;
/**
 * Author: Liall Arafa
   Imperial College London
   24 Apr 2018
	
 */

/**
 * @author la2817
 * 
 * Project: Training a neural network in order to correctly identify motor function in the upper limbs from a 2D camera
 * 
 * This class is used to calculate metrics for use of quantification of upper limb motor functionality analysis
 * It will be done by first analysing the non-paretic arm as a standard, and comparing the paretic arm to it
 * The skeletons of the non-paretic trial are stored in npSkeletonList, while the skeletons of the paretic trial are stored in the pSkeletonList
 * 
 * metrics aer calculated per frame rather than per second 
 * 
 *Metrics:
 *trajectory error - is a measure of spatial deviation of the wrist trajectory from the reference trajectory
 *velocity profile deviation - measure of the deviation of the speed profile from the reference speed profile, calculated from the non-paretic arm
 *jerkiness-( or smoothness) is a measure of the variations in the velocity profile. An 'efficient' reach movement should have a smooth velocity profile with an 
 *accelerating pattern followed by a decelerating pattern without any jerks
 *
 *TODO: change arrays into arraylists (if decide to cap number of frames being viewed
 *TODO: add confidence scores for each metric in order to weight higher confidence scores more
 *TODO: add optimisation weights and thresholds
 *TODO: segmentation in the X-Y plane possible? 
 *TODO: save to ARF File for Weka classes

 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;

public class OutputTemporalData {
	
	//change these parameters for each trial	
	public static final int trial=1; 	
	final static String OUTPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/symposiumCupExerciseTests/test" + trial +"Metrics"+"/";
	final static String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/openPose_outputs/testData/symposiumCupExercise/trial" +trial + "/";
	public static final boolean pIsRight=true; //Paretic limb position, change for each trial

		
	
	public static int numOfTests;//number of repetitions for each trial
	

	//where temporal analysis arff files will be stored
	final static String SPEEDFILE= OUTPUTFOLDER+ "speeds" +".arff";
	final static String JERKFILE = OUTPUTFOLDER + "jerkiness" +".arff";
	final static String DISFILE = OUTPUTFOLDER + "disFromRef" +".arff";
	final static String ANGFILE= OUTPUTFOLDER+ "angles" +".arff";

	static final double deltaFrame= 1;
	//static boolean pArmIsR;//checks if patients paretic arm is their R arm
	
	//Optimal Weights and Thresholds (for neural networks)
	//(Based on Paper: Component - Level tuning of Kinematic Features from Composite Therapists of Movement Quality)
	Double t1=0.13,t2=0.2, t3=0.1, t4=2.5, t5=0.99;
	Double w1=2.5,w2=2.5, w3=1.8, w4=0.05, w5=0.05;
	
	//JSON paths of paretic and Non-paretic JSON
	public static ArrayList<String> nonparPaths = new ArrayList<String>();
	public static ArrayList<String> parPaths = new ArrayList<String>();
	
	//Arraylists Of paretic and Non-paretic Skeletons at each frame
	public static ArrayList<Skeleton> npSkeletonList = new ArrayList<Skeleton>();//trial1:left arm
	public static ArrayList<Skeleton> pSkeletonList = new ArrayList<Skeleton>();//trial1: right arm
	
	//Arraylists Of angles of each Skeletons at each frame of both paretic and non-paretic skeletons at each frame
	public static ArrayList<Double> npAng0List = new ArrayList<Double>();
	public static ArrayList<Double> npAng1List = new ArrayList<Double>();
	public static ArrayList<Double> npAng2List = new ArrayList<Double>();
	public static ArrayList<Double> npAng3List = new ArrayList<Double>();
	public static ArrayList<Double> npAng4List = new ArrayList<Double>();
	public static ArrayList<Double> npAng5List = new ArrayList<Double>();
	
	public static ArrayList<Double> pAng0List = new ArrayList<Double>();
	public static ArrayList<Double> pAng1List = new ArrayList<Double>();
	public static ArrayList<Double> pAng2List = new ArrayList<Double>();
	public static ArrayList<Double> pAng3List = new ArrayList<Double>();
	public static ArrayList<Double> pAng4List = new ArrayList<Double>();
	public static ArrayList<Double> pAng5List = new ArrayList<Double>();
	
	
	//reference trajectory taken from non-paretic arm 
	public static ArrayList<Keypoint> refTrajKey0= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey1= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey2= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey3= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey4= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey5= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey6= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey7= new ArrayList<Keypoint>();
	
	//distances of keypoint in terms of x,y coordinates from reference trajectory Keypoints
	public static ArrayList<Double> disKey0X= new ArrayList<Double>();
	public static ArrayList<Double> disKey0Y= new ArrayList<Double>();
	public static ArrayList<Double> disKey1X= new ArrayList<Double>();
	public static ArrayList<Double> disKey1Y= new ArrayList<Double>();
	public static ArrayList<Double> disKey2X= new ArrayList<Double>();
	public static ArrayList<Double> disKey2Y= new ArrayList<Double>();
	public static ArrayList<Double> disKey3X= new ArrayList<Double>();
	public static ArrayList<Double> disKey3Y= new ArrayList<Double>();
	public static ArrayList<Double> disKey4X= new ArrayList<Double>();
	public static ArrayList<Double> disKey4Y= new ArrayList<Double>();
	public static ArrayList<Double> disKey5X= new ArrayList<Double>();
	public static ArrayList<Double> disKey5Y= new ArrayList<Double>();
	public static ArrayList<Double> disKey6X= new ArrayList<Double>();
	public static ArrayList<Double> disKey6Y= new ArrayList<Double>();
	public static ArrayList<Double> disKey7X= new ArrayList<Double>();
	public static ArrayList<Double> disKey7Y= new ArrayList<Double>();
	
	//normalised distances of keypoint from reference trajectory
	public static ArrayList<Double> disKey0Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey1Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey2Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey3Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey4Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey5Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey6Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey7Norm= new ArrayList<Double>();
	
	
	//speed  of keypoints in terms of pixel per frame between skeletons
	public static ArrayList<Double> npKey0SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> npKey1SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> npKey2SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> npKey3SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> npKey4SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> npKey5SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> npKey6SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> npKey7SpeedList= new ArrayList<Double>();

	public static ArrayList<Double> pKey0SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> pKey1SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> pKey2SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> pKey3SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> pKey4SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> pKey5SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> pKey6SpeedList= new ArrayList<Double>();
	public static ArrayList<Double> pKey7SpeedList= new ArrayList<Double>();

	//jerk (or smoothness) of keypoints
	public static ArrayList<Double> npKey0JerkList= new ArrayList<Double>();
	public static ArrayList<Double> npKey1JerkList= new ArrayList<Double>();
	public static ArrayList<Double> npKey2JerkList= new ArrayList<Double>();
	public static ArrayList<Double> npKey3JerkList= new ArrayList<Double>();
	public static ArrayList<Double> npKey4JerkList= new ArrayList<Double>();
	public static ArrayList<Double> npKey5JerkList= new ArrayList<Double>();
	public static ArrayList<Double> npKey6JerkList= new ArrayList<Double>();
	public static ArrayList<Double> npKey7JerkList= new ArrayList<Double>();

	public static ArrayList<Double> pKey0JerkList= new ArrayList<Double>();
	public static ArrayList<Double> pKey1JerkList= new ArrayList<Double>();
	public static ArrayList<Double> pKey2JerkList= new ArrayList<Double>();
	public static ArrayList<Double> pKey3JerkList= new ArrayList<Double>();
	public static ArrayList<Double> pKey4JerkList= new ArrayList<Double>();
	public static ArrayList<Double> pKey5JerkList= new ArrayList<Double>();
	public static ArrayList<Double> pKey6JerkList= new ArrayList<Double>();
	public static ArrayList<Double> pKey7JerkList= new ArrayList<Double>();
	//theta of keypoints per frame between skeletons
	
	/*static ArrayList<Double> npKey0thetaList= new ArrayList<Double>();
	static ArrayList<Double> npKey1thetaList= new ArrayList<Double>();
	static ArrayList<Double> npKey2thetaList= new ArrayList<Double>();
	static ArrayList<Double> npKey3thetaList= new ArrayList<Double>();
	static ArrayList<Double> npKey4thetaList= new ArrayList<Double>();
	static ArrayList<Double> npKey5thetaList= new ArrayList<Double>();
	static ArrayList<Double> npKey6thetaList= new ArrayList<Double>();
	static ArrayList<Double> npKey7thetaList= new ArrayList<Double>();

	static ArrayList<Double> pKey0thetaList= new ArrayList<Double>();
	static ArrayList<Double> pKey1thetaList= new ArrayList<Double>();
	static ArrayList<Double> pKey2thetaList= new ArrayList<Double>();
	static ArrayList<Double> pKey3thetaList= new ArrayList<Double>();
	static ArrayList<Double> pKey4thetaList= new ArrayList<Double>();
	static ArrayList<Double> pKey5thetaList= new ArrayList<Double>();
	static ArrayList<Double> pKey6thetaList= new ArrayList<Double>();
	static ArrayList<Double> pKey7thetaList= new ArrayList<Double>();
	
	*/
	
	//Must be able to look through multiple frames in order to calculate metrics
	//check if directory and loop through each file 
	public static void setPathArray(File[] files,ArrayList<String> strList) {
	    for (File file : files) {
	        if (file.isDirectory()) {
	           // System.out.println("Directory: " + file.getName());
	            setPathArray(file.listFiles(),strList); // Calls same method again.
	            
	        } else {	
	        	strList.add(file.getPath());
	            //System.out.println(file.getPath());
	        }
	    }
	}
	

	
	public static void setSkeletonList(ArrayList<String> strList, ArrayList<Skeleton> skList) throws IOException, ParseException{
		for (int i =0; i <strList.size(); i++){
			 //System.out.println( " skeletal strList : " +strList.get(i));
			 Skeleton Sk = new Skeleton(strList.get(i));
			 skList.add(Sk);
		}
	}
	public static void setAnglesList(ArrayList<Skeleton> skList, ArrayList<Double> angKey0List, ArrayList<Double> angKey1List, ArrayList<Double> angKey2List, ArrayList<Double> angKey3List,
			ArrayList<Double> angKey4List, ArrayList<Double> angKey5List) throws IOException, ParseException{
		for (int i =0; i <skList.size(); i++){
			angKey0List.add(skList.get(i).ang0);
			angKey1List.add(skList.get(i).ang1);
			angKey2List.add(skList.get(i).ang2);
			angKey3List.add(skList.get(i).ang3);
			angKey4List.add(skList.get(i).ang4);
			angKey5List.add(skList.get(i).ang5);			 
		}
	}
	
	public static void setReferenceTrajectory (ArrayList <Skeleton> skList){
		Keypoint refKey;
		for(int i= 0; i <skList.size();i++){
			refKey = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key0.x, skList.get(i).key0.y);
			refTrajKey0.add(refKey);
			refKey = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key1.x, skList.get(i).key1.y);
			refTrajKey1.add(refKey);
			refKey = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key2.x, skList.get(i).key2.y);
			refTrajKey2.add(refKey);
			refKey = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key3.x, skList.get(i).key3.y);
			refTrajKey3.add(refKey);
			refKey = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key4.x, skList.get(i).key4.y);
			refTrajKey4.add(refKey);
			refKey = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key5.x, skList.get(i).key5.y);
			refTrajKey5.add(refKey);
			refKey = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key6.x, skList.get(i).key6.y);
			refTrajKey6.add(refKey);
			refKey = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key7.x, skList.get(i).key7.y);
			refTrajKey7.add(refKey);
		}

		}
	
	public static Keypoint calculateReferenceTrajectory (Keypoint key1, double keyNx, double keyNy){
		Keypoint k1; //uses keypoint 1 as reference point as the neck does not move much
		//get displacement in x and y  of each Keypoint from Key1 as reference
		double dispFromKey1X, dispFromKey1Y, refX, refY;
			k1 = key1;
			dispFromKey1X = key1.x - keyNx;
			dispFromKey1Y=  key1.y - keyNy; 
			refX = k1.x +dispFromKey1X; 
			refY = k1.y +dispFromKey1Y;
			Keypoint refKey = new Keypoint (refX,refY);
			return refKey;
	}
	
	public static void setDisFromTraj(ArrayList<Keypoint> refTrajKey0, ArrayList<Keypoint> refTrajKey1, ArrayList<Keypoint> refTrajKey2, ArrayList<Keypoint> refTrajKey3,
			ArrayList<Keypoint> refTrajKey4, ArrayList<Keypoint> refTrajKey5, ArrayList<Keypoint> refTrajKey6, ArrayList<Keypoint> refTrajKey7, ArrayList<Skeleton> pSkelList){
		//get displacement in x and y  of each Keypoint from reference trajectory key
		double disRefX, disRefY;
		for(int i =0; i <refTrajKey0.size();i++){
			disRefX = refTrajKey0.get(i).x - pSkelList.get(i).key0.x;
			disRefY = refTrajKey0.get(i).y - pSkelList.get(i).key0.y;
			disKey0X.add(disRefX);
			disKey0Y.add(disRefY);
			disRefX = refTrajKey1.get(i).x - pSkelList.get(i).key1.x;
			disRefY = refTrajKey1.get(i).y - pSkelList.get(i).key1.y;
			disKey1X.add(disRefX);
			disKey1Y.add(disRefY);
			disRefX = refTrajKey2.get(i).x - pSkelList.get(i).key2.x;
			disRefY = refTrajKey2.get(i).y - pSkelList.get(i).key2.y;
			disKey2X.add(disRefX);
			disKey2Y.add(disRefY);
			disRefX = refTrajKey3.get(i).x - pSkelList.get(i).key3.x;
			disRefY = refTrajKey3.get(i).y - pSkelList.get(i).key3.y;
			disKey3X.add(disRefX);
			disKey3Y.add(disRefY);
			disRefX = refTrajKey4.get(i).x - pSkelList.get(i).key4.x;
			disRefY = refTrajKey4.get(i).y - pSkelList.get(i).key4.y;
			disKey4X.add(disRefX);
			disKey4Y.add(disRefY);
			disRefX = refTrajKey5.get(i).x - pSkelList.get(i).key5.x;
			disRefY = refTrajKey5.get(i).y - pSkelList.get(i).key5.y;
			disKey5X.add(disRefX);
			disKey5Y.add(disRefY);
			disRefX = refTrajKey6.get(i).x - pSkelList.get(i).key6.x;
			disRefY = refTrajKey6.get(i).y - pSkelList.get(i).key6.y;
			disKey6X.add(disRefX);
			disKey6Y.add(disRefY);
			disRefX = refTrajKey7.get(i).x - pSkelList.get(i).key7.x;
			disRefY = refTrajKey7.get(i).y - pSkelList.get(i).key7.y;
			disKey7X.add(disRefX);
			disKey7Y.add(disRefY);

		}
			
	}
	
	public static void setNormDistances(){
		Vec temp;
		double dist;
		for(int i =0; i< disKey0X.size();i++){
			temp=new Vec(disKey0X.get(i),disKey0Y.get(i));
			dist=Vec.normalise(temp);
			disKey0Norm.add(dist);
			temp=new Vec(disKey1X.get(i),disKey1Y.get(i));
			dist=Vec.normalise(temp);
			disKey1Norm.add(dist);
			temp=new Vec(disKey2X.get(i),disKey2Y.get(i));
			dist=Vec.normalise(temp);
			disKey2Norm.add(dist);
			temp=new Vec(disKey3X.get(i),disKey3Y.get(i));
			dist=Vec.normalise(temp);
			disKey3Norm.add(dist);
			temp=new Vec(disKey4X.get(i),disKey4Y.get(i));
			dist=Vec.normalise(temp);
			disKey4Norm.add(dist);
			temp=new Vec(disKey5X.get(i),disKey5Y.get(i));
			dist=Vec.normalise(temp);
			disKey5Norm.add(dist);
			temp=new Vec(disKey6X.get(i),disKey6Y.get(i));
			dist=Vec.normalise(temp);
			disKey6Norm.add(dist);
			temp=new Vec(disKey7X.get(i),disKey7Y.get(i));
			dist=Vec.normalise(temp);
			disKey7Norm.add(dist);

		}
		
		
	}

	
	//Calculates the velocity Vector between two keypoints between each frame
	//NOTE that since must calculate velocity vector from frame n and frame n+1, you lose 1 value
	public static void setKeypointSpeeds(ArrayList <Skeleton> skList, ArrayList <Double> k0List,ArrayList <Double> k1List,ArrayList <Double> k2List,
			ArrayList <Double> k3List, ArrayList <Double> k4List,ArrayList <Double> k5List,ArrayList <Double> k6List,ArrayList <Double> k7List) {
		double s;
		int f0,f1;
		for (int k = 0; k < skList.size()-1; k++){
			f0=k;f1=k+1;
			s= calculateSpeed(skList.get(k).key0.x,skList.get(k).key0.y,skList.get(k+1).key0.x,skList.get(k+1).key0.y,f0,f1 );
			k0List.add(s);
			s= calculateSpeed(skList.get(k).key1.x,skList.get(k).key1.y,skList.get(k+1).key1.x,skList.get(k+1).key1.y,f0,f1 );
			k1List.add(s);
			s= calculateSpeed(skList.get(k).key2.x,skList.get(k).key2.y,skList.get(k+1).key2.x,skList.get(k+1).key2.y,f0,f1 );
			k2List.add(s);
			s= calculateSpeed(skList.get(k).key3.x,skList.get(k).key3.y,skList.get(k+1).key3.x,skList.get(k+1).key3.y,f0,f1 );
			k3List.add(s);
			s= calculateSpeed(skList.get(k).key4.x,skList.get(k).key4.y,skList.get(k+1).key4.x,skList.get(k+1).key4.y,f0,f1 );
			k4List.add(s);
			s= calculateSpeed(skList.get(k).key5.x,skList.get(k).key5.y,skList.get(k+1).key5.x,skList.get(k+1).key5.y ,f0,f1);
			k5List.add(s);
			s= calculateSpeed(skList.get(k).key6.x,skList.get(k).key6.y,skList.get(k+1).key6.x,skList.get(k+1).key6.y ,f0,f1);
			k6List.add(s);
			s= calculateSpeed(skList.get(k).key7.x,skList.get(k).key7.y,skList.get(k+1).key7.x,skList.get(k+1).key7.y ,f0,f1);
			k7List.add(s);
		}
		
	}
		
	
	public static void setJerk(ArrayList <Skeleton> skList, ArrayList <Double> k0List,ArrayList <Double> k1List,ArrayList <Double> k2List,
			ArrayList <Double> k3List, ArrayList <Double> k4List,ArrayList <Double> k5List,ArrayList <Double> k6List,ArrayList <Double> k7List) {
		int f0,f1;
		Vec vTemp0, vTemp1, vTemp2, accTemp0, accTemp1;
		for (int k = 0; k < skList.size()-3; k++){
			f0=k;f1=k+1;
			vTemp0 = getVelocityVec(skList.get(k).key0.x,skList.get(k).key0.y,skList.get(k+1).key0.x,skList.get(k+1).key0.y,f0,f1);
			vTemp1 = getVelocityVec(skList.get(k+1).key0.x,skList.get(k+1).key0.y,skList.get(k+2).key0.x,skList.get(k+2).key0.y,f0,f1);
			vTemp2 = getVelocityVec(skList.get(k+2).key0.x,skList.get(k+2).key0.y,skList.get(k+3).key0.x,skList.get(k+3).key0.y,f0,f1);
			accTemp0 = getAcceleration(vTemp0, vTemp1, f0, f1);
			accTemp1 = getAcceleration(vTemp1, vTemp2, f0, f1);
			k0List.add(getJerkiness(accTemp0, accTemp1, f0,f1));
			vTemp0 = getVelocityVec(skList.get(k).key1.x,skList.get(k).key1.y,skList.get(k+1).key1.x,skList.get(k+1).key1.y,f0,f1);
			vTemp1 = getVelocityVec(skList.get(k+1).key1.x,skList.get(k+1).key1.y,skList.get(k+2).key1.x,skList.get(k+2).key1.y,f0,f1);
			vTemp2 = getVelocityVec(skList.get(k+2).key1.x,skList.get(k+2).key1.y,skList.get(k+3).key1.x,skList.get(k+3).key1.y,f0,f1);
			accTemp0 = getAcceleration(vTemp0, vTemp1, f0, f1);
			accTemp1 = getAcceleration(vTemp1, vTemp2, f0, f1);
			k1List.add(getJerkiness(accTemp0, accTemp1, f0,f1));
			vTemp0 = getVelocityVec(skList.get(k).key2.x,skList.get(k).key2.y,skList.get(k+1).key2.x,skList.get(k+1).key2.y,f0,f1);
			vTemp1 = getVelocityVec(skList.get(k+1).key2.x,skList.get(k+1).key2.y,skList.get(k+2).key2.x,skList.get(k+2).key2.y,f0,f1);
			vTemp2 = getVelocityVec(skList.get(k+2).key2.x,skList.get(k+2).key2.y,skList.get(k+3).key2.x,skList.get(k+3).key2.y,f0,f1);
			accTemp0 = getAcceleration(vTemp0, vTemp1, f0, f1);
			accTemp1 = getAcceleration(vTemp1, vTemp2, f0, f1);
			k2List.add(getJerkiness(accTemp0, accTemp1, f0,f1));
			vTemp0 = getVelocityVec(skList.get(k).key3.x,skList.get(k).key3.y,skList.get(k+1).key3.x,skList.get(k+1).key3.y,f0,f1);
			vTemp1 = getVelocityVec(skList.get(k+1).key3.x,skList.get(k+1).key3.y,skList.get(k+2).key3.x,skList.get(k+2).key3.y,f0,f1);
			vTemp2 = getVelocityVec(skList.get(k+2).key3.x,skList.get(k+2).key3.y,skList.get(k+3).key3.x,skList.get(k+3).key3.y,f0,f1);
			accTemp0 = getAcceleration(vTemp0, vTemp1, f0, f1);
			accTemp1 = getAcceleration(vTemp1, vTemp2, f0, f1);
			k3List.add(getJerkiness(accTemp0, accTemp1, f0,f1));
			vTemp0 = getVelocityVec(skList.get(k).key4.x,skList.get(k).key4.y,skList.get(k+1).key4.x,skList.get(k+1).key4.y,f0,f1);
			vTemp1 = getVelocityVec(skList.get(k+1).key4.x,skList.get(k+1).key4.y,skList.get(k+2).key4.x,skList.get(k+2).key4.y,f0,f1);
			vTemp2 = getVelocityVec(skList.get(k+2).key4.x,skList.get(k+2).key4.y,skList.get(k+3).key4.x,skList.get(k+3).key4.y,f0,f1);
			accTemp0 = getAcceleration(vTemp0, vTemp1, f0, f1);
			accTemp1 = getAcceleration(vTemp1, vTemp2, f0, f1);
			k4List.add(getJerkiness(accTemp0, accTemp1, f0,f1));
			vTemp0 = getVelocityVec(skList.get(k).key5.x,skList.get(k).key5.y,skList.get(k+1).key5.x,skList.get(k+1).key5.y,f0,f1);
			vTemp1 = getVelocityVec(skList.get(k+1).key5.x,skList.get(k+1).key5.y,skList.get(k+2).key5.x,skList.get(k+2).key5.y,f0,f1);
			vTemp2 = getVelocityVec(skList.get(k+2).key5.x,skList.get(k+2).key5.y,skList.get(k+3).key5.x,skList.get(k+3).key5.y,f0,f1);
			accTemp0 = getAcceleration(vTemp0, vTemp1, f0, f1);
			accTemp1 = getAcceleration(vTemp1, vTemp2, f0, f1);
			k5List.add(getJerkiness(accTemp0, accTemp1, f0,f1));
			vTemp0 = getVelocityVec(skList.get(k).key6.x,skList.get(k).key6.y,skList.get(k+1).key6.x,skList.get(k+1).key6.y,f0,f1);
			vTemp1 = getVelocityVec(skList.get(k+1).key6.x,skList.get(k+1).key6.y,skList.get(k+2).key6.x,skList.get(k+2).key6.y,f0,f1);
			vTemp2 = getVelocityVec(skList.get(k+2).key6.x,skList.get(k+2).key6.y,skList.get(k+3).key6.x,skList.get(k+3).key6.y,f0,f1);
			accTemp0 = getAcceleration(vTemp0, vTemp1, f0, f1);
			accTemp1 = getAcceleration(vTemp1, vTemp2, f0, f1);
			k6List.add(getJerkiness(accTemp0, accTemp1, f0,f1));
			vTemp0 = getVelocityVec(skList.get(k).key7.x,skList.get(k).key7.y,skList.get(k+1).key7.x,skList.get(k+1).key7.y,f0,f1);
			vTemp1 = getVelocityVec(skList.get(k+1).key7.x,skList.get(k+1).key7.y,skList.get(k+2).key7.x,skList.get(k+2).key7.y,f0,f1);
			vTemp2 = getVelocityVec(skList.get(k+2).key7.x,skList.get(k+2).key7.y,skList.get(k+3).key7.x,skList.get(k+3).key7.y,f0,f1);
			accTemp0 = getAcceleration(vTemp0, vTemp1, f0, f1);
			accTemp1 = getAcceleration(vTemp1, vTemp2, f0, f1);
			k7List.add(getJerkiness(accTemp0, accTemp1, f0,f1));
		}
		
	}
	public static double calculateSpeed (double keyX0, double keyY0, double keyX1, double keyY1, int f0, int f1){
		double speed;
		Vec velVec = getVelocityVec ( keyX0,  keyY0,  keyX1,  keyY1,  f0,  f1);
		speed = Vec.normalise(velVec);
		//double theta = Math.atan(y/x);
		//System.out.println(speed);
		return speed; 
	}
	
	public static Vec getVelocityVec(double keyX0, double keyY0, double keyX1, double keyY1, int f0, int f1){ //first derivative of position vector
		double i, j;
		Vec velVec;
		double r,s ;
		int t=f1-f0;;
		r= keyX1-keyX0;
		s=keyY1-keyY0;
		i = (r)/t;
		j = (s)/t;
		velVec = new Vec(i,j);
		return velVec;	
	}
	public static Vec getAcceleration(Vec velVec1, Vec velVec2, int f0, int f1){//second derivative of position vector
		Vec acc;
		int t=f1-f0;
		acc= new Vec (((1/t)*velVec2.i-velVec1.i),(-(1/t)*velVec2.j-velVec1.j));
		return acc;
	}
	public static double getJerkiness(Vec accVec1, Vec accVec2, int f0, int f1){//third derivative of position vector
		Vec jerk;
		int t=f1-f0;
		jerk= new Vec (((1/t)*accVec2.i-accVec1.i),(-(1/t)*accVec2.j-accVec1.j));
		return Vec.normalise(jerk); //magnitude of jerk
	}
	
	
	 public static void main(String[] args) throws IOException, ParseException {
		
		//File[] nonParFiles = new File(INPUTFOLDER + "nptest" +trial +".json").listFiles();
		//File[] parFiles = new File(INPUTFOLDER +"ptest" +trial +".json").listFiles();
		
		File[] nonParFiles = new File(INPUTFOLDER + "nptest" +trial +"JSON" ).listFiles();
		File[] parFiles = new File(INPUTFOLDER +"ptest" +trial+"JSON" ).listFiles();
		
		//System.out.println( "DEBUG " + INPUTFOLDER);

		//System.out.println("DEBUG:npFiles " + nonParFiles.length);
		//System.out.println("DEBUG:pFiles " + parFiles.length);

		 setPathArray(nonParFiles, nonparPaths);		
		//DEBUG Statements
		 //for(int i = 0; i< nonparPaths.size(); i++){
			// System.out.println("DEBUG:nonPar paths :" + nonparPaths.get(i));
		 //}
		
		
		 setPathArray(parFiles, parPaths);	
		 //for(int i = 0; i< parPaths.size(); i++){
			// System.out.println("DEBUG:Par paths :" + parPaths.get(i));
		// }
		 
		 setSkeletonList(nonparPaths, npSkeletonList); 
		 //System.out.println("DEBUG:nPSkeletonList size:" + npSkeletonList.size());
		 //for(int i = 0; i< npSkeletonList.size(); i++){
			// System.out.println("DEBUG:nPSkeletonList :" + npSkeletonList.get(i));}
		 
		 setSkeletonList(parPaths, pSkeletonList);
		// System.out.println("DEBUG:PSkeletonsize :" + pSkeletonList.size());

		 setAnglesList(npSkeletonList, npAng0List, npAng1List, npAng2List, npAng3List, npAng4List, npAng5List);
		 setAnglesList(pSkeletonList, pAng0List, pAng1List, pAng2List, pAng3List, pAng4List, pAng5List);
		 setKeypointSpeeds(npSkeletonList, npKey0SpeedList, npKey1SpeedList,npKey2SpeedList, npKey3SpeedList,npKey4SpeedList, npKey5SpeedList, npKey6SpeedList,npKey7SpeedList);
		 setKeypointSpeeds(pSkeletonList, pKey0SpeedList, pKey1SpeedList,pKey2SpeedList, pKey3SpeedList,pKey4SpeedList, pKey5SpeedList, pKey6SpeedList,pKey7SpeedList);
		 setReferenceTrajectory(npSkeletonList);
		 setDisFromTraj(refTrajKey0,refTrajKey1,refTrajKey2,refTrajKey3,refTrajKey4,refTrajKey5,refTrajKey6,refTrajKey7, pSkeletonList);
		 setNormDistances();
		 setJerk(npSkeletonList,npKey0JerkList,npKey1JerkList,npKey2JerkList,npKey3JerkList,npKey4JerkList,npKey5JerkList,npKey6JerkList,npKey7JerkList );
		 setJerk(pSkeletonList,pKey0JerkList,pKey1JerkList,pKey2JerkList,pKey3JerkList,pKey4JerkList,pKey5JerkList,pKey6JerkList,pKey7JerkList);
		
		 
		// System.out.println("DEBUG: npSkeletonlist " +npSkeletonList.size());
		// System.out.println("DEBUG: pSkeletonlist " +pSkeletonList.size());
		 //create folders for each set of metric data files
			File directory = new File(OUTPUTFOLDER);
		    if (! directory.exists()){
		        directory.mkdir();
		        // If you require it to make the entire directory path including parents,
		        // use directory.mkdirs(); here instead.
		    }
		    if (directory.list().length>0){ //directory is not empty
		    	FileUtils.cleanDirectory(directory); //clean directory

		    }
				
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
			String myDateString = sdf.format(myDate);
			
			
			BufferedWriter bwSpeed = null;
			FileWriter fwSpeed = null;
			BufferedWriter bwJerk = null;
			FileWriter fwJerk = null;
			BufferedWriter bwDis = null;
			FileWriter fwDis = null;
			BufferedWriter bwAng = null;
			FileWriter fwAng = null;
			

			try {

				fwSpeed = new FileWriter(SPEEDFILE);
				bwSpeed = new BufferedWriter(fwSpeed);
				fwJerk = new FileWriter(JERKFILE);
				bwJerk = new BufferedWriter(fwJerk);
				fwDis = new FileWriter(DISFILE);
				bwDis= new BufferedWriter(fwDis);
				fwAng = new FileWriter(ANGFILE);
				bwAng= new BufferedWriter(fwAng);
				
				String intro = "%" + myDateString +  "\n%Project: Training a neural network in order to correctly identify motor function in the upper limbs from a 2D camera \n" +
						"%Author: Liall Arafa (LA2817) \n" + "%Hamlyn Centre\n" ;
				
				bwSpeed.write(intro);
				bwJerk.write(intro);
				bwDis.write(intro);
				bwAng.write(intro);

				
				String speedHeader = "@RELATION speeds"  + "\n" +
						"@ATTRIBUTE npkey0SpeedList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey0SpeedList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey1SpeedList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey1SpeedList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey2SpeedList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey2SpeedList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey3SpeedList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey3SpeedList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey4SpeedList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey4SpeedList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey5SpeedList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey5SpeedList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey4SpeedList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey5SpeedList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey5SpeedList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey5SpeedList NUMERIC "  +"\n"+
						"@ATTRIBUTE pkey7SpeedList NUMERIC "  +"\n"+
						"@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +

						
						"\n \n" ;
				
				bwSpeed.write(speedHeader);
				
				String jerkHeader = "@RELATION jerks"  + "\n" +
						"@ATTRIBUTE npkey0JerkList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey0JerkList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey1JerkList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey1JerkList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey2JerkList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey2JerkList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey3JerkList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey3JerkList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey4JerkList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey4JerkList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey5JerkList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey5JerkList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey6JerkList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey6JerkList NUMERIC "  +"\n"+
						"@ATTRIBUTE npkey7JerkSpeedList NUMERIC " +"\n"+
						"@ATTRIBUTE pkey7JerkSpeedList NUMERIC "  +"\n"+
						"@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +

						
						"\n \n" ;
				bwJerk.write(jerkHeader);
				
				String disHeader = "@RELATION disFromRefTraj"  + "\n" +
						"@ATTRIBUTE disKey0X NUMERIC " +"\n"+
						"@ATTRIBUTE disKey0Y NUMERIC "  +"\n"+
						"@ATTRIBUTE disKey1X NUMERIC " +"\n"+
						"@ATTRIBUTE disKey1Y NUMERIC "  +"\n"+
						"@ATTRIBUTE disKey2X NUMERIC " +"\n"+
						"@ATTRIBUTE disKey2Y NUMERIC "  +"\n"+
						"@ATTRIBUTE disKey3X NUMERIC " +"\n"+
						"@ATTRIBUTE disKey3Y NUMERIC "  +"\n"+
						"@ATTRIBUTE disKey4X NUMERIC " +"\n"+
						"@ATTRIBUTE disKey4Y NUMERIC "  +"\n"+
						"@ATTRIBUTE disKey5X NUMERIC " +"\n"+
						"@ATTRIBUTE disKey5Y NUMERIC "  +"\n"+
						"@ATTRIBUTE disKey6X NUMERIC " +"\n"+
						"@ATTRIBUTE disKey6Y NUMERIC "  +"\n"+
						"@ATTRIBUTE disKey7X NUMERIC " +"\n"+
						"@ATTRIBUTE disKey7Y NUMERIC "  +"\n"+
						"@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +

						
						"\n \n" ;
				bwDis.write(disHeader);
				
				String AngHeader = "@RELATION angles"  + "\n" +
						"@ATTRIBUTE npAng0 NUMERIC " +"\n"+
						"@ATTRIBUTE pAng0  NUMERIC "  +"\n"+
						"@ATTRIBUTE npAng1 NUMERIC " +"\n"+
						"@ATTRIBUTE pAng1  NUMERIC "  +"\n"+
						"@ATTRIBUTE npAng2 NUMERIC " +"\n"+
						"@ATTRIBUTE pAng2  NUMERIC "  +"\n"+
						"@ATTRIBUTE npAng3 NUMERIC " +"\n"+
						"@ATTRIBUTE pAng3  NUMERIC "  +"\n"+
						"@ATTRIBUTE npAng4 NUMERIC " +"\n"+
						"@ATTRIBUTE pAng4  NUMERIC "  +"\n"+
						"@ATTRIBUTE npAng5 NUMERIC " +"\n"+
						"@ATTRIBUTE pAng5  NUMERIC "  +"\n"+
						"@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +

						"\n \n" ;
				bwAng.write(AngHeader);
				
				String data = "@DATA \n" ;
				bwSpeed.write(data);
				bwJerk.write(data);
				bwDis.write(data);

				for(int i =0; i <npKey0SpeedList.size();i++){
						bwSpeed.write(
						npKey0SpeedList.get(i).toString() + ","  +
						pKey0SpeedList.get(i).toString() + "," +
						npKey1SpeedList.get(i).toString() + "," +
						pKey1SpeedList.get(i).toString() + "," +
						npKey2SpeedList.get(i).toString() + ","  +
						pKey2SpeedList.get(i).toString() + "," +
						npKey3SpeedList.get(i).toString() + "," +
						pKey3SpeedList.get(i).toString() + "," +
						npKey4SpeedList.get(i).toString() + ","  +
						pKey4SpeedList.get(i).toString() + "," +
						npKey5SpeedList.get(i).toString() + "," +
						pKey5SpeedList.get(i).toString() + "," +
						npKey6SpeedList.get(i).toString() + ","  +
						pKey6SpeedList.get(i).toString() + "," +
						npKey7SpeedList.get(i).toString() + "," +
						pKey7SpeedList.get(i).toString() + "," +
						"?"									   +
								 "\n"  );
					}
				
				for(int i =0; i <npKey0JerkList.size();i++){
					bwJerk.write(
					npKey0JerkList.get(i).toString() + "," +
					pKey0JerkList.get(i).toString() + "," +
					npKey1JerkList.get(i).toString() + "," +
					pKey1JerkList.get(i).toString() + "," +
					npKey2JerkList.get(i).toString() + ","  +
					pKey2JerkList.get(i).toString() + "," +
					npKey3JerkList.get(i).toString() + "," +
					pKey3JerkList.get(i).toString() + "," +
					npKey4JerkList.get(i).toString() + ","  +
					pKey4JerkList.get(i).toString() + "," +
					npKey5JerkList.get(i).toString() + "," +
					pKey5JerkList.get(i).toString() + "," +
					npKey6JerkList.get(i).toString() + ","  +
					pKey6JerkList.get(i).toString() + "," +
					npKey7JerkList.get(i).toString() + "," +
					pKey7JerkList.get(i).toString() + "," +
					"?"										   +
							 "\n"  );
				}
			
				
				for(int i =0; i <disKey0X.size();i++){
					bwDis.write(
					disKey0X.get(i).toString() + "," +
					disKey0Y.get(i).toString() + "," +
					disKey1X.get(i).toString() + "," +
					disKey1Y.get(i).toString() + "," +
					disKey2X.get(i).toString() + "," +
					disKey2Y.get(i).toString() + "," +
					disKey3X.get(i).toString() + "," +
					disKey3Y.get(i).toString() + "," +
					disKey4X.get(i).toString() + "," +
					disKey4Y.get(i).toString() + "," +
					disKey5X.get(i).toString() + "," +
					disKey5Y.get(i).toString() + "," +
					disKey6X.get(i).toString() + "," +
					disKey6Y.get(i).toString() + "," +
					disKey7X.get(i).toString() + "," +
					disKey7Y.get(i).toString() + "," +
					"?"										   +
							 "\n"  );
				}
				
				for(int i =0; i <npSkeletonList.size();i++){
					bwAng.write(
					npAng0List.get(i).toString() + "," +
					pAng0List.get(i).toString() + "," +
					npAng1List.get(i).toString() + "," +
					pAng1List.get(i).toString() + "," +
					npAng2List.get(i).toString() + "," +
					pAng2List.get(i).toString() + "," +
					npAng3List.get(i).toString() + "," +
					pAng3List.get(i).toString() + "," +
					npAng4List.get(i).toString() + "," +
					pAng4List.get(i).toString() + "," +
					npAng5List.get(i).toString() + "," +
					pAng5List.get(i).toString() + "," +
					"?"										   +
								 "\n"  );
				}
				
					System.out.println("Done Writing Data to Files");
	
				} catch (IOException e) {
	
					e.printStackTrace();
	
				} finally {
	
					try {
	
						if (bwSpeed != null)
							bwSpeed.close();
	
						if (fwSpeed != null)
							fwSpeed.close();
	
					} catch (IOException ex) {
	
						ex.printStackTrace();
	
					}
					try {
	
						if (bwJerk != null)
							bwJerk.close();
	
						if (fwJerk != null)
							fwJerk.close();
	
					} catch (IOException ex) {
	
						ex.printStackTrace();
	
					}
					try {
	
						if (bwDis != null)
							bwDis.close();
	
						if (fwDis != null)
							fwDis.close();
	
					} catch (IOException ex) {
	
						ex.printStackTrace();
	
					}
	
				}

			
		/*DEBUG Statements
		 for(int i = 0; i< nonparPaths.size(); i++){
			 //System.out.println("nonPar paths :" + nonParPaths.get(i));
		 }
		 for(int i = 0; i< nonparPaths.size(); i++){
			 System.out.println("Par paths : " + parPaths.get(i));
		 }
		 for(int i = 0; i< npSkeletonList.size(); i++){
			 System.out.println("nPSkeletonList :" + npSkeletonList.get(i));
		 }
		 for(int i = 0; i< pSkeletonList.size(); i++){
			 System.out.println("pSkeletonList :" + pSkeletonList.get(i));
		 }
		 
		// for (int i =0; i < npKey0SpeedList.size(); i++){
			//System.out.println("np key0 SpeedList " + npKey0SpeedList.get(i));

		// }
		// for (int i =0; i < pKey0SpeedList.size(); i++){
			//System.out.println("p key0 SpeedList " + pKey0SpeedList.get(i));

		// }
		 //for (int i =0; i < pKey1SpeedList.size(); i++){
			//	System.out.println("p key1 SpeedList " + pKey1SpeedList.get(i));

			// }
		/*for (int i =0; i < refTrajKey0.size(); i++){
				System.out.println("reftrajKey0 " + refTrajKey0.get(i));
			}
		for (int i =0; i < refTrajKey1.size(); i++){
			System.out.println("reftrajKey1 " + refTrajKey1.get(i));
		 }
		for (int i =0; i < refTrajKey2.size(); i++){
			System.out.println("reftrajKey2 " + refTrajKey2.get(i));
		 }
		for (int i =0; i < refTrajKey3.size(); i++){
			System.out.println("reftrajKey3 " + refTrajKey3.get(i));
		 }
		for (int i =0; i < refTrajKey4.size(); i++){
			System.out.println("reftrajKey4 " + refTrajKey4.get(i));
		 }
		for (int i =0; i < refTrajKey5.size(); i++){
			System.out.println("reftrajKey5 " + refTrajKey5.get(i));
		 }
		for (int i =0; i < refTrajKey6.size(); i++){
			System.out.println("reftrajKey6 " + refTrajKey6.get(i));
		 }
		for (int i =0; i < refTrajKey7.size(); i++){
			System.out.println("reftrajKey7 " + refTrajKey7.get(i));
		 }*/
		/*System.out.println("refTrajKey0 x : " + refTrajKey0.get(3).x);
		System.out.println("refTrajKey0 y : " + refTrajKey0.get(3).y);
		
		System.out.println("pSkeleton key0 X : " + pSkeletonList.get(3).key0.x);
		System.out.println("pSkeleton key0 Y : " + pSkeletonList.get(3).key0.y);

		System.out.println("npSkeleton key0 X : " + npSkeletonList.get(3).key0.x);
		System.out.println("npSkeleton key0 Y : " + npSkeletonList.get(3).key0.y);*/
				/*for (int i =0; i < npKey0JerkList.size(); i++){
			System.out.println("npKey0JerkList :  " + npKey0JerkList.get(i));
		 }
		for (int i =0; i < npKey1JerkList.size(); i++){
			System.out.println("npKey1JerkList :  " + npKey1JerkList.get(i));
		 }
		for (int i =0; i < npKey2JerkList.size(); i++){
			System.out.println("npKey2JerkList :  " + npKey2JerkList.get(i));
		 }
		for (int i =0; i < npKey3JerkList.size(); i++){
			System.out.println("npKey3JerkList :  " + npKey3JerkList.get(i));
		 }
		for (int i =0; i < npKey4JerkList.size(); i++){
			System.out.println("npKey4JerkList :  " + npKey4JerkList.get(i));
		 }
		for (int i =0; i < npKey5JerkList.size(); i++){
			System.out.println("npKey5JerkList :  " + npKey5JerkList.get(i));
		 }
		for (int i =0; i < npKey6JerkList.size(); i++){
			System.out.println("npKey6JerkList :  " + npKey6JerkList.get(i));
		 }
		for (int i =0; i < npKey7JerkList.size(); i++){
			System.out.println("npKey7JerkList :  " + npKey7JerkList.get(i));
		 }
		for (int i =0; i < pKey0JerkList.size(); i++){
		System.out.println("pKey0JerkList :  " + pKey0JerkList.get(i));
		 }
		for (int i =0; i < pKey1JerkList.size(); i++){
			System.out.println("pKey1JerkList :  " + pKey1JerkList.get(i));
		 }
		for (int i =0; i < pKey2JerkList.size(); i++){
			System.out.println("pKey2JerkList :  " + pKey2JerkList.get(i));
		 }
		for (int i =0; i < pKey3JerkList.size(); i++){
			System.out.println("pKey3JerkList :  " + pKey3JerkList.get(i));
		 }
		for (int i =0; i < pKey4JerkList.size(); i++){
			System.out.println("pKey4JerkList :  " + pKey4JerkList.get(i));
		 }
		for (int i =0; i < pKey5JerkList.size(); i++){
			System.out.println("pKey5JerkList :  " + pKey5JerkList.get(i));
		 }
		for (int i =0; i < pKey6JerkList.size(); i++){
			System.out.println("pKey6JerkList :  " + pKey6JerkList.get(i));
		 }
		for (int i =0; i < pKey7JerkList.size(); i++){
			System.out.println("pKey7JerkList :  " + pKey7JerkList.get(i));
		 }*/
	
	 
		/*for (int i =0; i < disKey0X.size(); i++){
			System.out.println("disKey0X:  " + disKey0X.get(i));
		 }
		for (int i =0; i < disKey0Y.size(); i++){
			System.out.println("disKey0Y:  " + disKey0Y.get(i));
		 }
		for (int i =0; i < disKey0X.size(); i++){
			System.out.println("disKey1X:  " + disKey1X.get(i));
		 }
		for (int i =0; i < disKey0Y.size(); i++){
			System.out.println("disKey1Y:  " + disKey1Y.get(i));
		 }
		for (int i =0; i < disKey0X.size(); i++){
			System.out.println("disKey2X:  " + disKey2X.get(i));
		 }
		for (int i =0; i < disKey0Y.size(); i++){
			System.out.println("disKey2Y:  " + disKey2Y.get(i));
		 }
		for (int i =0; i < disKey0X.size(); i++){
			System.out.println("disKey3X:  " + disKey3X.get(i));
		 }
		for (int i =0; i < disKey0Y.size(); i++){
			System.out.println("disKey3Y:  " + disKey3Y.get(i));
		 }
		for (int i =0; i < disKey0X.size(); i++){
			System.out.println("disKey4X:  " + disKey4X.get(i));
		 }
		for (int i =0; i < disKey0Y.size(); i++){
			System.out.println("disKey4Y:  " + disKey4Y.get(i));
		 }
		for (int i =0; i < disKey0X.size(); i++){
			System.out.println("disKey5X:  " + disKey5X.get(i));
		 }
		for (int i =0; i < disKey0Y.size(); i++){
			System.out.println("disKey5Y:  " + disKey5Y.get(i));
		 }
		for (int i =0; i < disKey0X.size(); i++){
			System.out.println("disKey6X:  " + disKey6X.get(i));
		 }
		for (int i =0; i < disKey0Y.size(); i++){
			System.out.println("disKey7Y:  " + disKey7Y.get(i));
		 }*/
		
			/*
		for (int i =0; i < npAngKey0List.size(); i++){
		System.out.println("npAngKey0List:  " + npAngKey0List.get(i));
		 }
		for (int i =0; i < npAngKey1List.size(); i++){
			System.out.println("npAngKey1List:  " + npAngKey0List.get(i));
		 }
		for (int i =0; i < npAngKey2List.size(); i++){
			System.out.println("npAngKey2List:  " + npAngKey2List.get(i));
		 }
		for (int i =0; i < npAngKey3List.size(); i++){
			System.out.println("npAngKey3List:  " + npAngKey3List.get(i));
		 }
		for (int i =0; i < npAngKey4List.size(); i++){
			System.out.println("npAngKey4List:  " + npAngKey4List.get(i));
		 }
		for (int i =0; i < npAngKey5List.size(); i++){
			System.out.println("npAngKey5List:  " + npAngKey5List.get(i));
		 }
		for (int i =0; i < npAngKey6List.size(); i++){
			System.out.println("npAngKey6List:  " + npAngKey6List.get(i));
		 }
		for (int i =0; i < npAngKey7List.size(); i++){
			System.out.println("npAngKey7List:  " + npAngKey7List.get(i));
		 }
		for (int i =0; i < pAngKey0List.size(); i++){
		System.out.println("pAngKey0List:  " + pAngKey0List.get(i));
		 }
		for (int i =0; i < pAngKey1List.size(); i++){
			System.out.println("pAngKey1List:  " + pAngKey0List.get(i));
		 }
		for (int i =0; i < pAngKey2List.size(); i++){
			System.out.println("pAngKey2List:  " + pAngKey2List.get(i));
		 }
		for (int i =0; i < pAngKey3List.size(); i++){
			System.out.println("pAngKey3List:  " + pAngKey3List.get(i));
		 }
		for (int i =0; i < pAngKey4List.size(); i++){
			System.out.println("pAngKey4List:  " + pAngKey4List.get(i));
		 }
		for (int i =0; i < pAngKey5List.size(); i++){
			System.out.println("pAngKey5List:  " + pAngKey5List.get(i));
		 }
		for (int i =0; i < pAngKey6List.size(); i++){
			System.out.println("pAngKey6List:  " + pAngKey6List.get(i));
		 }
		for (int i =0; i < pAngKey7List.size(); i++){
			System.out.println("pAngKey7List:  " + pAngKey7List.get(i));
		 }
	*/
		
		
		
		
		
		
		
		
		//DEBUG check sizes
		/*System.out.println("npSkeleton List Size " + npSkeletonList.size());
		System.out.println("pSkeleton List Size " + pSkeletonList.size());
		
		System.out.println("refTrajKey0 List Size " + refTrajKey0.size());
		System.out.println("refTrajKey1 List Size " + refTrajKey0.size());
		System.out.println("refTrajKey2 List Size " + refTrajKey0.size());
		System.out.println("refTrajKey3 List Size " + refTrajKey0.size());
		System.out.println("refTrajKey4 List Size " + refTrajKey0.size());
		System.out.println("refTrajKey5 List Size " + refTrajKey0.size());
		System.out.println("refTrajKey6 List Size " + refTrajKey0.size());
		System.out.println("refTrajKey7 List Size " + refTrajKey0.size());
		System.out.println("refTrajKey0 List Size " + refTrajKey0.size());
		
		System.out.println(" disKey0X List Size " + disKey0X.size());
		System.out.println(" disKey0Y List Size " + disKey0Y.size());
		System.out.println(" disKey1X List Size " + disKey1X.size());
		System.out.println(" disKey1Y List Size " + disKey1Y.size());
		System.out.println(" disKey2X List Size " + disKey2X.size());
		System.out.println(" disKey2Y List Size " + disKey2Y.size());
		System.out.println(" disKey3X List Size " + disKey3X.size());
		System.out.println(" disKey3Y List Size " + disKey3Y.size());
		System.out.println(" disKey4X List Size " + disKey4X.size());
		System.out.println(" disKey4Y List Size " + disKey4Y.size());
		System.out.println(" disKey5X List Size " + disKey5X.size());
		System.out.println(" disKey5Y List Size " + disKey5Y.size());
		System.out.println(" disKey6X List Size " + disKey6X.size());
		System.out.println(" disKey6Y List Size " + disKey6Y.size());
		
		System.out.println("npKey0SpeedList List Size " + npKey0SpeedList.size());
		System.out.println("npKey1SpeedList List Size " + npKey1SpeedList.size());
		System.out.println("npKey2SpeedList List Size " + npKey2SpeedList.size());
		System.out.println("npKey3SpeedList List Size " +  npKey3SpeedList.size());
		System.out.println("npKey4SpeedList List Size " + npKey4SpeedList.size());
		System.out.println("npKey5SpeedList List Size " + npKey5SpeedList.size());
		System.out.println("npKey6SpeedList List Size " + npKey6SpeedList.size());
		System.out.println("npKey7SpeedList List Size " + npKey7SpeedList.size());
		
		System.out.println("pKey0SpeedList List Size " + pKey0SpeedList.size());
		System.out.println("pKey1SpeedList List Size " + pKey1SpeedList.size());
		System.out.println("pKey2SpeedList List Size " + pKey2SpeedList.size());
		System.out.println("pKey3SpeedList List Size " +  pKey3SpeedList.size());
		System.out.println("pKey4SpeedList List Size " + pKey4SpeedList.size());
		System.out.println("pKey5SpeedList List Size " + pKey5SpeedList.size());
		System.out.println("pKey6SpeedList List Size " + pKey6SpeedList.size());
		System.out.println("pKey7SpeedList List Size " + pKey7SpeedList.size());
		
		System.out.println("npKey0JerkList List Size " + npKey0JerkList.size());
		System.out.println("npKey1JerkList List Size " + npKey1JerkList.size());
		System.out.println("npKey2JerkList List Size " + npKey2JerkList.size());
		System.out.println("npKey3JerkList List Size " +  npKey3JerkList.size());
		System.out.println("npKey4JerkList List Size " + npKey4JerkList.size());
		System.out.println("npKey5JerkList List Size " + npKey5JerkList.size());
		System.out.println("npKey6JerkList List Size " + npKey6JerkList.size());
		System.out.println("npKey7JerkList List Size " + npKey7JerkList.size());
		
		System.out.println("pKey0JerkList List Size " + pKey0JerkList.size());
		System.out.println("pKey1JerkList List Size " + pKey1JerkList.size());
		System.out.println("pKey2JerkList List Size " + pKey2JerkList.size());
		System.out.println("pKey3SpeedList List Size " +  pKey3JerkList.size());
		System.out.println("pKey4JerkList List Size " + pKey4JerkList.size());
		System.out.println("pKey5JerkList List Size " + pKey5JerkList.size());
		System.out.println("pKey6JerkList List Size " + pKey6JerkList.size());
		System.out.println("pKey7JerkList List Size " + pKey7JerkList.size());
		
		
			
	
		for (int i =0; i < disKey0Norm.size(); i++){
		System.out.println("disKey0Norm:  " + disKey0Norm.get(i));
		 }
		for (int i =0; i < disKey0Norm.size(); i++){
			System.out.println("disKey1Norm:  " + disKey1Norm.get(i));
		 }
		for (int i =0; i < disKey2Norm.size(); i++){
			System.out.println("disKey2Norm:  " + disKey2Norm.get(i));
		 }
		for (int i =0; i < disKey3Norm.size(); i++){
			System.out.println("disKey3Norm:  " + disKey3Norm.get(i));
		 }
		for (int i =0; i < disKey4Norm.size(); i++){
			System.out.println("disKey4Norm:  " + disKey4Norm.get(i));
		 }
		for (int i =0; i < disKey0Norm.size(); i++){
			System.out.println("disKey5Norm:  " + disKey5Norm.get(i));
		 }
		for (int i =0; i < disKey0Norm.size(); i++){
			System.out.println("disKey6Norm:  " + disKey6Norm.get(i));
		 }
		for (int i =0; i < disKey7Norm.size(); i++){
			System.out.println("disKey7Norm:  " + disKey7Norm.get(i));
		 }*/
	

	 }

	 
	 
	 
 }
	 
	
