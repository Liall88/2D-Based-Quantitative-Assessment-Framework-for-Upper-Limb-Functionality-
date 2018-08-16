/**
 * Author: Liall Arafa
   Imperial College London
   11 Jun 2018
2DFuglMeyer
	
 */
package graphs.generic;

/**
 * @author la2817
 *
 */
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.util.ArrayList;

import arff.Arff;
import arff.ArffReader;
import arff.ArffWriter;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtilities; 

import dataExtraction.MultipleTrialsData;
	
public class graphMaker {
	
	public static void main( String[ ] args )throws Exception {
		String folderName="simulatedExercises/WMFT5";
		//TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
		String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName +"/";
		int trial=1;
		String metric ="Jerk";
		String unit ="pixel /f^3";
		String keypointName="Wrist";
		int npKeypoint =7;
		int pKeypoint=4;
		//String OUTPUTFOLDER ="/homes/la2817/Desktop/Outputs/graphs/"+folderName+"/"+"/test" +trial+"MetricsGraphs/"+metric+keypointName+".jpeg";
		String OUTPUTFOLDER="/homes/la2817/Desktop/Outputs/graphs/"+folderName+"/"+"AVRGMetricsGraphs/";
		String FILENAME =metric+keypointName+".jpeg";
		
		ArffReader.readAllARFFs(INPUTFOLDER);
		ArrayList<Arff> npArffs =ArffReader.getNPJerkArffs(INPUTFOLDER);
		System.out.println("DEBUG NPArffs.size() "+ npArffs.size());
		 
		ArrayList<Arff> pArffs =ArffReader.getPJerkArffs(INPUTFOLDER);
		System.out.println("DEBUG PArffs.size() "+ pArffs.size());
		
		//scaling trials before graphing
		/*ArrayList<Double> nptrial0 = MultipleTrialsData.scaleList(npArffs.get(0).multilist.get(npKeypoint), 100);
		ArrayList<Double> nptrial1 = MultipleTrialsData.scaleList(npArffs.get(1).multilist.get(npKeypoint), 100);
		ArrayList<Double> nptrial2= MultipleTrialsData.scaleList(npArffs.get(2).multilist.get(npKeypoint), 100);
		ArrayList<Double> nptrial3 = MultipleTrialsData.scaleList(npArffs.get(3).multilist.get(npKeypoint), 100);
		ArrayList<Double> nptrial4 = MultipleTrialsData.scaleList(npArffs.get(4).multilist.get(npKeypoint), 100);
		ArrayList<Double> nptrial5 = MultipleTrialsData.scaleList(npArffs.get(5).multilist.get(npKeypoint), 100);
		ArrayList<Double> nptrial6 = MultipleTrialsData.scaleList(npArffs.get(6).multilist.get(npKeypoint), 100);
		ArrayList<Double> nptrial7 = MultipleTrialsData.scaleList(npArffs.get(7).multilist.get(npKeypoint), 100);
		ArrayList<Double> nptrial8 = MultipleTrialsData.scaleList(npArffs.get(8).multilist.get(npKeypoint), 100);
		ArrayList<Double> nptrial9 = MultipleTrialsData.scaleList(npArffs.get(9).multilist.get(npKeypoint), 100);
		

		ArrayList<Double> ptrial0 = MultipleTrialsData.scaleList(pArffs.get(0).multilist.get(pKeypoint), 100);
		ArrayList<Double> ptrial1 = MultipleTrialsData.scaleList(pArffs.get(1).multilist.get(pKeypoint), 100);
		ArrayList<Double> ptrial2 = MultipleTrialsData.scaleList(pArffs.get(2).multilist.get(pKeypoint), 100);
		ArrayList<Double> ptrial3 = MultipleTrialsData.scaleList(pArffs.get(3).multilist.get(pKeypoint), 100);
		ArrayList<Double> ptrial4 = MultipleTrialsData.scaleList(pArffs.get(4).multilist.get(pKeypoint), 100);
		ArrayList<Double> ptrial5= MultipleTrialsData.scaleList(pArffs.get(5).multilist.get(pKeypoint), 100);
		ArrayList<Double> ptrial6 = MultipleTrialsData.scaleList(pArffs.get(6).multilist.get(pKeypoint), 100);
		ArrayList<Double> ptrial7= MultipleTrialsData.scaleList(pArffs.get(7).multilist.get(pKeypoint), 100);
		ArrayList<Double> ptrial8 = MultipleTrialsData.scaleList(pArffs.get(8).multilist.get(pKeypoint), 100);
		ArrayList<Double> ptrial9 = MultipleTrialsData.scaleList(pArffs.get(9).multilist.get(pKeypoint), 100);

		ArrayList<ArrayList<Double>> npList= new ArrayList<ArrayList<Double>>();
		npList.add(nptrial0);
		npList.add(nptrial1);
		npList.add(nptrial2);
		npList.add(nptrial3);
		npList.add(nptrial4);
		npList.add(nptrial5);
		npList.add(nptrial6);
		npList.add(nptrial7);
		npList.add(nptrial8);
		npList.add(nptrial9);

		ArrayList<ArrayList<Double>> pList= new ArrayList<ArrayList<Double>>();
		pList.add(ptrial0);
		pList.add(ptrial1);
		pList.add(ptrial2);
		pList.add(ptrial3);
		pList.add(ptrial4);
		pList.add(ptrial5);
		pList.add(ptrial6);
		pList.add(ptrial7);
		pList.add(ptrial8);
		pList.add(ptrial9);*/		
		
		ArrayList<ArrayList<Double>> npList= new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> pList= new ArrayList<ArrayList<Double>>();

		
		for(int i=0; i<npArffs.size();i++){
			
			npList.add(npArffs.get(i).multilist.get(npKeypoint));
			npList.add(npArffs.get(i).multilist.get(npKeypoint));
			
		}
		for(int i=0; i<npArffs.size();i++){
				
			pList.add(pArffs.get(i).multilist.get(pKeypoint));
			pList.add(pArffs.get(i).multilist.get(pKeypoint));
				
			}
		
		System.out.println("DEBUG: pList.get(0).size" + pList.get(0).size());
		System.out.println("DEBUG: npList.get(0).size" + npList.get(0).size());

		
		ArrayList<Double> avrgNPList = MultipleTrialsData.averageSingleList(npList);
		ArrayList<Double> avrgPList = MultipleTrialsData.averageSingleList(pList);

		makeGraphFromLists(INPUTFOLDER,OUTPUTFOLDER,folderName,FILENAME,trial, metric,unit,keypointName, avrgNPList,avrgPList);
		//makeGraphFromLists(INPUTFOLDER,OUTPUTFOLDER,trial, metric,unit,keypointName, npList,pList);

		//makeGraphFromArffs(INPUTFOLDER,OUTPUTFOLDER,trial, metric,keypointName, npKeypoint, pKeypoint);

	   }
	
		
		
