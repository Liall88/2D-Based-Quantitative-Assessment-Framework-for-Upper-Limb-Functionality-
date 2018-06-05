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
	final static String OUTPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName +"/test" + trial +"Metrics"+"/";
	
	
	
	//2D arrays for each metric and their contents in String [] format
	static ArrayList<ArrayList<String[]>> jerkMetricsFile=  new ArrayList<ArrayList<String[]>>();
	static ArrayList<ArrayList<String[]>> speedMetricsFile=  new ArrayList<ArrayList<String[]>>();
	static ArrayList<ArrayList<String[]>> angleMetricsFile=  new ArrayList<ArrayList<String[]>>();
	static ArrayList<ArrayList<String[]>> disFromRefMetricsFile=  new ArrayList<ArrayList<String[]>>();


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
	
	public static void rowExtractor(Scanner sc,  ArrayList<String[]> rowList){// extracts each line of the arff file
		//split file into columns by commas
		//BufferedReader br = new BufferedReader(new FileReader("C:\\readFile.txt"));
		//String line = br.readLine();
		int count =0;
		while(sc.hasNextLine()){
		String line =sc.nextLine();
    	//System.out.println("DEBUG Next line:"+line);
		//String[] row = line.split("");
		String[] lineTemp = line.split("\\r?\\n");//split each line
		rowList.add(lineTemp);
		//String [] valueTemp =line.split(","); //split each value
		//System.out.println("DEBUG: row temp[0] : "+valueTemp[0]);
		//System.out.println("Row" + count + "  : " + rows[count]);
		count++;
		}
	}	
	
	public static void valueExtractor(Scanner sc,  ArrayList<String[]> rowList){// extracts each line of the arff file
		//split file into columns by commas
		//BufferedReader br = new BufferedReader(new FileReader("C:\\readFile.txt"));
		//String line = br.readLine();
		int count =0;
		while(sc.hasNextLine()){
		String line =sc.nextLine();
    	//System.out.println("DEBUG Next line:"+line);
		//String[] row = line.split("");
		String[] lineTemp = line.split("\\r?\\n");//split each line
		rowList.add(lineTemp);
		//String [] valueTemp =line.split(","); //split each value
		//System.out.println("DEBUG: row temp[0] : "+valueTemp[0]);
		//System.out.println("Row" + count + "  : " + rows[count]);
		count++;
		}
	}	
	
	
	
	public static ArrayList<String> getAverageAcrossTrials(){
		
		return new ArrayList<String> ();
	
	}

	public static void main (String[] args) throws IOException{
		//loadDataFromArffFile(SPEEDFILE);		
		//loadDataFromArffFile(JERKFILE); //works
		// contains each row of a file in String []
		
		String searchWord ="@DATA";

		GetAllTrials.setFileLists();
		ArrayList <File> speedFiles = GetAllTrials.speedFiles;
		ArrayList <File> jerkFiles = GetAllTrials.jerkFiles;
		ArrayList <File> disFromRefFiles = GetAllTrials.disFromRefFiles;
		ArrayList <File> angleFiles = GetAllTrials.angleFiles;
		
		/*for (int i =0; i <jerkFiles.size(); i++){
			searchForWord(searchWord, jerkFiles.get(i));
		}*/
		
		/*for (int i =0; i <jerkFiles.size(); i++){ //go through each metrics file
				searchForWord(searchWord, jerkFiles.get(i), jerkRows);
								searchForWord(searchWord, jerkFiles.get(i), jerkRows);

;  
	}*/
		for(int i =0; i <jerkFiles.size(); i++){		
			ArrayList<String[]>jerkRows= new ArrayList<String[]>()  ; 
			searchFileAndSaveStringRows(jerkFiles.get(i), jerkRows);		
			jerkMetricsFile.add(jerkRows);
			//System.out.println("DEBUG: jerkMetricsFile.get("+i+"): " + jerkMetricsFile.get(i));
			System.out.println("DEBUG: jerkMetricsFile.size " + jerkMetricsFile.size());

			//System.out.println("DEBUG: jerkRowsSize"+jerkRows.size());

			
			ArrayList<String[]> speedRows= new ArrayList<String[]>() ; 
			searchFileAndSaveStringRows(speedFiles.get(i), speedRows);		
			speedMetricsFile.add(speedRows);
			//System.out.println("DEBUG: speedMetricsFile.get(i): " + speedMetricsFile.get(i));
			//System.out.println("DEBUG: speedRowsSize "+speedRows.size());
			System.out.println("DEBUG: speedMetricsFile.size " + speedMetricsFile.size());


			
			ArrayList<String[]> angleRows =new ArrayList<String[]>() ; 
			searchFileAndSaveStringRows(angleFiles.get(i), angleRows);		
			angleMetricsFile.add(angleRows);
			//System.out.println("DEBUG: angleMetricsFile.get(i): " + angleMetricsFile.get(i));
			//System.out.println("DEBUG: angleRowsSize "+angleRows.size());
			System.out.println("DEBUG: angleMetricsFile.size " + angleMetricsFile.size());


			ArrayList<String[]> disFromRefRows =new ArrayList<String[]>() ; 
			searchFileAndSaveStringRows(disFromRefFiles.get(i), disFromRefRows);		
			disFromRefMetricsFile.add(disFromRefRows);
			//System.out.println("DEBUG: disFromrefMetrics.get(i): " + disFromRefMetricsFile.get(i));
			//System.out.println("DEBUG: disFromRefSize "+disFromRefRows.size());
			System.out.println("DEBUG: disFromRefMetricsFile.size " + disFromRefMetricsFile.size());



			
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
	    //System.out.println("DEBUG : data "+data);
	    return data;
	} 
	/*
	//2D Array with arraylist outisde representing each file and inside representing line of each file 
	
	  
	
	/*public static void fileReader() throws IOException{//looks for @Data section and splits file into commas by ","
		Path path = Paths.get(SPEEDFILE).toAbsolutePath();
		System.out.println("debug:  " +path.toAbsolutePath());
		//careful of this line
		//List<String> titles =Files.lines(path).collect(Collectors.toList());
		
		//split file into columns by commas
		//BufferedReader br = new BufferedReader(new FileReader("C:\\readFile.txt"));
		//String line = br.readLine();
		//String[] columns = line.split(" ");

		String searchWord = getInput();
		
	}*/
	
}
