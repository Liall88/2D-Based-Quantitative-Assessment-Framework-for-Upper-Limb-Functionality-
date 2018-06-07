/**
 * Author: Liall Arafa
   Imperial College London
   11 May 2018
	
 */
package graphs.speed;

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

public class XYSpeedElbows {

   public static void main( String[ ] args )throws Exception {
	int trial= OutputTemporalDataMain.trial;
	dataExtraction.OutputTemporalDataMain.main(args);

	ArrayList <Double> npKey6SpeedList=dataExtraction.OutputTemporalDataMain.npKey6SpeedList;
	ArrayList <Double> pKey3SpeedList=dataExtraction.OutputTemporalDataMain.pKey3SpeedList;
		  
     final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );
      
      for(int i =1; i<npKey6SpeedList.size();i++){
    	  np.add(i,npKey6SpeedList.get(i));
    	  System.out.println("np " +npKey6SpeedList.get(i));
      }
      
      final XYSeries p = new XYSeries( "paretic Limb Trial" );

      for(int i =1; i<pKey3SpeedList.size();i++){
    	  p.add(i,pKey3SpeedList.get(i));
    	  System.out.println("p " +pKey3SpeedList.get(i));
      }
      
   
      final XYSeriesCollection dataset = new XYSeriesCollection( );
      dataset.addSeries( np );
      dataset.addSeries( p );

      
 
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Trial " + trial+ ": Speed at Paretic and Non-Paretic Elbows", 
         "Frame",
         "Speed", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
      
      int width = 640;   /* Width of the image */
      int height = 480;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/Speed/XYLineChartSpeedElbows.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
