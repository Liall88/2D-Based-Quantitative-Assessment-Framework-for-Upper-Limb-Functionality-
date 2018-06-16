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
import java.io.*;
import java.util.ArrayList;

import arff.Arff;
import arff.ArffReader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtilities; 

import dataExtraction.MultipleTrialsData;
	
public class graphMaker {

	public static void main( String[ ] args )throws Exception {
	 
	 String folderName="longExercisesSegmented";
	 //TODO:MAKE LOCAL PATHS TO ECLIPSE,GET LOCAL INPUT AND OUTPUT FILEPATHS
	 String INPUTFOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testData/"+folderName +"/";
	 
	 ArffReader.readAllARFFs(INPUTFOLDER);
	 
	 String metric ="Jerk";
	 ArrayList<Arff> NPArffList =ArffReader.getNPJerkArffs(INPUTFOLDER);
	 System.out.println("DEBUG NPArffs.size() "+ NPArffList.size());
	 
	 ArrayList<Arff> PArffList =ArffReader.getPJerkArffs(INPUTFOLDER);
	 System.out.println("DEBUG PArffs.size() "+ PArffList.size());

	
	 //Trial to be graphed	 
	 int trial = 1;
	 Arff npTrialArff = NPArffList.get(trial);
	 Arff pTrialArff = PArffList.get(trial);
	 
	 String keypointName= "Shoulder";
	// ArrayList<Double> npKeyList = npTrialArff.multilist.get(4);   	  
	
	// ArrayList<Double> npKeyList = MultipleTrialsData.listCubicSplineInterpolator(NPArffList.get(trial).multilist.get(4),PArffList.get(trial).multilist.get(2));
	 //ArrayList<Double> pKeyList = MultipleTrialsData.listCubicSplineInterpolator(NPArffList.get(trial).multilist.get(4),PArffList.get(trial).multilist.get(1));
	 ArrayList<Double> pKeyList = MultipleTrialsData.listScale(PArffList.get(trial).multilist.get(2), 100);
	 System.out.println("DEBUG npKeyList:" +pKeyList.size());

	 
	 ArrayList<Double> npKeyList = MultipleTrialsData.listScale(NPArffList.get(trial).multilist.get(5), 100);

  	  
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
	 
	  int width = 634;   /* Width of the image */
	  int height = 484;  /* Height of the image */ 
	  File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/"+folderName+"/"+"/test" +trial+"MetricsGraphs/"+metric+keypointName+".jpeg" ); 
	      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
	   }
	}

