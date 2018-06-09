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

public class XYAngShoulders {

   public static void main( String[ ] args )throws Exception {
	
	    boolean pIsR = dataExtraction.MakeSingleTrialMetricArffs.pIsRight;
	    
	    dataExtraction.MakeSingleTrialMetricArffs.main(args);
		int trial = dataExtraction.MakeSingleTrialMetricArffs.trial;
		
		ArrayList <Double> pAng1List=dataExtraction.MakeSingleTrialMetricArffs.pAng1List;
		ArrayList <Double> npAng4List=dataExtraction.MakeSingleTrialMetricArffs.npAng4List;
			  
	     final XYSeries p = new XYSeries( "paretic Limb Trial" );
	      
	      for(int i =1; i<pAng1List.size();i++){
	    	  p.add(i,pAng1List.get(i));
	    	  System.out.println("p " +pAng1List.get(i));
	      }
	      
	      final XYSeries np = new XYSeries( "Non-paretic Limb Trial" );

	      for(int i =1; i<npAng4List.size();i++){
	    	  np.add(i,npAng4List.get(i));
	    	  System.out.println("np " +npAng4List.get(i));
	      }
	      
	   
	      final XYSeriesCollection dataset = new XYSeriesCollection( );
	      dataset.addSeries( np );
	      dataset.addSeries( p );

	      
	 
	      JFreeChart xylineChart = ChartFactory.createXYLineChart(
	         "Trial " + trial+ ": Angles at Paretic and Non-Paretic Shoulder", 
	         "Frame",
	         "Angle (Degrees)", 
	         dataset,
	         PlotOrientation.VERTICAL, 
	         true, true, false);
	      
	      int width = 640;   /* Width of the image */
	      int height = 480;  /* Height of the image */ 
	      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/angles/XYLineChartAngShoulders.jpeg" ); 
	      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
