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
public class ArffReadAll {
	 
	/**
	 * Return the data set loaded from the Arff file at @param path
	 */
	
	/*
	 *change these parameters for each trial to generate arff files of kinematic features	
	 */
	/*
	 *	
	 */
	//Araylists of Arff objects for each trial
	static ArrayList<Arff> speedArffs;
	static ArrayList<Arff> angleArffs;
	static ArrayList<Arff> disArffs;
	static ArrayList<Arff> jerkArffs;
	
	
	//2D arrays for each metric file read and their contents in String [] format
	static ArrayList<ArrayList<String[]>> jerkMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	static ArrayList<ArrayList<String[]>> speedMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	static ArrayList<ArrayList<String[]>> angleMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	static ArrayList<ArrayList<String[]>> disFromRefMetricsFilesRows=  new ArrayList<ArrayList<String[]>>();
	
	//Data Structures for values extracted from ARFF Files:
	//WMFT Class
	

	
  //Arraylists For each Arff Object
	//ArrayLists of data read from Arff Files2D arrays for each metric and their contents in String [] format
	//2D arrays that contain arraylists of data for each keypoint(0-7)
	
	public static ArrayList<ArrayList<Double>> extnpAngList = new ArrayList<ArrayList<Double>>();
	public static ArrayList<ArrayList<Double>> extpAngList = new ArrayList<ArrayList<Double>>();

	/*public static ArrayList<Double> npAng0List = new ArrayList<Double>();
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
	public static ArrayList<Double> pAng5List = new ArrayList<Double>();*/
	
	//reference trajectory taken from non-paretic arm 

	/*public static ArrayList<Keypoint> refTrajKey0= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey1= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey2= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey3= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey4= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey5= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey6= new ArrayList<Keypoint>();
	public static ArrayList<Keypoint> refTrajKey7= new ArrayList<Keypoint>();*/
	
	//distances of keypoint in terms of x,y coordinates from reference trajectory Keypoints
	public static ArrayList<ArrayList<Double>> extdisXList= new ArrayList<ArrayList<Double>>(); ;
	public static ArrayList<ArrayList<Double>> extdisYList= new ArrayList<ArrayList<Double>>(); ;


	/*public static ArrayList<Double> disKey0X= new ArrayList<Double>();
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
	public static ArrayList<Double> disKey7Y= new ArrayList<Double>();*/
	
	
	//normalised distances of keypoint from reference trajectory
	public static ArrayList<ArrayList<Double>> extdisNormList= new ArrayList<ArrayList<Double>>(); ;

	/*public static ArrayList<Double> disKey0Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey1Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey2Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey3Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey4Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey5Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey6Norm= new ArrayList<Double>();
	public static ArrayList<Double> disKey7Norm= new ArrayList<Double>();*/
	
	
	//speed  of keypoints in terms of pixel per frame between skeletons
	public static ArrayList<ArrayList<Double>> extnpSpeedList= new ArrayList<ArrayList<Double>>(); ;
	public static ArrayList<ArrayList<Double>> extpSpeedList= new ArrayList<ArrayList<Double>>(); ;
	
	/*public static ArrayList<Double> npKey0SpeedList= new ArrayList<Double>();
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
	public static ArrayList<Double> pKey7SpeedList= new ArrayList<Double>();*/

	//jerk (or smoothness) of keypoints
	public static ArrayList<ArrayList<Double>> extnpJerkList= new ArrayList<ArrayList<Double>>(); ;
	public static ArrayList<ArrayList<Double>> extpJerkList= new ArrayList<ArrayList<Double>>(); ;
	
	/*public static ArrayList<Double> npKey0JerkList= new ArrayList<Double>();
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
	public static ArrayList<Double> pKey7JerkList= new ArrayList<Double>();*/


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
	
