/**
 * Author: Liall Arafa
   Imperial College London
   4 Jun 2018
2DFuglMeyer
	
 */
package arff;

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
public class GetAllArffsForExercise {
	
	static String INPUTDIR;
	static File testMetricsDir;
	static File[] testMetricFiles;
	//File [] metric files contains 0 is speeds, 1 is jerkiness, 2 is disFromRef, 3 is angles
	public static ArrayList <File> speedFiles = new ArrayList<File>();
	public static ArrayList <File> jerkFiles = new ArrayList<File>();
	public static ArrayList <File> disFromRefFiles = new ArrayList<File>();
	public static ArrayList <File> angleFiles = new ArrayList<File>();

	
	public static void setFileLists(String dir){
		
		INPUTDIR=dir;//list only directories and store in Files array
		testMetricsDir = new File(INPUTDIR);
		testMetricFiles = testMetricsDir.listFiles(new FileFilter() {
			    public boolean accept(File f) {
			        return f.isDirectory();
			    }
			});
		
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
			
			/*for(int k=0; k <10;k++){
			System.out.println("DEBUG: speedFiles:"+ speedFiles.get(i));
			System.out.println("DEBUG: jerkFiles:"+ jerkFiles.get(i));
			System.out.println("DEBUG: anglesFiles:"+ angleFiles.get(i));
			System.out.println("DEBUG: disFromRefFiles:"+ disFromRefFiles.get(i));
			}*/
				
			//System.out.println("DEBUG" + speedFiles.size());
		}
			
		/*for (int i =0;i <speedFiles.size();i++){
			System.out.println("DEBUG: speedFiles:"+i+ " : "+ speedFiles.get(i));
			System.out.println("DEBUG: jerkFiles:"+i+ " : "+  jerkFiles.get(i));
			System.out.println("DEBUG: disFromRefFiles:"+i+ " : "+  disFromRefFiles.get(i));
			System.out.println("DEBUG: angles:"+ i+ " : "+ angleFiles.get(i));
		}*/
			
		}
		//String INPUTFOLDER = INPUTDIR +"test"+trial+"Metrics" + "/";
		//System.out.println("DEBUG: num of files:"+ new File(INPUTFOLDER).listFiles().length);
		
		//0 is speeds, 1 is jerkiness, 2 is disFromRef, 3 is angles
		//File[] metricFiles = new File(INPUTFOLDER).listFiles();
		
	
		
		//public static void main (String [] args) {
			//System.out.println("DEBUG: InputDIR2 " +INPUTDIR2);
			//setFileLists();
				
		//}


}