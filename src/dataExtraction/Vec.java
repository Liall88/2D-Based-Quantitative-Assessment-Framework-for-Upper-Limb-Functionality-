package dataExtraction;
import java.io.IOException;

import org.json.simple.parser.ParseException;

/**
 * Author: Liall Arafa
   Imperial College London
   23 Apr 2018
	
 */

/**
 * @author la2817
 *
 */
public class Vec {
	double i;
	double j; 
	
	public Vec ( double X,double Y){
		i=X;
		j=Y;
	}
	
	public static double normalise (Vec v){
		 double norm =  Math.sqrt(Math.pow(v.i, 2)+ Math.pow(v.j, 2) );
		//System.out.println("DEBUG: norm is " + norm);
		 return norm; }
	
	public static double dotProduct (Vec v1, Vec v2){
		double product = (v1.i *v2.i) +(v1.j *v2.j);
		//System.out.println("DEBUG: dot product is " + product);
		return product;
		
	}
	
	public static double getCosAngle (Vec u, Vec v){
		/*if (((u.i) =(v.i) = (u.j) = (v.j))==0.0){
			System.out.println("keypoint Missing, Angle returned as 0.0");
			return 0.0;
			
		}*/
		//else {
			double numerator = dotProduct(u,v);
			//System.out.println("DEBUG: numerator is " + numerator);
			double normU= (normalise(u));
			//System.out.println("DEBUG: normU is " + normU);
			double normV= (normalise(v));
			//System.out.println("DEBUG: normV is " + normV);
			double denominator = normU * normV;
			//System.out.println("DEBUG: denominator is " + denominator);
			double cosAngle=  Math.acos(numerator/denominator);
			//System.out.println("DEBUG: cosAngle is " + Math.toDegrees(cosAngle));
			return Math.toDegrees(cosAngle);
		//}
	}
	
	 public static void main(String[] args) throws IOException, ParseException {
	        // test program 
		 	Vec v1 = new Vec(10, 20);
		 	Vec v2 = new Vec(5, 10);
		 	double ang1 =  Math.toDegrees(getCosAngle(v1,v2));
		 	
			System.out.println("Angle1:");
			System.out.println(ang1);
		 	
	 }

}
