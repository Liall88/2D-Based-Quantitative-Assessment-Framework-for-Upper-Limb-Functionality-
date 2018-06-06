/**
 * Author: Liall Arafa
   Imperial College London
   4 Jun 2018
2DFuglMeyer
	
 */
package ArffIO;

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
 */
public class ArffReader {
	 
	/**
	 * Return the data set loaded from the Arff file at @param path
	 */
	
	/*
	 *change these parameters for each trial to generate arff files of kinematic features	
	 */
	public static final int trial=0; 	
	final static String folderName="symposiumCupExercises";
	//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
	final static String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName +"/";
	/*
	 *	
	 */

	//where temporal analysis arff files will be stored
	final static String SPEEDFILE= INPUTFOLDER+ "speeds" +trial+".arff";
	final static String JERKFILE = INPUTFOLDER + "jerkiness" +trial+".arff";
	final static String DISFILE =  INPUTFOLDER+ "disFromRef" +trial+".arff";
	final static String ANGFILE= INPUTFOLDER+ "angles" +trial+".arff";
	
	
	//2D arrays for each metric and their contents in String [] format
	static ArrayList<ArrayList<String[]>> jerkMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	static ArrayList<ArrayList<String[]>> speedMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	static ArrayList<ArrayList<String[]>> angleMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	static ArrayList<ArrayList<String[]>> disFromRefMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	
	//ArrayLists of data read from Arff Files2D arrays for each metric and their contents in String [] format
	//2D arrays that contain arraylists of data for each keypoint(0-7)
	public static ArrayList<ArrayList<Double>> extnpAngList = new ArrayList<ArrayList<Double>>();
	public static ArrayList<ArrayList<Double>> extpAngList = new ArrayList<ArrayList<Double>>();

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
	public static ArrayList<ArrayList<Double>> extdisXList= new ArrayList<ArrayList<Double>>(); ;
	public static ArrayList<ArrayList<Double>> extdisYList= new ArrayList<ArrayList<Double>>(); ;


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
	public static ArrayList<ArrayList<Double>> extdisNormList= new ArrayList<ArrayList<Double>>(); ;

	public static ArrayList<Double> disKey0Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey1Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey2Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey3Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey4Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey5Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey6Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey7Norm= new ArrayList<Double>();
	
	
	//speed  of keypoints in terms of pixel per frame between skeletons
	public static ArrayList<ArrayList<Double>> extnpSpeedList= new ArrayList<ArrayList<Double>>(); ;
	public static ArrayList<ArrayList<Double>> extpSpeedList= new ArrayList<ArrayList<Double>>(); ;
	
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
	public static ArrayList<ArrayList<Double>> extnpJerkList= new ArrayList<ArrayList<Double>>(); ;
	public static ArrayList<ArrayList<Double>> extpJerkList= new ArrayList<ArrayList<Double>>(); ;
	
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


