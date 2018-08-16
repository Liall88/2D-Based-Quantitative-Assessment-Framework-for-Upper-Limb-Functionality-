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
 *This class gets all Arff files for metrics and stores in Arraylists <File>
 */
public class GetAllArffsForExercise {
	
	static String INPUTDIR;
	static File testMetricsDir;
	static File[] testMetricFiles;
	//File [] metric files contains 0 is NPspeeds, 1 is NPjerkiness, 2 is Xdis, 3 is NPangles
	private static ArrayList <File> npspeedFiles = new ArrayList<File>();
	private static ArrayList <File> npjerkFiles = new ArrayList<File>();
	private static ArrayList <File> xdisFiles = new ArrayList<File>();
	private static ArrayList <File> npangleFiles = new ArrayList<File>();
	private static ArrayList <File> npxFiles = new ArrayList<File>();
	private static ArrayList <File> npyFiles = new ArrayList<File>();
	//File [] metric files contains 4 is Pspeeds, 5 is Pjerkiness, 6 is Ydis, 7 is Pangles

	public static ArrayList <File> pspeedFiles = new ArrayList<File>();
	public static ArrayList <File> pjerkFiles = new ArrayList<File>();
	public static ArrayList <File> ydisFiles = new ArrayList<File>();
	public static ArrayList <File> pangleFiles = new ArrayList<File>();
	private static ArrayList <File> pxFiles = new ArrayList<File>();
	private static ArrayList <File> pyFiles = new ArrayList<File>();


	public static ArrayList<File> getNPSpeedFiles(){
		return npspeedFiles;
	}
	public static  ArrayList<File> getNPAngFiles(){
		return npangleFiles;
	}
	public static ArrayList<File> getXDisFiles(){
		return xdisFiles;
	}
	
	public static ArrayList<File> getNPJerkFiles(){
		return npjerkFiles;
	}
	public static ArrayList<File> getPSpeedFiles(){
		return pspeedFiles;
	}
	public static ArrayList<File> getPAngFiles(){
		return pangleFiles;
	}
	public static ArrayList<File> getYDisFiles(){
		return ydisFiles;
	}
	
	public static ArrayList<File> getPJerkFiles(){
		return pjerkFiles;
	}
	
	public static ArrayList<File> getNPXFiles(){
		return npxFiles;
	}
	public static ArrayList<File> getNPYFiles(){
		return npyFiles;
	}
	
	public static ArrayList<File> getPXFiles(){
		return pxFiles;
	}
	public static ArrayList<File> getPYFiles(){
		return pyFiles;
	}
	
	
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
				/*System.out.println("DEBUG: Metric Files 0"+ metricFiles[0].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 1"+ metricFiles[1].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 2"+ metricFiles[2].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 3"+ metricFiles[3].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 4"+ metricFiles[4].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 5"+ metricFiles[5].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 6"+ metricFiles[6].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 7"+ metricFiles[7].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 8"+ metricFiles[8].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 9"+ metricFiles[9].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 10"+ metricFiles[10].getAbsolutePath());
				System.out.println("DEBUG: Metric Files 11"+ metricFiles[11].getAbsolutePath());*/

				npspeedFiles.add(metricFiles[0]);
				npangleFiles.add(metricFiles[1]);				
				npxFiles.add(metricFiles[2]);
				npyFiles.add(metricFiles[3]);
				pxFiles.add(metricFiles[4]);
				pyFiles.add(metricFiles[5]);
				xdisFiles.add(metricFiles[6]);
				npjerkFiles.add(metricFiles[7]);
				pspeedFiles.add(metricFiles[8]);				
				pangleFiles.add(metricFiles[9]);
				ydisFiles.add(metricFiles[10]);
				pjerkFiles.add(metricFiles[11]);

		}
			
			
		}
		//String INPUTFOLDER = INPUTDIR +"test"+trial+"Metrics" + "/";
		//System.out.println("DEBUG: num of files:"+ new File(INPUTFOLDER).listFiles().length);
		
		//0 is speeds, 1 is jerkiness, 2 is disFromRef, 3 is angles
		//File[] metricFiles = new File(INPUTFOLDER).listFiles();
		
	
		
	/*	public static void main (String [] args) {
			//System.out.println("DEBUG: InputDIR2 " +INPUTDIR2);
			String folderName="symposiumCupExercises";
			//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
			String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName +"/";
			setFileLists(INPUTFOLDER);
				
		}*/


}