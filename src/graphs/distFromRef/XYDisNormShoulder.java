/**
 * Author: Liall Arafa
   Imperial College London
   11 May 2218
	
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

public class XYDisNormShoulder {

   public static void main( String[ ] args )throws Exception {
	
	dataExtraction.OutputTemporalData.main(args);
	int trial = dataExtraction.OutputTemporalData.trial;
	
	ArrayList <Double> disKey2Norm=dataExtraction.OutputTemporalData.disKey2Norm;
		  
     final XYSeries xDis = new XYSeries( "paretic Limb Trial" );
      
      for(int i =1; i<disKey2Norm.size();i++){
    	  xDis.add(i,disKey2Norm.get(i));
    	  //System.out.println("x " +disKey2Norm.get(i));
      }
      
  
   
      final XYSeriesCollection dataset = new XYSeriesCollection( );
      dataset.addSeries( xDis );
      
 
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Trial " + trial+ ": Normalised Distance of Paretic Shoulder from Reference Shoulder", 
         "Frame",
         "Distance", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
      
      int width = 644;   /* Width of the image */
      int height = 444;  /* Height of the image */ 
      File XYChart = new File( "/homes/la2817/Desktop/Outputs/graphs/test" +trial+"MetricsGraphs/disFromRef/XYLineChartDisShoulder.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
}