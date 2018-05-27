package wekaTutorials;
/**
 * Author: Liall Arafa
   Imperial College London
   11 May 2018
	
 */

/**
 * @author la2817
 *
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
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.functions.SMO;
public class Classification{
	public static void main(String args[]) throws Exception{
		//load dataset
		//where temporal analysis arff files will be stored
		/*int trial= TemporalData.trial;
		final  String FOLDER = "/homes/la2817/Desktop/Outputs/arff_Outputs/testMetrics" + trial +"/";
		final  String SPEEDFILE = FOLDER+ "speeds" +".arff";
		final  String JERKFILE = FOLDER + "jerkiness" +".arff";
		final  String DISFILE = FOLDER + "disFromRef" +".arff";*/
		
		DataSource source = new DataSource("/homes/la2817/Desktop/Outputs/arff_Outputs/iris/iris.arff");
		Instances dataset = source.getDataSet();	
		//set class index to the last attribute
		dataset.setClassIndex(dataset.numAttributes()-1);
		
		//create and build the classifier!
		NaiveBayes nb = new NaiveBayes(); 
		nb.buildClassifier(dataset);
		//print out capabilities
		System.out.println("NaiveBayes: " + nb.getCapabilities().toString());
		
		SMO svm = new SMO();
		svm.buildClassifier(dataset);
		System.out.println("SVM: " +svm.getCapabilities().toString());
		
		//options for tree
		String[] options = new String[4];
		options[0] = "-C"; options[1] = "0.11";
		options[2] = "-M"; options[3] = "3";
		
		J48 tree = new J48();
		tree.setOptions(options);
		tree.buildClassifier(dataset);
		System.out.println("Decision Tree:"+ tree.getCapabilities().toString());
		System.out.println(tree.graph());
		
	}
}