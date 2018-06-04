/**
 * Author: Liall Arafa
   Imperial College London
   4 Jun 2018
2DFuglMeyer
	
 */
package ArffIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import weka.core.Instances;

/**
 * @author la2817
 *
 *This class reads data from the stored arff files
 */
public class ArffReader {
	public static final int trial=1;	
	final static String folderName="symposiumCupExercises";
	final static String INPUTDIR = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName+"/";
	
	//enter each trial folder and look at angles,speed, jerk and distFromReference for each trial
	//send each to MultipleTrials
 	final static String OUTPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName+"/averages/";
 	final static String INPUTFOLDER = INPUTDIR +"test"+trial+"Metrics" + "/";
	//static File[] metricFiles = new File(INPUTFOLDER).listFiles();
	

	
	
	public void readArff(){
		
	}
		
	public static void main (String [] args) {
		System.out.println("DEBUG:"+ INPUTFOLDER);
		System.out.println("DEBUG:"+ new File(INPUTFOLDER).listFiles());
		
		//0 is speeds, 1 is jerkiness, 2 is disFromRef, 3 is angles
		File[] metricFiles = new File(INPUTFOLDER).listFiles();
		
	}

//ORIGINAL : general method which gets the path for each JSON file and saves it in an array 
		//check if directory and loop through each file and set paths which is used in setSekeltonList() to instantiate skeletons
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
}