	//sets all arraylists for each keypoint
	public static ArrayList<Arff> getArffList(String Path,ArrayList<ArrayList<String[]>> metricsFilesRows,ArrayList<Arff> arffList ){
		
		String path =Path;
		String WMFTclass;
		ArrayList<Double> npKey0=new ArrayList<Double>();
		ArrayList<Double>npKey1=new ArrayList<Double>();
		ArrayList<Double>npKey2=new ArrayList<Double>();
		ArrayList<Double>npKey3=new ArrayList<Double>();
		ArrayList<Double>npKey4=new ArrayList<Double>();
		ArrayList<Double>npKey5=new ArrayList<Double>();
		ArrayList<Double>npKey6=new ArrayList<Double>();
		ArrayList<Double>npKey7=new ArrayList<Double>();
		ArrayList<Double>pKey0=new ArrayList<Double>();
		ArrayList<Double>pKey1=new ArrayList<Double>();
		ArrayList<Double>pKey2=new ArrayList<Double>();
		ArrayList<Double>pKey3=new ArrayList<Double>();
		ArrayList<Double>pKey4=new ArrayList<Double>();
		ArrayList<Double>pKey5=new ArrayList<Double>();
		ArrayList<Double>pKey6=new ArrayList<Double>();
		ArrayList<Double>pKey7=new ArrayList<Double>();
		
		for(int i =0;i< metricsFilesRows.size();i++){//metric file for each trial
			//System.out.println("DEBUG: metricsFilesRows.size()" +metricsFilesRows.size());				
			//System.out.println("DEBUG: metricsFilesRows.get(i)" +metricsFilesRows.get(i));
			for(int j=0;j <metricsFilesRows.get(i).size(); j++){//ArrayList of String[] for each line
				//System.out.println("DEBUG: metricsFilesRows.get(i).size()" +metricsFilesRows.get(i).size());
				//valueExtractor(metricsFilesRows.get(i));
				//System.out.println("DEBUG: metricsFilesRows.geti.getj.[0]: "+ metricsFilesRows.get(i).get(j)[0]);//row string 
				String[] tempRow = metricsFilesRows.get(i).get(j)[0].split(",");
				//for (int k=0; k <tempRow.length; k++){
					//System.out.println("DEBUG:tempRow " + tempRow[k]);//row string 
					npKey0.add(Double.parseDouble(tempRow[0]));
					npKey1.add(Double.parseDouble(tempRow[1]));
					npKey2.add(Double.parseDouble(tempRow[2]));
					npKey3.add(Double.parseDouble(tempRow[3]));
					npKey4.add(Double.parseDouble(tempRow[4]));
					npKey5.add(Double.parseDouble(tempRow[5]));
					npKey6.add(Double.parseDouble(tempRow[6]));
					npKey7.add(Double.parseDouble(tempRow[7]));
					pKey0.add(Double.parseDouble(tempRow[8]));
					pKey1.add(Double.parseDouble(tempRow[9]));
					pKey2.add(Double.parseDouble(tempRow[10]));
					pKey3.add(Double.parseDouble(tempRow[11]));
					pKey4.add(Double.parseDouble(tempRow[12]));
					pKey5.add(Double.parseDouble(tempRow[13]));
					pKey6.add(Double.parseDouble(tempRow[14]));
					pKey7.add(Double.parseDouble(tempRow[15]));
					WMFTclass =tempRow[16];
				//}
				
				ArrayList<ArrayList<Double>> extractNPList= setMultiArrays(npKey0,npKey1,npKey2,npKey3,npKey4,npKey5,npKey6,npKey7);
				ArrayList<ArrayList<Double>> extractPList= setMultiArrays(pKey0,pKey1,pKey2,pKey3,pKey4,pKey5,pKey6,pKey7);
				arffList.add(new Arff(path,WMFTclass,extractNPList,extractPList));
				}
			}
		return arffList;
		}
	
	//sets 2D arraylists holding arraylists of each keypoint

	//for angle
	public static ArrayList<ArrayList<Double>> setMultiArrays( ArrayList<Double>Key0,ArrayList<Double>Key1,ArrayList<Double>Key2,
			ArrayList<Double>Key3,ArrayList<Double>Key4,ArrayList<Double>Key5){
		ArrayList<ArrayList<Double>> multiList = new ArrayList<ArrayList<Double>>();
		multiList.add(Key0);
		multiList.add(Key1);
		multiList.add(Key2);
		multiList.add(Key3);
		multiList.add(Key4);
		multiList.add(Key5);
		
		return multiList;
	}
	//for speed,jerk, dis
	public static ArrayList<ArrayList<Double>> setMultiArrays( ArrayList<Double>Key0,ArrayList<Double>Key1,ArrayList<Double>Key2,
			ArrayList<Double>Key3,ArrayList<Double>Key4,ArrayList<Double>Key5,ArrayList<Double>Key6,
			ArrayList<Double>Key7){
		ArrayList<ArrayList<Double>> multiList = new ArrayList<ArrayList<Double>>();
		multiList.add(Key0);
		multiList.add(Key1);
		multiList.add(Key2);
		multiList.add(Key3);
		multiList.add(Key4);
		multiList.add(Key5);
		multiList.add(Key6);
		multiList.add(Key7);
	
		return multiList;
	}
	
	public static void readAllARFFs(String folder){//reads all arff Files in a directory and sets a File List for each metric
		//loadDataFromArffFile(SPEEDFILE); 
		//loadDataFromArffFile(JERKFILE); //works
		// contains each row of a file in String []
		
		//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
		GetAllArffsForExercise.setFileLists(folder);
		ArrayList <File> speedFiles = GetAllArffsForExercise.speedFiles;
		System.out.println("DEBUG: speedFiles.size"+speedFiles.size());
		ArrayList <File> jerkFiles = GetAllArffsForExercise.jerkFiles;
		System.out.println("DEBUG: jerkFiles.size"+jerkFiles.size());

		ArrayList <File> disFromRefFiles = GetAllArffsForExercise.disFromRefFiles;
		System.out.println("DEBUG: disFromRef.size"+disFromRefFiles.size());

		ArrayList <File> angleFiles = GetAllArffsForExercise.angleFiles;
		setMetricFilesRows(speedFiles,jerkFiles,disFromRefFiles,angleFiles);
		System.out.println("DEBUG: anglesFiles.size"+angleFiles.size());

	}
	
	public static ArrayList<Arff> getJerkArffs(String INPUTFOLDER){
		readAllARFFs(INPUTFOLDER);
		ArrayList<Arff> jerkArffList=getArffList(INPUTFOLDER,jerkMetricsFilesRows,jerkArffs);
		//System.out.println("DEBUG: metricsFilesRows.get 0.get 5"+ jerkMetricsFilesRows.get(0).get(5));
		
		ArrayList<Arff> jArff = new ArrayList<Arff>();
		return jArff;
		
	}
	
	
	
	
	public static void main (String[] args) throws IOException{
		String folderName="symposiumCupExercises";
		//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
		String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName +"/";
		ArrayList<Arff> jArffs=getJerkArffs(INPUTFOLDER);
		System.out.println("DEBUG jArffs.size()"+ jArffs.size());
			
	}	
		  

		
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
