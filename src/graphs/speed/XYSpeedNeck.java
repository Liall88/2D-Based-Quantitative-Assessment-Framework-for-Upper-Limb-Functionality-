/**
 * Author: Liall Arafa
   Imperial College London
   11 May 1018
	
 */
package graphs.speed;

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

import dataExtraction.OutputTemporalData;

public class XYSpeedNeck {

   public static void main( String[ ] args )throws Exception {
	int trial= OutputTemporalData.trial;
	dataExtraction.OutputTemporalData.main(args);

	ArrayList <Double> npKey1SpeedList=dataExtraction.OutputTemporalData.npKey1SpeedList;
	ArrayList <Double> pKey1SpeedList=dataExtraction.OutputTemporalData.pKey1SpeedList;
		  
     final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );
      
      for(int i =1; i<npKey1SpeedList.size();i++){
    	  np.add(i,npKey1SpeedList.get(i));
    	  System.out.println("np " +npKey1SpeedList.get(i));
      }
      
      final XYSeries p = new XYSeries( "paretic Limb Trial" );

      for(int i =1; i<pKey1SpeedList.size();i++){
    	  p.add(i,pKey1SpeedList.get(i));
    	  System.out.println("p " +pKey1SpeedList.get(i));
      }
      
   
      final XYSeriesCollection dataset = new XYSeriesCollection( );
      dataset.addSeries( np );
      dataset.addSeries( p );

      
 
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Trial " + trial+ ": Speed at Neck", 
         "Frame",
         "Speed", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
      
      int width = 640;   /* Width of the image */
      int height = 480;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/Speed/XYLineChartSpeedNeck.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
