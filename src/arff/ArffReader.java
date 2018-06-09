/**
 * Author: Liall Arafa
   Imperial College London
   4 Jun 2018
2DFuglMeyer
	
 */
package arff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.function.Predicate;

import dataExtraction.Keypoint;


import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * @author la2817
 *
 *This class reads all Arffs in a directory and stores them in arrays of Arff files
 */
public class ArffReader {
	 
	/**
	 * Return the data set loaded from the Arff file at @param path
	 */
	
	/*
	 *change these parameters for each trial to generate arff files of kinematic features	
	 */
	/*
	 *	TODO: 2D arraylists have two 2 null values at  the end..why?
	 */
	//Araylists of Arff objects for each trial
	private static ArrayList<Arff> npSpeedArffs=new ArrayList <Arff>();
	private static ArrayList<Arff> npAngleArffs= new ArrayList <Arff>();
	private static ArrayList<Arff> xDisArffs= new  ArrayList <Arff>();
	private static ArrayList<Arff> npJerkArffs= new  ArrayList <Arff>();
	
	private static ArrayList<Arff> pSpeedArffs=new ArrayList <Arff>();
	private static ArrayList<Arff> pAngleArffs= new ArrayList <Arff>();
	private static ArrayList<Arff> yDisArffs= new  ArrayList <Arff>();
	private static ArrayList<Arff> pJerkArffs= new  ArrayList <Arff>();
	
	
	//2D arrays for each metric file read and their contents in String [] format
	private static ArrayList<ArrayList<String[]>> npJerkMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	private static ArrayList<ArrayList<String[]>> npSpeedMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	private static ArrayList<ArrayList<String[]>> npAngleMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	private static ArrayList<ArrayList<String[]>> xDisMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();

	private static ArrayList<ArrayList<String[]>> pJerkMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	private static ArrayList<ArrayList<String[]>> pSpeedMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	private static ArrayList<ArrayList<String[]>> pAngleMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	private static ArrayList<ArrayList<String[]>> yDisMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	
	//Data Structures for values extracted from ARFF Files:
	//WMFT Class
	

	
  //Arraylists For each Arff Object
	//ArrayLists of data read from Arff Files2D arrays for each metric and their contents in String [] format
	//2D arrays that contain arraylists of data for each keypoint(0-7)
	
	private static ArrayList<ArrayList<Double>> extnpAngList = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> extpAngList = new ArrayList<ArrayList<Double>>();


	
	//distances of keypoint in terms of x,y coordinates from reference trajectory Keypoints
	private static ArrayList<ArrayList<Double>> extdisXList= new ArrayList<ArrayList<Double>>(); ;
	private static ArrayList<ArrayList<Double>> extdisYList= new ArrayList<ArrayList<Double>>(); ;


	
	//normalised distances of keypoint from reference trajectory
	private static ArrayList<ArrayList<Double>> extdisNormList= new ArrayList<ArrayList<Double>>(); ;

	
	
	//speed  of keypoints in terms of pixel per frame between skeletons
	private static ArrayList<ArrayList<Double>> extnpSpeedList= new ArrayList<ArrayList<Double>>(); ;
	private static ArrayList<ArrayList<Double>> extpSpeedList= new ArrayList<ArrayList<Double>>(); ;
	


	//jerk (or smoothness) of keypoints
	private static ArrayList<ArrayList<Double>> extnpJerkList= new ArrayList<ArrayList<Double>>(); ;
	private static ArrayList<ArrayList<Double>> extpJerkList= new ArrayList<ArrayList<Double>>(); ;
	