	public static void makeGraphFromLists(String INPUTFOLDER, String OUTPUTFOLDER, String NAME, String FILENAME, int trial, String metric, String unit, String keypointName, 
			ArrayList<Double> npKeyList,ArrayList<Double> pKeyList ) throws IOException{
		
		 //System.out.println("DEBUG PKeyListSize:" +pKeyList.size());
		 final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );
		 for(int i =1; i<npKeyList.size();i++){
		  np.add(i,npKeyList.get(i));
		  //System.out.println("np " +npKeyList.get(i));
		 }
		
		 final XYSeries p = new XYSeries( "Paretic Limb Trial" );
		 for(int i =1; i<pKeyList.size();i++){
		  	  p.add(i,pKeyList.get(i));
		  	 // System.out.println("p " +npKeyList.get(i));
		  }
		     
	     final XYSeriesCollection dataset = new XYSeriesCollection( );
	     dataset.addSeries( np );
	     dataset.addSeries( p );
	 		
		 JFreeChart xylineChart = ChartFactory.createXYLineChart(
		NAME + " "+ "Avg."+" "+ metric + " "+ "Non-Paretic and Paretic" + " " + keypointName,
		"Frame (f)",
		metric+ " "+"("+ unit+")", 
		dataset,
		PlotOrientation.VERTICAL, 
		true, true, false);
		 
		 xylineChart.getPlot().setBackgroundPaint(Color.WHITE);
		 
