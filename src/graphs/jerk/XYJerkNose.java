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

public class XYJerkNose {

   public static void main( String[ ] args )throws Exception {
	
	dataExtraction.OutputTemporalDataMain.main(args);
	int trial = dataExtraction.OutputTemporalDataMain.trial;
	
	ArrayList <Double> npKey0JerkList=dataExtraction.OutputTemporalDataMain.npKey0JerkList;
	ArrayList <Double> pKey0JerkList=dataExtraction.OutputTemporalDataMain.pKey0JerkList;
		  
     final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );
      
      for(int i =1; i<npKey0JerkList.size();i++){
    	  np.add(i,npKey0JerkList.get(i));
    	  System.out.println("np " +npKey0JerkList.get(i));
      }
      
      final XYSeries p = new XYSeries( "paretic Limb Trial" );

      for(int i =1; i<pKey0JerkList.size();i++){
    	  p.add(i,pKey0JerkList.get(i));
    	  System.out.println("p " +pKey0JerkList.get(i));
      }
      
   
      final XYSeriesCollection dataset = new XYSeriesCollection( );
      dataset.addSeries( np );
      dataset.addSeries( p );

      
 
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Trial " + trial+ ": Jerk at Nose", 
         "Frame",
         "Jerk", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
      
      int width = 640;   /* Width of the image */
      int height = 480;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/jerk/XYLineChartJerkNose.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
