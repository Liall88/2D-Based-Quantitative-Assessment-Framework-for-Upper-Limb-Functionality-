
                           _ooOoo_
                          o8888888o
                          88" . "88
                          (| -_- |)
                          O\  =  /O
                       ____/`---'\____
                     .'  \\|     |//  `.
                    /  \\|||  :  |||//  \
                   /  _||||| -:- |||||_  \
                   |   | \\\  -  /'| |   |
                   | \_|  `\`---'//  |_/ |
                   \  .-\__ `-. -'__/-.  /
                 ___`. .'  /--.--\  `. .'___
              ."" '<  `.___\_<|>_/___.' _> \"".
             | | :  `- \`. ;`. _/; .'/ /  .' ; |
             \  \ `-.   \_\_`. _.'_/_/  -' _.' /
   ===========`-.`___`-.__\ \___  /__.-'_.'_.-'================
                           `=--=-'                    hjw

 

#2D-Based-Quantitative-Assessment-Framework-for-Upper-Limb-Functionality
Liall Arafa
Imperial College 
MRes Project

The increase in the world elderly population paired with a global nursing shortage has motivated
the research and development of quantitative assessment for rehabilitation. In spite of existing
assessment technology, there is yet to be a method of quantitative assessment of upper limb
functionality that has been universally adopted, with most clinicians using traditional and
subjective means. In order to mitigate this lack of adoption, this thesis presents a 2D Based
Quantitative Assessment Framework for Upper Limb Functionality aimed at providing effective
quantitative assessment while exploiting the ease of use of 2D data collection. The framework is
based on clinical standards, extracting functionality related features from 2D video of subjects
completing movement exercises in accordance with an experimental set up. Those features are
used to train support vector regression models to learn quantitative assessment functions which
generate an assessment score given a set of features.

Please read the thesis included in this project for the more detail on the background , motivation and reasoning behind the code.  
_



##Modules

###arff:
An arff file contains the differences in speed, angle, jerk and normalised distance between paretic and non- paretic limbs for each keypoint for each frame of a patient completing a particular exercise 

###dataExtraction -
MakeSingleTrialArffs.java (Main)- creates an Arff file for a single patient trial of an exercise 
MultipleTrialsDataArffs.java (Main) - Calculates the averages of the differences (WMFT5-WMFTx)at each frame for angle, speed, jerk over multiple trials and outputs in an arff file for each metric. These average arff files can be used for SVR and machine learning techniques and methods 

###graphs - 
graphMaker.java -Used to output graphs of an arff directly from code 

###wekaTutorials  - Test classes for Weka
