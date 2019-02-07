package dataExtraction;
/**
 * Author: Liall Arafa
   Imperial College London
   24 Apr 2018
	
 */
/**
 * @author la2817
 *  * 
 * This class is used to calculate kinematic features for use of quantification of upper limb motor functionality analysis
 * It will be done by first analysing the non-paretic arm as a standard, and comparing the paretic arm to it
 * It then outputs the kinematic features into arff files in order to prepare for machine learrnign
 * 
 * metrics are calculated per frame rather than per second 
 * assume PARIETIC IS THE PATIENTS RIGHT ARM, if not flip the video

 *Metrics:
 *trajectory error - is a measure of spatial deviation of the wrist trajectory from the reference trajectory
 *velocity profile deviation - measure of the deviation of the speed profile from the reference speed profile, calculated from the non-paretic arm
 *jerkiness-( or smoothness) is a measure of the variations in the velocity profile. An 'efficient' reach movement should have a smooth velocity profile with an 
 *accelerating pattern followed by a decelerating pattern without any jerks
 *
 *InputFolder must contain directories which contain all the JSON files for the paretic and non-paretic trials in:
 * /path/inputfolder/test(trial)/nptest(trial)JSON  and /path/inputfolder/test(trial)/ptest(trial)JSON
 * It will generate folders for the arff outputs in the specified directory
 * 
 * class generates and returns arff object generated for each metric for each openpose trial
 
 
 *TODO: change arraylists into arrays
 *TODO: add confidence scores for each metric in order to weight higher confidence scores more
 *TODO: add optimisation weights and thresholds
 *TODO: segmentation in the X-Y plane possible? 
 *TODO: put arff writing into separate class
 *TODO:upload File Structure needed in GitHub
 *TODO: save openpose outputs and arff outputs relative to eclipse project not using absolute paths
 *TODO: Write patientinfo.txt into folder
 *TODO: use indicator for which arm is parietic L or R for automatic analysis
 *
 *

 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import org.json.simple.parser.ParseException;

import arff.Arff;
import arff.ArffWriter;
import arff.GetAllArffsForExercise;


public class MakeSingleTrialArffs {
	/*
	 * variables for input/output of arff files
	 */
	public  static int trial; 	
	private static String WMFTClass;//wolf motor function class (0-5) or ? if unknown
	private static String folderName;
	private static String FILEEXT;
	private static Integer ptrialSize;
	private static Integer nptrialSize;
	private static Integer difftrialSize;

	//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
	private static String OUTPUTFOLDER;
	 //static String OUT ="/Outputs/arff_Outputs/testDara/symposiumCupExercises/test"+trial+"Metrics";
	//arff_Outputs.testData.symposiumCupExercises.test1Metrics;
	//final static String OUTPUTFOLDER = "/Outputs/arff_Outputs/testData/"+folderName +"/test" + trial +"Metrics/";
 	//String OUTPUTFOLDER = getClass().getResource(arg0)"/Outputs/arff_Outputs/testData"+folderName+"/test"+trial+"Metrics/");
	private static String INPUTFOLDER;
	/*
	 * 
	 */
	
	//where temporal analysis arff files will be stored
	//NPFILES are for the NP trial (patient L arm)
	//P Files are for the P trial (patient R arm)
	
	private static String NPSPEEDFILE;
	private static String NPANGFILE;
	private static String XDISFILE;
	private static String NPJERKFILE;
	private static String NPXFILE;
	private static String NPYFILE;
	
	private static String PSPEEDFILE;
	private static String PANGFILE;
	private static String YDISFILE;
	private static String PJERKFILE;
	private static String PXFILE;
	private static String PYFILE;
	
	private static String DIFFFILE;

	//where JSON inputs are
	private static File[] nonParFiles;
	private static File[] parFiles ;
	public static boolean pIsRight; //Paretic limb position, change for each trial
	public static int numOfTests;

	private static final double deltaFrame= 1;
	//static boolean pArmIsR;//checks if patients paretic arm is their R arm
	
	//Optimal Weights and Thresholds (for neural networks)
	//(Based on Paper: Component - Level tuning of Kinematic Features from Composite Therapists of Movement Quality)
	private Double t1=0.13,t2=0.2, t3=0.1, t4=2.5, t5=0.99;
	private Double w1=2.5,w2=2.5, w3=1.8, w4=0.05, w5=0.05;
	
	//JSON paths of paretic and Non-paretic JSON
	private static ArrayList<String> nonparPaths = new ArrayList<String>();
	private  static ArrayList<String> parPaths = new ArrayList<String>();
	
	//Arraylists Of paretic and Non-paretic Skeletons at each frame
	private static ArrayList<Skeleton> npSkeletonList = new ArrayList<Skeleton>();//trial1:left arm
	private static ArrayList<Skeleton> pSkeletonList = new ArrayList<Skeleton>();//trial1: right arm
	
	//Arraylists Of angles of each Skeletons at each frame of both paretic and non-paretic skeletons at each frame
	//2D arrays that contain arraylists of data for each keypoint useful for that trial
	//GIVEN that p is R arm - p files contain the 0-4 keypoints  and angles 0,1,2 while L arm contains the 0,5,6,7 keypoints and angles 3,4,5
	
	//arraylists of x and y coordinates of openpose test
	private static ArrayList<ArrayList<Double>> npXList = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> npYList = new ArrayList<ArrayList<Double>>();
	
	private static ArrayList<ArrayList<Double>> pXList = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> pYList = new ArrayList<ArrayList<Double>>();
	
	private static ArrayList<Double> npXKey0List= new ArrayList<Double>();
	private static ArrayList<Double> npXKey1List= new ArrayList<Double>();
	private static ArrayList<Double> npXKey2List= new ArrayList<Double>();
	private static ArrayList<Double> npXKey3List= new ArrayList<Double>();
	private static ArrayList<Double> npXKey4List= new ArrayList<Double>();
	private static ArrayList<Double> npXKey5List= new ArrayList<Double>();
	private static ArrayList<Double> npXKey6List= new ArrayList<Double>();
	private static ArrayList<Double> npXKey7List= new ArrayList<Double>();
	
	private static ArrayList<Double> npYKey0List= new ArrayList<Double>();
	private static ArrayList<Double> npYKey1List= new ArrayList<Double>();
	private static ArrayList<Double> npYKey2List= new ArrayList<Double>();
	private static ArrayList<Double> npYKey3List= new ArrayList<Double>();
	private static ArrayList<Double> npYKey4List= new ArrayList<Double>();
	private static ArrayList<Double> npYKey5List= new ArrayList<Double>();
	private static ArrayList<Double> npYKey6List= new ArrayList<Double>();
	private static ArrayList<Double> npYKey7List= new ArrayList<Double>();
	
	private static ArrayList<Double> pXKey0List= new ArrayList<Double>();
	private static ArrayList<Double> pXKey1List= new ArrayList<Double>();
	private static ArrayList<Double> pXKey2List= new ArrayList<Double>();
	private static ArrayList<Double> pXKey3List= new ArrayList<Double>();
	private static ArrayList<Double> pXKey4List= new ArrayList<Double>();
	private static ArrayList<Double> pXKey5List= new ArrayList<Double>();
	private static ArrayList<Double> pXKey6List= new ArrayList<Double>();
	private static ArrayList<Double> pXKey7List= new ArrayList<Double>();
	
	private static ArrayList<Double> pYKey0List= new ArrayList<Double>();
	private static ArrayList<Double> pYKey1List= new ArrayList<Double>();
	private static ArrayList<Double> pYKey2List= new ArrayList<Double>();
	private static ArrayList<Double> pYKey3List= new ArrayList<Double>();
	private static ArrayList<Double> pYKey4List= new ArrayList<Double>();
	private static ArrayList<Double> pYKey5List= new ArrayList<Double>();
	private static ArrayList<Double> pYKey6List= new ArrayList<Double>();
	private static ArrayList<Double> pYKey7List= new ArrayList<Double>();
	
	//Arraylists For Arff Object
	private static ArrayList<ArrayList<Double>> npAngList = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> pAngList = new ArrayList<ArrayList<Double>>();

	private static ArrayList<Double> npAng0List = new ArrayList<Double>();
	private static ArrayList<Double> npAng1List = new ArrayList<Double>();
	private static ArrayList<Double> npAng2List = new ArrayList<Double>();
	private static ArrayList<Double> npAng3List = new ArrayList<Double>();
	private static ArrayList<Double> npAng4List = new ArrayList<Double>();
	private static ArrayList<Double> npAng5List = new ArrayList<Double>();
	
	private static ArrayList<Double> pAng0List = new ArrayList<Double>();
	private static ArrayList<Double> pAng1List = new ArrayList<Double>();
	private static ArrayList<Double> pAng2List = new ArrayList<Double>();
	private static ArrayList<Double> pAng3List = new ArrayList<Double>();
	private static ArrayList<Double> pAng4List = new ArrayList<Double>();
	private static ArrayList<Double> pAng5List = new ArrayList<Double>();
	
	//reference trajectory taken from non-paretic arm 

	private static ArrayList <ArrayList<Keypoint>> refTraj;
	private static ArrayList<Keypoint> refTrajKey0= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> refTrajKey1= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> refTrajKey2= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> refTrajKey3= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> refTrajKey4= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> refTrajKey5= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> refTrajKey6= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> refTrajKey7= new ArrayList<Keypoint>();
	
	//distances of keypoint in terms of x,y coordinates from reference trajectory keypoints, where reference trajectory is based on the non-parietic trial
	//thus disX and disY are only for the parietic trial 
	private static ArrayList<ArrayList<Double>> disXList= new ArrayList<ArrayList<Double>>(); ;
	private static ArrayList<ArrayList<Double>> disYList= new ArrayList<ArrayList<Double>>(); ;


	private static ArrayList<Double> disKey0X= new ArrayList<Double>();
	private static ArrayList<Double> disKey0Y= new ArrayList<Double>();
	private static ArrayList<Double> disKey1X= new ArrayList<Double>();
	private static ArrayList<Double> disKey1Y= new ArrayList<Double>();
	private static ArrayList<Double> disKey2X= new ArrayList<Double>();
	private static ArrayList<Double> disKey2Y= new ArrayList<Double>();
	private static ArrayList<Double> disKey3X= new ArrayList<Double>();
	private static ArrayList<Double> disKey3Y= new ArrayList<Double>();
	private static ArrayList<Double> disKey4X= new ArrayList<Double>();
	private static ArrayList<Double> disKey4Y= new ArrayList<Double>();
	private static ArrayList<Double> disKey5X= new ArrayList<Double>();
	private static ArrayList<Double> disKey5Y= new ArrayList<Double>();
	private static ArrayList<Double> disKey6X= new ArrayList<Double>();
	private static ArrayList<Double> disKey6Y= new ArrayList<Double>();
	private static ArrayList<Double> disKey7X= new ArrayList<Double>();
	private static ArrayList<Double> disKey7Y= new ArrayList<Double>();
	
	//normalised distances of keypoint from reference trajectory
	private static ArrayList<ArrayList<Double>> disNormList= new ArrayList<ArrayList<Double>>(); ;

	private static ArrayList<Double> disKey0Norm= new ArrayList<Double>();
	private static ArrayList<Double> disKey1Norm= new ArrayList<Double>();
	private static ArrayList<Double> disKey2Norm= new ArrayList<Double>();
	private static ArrayList<Double> disKey3Norm= new ArrayList<Double>();
	private static ArrayList<Double> disKey4Norm= new ArrayList<Double>();
	private static ArrayList<Double> disKey5Norm= new ArrayList<Double>();
	private static ArrayList<Double> disKey6Norm= new ArrayList<Double>();
	private static ArrayList<Double> disKey7Norm= new ArrayList<Double>();
	
	
	//speed  of keypoints in terms of pixel per frame between skeletons
	private static ArrayList<ArrayList<Double>> npSpeedList= new ArrayList<ArrayList<Double>>(); ;
	private static ArrayList<ArrayList<Double>> pSpeedList= new ArrayList<ArrayList<Double>>(); ;
	
	private static ArrayList<Double> npKey0SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> npKey1SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> npKey2SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> npKey3SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> npKey4SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> npKey5SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> npKey6SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> npKey7SpeedList= new ArrayList<Double>();

	private static ArrayList<Double> pKey0SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> pKey1SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> pKey2SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> pKey3SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> pKey4SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> pKey5SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> pKey6SpeedList= new ArrayList<Double>();
	private static ArrayList<Double> pKey7SpeedList= new ArrayList<Double>();

	//jerk (or smoothness) of keypoints
	private static ArrayList<ArrayList<Double>> npJerkList= new ArrayList<ArrayList<Double>>(); 
	private static ArrayList<ArrayList<Double>> pJerkList= new ArrayList<ArrayList<Double>>(); 
	
	private static ArrayList<Double> npKey0JerkList= new ArrayList<Double>();
	private static ArrayList<Double> npKey1JerkList= new ArrayList<Double>();
	private static ArrayList<Double> npKey2JerkList= new ArrayList<Double>();
	private static ArrayList<Double> npKey3JerkList= new ArrayList<Double>();
	private static ArrayList<Double> npKey4JerkList= new ArrayList<Double>();
	private static ArrayList<Double> npKey5JerkList= new ArrayList<Double>();
	private static ArrayList<Double> npKey6JerkList= new ArrayList<Double>();
	private static ArrayList<Double> npKey7JerkList= new ArrayList<Double>();

	private static ArrayList<Double> pKey0JerkList= new ArrayList<Double>();
	private static ArrayList<Double> pKey1JerkList= new ArrayList<Double>();
	private static ArrayList<Double> pKey2JerkList= new ArrayList<Double>();
	private static ArrayList<Double> pKey3JerkList= new ArrayList<Double>();
	private static ArrayList<Double> pKey4JerkList= new ArrayList<Double>();
	private static ArrayList<Double> pKey5JerkList= new ArrayList<Double>();
	private static ArrayList<Double> pKey6JerkList= new ArrayList<Double>();
	private static ArrayList<Double> pKey7JerkList= new ArrayList<Double>();
	
	//Diff of keypoints
	private static ArrayList<ArrayList<Double>> diffSpeedList = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> diffAngList = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> diffJerkList = new ArrayList<ArrayList<Double>>();

	
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
	
	
	private static void setSkeletonList(ArrayList<String> strList, ArrayList<Skeleton> skList) throws IOException, ParseException{
		for (int i =0; i <strList.size(); i++){
			 //System.out.println( " skeletal strList : " +strList.get(i));
			 Skeleton Sk = new Skeleton(strList.get(i));
			 skList.add(Sk);
		}
	}
	private static void setAnglesList(ArrayList<Skeleton> skList, ArrayList<Double> angKey0List, ArrayList<Double> angKey1List, ArrayList<Double> angKey2List, ArrayList<Double> angKey3List,
			ArrayList<Double> angKey4List, ArrayList<Double> angKey5List ) throws IOException, ParseException{
		for (int i =0; i <skList.size(); i++){
			angKey0List.add(skList.get(i).ang0);
			angKey1List.add(skList.get(i).ang1);
			angKey2List.add(skList.get(i).ang2);
			angKey3List.add(skList.get(i).ang3);
			angKey4List.add(skList.get(i).ang4);
			angKey5List.add(skList.get(i).ang5);					
		}
	}
	
	private static void setCoorList(ArrayList <Skeleton> skList, ArrayList<Double>xKey0List,ArrayList<Double> xKey1List,
			ArrayList<Double>xKey2List,ArrayList<Double>xKey3List,ArrayList<Double>xKey4List,ArrayList<Double>xKey5List,
			ArrayList<Double>xKey6List,ArrayList<Double>xKey7List,ArrayList<Double>yKey0List,ArrayList<Double> yKey1List,
			ArrayList<Double>yKey2List,ArrayList<Double>yKey3List,ArrayList<Double>yKey4List,ArrayList<Double>yKey5List,
			ArrayList<Double>yKey6List,ArrayList<Double>yKey7List){
		for(int i =0; i<skList.size(); i++){
			xKey0List.add(skList.get(i).key0.x);
			xKey1List.add(skList.get(i).key1.x);
			xKey2List.add(skList.get(i).key2.x);
			xKey3List.add(skList.get(i).key3.x);
			xKey4List.add(skList.get(i).key4.x);
			xKey5List.add(skList.get(i).key5.x);
			xKey6List.add(skList.get(i).key6.x);
			xKey7List.add(skList.get(i).key7.x);	
			
			yKey0List.add(skList.get(i).key0.y);
			yKey1List.add(skList.get(i).key1.y);
			yKey2List.add(skList.get(i).key2.y);
			yKey3List.add(skList.get(i).key3.y);
			yKey4List.add(skList.get(i).key4.y);
			yKey5List.add(skList.get(i).key5.y);
			yKey6List.add(skList.get(i).key6.y);
			yKey7List.add(skList.get(i).key7.y);
		}
	}
	private static void setReferenceTrajectory (ArrayList <Skeleton> skList){
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
	
	private static Keypoint calculateReferenceTrajectory (Keypoint key1, double keyNx, double keyNy){
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
	
	private static void setDisFromTraj(ArrayList<Keypoint> refTrajKey0, ArrayList<Keypoint> refTrajKey1, ArrayList<Keypoint> refTrajKey2, ArrayList<Keypoint> refTrajKey3,
			ArrayList<Keypoint> refTrajKey4, ArrayList<Keypoint> refTrajKey5, ArrayList<Keypoint> refTrajKey6, ArrayList<Keypoint> refTrajKey7, ArrayList<Skeleton> pSkelList){
		//get displacement in x and y  of each Keypoint from reference trajectory key
		double disRefX, disRefY;
		for(int i =0; i <refTrajKey0.size();i++){//DEBUG: THIS ONLY COMPARES VALUES FOR THE SIZE OF THE REFTRAJ
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
	
	private static void setNormDistances(){
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
	private static void setKeypointSpeeds(ArrayList <Skeleton> skList, ArrayList <Double> k0List,ArrayList <Double> k1List,ArrayList <Double> k2List,
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
		
	
	private static void setJerk(ArrayList <Skeleton> skList, ArrayList <Double> k0List,ArrayList <Double> k1List,ArrayList <Double> k2List,
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
	private static double calculateSpeed (double keyX0, double keyY0, double keyX1, double keyY1, int f0, int f1){
		double speed;
		Vec velVec = getVelocityVec ( keyX0,  keyY0,  keyX1,  keyY1,  f0,  f1);
		speed = Vec.normalise(velVec);
		//double theta = Math.atan(y/x);
		//System.out.println(speed);
		return speed; 
	}
	
	
	private static Vec getVelocityVec(double keyX0, double keyY0, double keyX1, double keyY1, int f0, int f1){ //first derivative of position vector
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
	private static Vec getAcceleration(Vec velVec1, Vec velVec2, int f0, int f1){//second derivative of position vector
		Vec acc;
		int t=f1-f0;
		acc= new Vec (((1/t)*velVec2.i-velVec1.i),(-(1/t)*velVec2.j-velVec1.j));
		return acc;
	}
	private static double getJerkiness(Vec accVec1, Vec accVec2, int f0, int f1){//third derivative of position vector
		Vec jerk;
		int t=f1-f0;
		jerk= new Vec (((1/t)*accVec2.i-accVec1.i),(-(1/t)*accVec2.j-accVec1.j));
		return Vec.normalise(jerk); //magnitude of jerk
	}
	
	//method used to scale trials of different lengths
	private static <E> ArrayList<E> scaleList (ArrayList<E> list, int scaleSize){
//		System.out.println("DEBUG: listSize: " +list.size());
		//System.out.println("DEBUG: scaleSize: " +scaleSize);


		ArrayList<E> scaledList = new ArrayList<E>();
		int step = list.size()/scaleSize;
		//System.out.println("DEBUG: step: " +step);
		int stepCount=0;
		
		int i =0; 
		while (scaledList.size()<scaleSize){ //there are some rounding errors

			//System.out.println("DEBUG: stepCount" +stepCount);
			if(stepCount==step){
				scaledList.add(list.get(i));
				//System.out.println("DEBUG: Scalelist: " + list.get(i));
				stepCount=0;
			}		
			stepCount++;
			i++;

		}	
		//System.out.println("DEBUG: ScalelistSize: " + scaledList.size());

		return scaledList;

	}
	//gets differences between two difference lists
	public static ArrayList<Double> calDiff (ArrayList<Double> npList, ArrayList<Double> pList){
		ArrayList<Double> distList = new ArrayList<Double>();
		for(int i =0; i<npList.size(); i++){
			distList.add((Math.abs(npList.get(i)- pList.get(i))));
			//System.out.println("debug diff value:" + distList.get(i));
			//System.out.println("debug: i:" +i);
			}
		
		return distList;
	}
	
	//gets differences between two difference lists
		public static ArrayList<ArrayList<Double>> setDiff (ArrayList<ArrayList<Double>> diffList, ArrayList<ArrayList<Double>> npMultiList, ArrayList<ArrayList<Double>>pMultiList){
			
			ArrayList<Double> nullList=new ArrayList<Double>();
			for(int i =0; i<npMultiList.get(0).size();i++){
				nullList.add(null);
			}
			
		
				for(int i =0;i<npMultiList.size();i++ ){//for each keypoint
					try{
						diffList.add(calDiff(npMultiList.get(i), pMultiList.get(i)));}
					catch(NullPointerException e){
						diffList.add(nullList);
					}
			
				}
			return diffList;
		}
	

	private static void setMetricsLists(){ //sets the 2D arrays representing arraylist for each keypoint 
		//given that patient R limb is always considered parietic 
		//add all lists to correct list for p and np
		ArrayList<Double> nullList= new ArrayList<Double>();
		
		//add null lists to Ang so all lists are of same size
		for(int i =0; i<pAng1List.size();i++){
			nullList.add(null);
		}
		pAngList.add(nullList);
		pAngList.add(pAng0List);		
		pAngList.add(pAng1List);
		pAngList.add(pAng2List);
		pAngList.add(nullList);

		npAngList.add(nullList);
		npAngList.add(npAng3List);
		npAngList.add(npAng4List);
		npAngList.add(npAng5List);
		npAngList.add(nullList);

		pSpeedList.add(pKey0SpeedList);
		pSpeedList.add(pKey1SpeedList);
		pSpeedList.add(pKey2SpeedList);
		pSpeedList.add(pKey3SpeedList);
		pSpeedList.add(pKey4SpeedList);

		npSpeedList.add(npKey0SpeedList);
		npSpeedList.add(npKey1SpeedList);
		npSpeedList.add(npKey5SpeedList);
		npSpeedList.add(npKey6SpeedList);
		npSpeedList.add(npKey7SpeedList);
		
		pJerkList.add(pKey0JerkList);
		pJerkList.add(pKey1JerkList);
		pJerkList.add(pKey2JerkList);
		pJerkList.add(pKey3JerkList);
		pJerkList.add(pKey4JerkList);

		npJerkList.add(npKey0JerkList);
		npJerkList.add(npKey1JerkList);
		npJerkList.add(npKey5JerkList);
		npJerkList.add(npKey6JerkList);
		npJerkList.add(npKey7JerkList);	
		
		disNormList.add(disKey0Norm);
		disNormList.add(disKey1Norm);
		disNormList.add(disKey2Norm);
		disNormList.add(disKey3Norm);
		disNormList.add(disKey4Norm);

	
	}
	
	
	
	//getter methods for Arff objects 
	private static Arff getNPSpeedArff(){
		Arff sArff = new Arff(NPSPEEDFILE,trial,WMFTClass,npSpeedList,nptrialSize);
		return sArff;
	}
	private static Arff getNPJerkArff(){
		Arff jArff = new Arff(NPJERKFILE,trial,WMFTClass,npJerkList,nptrialSize);
		return jArff;
	}
	private static Arff getNPAngArff(){
		Arff aArff = new Arff(NPANGFILE,trial,WMFTClass,npAngList,nptrialSize);
		return aArff;
	}
	/*private static Arff getXDisFromRefArff(){
		Arff dArff = new Arff(XDISFILE,trial,WMFTClass,disXList);
		return dArff;
	}*/
	private static Arff getNPXArff(){
		Arff npxArff = new Arff(NPXFILE,trial,WMFTClass,npXList,nptrialSize);
		return npxArff;
	}
	private static Arff getNPYArff(){
		Arff npyArff = new Arff(NPYFILE,trial,WMFTClass,npYList,ptrialSize);
		return npyArff;
	}
	private static Arff getPXArff(){
		Arff pxArff = new Arff(PXFILE,trial,WMFTClass,pXList,ptrialSize);
		return pxArff;
	}
	private static Arff getPYArff(){
		Arff pyArff = new Arff(PYFILE,trial,WMFTClass,pYList,ptrialSize);
		return pyArff;
	}
	private static Arff getPSpeedArff(){
		Arff sArff = new Arff(PSPEEDFILE,trial,WMFTClass,pSpeedList,ptrialSize);
		return sArff;
	}
	private static Arff getPJerkArff(){
		Arff jArff = new Arff(PJERKFILE,trial,WMFTClass,pJerkList,ptrialSize);
		return jArff;
	}
	private static Arff getPAngArff(){
		Arff aArff = new Arff(PANGFILE,trial,WMFTClass,pAngList,ptrialSize);
		return aArff;
	}
	/*private static Arff getYDisFromRefArff(){
		Arff dArff = new Arff(YDISFILE,trial,WMFTClass,disYList);
		return dArff;
	}*/
	
	private static Arff getDiffArff(){
		Arff diffArff = new Arff(DIFFFILE, trial,WMFTClass, diffSpeedList, diffAngList, diffJerkList, disNormList,difftrialSize);
		return diffArff;
	}

	

	private static void setAllMetrics(ArrayList<Skeleton> npSkeletonList, ArrayList <Skeleton> pSkeletonList) throws IOException, ParseException{
		
		 setCoorList(npSkeletonList,npXKey0List,npXKey1List,npXKey2List,npXKey3List,npXKey4List,npXKey5List,npXKey6List,npXKey7List,
				 npYKey0List,npYKey1List,npYKey2List,npYKey3List,npYKey4List,npYKey5List,npYKey6List,npYKey7List);
		 setCoorList(pSkeletonList,pXKey0List,pXKey1List,pXKey2List,pXKey3List,pXKey4List,pXKey5List,pXKey6List,pXKey7List,
				 pYKey0List,pYKey1List,pYKey2List,pYKey3List,pYKey4List,pYKey5List,pYKey6List,pYKey7List);
		 setAnglesList(npSkeletonList, npAng0List, npAng1List, npAng2List, npAng3List, npAng4List, npAng5List);
		 setAnglesList(pSkeletonList, pAng0List, pAng1List, pAng2List, pAng3List, pAng4List, pAng5List);
		 setKeypointSpeeds(npSkeletonList, npKey0SpeedList, npKey1SpeedList,npKey2SpeedList, npKey3SpeedList,npKey4SpeedList, npKey5SpeedList, npKey6SpeedList,npKey7SpeedList);
		 setKeypointSpeeds(pSkeletonList, pKey0SpeedList, pKey1SpeedList,pKey2SpeedList, pKey3SpeedList,pKey4SpeedList, pKey5SpeedList, pKey6SpeedList,pKey7SpeedList);
		 setJerk(npSkeletonList,npKey0JerkList,npKey1JerkList,npKey2JerkList,npKey3JerkList,npKey4JerkList,npKey5JerkList,npKey6JerkList,npKey7JerkList );
		 setJerk(pSkeletonList,pKey0JerkList,pKey1JerkList,pKey2JerkList,pKey3JerkList,pKey4JerkList,pKey5JerkList,pKey6JerkList,pKey7JerkList);
		 
		  setReferenceTrajectory(npSkeletonList);
		 setDisFromTraj(refTrajKey0,refTrajKey1,refTrajKey2,refTrajKey3,refTrajKey4,refTrajKey5,refTrajKey6,refTrajKey7, pSkeletonList);
		 setNormDistances();		 
		 setMetricsLists();	
		 
		 setDiff(diffSpeedList,npSpeedList,pSpeedList);
		 setDiff(diffAngList,npAngList,pAngList);
		 setDiff(diffJerkList,npJerkList,pJerkList);	
			 

	}
		
	
	 public static void main(String[] args) throws IOException, ParseException {
		
		 System.out.println("MakeSingleArffs: writing Trial");
		 System.out.println("MakeSingleArffs: writing Trial");

		
		WMFTClass="5";//wolf motor function class (0-5) or ? if unknown
		trial = 0; 
		INPUTFOLDER="/data/la2817/LIALLFILE/JSONandVIDS/JSON/Side/LiallSide/WMFT"+WMFTClass+"/trial" +trial + "/";
		OUTPUTFOLDER="/data/la2817/LIALLFILE/CSV/csvOutputs/ResultsDataset/SimulatedStrokeCup/WMFT"+WMFTClass+"/trial"+trial+"Metrics/";		
		FILEEXT=".csv"; 	
		FILEEXT=".csv";

		System.out.println("inputFolder: "+INPUTFOLDER);
		System.out.println("outputFolder: "+OUTPUTFOLDER);
				
		//INPUTFOLDER= "/data/la2817/LIALLFILE/JSONandVIDS/JSON/Side/LiallSide/WMFT"+WMFTClass+"/trial" +trial + "/";
		//OUTPUTFOLDER="/data/la2817/LIALLFILE/CSV/csvOutputs/ResultsDataset/Side/LiallSide/SimStroke/WMFT"+WMFTClass+"/trial"+trial+"Metrics/";	
		
		//INPUTFOLDER="/data/la2817/LIALLFILE/mixedData/JSON/Katy/WMFT"+WMFTClass+"/trial" +trial + "/";
		//folderName="lateExperimentWMFT4";		//OUTPUTFOLDER = "/homes/la2817/Desktop/Outputs/csvOutputs/newCSV1/Mohammed/WMFT"+WMFTClass+"/"+"/test"+trial+"Metrics/";		
		//folderName="lateExperiment";
		//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
		///homes/la2817/Desktop/Outputs/arff_Outputs/testData/longExcercises/test0Metrics
		 //OUTPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/trainingData/"+folderName +"/test" + trial +"Metrics/";
		 //INPUTFOLDER = "/homes/la2817/Desktop/Outputs/openPose_outputs/trainingData/MLExercises/"+folderName+"/trial" +trial + "/";
		
		 //OUTPUTFOLDER = "/homes/la2817/Desktop/Outputs/csv_Outputs/trainingData/MLExercises/"+folderName +"/test" + trial +"Metrics/";
		 //FILEEXT=".arff";


		 //static String OUT ="/Outputs/arff_Outputs/testDara/symposiumCupExercises/test"+trial+"Metrics";
		//arff_Outputs.testData.symposiumCupExercises.test1Metrics;
		//final static String OUTPUTFOLDER = "/Outputs/arff_Outputs/testData/"+folderName +"/test" + trial +"Metrics/";
		//String OUTPUTFOLDER = getClass().getResource(arg0)"/Outputs/arff_Outputs/testData"+folderName+"/test"+trial+"Metrics/");

		NPSPEEDFILE= OUTPUTFOLDER+ "NPspeeds" +trial+FILEEXT;	
		NPANGFILE= OUTPUTFOLDER+ "NPangles" +trial+FILEEXT;
		XDISFILE = OUTPUTFOLDER + "XdisFromRef" +trial+FILEEXT;
		NPJERKFILE = OUTPUTFOLDER + "NPjerkiness" +trial+FILEEXT;
		NPXFILE = OUTPUTFOLDER + "NPxPos" +trial+FILEEXT;
		NPYFILE = OUTPUTFOLDER + "NPyPos" +trial+FILEEXT;
	
		PSPEEDFILE= OUTPUTFOLDER+ "Pspeeds" +trial+FILEEXT;	
		PANGFILE= OUTPUTFOLDER+ "Pangles" +trial+FILEEXT;
		YDISFILE = OUTPUTFOLDER + "YdisFromRef" +trial+FILEEXT;
		PJERKFILE = OUTPUTFOLDER + "Pjerkiness" +trial+FILEEXT;
		PXFILE = OUTPUTFOLDER + "PxPos" +trial+FILEEXT;
		PYFILE = OUTPUTFOLDER + "PyPos" +trial+FILEEXT;
		
		DIFFFILE = OUTPUTFOLDER + "diffFile" +trial+FILEEXT;

		
		//where JSON inputs are
		nonParFiles = new File(INPUTFOLDER + "nptrial" +trial +"JSON" ).listFiles();
		parFiles = new File(INPUTFOLDER +"ptrial" +trial+"JSON" ).listFiles();
		pIsRight=true; //Paretic limb position, change for each trial
		
		 arff.ArffReader.generalSetPathArray(nonParFiles, nonparPaths);		
		 arff.ArffReader.generalSetPathArray(parFiles, parPaths);	
		 
		 setSkeletonList(nonparPaths, npSkeletonList); 
		 setSkeletonList(parPaths, pSkeletonList);
		 
		 ptrialSize=Math.abs(pSkeletonList.size());
		 nptrialSize=Math.abs(npSkeletonList.size());
		 difftrialSize=Math.abs(nptrialSize-ptrialSize);

		 System.out.println("debug: ptrialSize:"+ptrialSize);
		 System.out.println("debug: nptrialSize:"+nptrialSize);

		 
		// System.out.println("Debug: npListScaledSize: "+npListScaled.size());
		 
		 //WMFT Simulator trials
		 //ArrayList <Skeleton> npListScaled = MultipleTrialsData.scaleList(npSkeletonList, 100);
		 //ArrayList <Skeleton> simWMFT=WMFTSimulator.getSimulatedWMFTSKList(5,MultipleTrialsData.scaleList(pSkeletonList, 100), npListScaled);		
		 // setAllMetrics(npListScaled, simWMFT);

		 //System.out.println("DEBUG: simWMFT size: "+ simWMFT.size());
		 
		 
		 //real trials
		 //scale np and p lists before writing
		 setAllMetrics(scaleList(npSkeletonList, 100),scaleList(pSkeletonList, 100));	
		 
		 Arff npspeedArff= getNPSpeedArff();
		 Arff npangArff= getNPAngArff();
		 	 
		 Arff npjerkArff= getNPJerkArff();
		 
		 Arff pspeedArff= getPSpeedArff();
		 Arff pangArff= getPAngArff();
		
		 Arff pjerkArff= getPJerkArff();
		 
		 Arff diffArff=getDiffArff();
		
		 
		 //Arff xdisArff= getXDisFromRefArff();
		 //Arff npXPosArff = getNPXArff();
		 //Arff npYPosArff = getNPYArff(); 
		 //Arff pXPosArff = getPXArff();
		 //Arff pYPosArff = getPYArff();
		// Arff ydisArff= getYDisFromRefArff();
		 //Arff simulatedWMFT2 = WMFTSimulator.
		 
		 //System.out.println("DEBUG: npangArff.list.get(0).size()"+npangArff.list.get(0).size());
		 //System.out.println("DEBUG: npjerkArff.list.get(0).size()"+npjerkArff.list.get(0).size());
		 //System.out.println("DEBUG: npspeedArff.list.get(0).size()"+npspeedArff.list.get(0).size());
		// System.out.println("DEBUG: XdisArff.list.get(0).size()"+xdisArff.multilist.get(0).size());
		
		 //System.out.println("DEBUG: outputfolder : "+OUTPUTFOLDER);
		
		 
		 ArffWriter.cleanAndMakeOutputDirectory(OUTPUTFOLDER);
		
		 ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, npspeedArff, "speed",5);
		 ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, npangArff, "angle",5);
		 ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, npjerkArff, "jerk",5);

		 ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, pspeedArff, "speed",5);
		 ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, pangArff, "angle",5);
		 ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, pjerkArff, "jerk",5);
		// ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, xdisArff, "xDis",8);
		// ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, ydisArff, "yDis",8);		 

		 
		 ArffWriter.writeDiffCSV(OUTPUTFOLDER,diffArff, "diff");
		 
		 
		// ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, npXPosArff, "xpos", 8);
		 //ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, npYPosArff, "ypos", 8);
		// ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, pXPosArff, "xpos", 8);
		// ArffWriter.writeSingleTrialCSV(OUTPUTFOLDER, pYPosArff, "ypos", 8);
		
		 
	}
}
	 

	 /* OLD METHOD
	  * private static void setMetricsLists(){ //sets the 2D arrays representing arraylist for each keypoint 
		
		npAngList.add(npAng0List);pAngList.add(pAng0List);
		npAngList.add(npAng1List);pAngList.add(pAng1List);
		npAngList.add(npAng2List);pAngList.add(pAng2List);
		npAngList.add(npAng3List);pAngList.add(pAng3List);
		npAngList.add(npAng4List);pAngList.add(pAng4List);
		npAngList.add(npAng5List);pAngList.add(pAng5List);

		disXList.add(disKey0X);disYList.add(disKey0Y);
		disXList.add(disKey1X);disYList.add(disKey1Y);
		disXList.add(disKey2X);disYList.add(disKey2Y);
		disXList.add(disKey3X);disYList.add(disKey3Y);
		disXList.add(disKey4X);disYList.add(disKey4Y);
		disXList.add(disKey5X);disYList.add(disKey5Y);
		disXList.add(disKey6X);disYList.add(disKey6Y);
		disXList.add(disKey7X);disYList.add(disKey7Y);

		//add x and y coordinates
		npXList.add(npXKey0List);pXList.add(pXKey0List);
		npXList.add(npXKey1List);pXList.add(pXKey1List);
		npXList.add(npXKey2List);pXList.add(pXKey2List);
		npXList.add(npXKey3List);pXList.add(pXKey3List);
		npXList.add(npXKey4List);pXList.add(pXKey4List);
		npXList.add(npXKey5List);pXList.add(pXKey5List);
		npXList.add(npXKey6List);pXList.add(pXKey6List);
		npXList.add(npXKey7List);pXList.add(pXKey7List);

		npYList.add(npYKey0List);pYList.add(pYKey0List);
		npYList.add(npYKey1List);pYList.add(pYKey1List);
		npYList.add(npYKey2List);pYList.add(pYKey2List);
		npYList.add(npYKey3List);pYList.add(pYKey3List);
		npYList.add(npYKey4List);pYList.add(pYKey4List);
		npYList.add(npYKey5List);pYList.add(pYKey5List);
		npYList.add(npYKey6List);pYList.add(pYKey6List);
		npYList.add(npYKey7List);pYList.add(pXKey7List);
		
		//add speeds
		npSpeedList.add(npKey0SpeedList);pSpeedList.add(pKey0SpeedList);
		npSpeedList.add(npKey1SpeedList);pSpeedList.add(pKey1SpeedList);
		npSpeedList.add(npKey2SpeedList);pSpeedList.add(pKey2SpeedList);
		npSpeedList.add(npKey3SpeedList);pSpeedList.add(pKey3SpeedList);
		npSpeedList.add(npKey4SpeedList);pSpeedList.add(pKey4SpeedList);
		npSpeedList.add(npKey5SpeedList);pSpeedList.add(pKey5SpeedList);
		npSpeedList.add(npKey6SpeedList);pSpeedList.add(pKey6SpeedList);
		npSpeedList.add(npKey7SpeedList);pSpeedList.add(pKey7SpeedList);
		
		//add jerks
		npJerkList.add(npKey0JerkList);pJerkList.add(pKey0JerkList);
		npJerkList.add(npKey1JerkList);pJerkList.add(pKey1JerkList);
		npJerkList.add(npKey2JerkList);pJerkList.add(pKey2JerkList);
		npJerkList.add(npKey3JerkList);pJerkList.add(pKey3JerkList);
		npJerkList.add(npKey4JerkList);pJerkList.add(pKey4JerkList);
		npJerkList.add(npKey5JerkList);pJerkList.add(pKey5JerkList);
		npJerkList.add(npKey6JerkList);pJerkList.add(pKey6JerkList);
		npJerkList.add(npKey7JerkList);pJerkList.add(pKey7JerkList);

	}*/
	  
	 
	 
 
	 
	
