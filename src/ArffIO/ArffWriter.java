/**
 * Author: Liall Arafa
   Imperial College London
   6 Jun 2018
2DFuglMeyer
	
 */
package ArffIO;

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

	public static void writeSingleTrialArff(int trial, String OUTPUTFOLDER, ArrayList<ArrayList<Double>> npAngList,ArrayList<ArrayList<Double>> pAngList,
			ArrayList<ArrayList<Double>> disXList, ArrayList<ArrayList<Double>> disYList,
			ArrayList<ArrayList<Double>> npSpeedList, ArrayList<ArrayList<Double>> pSpeedList, 
			ArrayList<ArrayList<Double>> npJerkList, ArrayList<ArrayList<Double>> pJerkList) throws IOException {
		
		//where temporal analysis arff files will be stored
		final  String SPEEDFILE= OUTPUTFOLDER+ "speeds" +trial+".arff";
		final  String JERKFILE = OUTPUTFOLDER + "jerkiness" +trial+".arff";
		final  String DISFILE = OUTPUTFOLDER + "disFromRef" +trial+".arff";
		final  String ANGFILE= OUTPUTFOLDER+ "angles" +trial+".arff";

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

			fwSpeed = new FileWriter(SPEEDFILE);
			bwSpeed = new BufferedWriter(fwSpeed);
			fwJerk = new FileWriter(JERKFILE);
			bwJerk = new BufferedWriter(fwJerk);
			fwDis = new FileWriter(DISFILE);
			bwDis= new BufferedWriter(fwDis);
			fwAng = new FileWriter(ANGFILE);
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
					"@ATTRIBUTE 76i NUMERIC "  +"\n"+
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
			bwAng.write(data);
			bwSpeed.write(data);
			bwJerk.write(data);
			bwDis.write(data);

			for(int i =0; i <npSpeedList.get(0).size();i++){
					bwSpeed.write(
					npSpeedList.get(0).get(i).toString() + ","  +
					pSpeedList.get(0).get(i).toString() + "," +
					npSpeedList.get(1).get(i).toString() + "," +
					pSpeedList.get(1).get(i).toString() + "," +
					npSpeedList.get(2).get(i).toString() + ","  +
					pSpeedList.get(2).get(i).toString() + "," +
					npSpeedList.get(3).get(i).toString() + "," +
					pSpeedList.get(3).toString() + "," +
					npSpeedList.get(4).get(i).toString() + ","  +
					pSpeedList.get(4).get(i).toString() + "," +
					npSpeedList.get(5).get(i).toString() + "," +
					pSpeedList.get(5).get(i).toString() + "," +
					npSpeedList.get(6).get(i).toString() + ","  +
					npSpeedList.get(6).get(i).toString() + "," +
					npSpeedList.get(7).get(i).toString() + "," +
					npSpeedList.get(7).get(i).toString() + "," +
					"?"									   +
							 "\n"  );
				}
			
			for(int i =0; i <npJerkList.get(0).size();i++){
				bwJerk.write(
				npJerkList.get(0).get(i).toString() + ","  +
				pJerkList.get(0).get(i).toString() + "," +
				npJerkList.get(1).get(i).toString() + "," +
				pJerkList.get(1).get(i).toString() + "," +
				npJerkList.get(2).get(i).toString() + ","  +
				pJerkList.get(2).get(i).toString() + "," +
				npJerkList.get(3).get(i).toString() + "," +
				pJerkList.get(3).toString() + "," +
				npJerkList.get(4).get(i).toString() + ","  +
				pJerkList.get(4).get(i).toString() + "," +
				npJerkList.get(5).get(i).toString() + "," +
				pJerkList.get(5).get(i).toString() + "," +
				npJerkList.get(6).get(i).toString() + ","  +
				npJerkList.get(6).get(i).toString() + "," +
				npJerkList.get(7).get(i).toString() + "," +
				npJerkList.get(7).get(i).toString() + "," +
				"?"									   +
						 "\n"  );
			}
			
			
			for(int i =0; i <disXList.get(0).size();i++){
				bwDis.write(
				disXList.get(0).get(i).toString() + "," +
				disYList.get(0).get(i).toString() + "," +
				disXList.get(1).get(i).toString() + "," +
				disYList.get(1).get(i).toString() + "," +
				disXList.get(2).get(i).toString() + "," +
				disYList.get(2).get(i).toString() + "," +
				disXList.get(3).get(i).toString() + "," +
				disYList.get(3).get(i).toString() + "," +
				disXList.get(4).get(i).toString() + "," +
				disYList.get(4).get(i).toString() + "," +
				disXList.get(5).get(i).toString() + "," +
				disYList.get(5).get(i).toString() + "," +
				disXList.get(6).get(i).toString() + "," +
				disYList.get(6).get(i).toString() + "," +
				disXList.get(7).get(i).toString() + "," +
				disYList.get(7).get(i).toString() + "," +
				"?"										+
						 "\n"  );
			}
			
			for(int i =0; i <npAngList.get(0).size();i++){
				bwAng.write(
				npAngList.get(0).get(i).toString() + "," +
				pAngList.get(0).get(i).toString() + "," +
				npAngList.get(1).get(i).toString() + "," +
				pAngList.get(1).get(i).toString() + "," +
				npAngList. get(2).get(i).toString() + "," +
				pAngList.get(2).get(i).toString() + "," +
				npAngList.get(3).get(i).toString() + "," +
				pAngList.get(3).get(i).toString() + "," +
				npAngList. get(4).get(i).toString() + "," +
				pAngList. get(4).get(i).toString() + "," +
				npAngList.get(5).get(i).toString() + "," +
				pAngList.get(5).get(i).toString() + "," +
				"?"										   +
				 "\n"  );
			}
			
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
