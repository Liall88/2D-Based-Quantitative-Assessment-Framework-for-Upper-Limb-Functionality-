/**
 * Author: Liall Arafa
   Imperial College London
   6 Jun 2018
2DFuglMeyer
	
 */
package arff;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;

/**
 * @author la2817
 *
 */
public class ArffWriter {

	public static void cleanAndMakeOutputDirectory (String OUTPUTFOLDER) throws IOException{
		File directory = new File(OUTPUTFOLDER);
		System.out.println("DEBUG: 1 " + directory.getAbsolutePath());
		System.out.println("DEBUG:2 " + directory.list());
	
	    if (! directory.exists()){
			System.out.println("DEBUG:Directory does not exist");
			// If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	        directory.mkdirs();
			System.out.println("DEBUG:new Directory Made");

	        
	    }
	    if (directory.list().length>0){ //directory is not empty
	    	FileUtils.cleanDirectory(directory); //clean directory
	
	    }
	}	
	
public static void writeMetricsData(BufferedWriter bw, Arff arff, int keyNum ) throws IOException{
		
			
				for(int i =0; i <arff.multilist.get(0).size();i++){
					bw.write(i + ",");
					for(int j =0; j<keyNum;j++){		
						bw.write(
								 arff.multilist.get(j).get(i).toString() + ","
								
								);
							 //System.out.println("debug:"+	 arff.multilist.get(j).get(i).toString() + ",");
					}
				//bw.write(arff.WMFTClass+"\n");
				if(i==0){
					bw.write(arff.pClass);

				}
				bw.write("\n");

				}
				
				
}


public static void writeDiffData(BufferedWriter bw, Arff arff) throws IOException{
	
	ArrayList<ArrayList<Double>>speedList= arff.speedDifflist;
	ArrayList<ArrayList<Double>>angList= arff.angleDifflist;
	ArrayList<ArrayList<Double>>jerkList= arff.jerkDifflist;

	//TODO:change this to original length after cropping data
	for(int i =0; i <jerkList.get(0).size();i++){//for 97 frames for jerk which the least must fix
			bw.write(
					i 
					+","+ speedList.get(0).get(i).toString() + 
					","+ speedList.get(1).get(i).toString() + 
					","+ speedList.get(2).get(i).toString() +
					","+ speedList.get(3).get(i).toString() +
					","+ speedList.get(4).get(i).toString() +
					","+ speedList.get(5).get(i).toString() +
					","+ speedList.get(6).get(i).toString() +
					","+ speedList.get(7).get(i).toString() +
					","+ angList.get(0).get(i).toString() + 
					","+ angList.get(1).get(i).toString() + 
					","+ angList.get(2).get(i).toString() +
					","+ angList.get(3).get(i).toString() +
					","+ angList.get(4).get(i).toString() +
					","+ angList.get(5).get(i).toString() +
					","+ jerkList.get(0).get(i).toString() + 
					","+ jerkList.get(1).get(i).toString() + 
					","+ jerkList.get(2).get(i).toString() +
					","+ jerkList.get(3).get(i).toString() +
					","+ jerkList.get(4).get(i).toString() +
					","+ jerkList.get(5).get(i).toString() +
					","+ jerkList.get(6).get(i).toString() +
					","+ jerkList.get(7).get(i).toString() +
					",");
			
					if(i==0){
						bw.write(arff.pClass);
		
					}
					bw.write("\n");
													
	}
}



/*
 * CSV methods
 */
public static void writeSingleTrialCSV(String OUTPUTFOLDER, Arff arff, String relation, int keyNum) throws IOException {
		
		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
		String myDateString = sdf.format(myDate);
		
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		
		try {
			
			fw = new FileWriter(arff.path);
			bw= new BufferedWriter(fw);
		
			
			String header="frame" +",";
			String keys="";
			for(int i=0; i <keyNum; i++){
				
				keys = keys +"key"+Integer.toString(i)+",";

			}

			bw.write(header + keys+"pClass"+"\n");
			
			
			
			
			writeMetricsData(bw,arff,keyNum);
						
			System.out.println("Done Writing "+arff.path +"");

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
	}
	
public static void writeDiffCSV(String OUTPUTFOLDER, Arff arff, String relation) throws IOException {
		
		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
		String myDateString = sdf.format(myDate);
		
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		
		try {
			
			fw = new FileWriter(arff.path);
			bw= new BufferedWriter(fw);
		
			
			String header = "frame," +
					"speedKey0,speedKey1,speedKey2,speedKey3,speedKey4,speedKey5,speedKey6,speedKey7," +
					"angKey0,angKey1,angKey2,angKey3,angKey4,angKey5," +
					"jerkKey0,jerkKey1,jerkKey2,jerkKey3,jerkKey4,jerkKey5,jerkKey6,jerkKey7,pClass" +"\n" ;
			bw.write(header);
			
			
			
			writeDiffData(bw,arff);
						
			System.out.println("Done Writing "+arff.path +"");

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
	}


	/*
	 * //Arff Methods
	 */
public static void writeTotalTrialArff(String OUTPUTFOLDER, Arff arff, String relation, int keyNum) throws IOException {
		
		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
		String myDateString = sdf.format(myDate);
		
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		
		try {
			
			fw = new FileWriter(arff.path);
			bw= new BufferedWriter(fw);
		
			
			String intro = "%" + myDateString +  "\n%Project: Training a neural network in order to correctly identify motor function " +
					"in the upper limbs from a 2D camera \n" +
					"%Author: Liall Arafa (LA2817) \n" + "%Hamlyn Centre\n" ;
			//bw.write(intro);
			
			//write header
			String relat = "@RELATION " + relation  + "\n"; 			
			//bw.write(relat);

			String header="";
			for(int i=0; i <keyNum; i++){
				
				header = header +"@ATTRIBUTE"+" "+"key"+Integer.toString(i) +" NUMERIC " +"\n";
				//header = header +"key"+Integer.toString(i) +",";

			}
			header=header + "@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +"\n \n";
			//header=header + "class" + "\n";

			bw.write(header);
			
			
			String data = "@DATA \n" ;
			//bw.write(data);
			
			
			writeMetricsData(bw,arff,keyNum);
						
			System.out.println("Done Writing "+arff.path +"");

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
	}

	public static void writeSingleTrialArff(String OUTPUTFOLDER, Arff arff, String relation, int keyNum) throws IOException {
		
		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
		String myDateString = sdf.format(myDate);
		
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		
		try {
			
			fw = new FileWriter(arff.path);
			bw= new BufferedWriter(fw);
		
			
			String intro = "%" + myDateString +  "\n%Project: Training a neural network in order to correctly identify motor function " +
					"in the upper limbs from a 2D camera \n" +
					"%Author: Liall Arafa (LA2817) \n" + "%Hamlyn Centre\n" ;
			//bw.write(intro);
			
			//write header
			String relat = "@RELATION " + relation  + "\n"; 			
			bw.write(relat);
	
			String header="";
			for(int i=0; i <keyNum; i++){
				
				header = header +"@ATTRIBUTE"+" "+"key"+Integer.toString(i) +" NUMERIC " +"\n";
				//header = header +"key"+Integer.toString(i) +",";
	
			}
			header=header + "@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +"\n \n";
			header=header + "class" + "\n";
	
			bw.write(header);
			
			
			
			
			writeMetricsData(bw,arff,keyNum);
						
			System.out.println("Done Writing "+arff.path +"");
	
			} catch (IOException e) {
	
				e.printStackTrace();
	
			} finally {
	
				try {
	
					if (bw != null)
						bw.close();
	
					if (fw != null)
						fw.close();
	
				} catch (IOException ex) {
	
					ex.printStackTrace();
	
				}
	
			}
	}
}
