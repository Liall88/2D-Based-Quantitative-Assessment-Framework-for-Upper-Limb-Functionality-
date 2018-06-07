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

public class XYSpeedWrists {

   public static void main( String[ ] args )throws Exception {
	int trial= OutputTemporalDataMain.trial;
	dataExtraction.OutputTemporalDataMain.main(args);

	ArrayList <Double> npKey7SpeedList=dataExtraction.OutputTemporalDataMain.npKey7SpeedList;
	ArrayList <Double> pKey4SpeedList=dataExtraction.OutputTemporalDataMain.pKey4SpeedList;
		  
     final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );
      
      for(int i =1; i<npKey7SpeedList.size();i++){
    	  np.add(i,npKey7SpeedList.get(i));
    	  System.out.println("np " +npKey7SpeedList.get(i));
      }
      
      final XYSeries p = new XYSeries( "paretic Limb Trial" );

      for(int i =1; i<pKey4SpeedList.size();i++){
    	  p.add(i,pKey4SpeedList.get(i));
    	  System.out.println("p " +pKey4SpeedList.get(i));
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
      
      int width = 740;   /* Width of the image */
      int height = 480;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/Speed/XYLineChartSpeedWrists.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
