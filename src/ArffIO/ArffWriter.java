package ArffIO;
/*package dataExtraction;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

/**
 * Author: Liall Arafa
   Imperial College London
   2 Jun 2018
2DFuglMeyer
	
 TODO: finish putting all arff writer in OutputTemporalData functionality into this class
 This class writes the extracted kinematic features into arff files in preperation for machine learning 

/**
 * @author la2817
 *
 
public class ArffWriter {
	//change these parameters for each trial to generate arff files of kinematic features	
		public static final int trial=10; 	
		final static String OUTPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/symposiumCupExerciseTests/test" + trial +"Metrics"+"/";
		final static String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/openPose_outputs/testData/symposiumCupExercise/trial" +trial + "/";
		static File[] nonParFiles = new File(INPUTFOLDER + "nptest" +trial +"JSON" ).listFiles();
		static File[] parFiles = new File(INPUTFOLDER +"ptest" +trial+"JSON" ).listFiles();
		public static final boolean pIsRight=true; //Paretic limb position, change for each trial
		public static int numOfTests;//number of repetitions for each trial
		

		//where temporal analysis arff files will be stored
		final static String SPEEDFILE= OUTPUTFOLDER+ "speeds" +trial+".arff";
		final static String JERKFILE = OUTPUTFOLDER + "jerkiness" +trial+".arff";
		final static String DISFILE = OUTPUTFOLDER + "disFromRef" +trial+".arff";
		final static String ANGFILE= OUTPUTFOLDER+ "angles" +trial+".arff";
		
		public static void main (){
			 
			OutputTemporalData.setLists();
			
			
			
			
			
			// System.out.println("DEBUG: npSkeletonlist " +npSkeletonList.size());
			// System.out.println("DEBUG: pSkeletonlist " +pSkeletonList.size());
			 //create folders for each set of metric data files
				File directory = new File(OUTPUTFOLDER);
			    if (! directory.exists()){
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

					
					String speedHeader = "@RELATION speeds"  + "\n" +
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
					bwJerk.write(data);
					bwDis.write(data);

					/*for(int i =0; i <npKey0SpeedList.size();i++){
							bwSpeed.write(
							npKey0SpeedList.get(i).toString() + ","  +
							pKey0SpeedList.get(i).toString() + "," +
							npKey1SpeedList.get(i).toString() + "," +
							pKey1SpeedList.get(i).toString() + "," +
							npKey2SpeedList.get(i).toString() + ","  +
							pKey2SpeedList.get(i).toString() + "," +
							npKey3SpeedList.get(i).toString() + "," +
							pKey3SpeedList.get(i).toString() + "," +
							npKey4SpeedList.get(i).toString() + ","  +
							pKey4SpeedList.get(i).toString() + "," +
							npKey5SpeedList.get(i).toString() + "," +
							pKey5SpeedList.get(i).toString() + "," +
							npKey6SpeedList.get(i).toString() + ","  +
							pKey6SpeedList.get(i).toString() + "," +
							npKey7SpeedList.get(i).toString() + "," +
							pKey7SpeedList.get(i).toString() + "," +
							"?"									   +
									 "\n"  );
						}
					
					for(int i =0; i <npKey0JerkList.size();i++){
						bwJerk.write(
						npKey0JerkList.get(i).toString() + "," +
						pKey0JerkList.get(i).toString() + "," +
						npKey1JerkList.get(i).toString() + "," +
						pKey1JerkList.get(i).toString() + "," +
						npKey2JerkList.get(i).toString() + ","  +
						pKey2JerkList.get(i).toString() + "," +
						npKey3JerkList.get(i).toString() + "," +
						pKey3JerkList.get(i).toString() + "," +
						npKey4JerkList.get(i).toString() + ","  +
						pKey4JerkList.get(i).toString() + "," +
						npKey5JerkList.get(i).toString() + "," +
						pKey5JerkList.get(i).toString() + "," +
						npKey6JerkList.get(i).toString() + ","  +
						pKey6JerkList.get(i).toString() + "," +
						npKey7JerkList.get(i).toString() + "," +
						pKey7JerkList.get(i).toString() + "," +
						"?"										   +
								 "\n"  );
					}
				
					
					for(int i =0; i <disKey0X.size();i++){
						bwDis.write(
						disKey0X.get(i).toString() + "," +
						disKey0Y.get(i).toString() + "," +
						disKey1X.get(i).toString() + "," +
						disKey1Y.get(i).toString() + "," +
						disKey2X.get(i).toString() + "," +
						disKey2Y.get(i).toString() + "," +
						disKey3X.get(i).toString() + "," +
						disKey3Y.get(i).toString() + "," +
						disKey4X.get(i).toString() + "," +
						disKey4Y.get(i).toString() + "," +
						disKey5X.get(i).toString() + "," +
						disKey5Y.get(i).toString() + "," +
						disKey6X.get(i).toString() + "," +
						disKey6Y.get(i).toString() + "," +
						disKey7X.get(i).toString() + "," +
						disKey7Y.get(i).toString() + "," +
						"?"										   +
								 "\n"  );
					}
					
					for(int i =0; i <npSkeletonList.size();i++){
						bwAng.write(
						npAng0List.get(i).toString() + "," +
						pAng0List.get(i).toString() + "," +
						npAng1List.get(i).toString() + "," +
						pAng1List.get(i).toString() + "," +
						npAng2List.get(i).toString() + "," +
						pAng2List.get(i).toString() + "," +
						npAng3List.get(i).toString() + "," +
						pAng3List.get(i).toString() + "," +
						npAng4List.get(i).toString() + "," +
						pAng4List.get(i).toString() + "," +
						npAng5List.get(i).toString() + "," +
						pAng5List.get(i).toString() + "," +
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
					
}*/
