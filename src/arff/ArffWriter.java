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

	public static void writeMetricsData(BufferedWriter bw, Arff arff ) throws IOException{
		
		for(int i =0; i <arff.npList.get(0).size();i++){
			bw.write(
			arff.npList.get(0).get(i).toString() + ","  +
			arff.pList.get(0).get(i).toString() + "," +
			arff.npList.get(1).get(i).toString() + "," +
			arff.pList.get(1).get(i).toString() + "," +
			arff.npList.get(2).get(i).toString() + ","  +
			arff.pList.get(2).get(i).toString() + "," +
			arff.npList.get(3).get(i).toString() + "," +
			arff.pList.get(3).get(i).toString() + "," +
			arff.npList.get(4).get(i).toString() + ","  +
			arff.pList.get(4).get(i).toString() + "," +
			arff.npList.get(5).get(i).toString() + "," +
			arff.pList.get(5).get(i).toString() + "," +
			arff.npList.get(6).get(i).toString() + ","  +
			arff.npList.get(6).get(i).toString() + "," +
			arff.npList.get(7).get(i).toString() + "," +
			arff.npList.get(7).get(i).toString() + "," +
			arff.WMFTClass									   +
					 "\n"  );
		}
		
	}
		
		public static void writeAngData(BufferedWriter bw, Arff arff) throws IOException{
			
			for(int i =0; i <arff.npList.get(0).size();i++){
				bw.write(
				arff.npList.get(0).get(i).toString() + ","  +
				arff.pList.get(0).get(i).toString() + "," +
				arff.npList.get(1).get(i).toString() + "," +
				arff.pList.get(1).get(i).toString() + "," +
				arff.npList.get(2).get(i).toString() + ","  +
				arff.pList.get(2).get(i).toString() + "," +
				arff.npList.get(3).get(i).toString() + "," +
				arff.pList.get(3).get(i).toString() + "," +
				arff.npList.get(4).get(i).toString() + ","  +
				arff.pList.get(4).get(i).toString() + "," +
				arff.npList.get(5).get(i).toString() + "," +
				arff.pList.get(5).get(i).toString() + "," +
				arff.WMFTClass									   +
						 "\n"  );
			}
			
		
	}
	public static void writeSingleTrialArff(String OUTPUTFOLDER, Arff speedArff, Arff angArff, Arff disArff, Arff jerkArff) throws IOException {
		
		//write into arff files in output directory
		File directory = new File(OUTPUTFOLDER);
		//System.out.println("DEBUG: 1 " + directory);
		//System.out.println("DEBUG:2 " + directory.list());

	    if (! directory.exists()){
			System.out.println("DEBUG:Direciry does not exist");

	        directory.mkdir();
	        // If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	    }
	    if (directory.list().length>0){ //directory is not empty
	    	FileUtils.cleanDirectory(directory); //clean directory

	    }
			
		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
		String myDateString = sdf.format(myDate);
		
		
		BufferedWriter bwSpeed = null;
		FileWriter fwSpeed = null;
		BufferedWriter bwJerk = null;
		FileWriter fwJerk = null;
		BufferedWriter bwDis = null;
		FileWriter fwDis = null;
		BufferedWriter bwAng = null;
		FileWriter fwAng = null;
		

		try {

			fwSpeed = new FileWriter(speedArff.path);
			bwSpeed = new BufferedWriter(fwSpeed);
			fwJerk = new FileWriter(jerkArff.path);
			bwJerk = new BufferedWriter(fwJerk);
			fwDis = new FileWriter(disArff.path);
			bwDis= new BufferedWriter(fwDis);
			fwAng = new FileWriter(angArff.path);
			bwAng= new BufferedWriter(fwAng);
			
			String intro = "%" + myDateString +  "\n%Project: Training a neural network in order to correctly identify motor function in the upper limbs from a 2D camera \n" +
					"%Author: Liall Arafa (LA2817) \n" + "%Hamlyn Centre\n" ;
			
			bwSpeed.write(intro);
			bwJerk.write(intro);
			bwDis.write(intro);
			bwAng.write(intro);

			
			/* ORIGINAL
			 * String speedHeader = "@RELATION speeds"  + "\n" +
					"@ATTRIBUTE npkey0SpeedList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey0SpeedList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey1SpeedList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey1SpeedList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey2SpeedList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey2SpeedList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey3SpeedList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey3SpeedList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey4SpeedList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey4SpeedList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey5SpeedList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey5SpeedList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey4SpeedList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey5SpeedList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey5SpeedList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey5SpeedList NUMERIC "  +"\n"+
					"@ATTRIBUTE pkey7SpeedList NUMERIC "  +"\n"+
					"@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +

					
					"\n \n" ;*/
			
			String speedHeader = "@RELATION speeds"  + "\n" +
					"@ATTRIBUTE df NUMERIC " +"\n"+
					"@ATTRIBUTE cef NUMERIC "  +"\n"+
					"@ATTRIBUTE ce NUMERIC " +"\n"+
					"@ATTRIBUTE cd NUMERIC "  +"\n"+
					"@ATTRIBUTE ss NUMERIC " +"\n"+
					"@ATTRIBUTE i NUMERIC "  +"\n"+
					"@ATTRIBUTE kik NUMERIC " +"\n"+
					"@ATTRIBUTE juj NUMERIC "  +"\n"+
					"@ATTRIBUTE juyj NUMERIC " +"\n"+
					"@ATTRIBUTE yuj NUMERIC "  +"\n"+
					"@ATTRIBUTE jyu NUMERIC " +"\n"+
					"@ATTRIBUTE yyyy NUMERIC "  +"\n"+
					"@ATTRIBUTE uju NUMERIC " +"\n"+
					"@ATTRIBUTE dd NUMERIC "  +"\n"+
					"@ATTRIBUTE b NUMERIC " +"\n"+
					"@ATTRIBUTE k NUMERIC "  +"\n"+
					"@ATTRIBUTE aaaa NUMERIC "  +"\n"+
					"@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +

					
					"\n \n" ;
			
			bwSpeed.write(speedHeader);
			
			String jerkHeader = "@RELATION jerks"  + "\n" +
					"@ATTRIBUTE npkey0JerkList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey0JerkList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey1JerkList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey1JerkList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey2JerkList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey2JerkList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey3JerkList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey3JerkList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey4JerkList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey4JerkList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey5JerkList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey5JerkList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey6JerkList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey6JerkList NUMERIC "  +"\n"+
					"@ATTRIBUTE npkey7JerkSpeedList NUMERIC " +"\n"+
					"@ATTRIBUTE pkey7JerkSpeedList NUMERIC "  +"\n"+
					"@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +

					
					"\n \n" ;
			bwJerk.write(jerkHeader);
			
			String disHeader = "@RELATION disFromRefTraj"  + "\n" +
					"@ATTRIBUTE disKey0X NUMERIC " +"\n"+
					"@ATTRIBUTE disKey0Y NUMERIC "  +"\n"+
					"@ATTRIBUTE disKey1X NUMERIC " +"\n"+
					"@ATTRIBUTE disKey1Y NUMERIC "  +"\n"+
					"@ATTRIBUTE disKey2X NUMERIC " +"\n"+
					"@ATTRIBUTE disKey2Y NUMERIC "  +"\n"+
					"@ATTRIBUTE disKey3X NUMERIC " +"\n"+
					"@ATTRIBUTE disKey3Y NUMERIC "  +"\n"+
					"@ATTRIBUTE disKey4X NUMERIC " +"\n"+
					"@ATTRIBUTE disKey4Y NUMERIC "  +"\n"+
					"@ATTRIBUTE disKey5X NUMERIC " +"\n"+
					"@ATTRIBUTE disKey5Y NUMERIC "  +"\n"+
					"@ATTRIBUTE disKey6X NUMERIC " +"\n"+
					"@ATTRIBUTE disKey6Y NUMERIC "  +"\n"+
					"@ATTRIBUTE disKey7X NUMERIC " +"\n"+
					"@ATTRIBUTE disKey7Y NUMERIC "  +"\n"+
					"@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +

					
					"\n \n" ;
			bwDis.write(disHeader);
			
			String AngHeader = "@RELATION angles"  + "\n" +
					"@ATTRIBUTE npAng0 NUMERIC " +"\n"+
					"@ATTRIBUTE pAng0  NUMERIC "  +"\n"+
					"@ATTRIBUTE npAng1 NUMERIC " +"\n"+
					"@ATTRIBUTE pAng1  NUMERIC "  +"\n"+
					"@ATTRIBUTE npAng2 NUMERIC " +"\n"+
					"@ATTRIBUTE pAng2  NUMERIC "  +"\n"+
					"@ATTRIBUTE npAng3 NUMERIC " +"\n"+
					"@ATTRIBUTE pAng3  NUMERIC "  +"\n"+
					"@ATTRIBUTE npAng4 NUMERIC " +"\n"+
					"@ATTRIBUTE pAng4  NUMERIC "  +"\n"+
					"@ATTRIBUTE npAng5 NUMERIC " +"\n"+
					"@ATTRIBUTE pAng5  NUMERIC "  +"\n"+
					"@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +

					"\n \n" ;
			bwAng.write(AngHeader);
			
			String data = "@DATA \n" ;
			bwSpeed.write(data);
			bwAng.write(data);
			bwDis.write(data);
			bwJerk.write(data);
			
			writeMetricsData(bwSpeed,speedArff);
			writeAngData(bwAng,angArff);
			writeMetricsData(bwDis,disArff);
			writeMetricsData(bwJerk,jerkArff);			
			System.out.println("Done Writing Data to Files");

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (bwSpeed != null)
						bwSpeed.close();

					if (fwSpeed != null)
						fwSpeed.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}
				try {

					if (bwJerk != null)
						bwJerk.close();

					if (fwJerk != null)
						fwJerk.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}
				try {

					if (bwDis != null)
						bwDis.close();

					if (fwDis != null)
						fwDis.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
	}
	
}
