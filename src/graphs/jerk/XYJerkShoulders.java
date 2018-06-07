/**
 * Author: Liall Arafa
   Imperial College London
   11 May 2018
	
 */
package graphs.jerk;

/**
 * @author la2817
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

public class XYJerkShoulders {

   public static void main( String[ ] args )throws Exception {
	int trial= OutputTemporalDataMain.trial;
	dataExtraction.OutputTemporalDataMain.main(args);

	ArrayList <Double> npKey5JerkList=dataExtraction.OutputTemporalDataMain.npKey5JerkList;
	ArrayList <Double> pKey2JerkList=dataExtraction.OutputTemporalDataMain.pKey2JerkList;
		  
     final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );
      
      for(int i =1; i<npKey5JerkList.size();i++){
    	  np.add(i,npKey5JerkList.get(i));
    	  System.out.println("np " +npKey5JerkList.get(i));
      }
      
      final XYSeries p = new XYSeries( "paretic Limb Trial" );

      for(int i =1; i<pKey2JerkList.size();i++){
    	  p.add(i,pKey2JerkList.get(i));
    	  System.out.println("p " +pKey2JerkList.get(i));
      }
      
   
      final XYSeriesCollection dataset = new XYSeriesCollection( );
      dataset.addSeries( np );
      dataset.addSeries( p );

      
 
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Trial " + trial+ ": Jerk at Paretic and Non-Paretic Shoulders", 
         "Frame",
         "Jerk", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
      
      int width = 600;   /* Width of the image */
      int height = 800;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/jerk/XYLineChartJerkShoulders.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
