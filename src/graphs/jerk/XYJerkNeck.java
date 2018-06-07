/**
 * Author: Liall Arafa
   Imperial College London
   11 May 1018
	
 */
package graphs.jerk;

/**
 * @author la1817
 *
 */
import java.io.*;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtilities; 

import dataExtraction.OutputTemporalDataMain;

public class XYJerkNeck {

   public static void main( String[ ] args )throws Exception {
	int trial= OutputTemporalDataMain.trial;
	dataExtraction.OutputTemporalDataMain.main(args);

	ArrayList <Double> npKey1JerkList=dataExtraction.OutputTemporalDataMain.npKey1JerkList;
	ArrayList <Double> pKey1JerkList=dataExtraction.OutputTemporalDataMain.pKey1JerkList;
		  
     final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );
      
      for(int i =1; i<npKey1JerkList.size();i++){
    	  np.add(i,npKey1JerkList.get(i));
    	  System.out.println("np " +npKey1JerkList.get(i));
      }
      
      final XYSeries p = new XYSeries( "paretic Limb Trial" );

      for(int i =1; i<pKey1JerkList.size();i++){
    	  p.add(i,pKey1JerkList.get(i));
    	  System.out.println("p " +pKey1JerkList.get(i));
      }
      
   
      final XYSeriesCollection dataset = new XYSeriesCollection( );
      dataset.addSeries( np );
      dataset.addSeries( p );

      
 
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Trial " + trial+ ": Jerk at Neck", 
         "Frame",
         "Jerk", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
      
      int width = 640;   /* Width of the image */
      int height = 480;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/jerk/XYLineChartJerkNeck.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
