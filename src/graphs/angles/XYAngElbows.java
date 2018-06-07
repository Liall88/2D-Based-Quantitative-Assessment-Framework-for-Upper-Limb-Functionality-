/**
 * Author: Liall Arafa
   Imperial College London
   11 May 2318
	
 */
package graphs.angles;

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

public class XYAngElbows {

   public static void main( String[ ] args )throws Exception {
	
	   dataExtraction.OutputTemporalDataMain.main(args);
		int trial = dataExtraction.OutputTemporalDataMain.trial;
		
		ArrayList <Double> pAng2List=dataExtraction.OutputTemporalDataMain.npAng2List;
		ArrayList <Double> npAng5List=dataExtraction.OutputTemporalDataMain.pAng5List;
			  
	     final XYSeries p = new XYSeries( "paretic Limb Trial" );
	      
	      for(int i =1; i<pAng2List.size();i++){
	    	  p.add(i,pAng2List.get(i));
	    	  System.out.println("p " +pAng2List.get(i));
	      }
	      
	      final XYSeries np = new XYSeries( "non-paretic Limb Trial" );

	      for(int i =1; i<npAng5List.size();i++){
	    	  np.add(i,npAng5List.get(i));
	    	  System.out.println("np " +npAng5List.get(i));
	      }
	      
	   
	      final XYSeriesCollection dataset = new XYSeriesCollection( );
	      dataset.addSeries( np );
	      dataset.addSeries( p );

	      
	 
	      JFreeChart xylineChart = ChartFactory.createXYLineChart(
	         "Trial " + trial+ ": Angles at Paretic and Non-Paretic Elbows", 
	         "Frame",
	         "Angle (Degrees)", 
	         dataset,
	         PlotOrientation.VERTICAL, 
	         true, true, false);
	      
	      int width = 640;   /* Width of the image */
	      int height = 480;  /* Height of the image */ 
	      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/angles/XYLineChartAngElbows.jpeg" ); 
	      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
