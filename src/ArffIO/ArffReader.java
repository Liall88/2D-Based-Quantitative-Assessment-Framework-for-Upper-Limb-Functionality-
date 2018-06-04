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
	
	//change these parameters for each trial to generate arff files of kinematic features	
	public static final int trial=0; 	
	final static String folderName="symposiumCupExercises";
	//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
	final static String OUTPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName +"/test" + trial +"Metrics"+"/";
	
	final static String SPEEDFILE= OUTPUTFOLDER+ "speeds" +trial+".arff";
	final static String JERKFILE = OUTPUTFOLDER + "jerkiness" +trial+".arff";
	final static String DISFILE = OUTPUTFOLDER + "disFromRef" +trial+".arff";
	final static String ANGFILE= OUTPUTFOLDER+ "angles" +trial+".arff";

	static File jerkFile = new File(JERKFILE);
	
	public static Instances loadDataFromArffFile(String path) throws IOException{
		ArffLoader loader = new ArffLoader();
	    loader.setSource(new File(path));
	    Instances data = loader.getDataSet();
	    
	    System.out.println("\nHeader of dataset:\n");
	    System.out.println(new Instances(data, 0));
	    //System.out.println("DEBUG : data "+data);
	    return data;
	}		
	
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
	
	public static void searchForWord(String search){
		try {
			Scanner scanner = new Scanner(jerkFile);
			while (scanner.hasNextLine()) {
		    String nextToken = scanner.nextLine();
	    	//System.out.println("DEBUG Next Token "+nextToken);
	    	if (nextToken.contains("@DATA")){
	    		System.out.println("DEBUG: FOUND @DATA");
	    		columnExtractor(scanner);//send scanner position to method
	    	}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		    }
		    
	}
	
	public static void columnExtractor(Scanner sc){
		//split file into columns by commas
		//BufferedReader br = new BufferedReader(new FileReader("C:\\readFile.txt"));
		//String line = br.readLine();
		String line =sc.nextLine();
		String[] columns = line.split(" ");
		System.out.println("Column 0 " + columns[0]);

		
	}

	public static void main (String[] args) throws IOException{
		//loadDataFromArffFile(SPEEDFILE);		
		//loadDataFromArffFile(JERKFILE); //works
		
		//Path path = Paths.get(SPEEDFILE).toAbsolutePath();
		//System.out.println("debug:  " +path.toAbsolutePath());
		//careful of this line
		//List<Object> titles =Files.lines(path).collect(Collectors.toList());
		
		//String searchWord = getInput();
		String searchWord ="@DATA";
		searchForWord(searchWord);
		
		
		//split file into columns by commas
		//BufferedReader br = new BufferedReader(new FileReader("C:\\readFile.txt"));
		//String line = br.readLine();
		//String[] columns = line.split(" ");


	}
	
	
}
