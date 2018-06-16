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

	
public static void writeMetricsData(BufferedWriter bw, Arff arff, int keyNum ) throws IOException{
		
		for(int i =0; i <arff.multilist.get(0).size();i++){
			for(int j =0; j<keyNum;j++){
				bw.write(
				arff.multilist.get(j).get(i).toString() + ","  );
				//arff.list.get(j).get(i).toString() + "," );
			}
		bw.write(arff.WMFTClass+"\n");
		}
}

	public static void cleanOutputDirectory (String OUTPUTFOLDER) throws IOException{
		File directory = new File(OUTPUTFOLDER);
		System.out.println("DEBUG: 1 " + directory.getAbsolutePath());
		System.out.println("DEBUG:2 " + directory.list());
	
	    if (! directory.exists()){
			System.out.println("DEBUG:Directory does not exist");
	        directory.mkdirs();
			System.out.println("DEBUG:new Directory Made");

	        // If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	    }
	    if (directory.list().length>0){ //directory is not empty
	    	FileUtils.cleanDirectory(directory); //clean directory
	
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
			bw.write(intro);
			
			//write header
			String relat = "@RELATION " + relation  + "\n"; 			
			bw.write(relat);

			String header="";
			for(int i=0; i <keyNum; i++){
				
				header = header +"@ATTRIBUTE"+" "+"key"+Integer.toString(i) +" NUMERIC " +"\n";
				
			}
			header=header + "@ATTRIBUTE class {WMFT0, WMFT1, WMFT2,WMFT3,WMFT4,WMFT5} "  +"\n \n";

			bw.write(header);
			
			
			String data = "@DATA \n" ;
			bw.write(data);
			
			
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
