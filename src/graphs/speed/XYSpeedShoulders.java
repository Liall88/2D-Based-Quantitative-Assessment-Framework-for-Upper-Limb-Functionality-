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

import dataExtraction.MakeSingleTrialMetricArffs;

public class XYSpeedShoulders {

   public static void main( String[ ] args )throws Exception {
	int trial= MakeSingleTrialMetricArffs.trial;
	dataExtraction.MakeSingleTrialMetricArffs.main(args);

	ArrayList <Double> npKey5SpeedList=dataExtraction.MakeSingleTrialMetricArffs.npKey5SpeedList;
	ArrayList <Double> pKey2SpeedList=dataExtraction.MakeSingleTrialMetricArffs.pKey2SpeedList;
		  
     final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );
      
      for(int i =1; i<npKey5SpeedList.size();i++){
    	  np.add(i,npKey5SpeedList.get(i));
    	  System.out.println("np " +npKey5SpeedList.get(i));
      }
      
      final XYSeries p = new XYSeries( "paretic Limb Trial" );

      for(int i =1; i<pKey2SpeedList.size();i++){
    	  p.add(i,pKey2SpeedList.get(i));
    	  System.out.println("p " +pKey2SpeedList.get(i));
      }
      
   
      final XYSeriesCollection dataset = new XYSeriesCollection( );
      dataset.addSeries( np );
      dataset.addSeries( p );

      
 
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Trial " + trial+ ": Speed at Paretic and Non-Paretic Shoulders", 
         "Frame",
         "Speed", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
      
      int width = 640;   /* Width of the image */
      int height = 480;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/Speed/XYLineChartSpeedShoulders.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
