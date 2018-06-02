/**
 * Author: Liall Arafa
   Imperial College London
   29 May 2018
2DFuglMeyer
	
 */
package wekaTutorials;

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
 */

//import required classes
import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class AssocRules{
	public static void main(String args[]) throws Exception{
		//load dataset
		String dataset =  "/homes/la2817/Desktop/Outputs/arff_Outputs/iris/weatherNominal.arff";
		DataSource source = new DataSource(dataset);
		//get instances object 
		Instances data = source.getDataSet();
		//the Apriori algorithm
		Apriori model = new Apriori();
		//build model
		model.buildAssociations(data);
		//print out the extracted rules
		System.out.println(model);
	}
}