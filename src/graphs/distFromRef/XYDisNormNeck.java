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

public class XYDisNormNeck {

   public static void main( String[ ] args )throws Exception {
	
	dataExtraction.OutputTemporalDataMain.main(args);
	int trial = dataExtraction.OutputTemporalDataMain.trial;
	
	ArrayList <Double> disKey1Norm=dataExtraction.OutputTemporalDataMain.disKey1Norm;
		  
     final XYSeries xDis = new XYSeries( "paretic Limb Trial" );
      
      for(int i =1; i<disKey1Norm.size();i++){
    	  xDis.add(i,disKey1Norm.get(i));
    	  //System.out.println("x " +disKey1Norm.get(i));
      }
      
  
   
      final XYSeriesCollection dataset = new XYSeriesCollection( );
      dataset.addSeries( xDis );
      
 
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Trial " + trial+ ": Normalised Distance of Paretic Neck from Reference Neck", 
         "Frame",
         "Distance", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
      
      int width = 614;   /* Width of the image */
      int height = 484;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/disFromRef/XYLineChartDisNeck.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}
