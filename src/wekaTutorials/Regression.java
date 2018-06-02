/**
 * Author: Liall Arafa
   Imperial College London
   14 May 2018
	
 */
package wekaTutorials;

/**
 * @author la2817
 *
 */
/*
 *  How to use WEKA API in Java 
 *  Copyright (C) 2014 
 *  @author Dr Noureddin M. Sadawi (noureddin.sadawi@gmail.com)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it as you wish ... 
 *  I ask you only, as a professional courtesy, to cite my name, web page 
 *  and my YouTube Channel!
 *  
 *  
 *  Regression :regression is when the class value is numerical 
 */

//import required classes
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SMOreg;
//import weka.classifiers.functions.SMOreg;
public class Regression{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource source = new DataSource("/homes/la2817/Desktop/Outputs/arff_Outputs/testMetrics1/speeds.arff");
		Instances dataset = source.getDataSet();
		//set class index to the last attribute
		dataset.setClassIndex(dataset.numAttributes()-1);
		System.out.println("classindex: ");
		System.out.println(dataset.numAttributes()-1);
		
		//build model
		//LinearRegression lr = new LinearRegression();
		//lr.buildClassifier(dataset);
		//output model
		//System.out.println(lr);		
		
		//build model
		SMOreg smo = new SMOreg();
		smo.buildClassifier(dataset);
		//output model
		System.out.println(smo);
			
	}
}

