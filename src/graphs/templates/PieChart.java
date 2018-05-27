/**
 * Author: Liall Arafa
   Imperial College London
   11 May 2018
	
 */
package graphs.templates;

/**
 * @author la2817
 *
 */
import java.io.*;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart {
   
   public static void main( String[ ] args ) throws Exception {
      DefaultPieDataset dataset = new DefaultPieDataset( );
      dataset.setValue("IPhone 5s", new Double( 20 ) );
      dataset.setValue("SamSung Grand", new Double( 20 ) );
      dataset.setValue("MotoG", new Double( 40 ) );
      dataset.setValue("Nokia Lumia", new Double( 10 ) );

      JFreeChart chart = ChartFactory.createPieChart(
         "Mobile Sales",   // chart title
         dataset,          // data
         true,             // include legend
         true,
         false);
         
      int width = 640;   /* Width of the image */
      int height = 480;  /* Height of the image */ 
      File pieChart = new File( "/homes/la2817/Desktop/Outputs/graphs/PieChart.jpeg" ); 
      ChartUtilities.saveChartAsJPEG( pieChart , chart , width , height );
   }
}
