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

public class XYSpeedNose {

   public static void main( String[ ] args )throws Exception {
	
	dataExtraction.OutputTemporalData.main(args);
	int trial = dataExtraction.OutputTemporalData.trial;
	
	ArrayList <Double> npKey0SpeedList=dataExtraction.OutputTemporalData.npKey0SpeedList;
	ArrayList <Double> pKey0SpeedList=dataExtraction.OutputTemporalData.pKey0SpeedList;
		  
     final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );
      
      for(int i =1; i<npKey0SpeedList.size();i++){
    	  np.add(i,npKey0SpeedList.get(i));
    	  System.out.println("np " +npKey0SpeedList.get(i));
      }
      
      final XYSeries p = new XYSeries( "paretic Limb Trial" );

      for(int i =1; i<pKey0SpeedList.size();i++){
    	  p.add(i,pKey0SpeedList.get(i));
    	  System.out.println("p " +pKey0SpeedList.get(i));
      }
      
   
      final XYSeriesCollection dataset = new XYSeriesCollection( );
      dataset.addSeries( np );
      dataset.addSeries( p );

      
 
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Trial " + trial+ ": Speed at Nose", 
         "Frame",
         "Speed", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
      
      int width = 640;   /* Width of the image */
      int height = 480;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/Speed/XYLineChartSpeedNose.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