	public static void searchFileAndSaveStringRows( File file, ArrayList<String[]> rowList){	//searches for term @DATA and starts reading after that line
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
	
	public static void searchRowsandSaveStringValues( File file, ArrayList<String[]> rowList){	//searches for term @DATA and starts reading after that line
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
	
	public static void rowExtractor(Scanner sc,  ArrayList<String[]> rowList){// extracts each line of the arff file
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
	
	public static void valueExtractor( String [] row,ArrayList<String[]> valList){// extracts each value/attribute of each row of the arff file
		//split file into columns by commas
		//BufferedReader br = new BufferedReader(new FileReader("C:\\readFile.txt"));
		//String line = br.readLine();
		int count =0;
		//row.
		//while(sc.hasNextLine()){
		//String line =sc.nextLine();
    	//System.out.println("DEBUG Next line:"+line);
		//String[] row = line.split("");
		//String[] lineTemp = line.split("\\r?\\n");//split each line
		//rowList.add(lineTemp);
		//String [] valueTemp =line.split(","); //split each value
		//valList.add(valueTemp);
		System.out.println("DEBUG:  ValList: "+valList.get(0));
		//System.out.println("Row" + count + "  : " + rows[count]);
		count++;
		}
	//}	
	
	
	public static void  setMetricFilesRows(ArrayList <File> speedFiles,ArrayList <File> jerkFiles, ArrayList <File> disFromRefFiles,ArrayList <File> angleFiles ){

		//save each metric file into String [] format and save in MetricsFilesRows
		for(int i =0; i <jerkFiles.size(); i++){ //number of speed,jerk, disFromRefFiles and angles should be the same
			ArrayList<String[]>jerkRows= new ArrayList<String[]>()  ; 
			searchFileAndSaveStringRows(jerkFiles.get(i), jerkRows);		
			jerkMetricsFilesRows.add(jerkRows);
			//System.out.println("DEBUG: jerkMetricsFile.get("+i+"): " + jerkMetricsFilesRows.get(i));
			//System.out.println("DEBUG: jerkMetricsFile.size " + jerkMetricsFilesRows.size());
			//System.out.println("DEBUG: jerkMetricsFile.get(i).get(0) " + jerkMetricsFilesRows.get(i).get(0).toString());

			//System.out.println("DEBUG: jerkRowsSize"+jerkRows.size());

			
			ArrayList<String[]> speedRows= new ArrayList<String[]>() ; 
			searchFileAndSaveStringRows(speedFiles.get(i), speedRows);		
			speedMetricsFilesRows.add(speedRows);
			//System.out.println("DEBUG: speedMetricsFile.get(i): " + speedMetricsFilesRows.get(i));
			//System.out.println("DEBUG: speedRowsSize "+speedMetricsFilesRows.size());
			//System.out.println("DEBUG: speedMetricsFile.size " + speedMetricsFile.size());


			ArrayList<String[]> angleRows =new ArrayList<String[]>() ; 
			searchFileAndSaveStringRows(angleFiles.get(i), angleRows);		
			angleMetricsFilesRows.add(angleRows);
			//System.out.println("DEBUG: angleMetricsFile.get(i): " + angleMetricsFilesRows.get(i));
			//System.out.println("DEBUG: angleRowsSize "+angleMetricsFilesRows.size());
			//System.out.println("DEBUG: angleMetricsFile.size " + angleMetricsFile.size());


			ArrayList<String[]> disFromRefRows =new ArrayList<String[]>() ; 
			searchFileAndSaveStringRows(disFromRefFiles.get(i), disFromRefRows);		
			disFromRefMetricsFilesRows.add(disFromRefRows);
			//System.out.println("DEBUG: disFromrefMetrics.get(i): " + disFromRefMetricsFilesRows.get(i));
			//System.out.println("DEBUG: disFromRefSize "+disFromRefMetricsFilesRows.size());
			//System.out.println("DEBUG: disFromRefMetricsFile.size " + disFromRefMetricsFile.size());
			
		}
		
	}
	
	public static void setMultiArrays(){
			
			extnpAngList.add(npAng0List);extpAngList.add(pAng0List);
			extnpAngList.add(npAng1List);extpAngList.add(pAng1List);
			extnpAngList.add(npAng2List);extpAngList.add(pAng2List);
			extnpAngList.add(npAng3List);extpAngList.add(pAng3List);
			extnpAngList.add(npAng4List);extpAngList.add(pAng4List);
			extnpAngList.add(npAng5List);extpAngList.add(pAng5List);
	
			extdisXList.add(disKey0X);extdisYList.add(disKey0Y);
			extdisXList.add(disKey1X);extdisYList.add(disKey1Y);
			extdisXList.add(disKey2X);extdisYList.add(disKey2Y);
			extdisXList.add(disKey3X);extdisYList.add(disKey3Y);
			extdisXList.add(disKey4X);extdisYList.add(disKey4Y);
			extdisXList.add(disKey5X);extdisYList.add(disKey5Y);
			extdisXList.add(disKey6X);extdisYList.add(disKey6Y);
			extdisXList.add(disKey7X);extdisYList.add(disKey7Y);
	
			extnpSpeedList.add(npKey0SpeedList);extpSpeedList.add(pKey0SpeedList);
			extnpSpeedList.add(npKey1SpeedList);extpSpeedList.add(pKey1SpeedList);
			extnpSpeedList.add(npKey2SpeedList);extpSpeedList.add(pKey2SpeedList);
			extnpSpeedList.add(npKey3SpeedList);extpSpeedList.add(pKey3SpeedList);
			extnpSpeedList.add(npKey4SpeedList);extpSpeedList.add(pKey4SpeedList);
			extnpSpeedList.add(npKey5SpeedList);extpSpeedList.add(pKey5SpeedList);
			extnpSpeedList.add(npKey6SpeedList);extpSpeedList.add(pKey6SpeedList);
			extnpSpeedList.add(npKey7SpeedList);extpSpeedList.add(pKey7SpeedList);
	
			extnpJerkList.add(npKey0JerkList);extpJerkList.add(pKey0JerkList);
			extnpJerkList.add(npKey1JerkList);extpJerkList.add(pKey1JerkList);
			extnpJerkList.add(npKey2JerkList);extpJerkList.add(pKey2JerkList);
			extnpJerkList.add(npKey3JerkList);extpJerkList.add(pKey3JerkList);
			extnpJerkList.add(npKey4JerkList);extpJerkList.add(pKey4JerkList);
			extnpJerkList.add(npKey5JerkList);extpJerkList.add(pKey5JerkList);
			extnpJerkList.add(npKey6JerkList);extpJerkList.add(pKey6JerkList);
			extnpJerkList.add(npKey7JerkList);extpJerkList.add(pKey7JerkList);
			
		}
	public static void setExtSetLists(ArrayList<ArrayList<String[]>> metricsFilesRows,ArrayList<Double>npKey0,
		ArrayList<Double>npKey1,ArrayList<Double>npKey2,ArrayList<Double>npKey3,
		ArrayList<Double>npKey4,ArrayList<Double>npKey5,ArrayList<Double>npKey6,
		ArrayList<Double>npKey7,ArrayList<Double>pKey0,
		ArrayList<Double>pKey1,ArrayList<Double>pKey2,ArrayList<Double>pKey3,
		ArrayList<Double>pKey4,ArrayList<Double>pKey5,ArrayList<Double>pKey6,
		ArrayList<Double>pKey7){
	
		for(int i =0;i< metricsFilesRows.size();i++){
			for(int j=0;j <metricsFilesRows.get(j).size(); j++){
				//valueExtractor(metricsFilesRows.get(i));
				System.out.println("DEBUG: metricsFilesRows.geti.getj.[0 "+ metricsFilesRows.get(i).get(j)[0]);//row string []
				
			}
			
			
		
	}
		
	}

	public static void main (String[] args) throws IOException{
		//loadDataFromArffFile(SPEEDFILE); 
		//loadDataFromArffFile(JERKFILE); //works
		// contains each row of a file in String []
		
		GetAllArffsForExercise.setFileLists();
		ArrayList <File> speedFiles = GetAllArffsForExercise.speedFiles;
		ArrayList <File> jerkFiles = GetAllArffsForExercise.jerkFiles;
		ArrayList <File> disFromRefFiles = GetAllArffsForExercise.disFromRefFiles;
		ArrayList <File> angleFiles = GetAllArffsForExercise.angleFiles;
		setMetricFilesRows(speedFiles,jerkFiles,disFromRefFiles,angleFiles);
			
		setExtSetLists(jerkMetricsFilesRows,npKey0JerkList,npKey1JerkList,npKey2JerkList,npKey3JerkList,npKey4JerkList,npKey5JerkList,npKey6JerkList,npKey7JerkList,
				pKey0JerkList,pKey1JerkList,pKey2JerkList,pKey3JerkList,pKey4JerkList,pKey5JerkList,pKey6JerkList,pKey7JerkList);
		//System.out.println("DEBUG: metricsFilesRows.get 0.get 5"+ jerkMetricsFilesRows.get(0).get(5));

		
		/*for (int i =0; i <jerkFiles.size(); i++){
			searchForWord(searchWord, jerkFiles.get(i));
		}*/
		
		/*for (int i =0; i <jerkFiles.size(); i++){ //go through each metrics file
				searchForWord(searchWord, jerkFiles.get(i), jerkRows);
								searchForWord(searchWord, jerkFiles.get(i), jerkRows);

		for(int i =0; i <jerkMetricsFiles.size(); i++){
			
		}
		
		
		
		
		/*for(int i =0; i <jerkMetricsFile.size(); i++){
			System.out.println("DEBUG: jerkMetricsFile.get(i): " + jerkMetricsFile.get(i));
			System.out.println("DEBUG: jerkMetricsFile size:"+jerkMetricsFile.size());
		}*/
		//split file into columns by commas
		//BufferedReader br = new BufferedReader(new FileReader("C:\\readFile.txt"));
		//String line = br.readLine();
		//String[] columns = line.split(" ");


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
