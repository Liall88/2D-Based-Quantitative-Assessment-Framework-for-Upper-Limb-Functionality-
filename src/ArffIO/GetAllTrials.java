/**
 * Author: Liall Arafa
   Imperial College London
   4 Jun 2018
2DFuglMeyer
	
 */
package ArffIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.ArrayList;

import weka.core.Instances;

/**
 * @author la2817
 *
 *This class gets all Arff files for specific metrics and stores in arrays
 */
public class GetAllTrials {
	//Change these parameters to access different files
	final static String folderName="symposiumCupExercises";
	//final static String INPUTDIR = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName+"/";
	final static String INPUTDIR = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName+"/";

	static File testMetricsDir = new File(INPUTDIR);
	
	//File [] metric files contains 0 is speeds, 1 is jerkiness, 2 is disFromRef, 3 is angles
	static ArrayList <File> speedFiles = new ArrayList<File>();
	static ArrayList <File> jerkFiles = new ArrayList<File>();
	static ArrayList <File> disFromRefFiles = new ArrayList<File>();
	static ArrayList <File> angleFiles = new ArrayList<File>();

	
 	//final static String OUTPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName+"/averages/";
	//static File[] metricFiles = new File(INPUTFOLDER).listFiles();
	
	//list only directories and store in Files array
 	static File[] testMetricFiles = testMetricsDir.listFiles(new FileFilter() {
			    public boolean accept(File f) {
			        return f.isDirectory();
			    }
			});
		
	
	public static void readArff(){
		
		//System.out.println("Folders count: " + files.length);
		
		//System.out.println("DEBUG: inputDIR length"+ dirFile.length());
		File [] temp;
		for(int i =0; i <testMetricFiles.length;i++){ //loop through directory where trials are located
			
			//System.out.println("DEBUG: File:"+ testMetricFiles[i]);
			//System.out.println("DEBUG: InsideFiles:"+ testMetricFiles[i].getAbsolutePath());
			String INPUTFOLDER = INPUTDIR +"test"+i+"Metrics" + "/";
			File [] metricFiles = new File(INPUTFOLDER).listFiles();
			//for(int j=0; j<metricFiles.length;j++){
				//System.out.println("DEBUG: Metric Files"+ metricFiles[j].getAbsolutePath());
				speedFiles.add(metricFiles[0]);
				jerkFiles.add(metricFiles[1]);
				disFromRefFiles.add(metricFiles[2]);
				angleFiles.add(metricFiles[3]);
				//make a File array of each testMetricFiles
			//}
			//speedFiles.add(testMetricFiles[i].listFiles());
			
			for(int k=0; k <10;k++){
			System.out.println("DEBUG: speedFiles:"+ speedFiles.get(i));
			System.out.println("DEBUG: jerkFiles:"+ jerkFiles.get(i));
			System.out.println("DEBUG: anglesFiles:"+ angleFiles.get(i));
			System.out.println("DEBUG: disFromRefFiles:"+ disFromRefFiles.get(i));

			}
			//angleFiles.add(files[i].listFiles());
			
			//File[] speed = new File(testMetricFiles[i].listFiles()).listFiles();

			
		}
		//String INPUTFOLDER = INPUTDIR +"test"+trial+"Metrics" + "/";
		//System.out.println("DEBUG: num of files:"+ new File(INPUTFOLDER).listFiles().length);
		
		//0 is speeds, 1 is jerkiness, 2 is disFromRef, 3 is angles
		//File[] metricFiles = new File(INPUTFOLDER).listFiles();
		
	}
		
	public static void main (String [] args) {
		readArff();
			
	}

//ORIGINAL : general method which gets the path for each JSON file and saves it in an array 
		//check if directory and loop through each file and set paths which is used in setSkeletonList() to instantiate skeletons
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