		// CategoryPlot cp = xylineChart.getCategoryPlot(); 
		// ValueAxis yaxis = cp.getRangeAxis();
		// CategoryAxis xaxis = cp.getDomainAxis();
		// Font font = new Font("Dialog", Font.PLAIN, 30);
		// xaxis.setTickLabelFont(font);
		 
		 Font font0 = new Font("Dialog", Font.PLAIN, 15); 
		 Font font1 = new Font("Dialog", Font.PLAIN, 20); 
		 xylineChart.getXYPlot().getDomainAxis().setLabelFont(font1);
		 xylineChart.getXYPlot().getRangeAxis().setLabelFont(font1);
		 xylineChart.getXYPlot().getDomainAxis().setTickLabelFont(font0);
		 xylineChart.getXYPlot().getRangeAxis().setTickLabelFont(font0);
		 xylineChart.getXYPlot().setDomainGridlinePaint(Color.GRAY);
		 xylineChart.getXYPlot().setRangeGridlinePaint(Color.GRAY);

		  int width = 634;   /* Width of the image */
		  int height = 484;  /* Height of the image */ 	
		  
		  makeOutputDirectory(OUTPUTFOLDER);
		  File XYChart = new File(OUTPUTFOLDER+FILENAME);
		  ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
				
	}
	

	public static void makeOutputDirectory (String OUTPUTFOLDER) throws IOException{
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
	    	//FileUtils.cleanDirectory(directory); //clean directory
	
	    }
	}	
/*public static void makeGraphFromArffs(String INPUTFOLDER, String OUTPUTFOLDER, int trial, String metric, String keypointName, int npKeypoint, int pKeypoint) throws IOException{

ArffReader.readAllARFFs(INPUTFOLDER);
ArrayList<Arff> NPArffList =ArffReader.getNPSpeedArffs(INPUTFOLDER);
 System.out.println("DEBUG NPArffs.size() "+ NPArffList.size());
 
 ArrayList<Arff> PArffList =ArffReader.getPSpeedArffs(INPUTFOLDER);
 System.out.println("DEBUG PArffs.size() "+ PArffList.size());


 //Trial to be graphed	 
 
 Arff npTrialArff = NPArffList.get(trial);
 Arff pTrialArff = PArffList.get(trial);
 
// ArrayList<Double> npKeyList = npTrialArff.multilist.get(4);   	  

// ArrayList<Double> npKeyList = MultipleTrialsData.listCubicSplineInterpolator(NPArffList.get(trial).multilist.get(4),PArffList.get(trial).multilist.get(2));
 //ArrayList<Double> pKeyList = MultipleTrialsData.listCubicSplineInterpolator(NPArffList.get(trial).multilist.get(4),PArffList.get(trial).multilist.get(1));
 ArrayList<Double> pKeyList = MultipleTrialsData.scaleList(PArffList.get(trial).multilist.get(pKeypoint), 100);
 System.out.println("DEBUG npKeyList:" +pKeyList.size());
 ArrayList<Double> npKeyList = MultipleTrialsData.scaleList(NPArffList.get(trial).multilist.get(npKeypoint), 100);

	  
	  //Linear Interpolation

	  System.out.println("DEBUG PKeyListSize:" +pKeyList.size());


 final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );
 
 for(int i =1; i<npKeyList.size();i++){
	  np.add(i,npKeyList.get(i));
	  //System.out.println("np " +npKeyList.get(i));
 }
 
 final XYSeries p = new XYSeries( "paretic Limb Trial" );

 for(int i =1; i<pKeyList.size();i++){
  	  p.add(i,pKeyList.get(i));
  	 // System.out.println("p " +npKeyList.get(i));
    }
 

 final XYSeriesCollection dataset = new XYSeriesCollection( );
 dataset.addSeries( np );
 dataset.addSeries( p );

 

 JFreeChart xylineChart = ChartFactory.createXYLineChart(
    "Trial " + trial+ ":" + " " + metric + " "+ "Paretic and Non-Paretic " + keypointName,
    "Frame",
    metric, 
    dataset,
    PlotOrientation.VERTICAL, 
    true, true, false);
 
  int width = 634;   /* Width of the image 
  int height = 484;  /* Height of the image 
 // File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/"+folderName+"/"+"/test" +trial+"MetricsGraphs/"+metric+keypointName+".jpeg" ); 
   File XYChart = new File(OUTPUTFOLDER);
  ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);

}*/
	
	}

