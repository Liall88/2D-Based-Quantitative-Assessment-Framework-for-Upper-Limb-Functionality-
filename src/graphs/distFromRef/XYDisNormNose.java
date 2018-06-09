/**
 * Author: Liall Arafa
   Imperial College London
   11 May 2118
	
 */
package graphs.distFromRef;

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

public class XYDisNormNose {

   public static void main( String[ ] args )throws Exception {
	
	dataExtraction.MakeSingleTrialMetricArffs.main(args);
	int trial = dataExtraction.MakeSingleTrialMetricArffs.trial;
	
	ArrayList <Double> disKey0Norm=dataExtraction.MakeSingleTrialMetricArffs.disKey0Norm;
		  
     final XYSeries xDis = new XYSeries( "paretic Limb Trial" );
      
      for(int i =0; i<disKey0Norm.size();i++){
    	  xDis.add(i,disKey0Norm.get(i));
    	  //System.out.println("x " +disKey0Norm.get(i));
      }
      
  
   
      final XYSeriesCollection dataset = new XYSeriesCollection( );
      dataset.addSeries( xDis );
      
 
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Trial " + trial+ ": Normalised Distance of Paretic Nose from Reference Nose", 
         "Frame",
         "Distance", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
      
      int width = 614;   /* Width of the image */
      int height = 484;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/disFromRef/XYLineChartDisNose.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