	private static void searchFileAndSaveStringRows( File file, ArrayList<String[]> rowList){	//searches for term @DATA and starts reading after that line
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
		    String nextToken = scanner.nextLine();
	    	//System.out.println("DEBUG Next Token "+nextToken);
	    	if (nextToken.contains("@DATA")){//search term
	    		//System.out.println("DEBUG: FOUND @DATA");
	    		rowExtractor(scanner, rowList);//send scanner position to method
	    	}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		    }
		    
	}
	
	private static void searchRowsandSaveStringValues( File file, ArrayList<String[]> rowList){	//searches for term @DATA and starts reading after that line
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
		    String nextToken = scanner.nextLine();
	    	//System.out.println("DEBUG Next Token "+nextToken);
	    	if (nextToken.contains("@DATA")){//search term
	    		//System.out.println("DEBUG: FOUND @DATA");
	    		rowExtractor(scanner, rowList);//send scanner position to method
	    	}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		    }
		    
	}
	
	private static void rowExtractor(Scanner sc,  ArrayList<String[]> rowList){// extracts each line of the arff file
		//split file into columns by commas
		//BufferedReader br = new BufferedReader(new FileReader("C:\\readFile.txt"));
		//String line = br.readLine();
		while(sc.hasNextLine()){
		String line =sc.nextLine();
    	//System.out.println("DEBUG Next line:"+line);
		//String[] row = line.split("");
		String[] lineTemp = line.split("\\r?\\n");//split each line
		rowList.add(lineTemp);
		//String [] valueTemp =line.split(","); //split each value
		//System.out.println("DEBUG: row temp[0] : "+valueTemp[0]);
		//System.out.println("Row" + count + "  : " + rows[count]);
		}
	}	
	
	
	
	
	private static void  setMetricFilesRows(ArrayList <File> speedFiles,ArrayList <File> angleFiles,ArrayList <File> disFiles, ArrayList <File> jerkFiles, 
			ArrayList<ArrayList<String[]>> speedFilesRows,ArrayList<ArrayList<String[]>> angleFilesRows, ArrayList<ArrayList<String[]>> disFilesRows, ArrayList<ArrayList<String[]>> jerkFilesRows){

		//save each metric file into String [] format and save in MetricsFilesRows
		for(int i =0; i <jerkFiles.size(); i++){ //number of speed,jerk, disFromRefFiles and angles should be the same
			ArrayList<String[]>jerkRows= new ArrayList<String[]>()  ; 
			searchFileAndSaveStringRows(jerkFiles.get(i), jerkRows);		
			jerkFilesRows.add(jerkRows);
			//System.out.println("DEBUG: jerkMetricsFile.get("+i+"): " + jerkMetricsFilesRows.get(i));
			//System.out.println("DEBUG: jerkMetricsFile.size " + jerkMetricsFilesRows.size());
			//System.out.println("DEBUG: jerkMetricsFile.get(i).get(0) " + jerkMetricsFilesRows.get(i).get(0).toString());

			//System.out.println("DEBUG: jerkRowsSize"+jerkRows.size());

			
			ArrayList<String[]> speedRows= new ArrayList<String[]>() ; 
			searchFileAndSaveStringRows(speedFiles.get(i), speedRows);		
			speedFilesRows.add(speedRows);
			//System.out.println("DEBUG: speedMetricsFile.get(i): " + speedMetricsFilesRows.get(i));
			//System.out.println("DEBUG: speedRowsSize "+speedMetricsFilesRows.size());
			//System.out.println("DEBUG: speedMetricsFile.size " + speedMetricsFile.size());


			ArrayList<String[]> angleRows =new ArrayList<String[]>() ; 
			searchFileAndSaveStringRows(angleFiles.get(i), angleRows);		
			angleFilesRows.add(angleRows);
			//System.out.println("DEBUG: angleMetricsFile.get(i): " + angleMetricsFilesRows.get(i));
			//System.out.println("DEBUG: angleRowsSize "+angleMetricsFilesRows.size());
			//System.out.println("DEBUG: angleMetricsFile.size " + angleMetricsFile.size());


			ArrayList<String[]> disFromRefRows =new ArrayList<String[]>() ; 
			searchFileAndSaveStringRows(disFiles.get(i), disFromRefRows);		
			disFilesRows.add(disFromRefRows);
			//System.out.println("DEBUG: disFromrefMetrics.get(i): " + disFromRefMetricsFilesRows.get(i));
			//System.out.println("DEBUG: disFromRefSize "+disFromRefMetricsFilesRows.size());
			//System.out.println("DEBUG: disFromRefMetricsFile.size " + disFromRefMetricsFile.size());
			
		}
		
	}
	
	//sets all arraylists for each keypoint for speed, jerk and dist
	private static ArrayList<Arff> getArffList(String Path,ArrayList<ArrayList<String[]>> metricsFilesRows,ArrayList<Arff> arffList, int attributeNum){
		for(int i =0;i< metricsFilesRows.size();i++){//metric file for each trial
			String path =Path;
			String WMFTclass="";
			int keyNum = attributeNum -1;
			ArrayList<ArrayList<Double>> keyList = new ArrayList<ArrayList<Double>>();
			for (int z=0;z<keyNum;z++){
				keyList.add(new ArrayList<Double>()); //generate arraylists for each keypoint
			}
		
			
			for(int j=0;j <metricsFilesRows.get(i).size(); j++){//ArrayList of String[] for each line
				String[] tempRow = metricsFilesRows.get(i).get(j)[0].split(","); //each row split by , 		
				//System.out.println("DEBUG temprow length" + tempRow.length);
				//System.out.println("DEBUG temprow [0]:" + tempRow[0]);
				//System.out.println("DEBUG temprow [1]:" + tempRow[1]);
				///System.out.println("DEBUG temprow [2]:" + tempRow[2]);
				//System.out.println("DEBUG temprow [3]:" + tempRow[3]);
				//System.out.println("DEBUG temprow [4]:" + tempRow[4]);
				//System.out.println("DEBUG temprow [5]:" + tempRow[5]);
				//System.out.println("DEBUG temprow [6]:" + tempRow[6]);
				//System.out.println("DEBUG temprow [7]:" + tempRow[7]);
				//System.out.println("DEBUG temprow [8]:" + tempRow[8]);
				//System.out.println("DEBUG temprow [9]:" + tempRow[9]);


				//WMFTclass =tempRow[keyNum];
				for(int k=0; k<keyNum;k++){//add point at each keyPoint array
					keyList.get(k).add(Double.parseDouble(tempRow[k]));
					//System.out.println("DEBUG: tempRow" + k + " : "+ tempRow[k]);
				}
					//System.out.println("DEBUG: tempRow[attNum] :" + attributeNum);
						
				}
			    ArrayList<ArrayList<Double>> extractList= setMultiArrays(keyList,keyNum);
				arffList.add(new Arff(path,WMFTclass,extractList));
			}
		return arffList;
		}
	

	
	private static ArrayList<ArrayList<Double>> setMultiArrays(ArrayList<ArrayList<Double>> multiKeyList, int attributeNum){
		ArrayList<ArrayList<Double>> multiList = new ArrayList<ArrayList<Double>>();
		for (int i=0; i <attributeNum; i++){
			multiList.add(multiKeyList.get(i));
		}
		return multiList;
	}
	
	public static void readAllARFFs(String folder){//reads all arff Files in a directory and sets a File List for each metric
		//loadDataFromArffFile(SPEEDFILE); 
		//loadDataFromArffFile(JERKFILE); //works
		// contains each row of a file in String []
		
		//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
		GetAllArffsForExercise.setFileLists(folder);
		
		ArrayList <File> npSpeedFiles = GetAllArffsForExercise.getNPSpeedFiles();;
		//System.out.println("DEBUG: npspeedFiles.size"+npSpeedFiles.size());
		
		ArrayList <File> npJerkFiles = GetAllArffsForExercise.getNPJerkFiles();

		ArrayList <File> xDisFiles = GetAllArffsForExercise.getXDisFiles();

		ArrayList <File> npAngFiles = GetAllArffsForExercise.getNPAngFiles();

		ArrayList <File> pSpeedFiles = GetAllArffsForExercise.getPSpeedFiles();;
		
		ArrayList <File> pJerkFiles = GetAllArffsForExercise.getPJerkFiles();

		ArrayList <File> yDisFiles = GetAllArffsForExercise.getYDisFiles();

		ArrayList <File> pAngFiles = GetAllArffsForExercise.getPAngFiles();
		
		setMetricFilesRows(npSpeedFiles,npAngFiles, xDisFiles ,npJerkFiles,
						   npSpeedMetricsFilesRows,npAngleMetricsFilesRows,xDisMetricsFilesRows, npJerkMetricsFilesRows );
		setMetricFilesRows(pSpeedFiles,pAngFiles, yDisFiles ,pJerkFiles,
				   pSpeedMetricsFilesRows,pAngleMetricsFilesRows,yDisMetricsFilesRows, pJerkMetricsFilesRows );

	}
	
	
	public static ArrayList<Arff> getNPSpeedArffs(String INPUTFOLDER){
		ArrayList<Arff> sArff=getArffList(INPUTFOLDER,npSpeedMetricsFilesRows,npSpeedArffs,9);
		//System.out.println("DEBUG: metricsFilesRows.get 0.get 5"+ jerkMetricsFilesRows.get(0).get(5));
		return sArff;
		
	}
	
	public static ArrayList<Arff> getNPAngArffs(String INPUTFOLDER){
		ArrayList<Arff> aArff=getArffList(INPUTFOLDER,npAngleMetricsFilesRows,npAngleArffs,7);
		//System.out.println("DEBUG: metricsFilesRows.get 0.get 5"+ jerkMetricsFilesRows.get(0).get(5));
		return aArff;
		
	}
	public static ArrayList<Arff> getXDisArffs(String INPUTFOLDER){
		ArrayList<Arff> xdisArff=getArffList(INPUTFOLDER,xDisMetricsFilesRows,xDisArffs,9);
		//System.out.println("DEBUG: metricsFilesRows.get 0.get 5"+ jerkMetricsFilesRows.get(0).get(5));
		return xdisArff;
		
	}
	public static ArrayList<Arff> getNPJerkArffs(String INPUTFOLDER){
		ArrayList<Arff> jArff=getArffList(INPUTFOLDER,npJerkMetricsFilesRows,npJerkArffs,9);
		//System.out.println("DEBUG: metricsFilesRows.get 0.get 5"+ jerkMetricsFilesRows.get(0).get(5));
		return jArff;
		
	}
	public static ArrayList<Arff> getPSpeedArffs(String INPUTFOLDER){
		ArrayList<Arff> sArff=getArffList(INPUTFOLDER,pSpeedMetricsFilesRows,pSpeedArffs,9);
		//System.out.println("DEBUG: metricsFilesRows.get 0.get 5"+ jerkMetricsFilesRows.get(0).get(5));
		return sArff;
		
	}
	
	public static ArrayList<Arff> getPAngArffs(String INPUTFOLDER){
		ArrayList<Arff> aArff=getArffList(INPUTFOLDER,pAngleMetricsFilesRows,pAngleArffs,7);
		//System.out.println("DEBUG: metricsFilesRows.get 0.get 5"+ jerkMetricsFilesRows.get(0).get(5));
		return aArff;
		
	}
	public static ArrayList<Arff> getYDisArffs(String INPUTFOLDER){
		ArrayList<Arff> ydisArff=getArffList(INPUTFOLDER,yDisMetricsFilesRows,yDisArffs,9);
		//System.out.println("DEBUG: metricsFilesRows.get 0.get 5"+ jerkMetricsFilesRows.get(0).get(5));
		return ydisArff;
		
	}
	public static ArrayList<Arff> getPJerkArffs(String INPUTFOLDER){
		ArrayList<Arff> jArff=getArffList(INPUTFOLDER,pJerkMetricsFilesRows,pJerkArffs,9);
		//System.out.println("DEBUG: metricsFilesRows.get 0.get 5"+ jerkMetricsFilesRows.get(0).get(5));
		return jArff;
		
	}
	
	

	
	
	public static void main (String[] args) throws IOException{
		String folderName="symposiumCupExercises";
		//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
		String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName +"/";
		
		readAllARFFs(INPUTFOLDER);
		
		ArrayList<Arff> npSArffs=getNPSpeedArffs(INPUTFOLDER);	
		System.out.println("DEBUG npsArffs.size() "+ npSArffs.size());
	
		ArrayList<Arff> npAArffs=getNPAngArffs(INPUTFOLDER);	
		System.out.println("DEBUG npAArffs.size() "+ npAArffs.size());
		
		ArrayList<Arff> xDisArffs=getXDisArffs(INPUTFOLDER);	
		System.out.println("DEBUG xDisArffs.size() "+ xDisArffs.size());
		
		ArrayList<Arff> npJArffs=getNPJerkArffs(INPUTFOLDER);	
		System.out.println("DEBUG npJArffs.size() "+ npJArffs.size());
	
		ArrayList<Arff> pSArffs=getPSpeedArffs(INPUTFOLDER);	
		System.out.println("DEBUG psArffs.size() "+ pSArffs.size());
		
		ArrayList<Arff> pAArffs=getPAngArffs(INPUTFOLDER);	
		System.out.println("DEBUG pAArffs.size() "+ pAArffs.size());
		
		ArrayList<Arff> yDisArffs=getYDisArffs(INPUTFOLDER);	
		System.out.println("DEBUG yDisArffs.size() "+ yDisArffs.size());
		
		ArrayList<Arff> pJArffs=getPJerkArffs(INPUTFOLDER);	
		System.out.println("DEBUG pJArffs.size() "+ pJArffs.size());
			
	}	
		  

	
	public static Instances loadDataFromArffFile(String path) throws IOException{
		ArffLoader loader = new ArffLoader();
	    loader.setSource(new File(path));
	    Instances data = loader.getDataSet();
	    
	    System.out.println("\nHeader of dataset:\n");
	    System.out.println(new Instances(data, 0));
	    System.out.println("DEBUG : data "+data);
	    return data;
	} 	  
	
	
	//ORIGINAL : general method which gets the path for each JSON file and saves it in an array 
			//check if directory and loop through each file and set paths which is used in setSkeletonList() to instantiate skeletons
			public static void generalSetPathArray(File[] files,ArrayList<String> strList) {
			    for (File file : files) {
			        if (file.isDirectory()) {
			           // System.out.println("Directory: " + file.getName());
			            generalSetPathArray(file.listFiles(),strList); // Calls same method again.
			            
			        } else {	
			        	strList.add(file.getPath());
			            //System.out.println(file.getPath());
			        }
			    }
			}	
}
