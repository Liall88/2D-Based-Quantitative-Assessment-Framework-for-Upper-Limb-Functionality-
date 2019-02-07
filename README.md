
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
 



# 2D-Based-Quantitative-Assessment-Framework-for-Upper-Limb-Functionality
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
_______________

Prerequistites: OpenPose (and it's requirements)

OpenPose :https://github.com/CMU-Perceptual-Computing-Lab/openpose

## Modules

### arff:
An arff file contains the differences in the metrics speed, angle, jerk and normalised distance between paretic and non- paretic limbs for each keypoint for each frame of a patient completing a particular exercise 

### dataExtraction -
MakeSingleTrialArffs.java (Main)-  Takes the JSON outputs from a video of a patient completing an exercise using the non-paretic limb run through OpenPose, and a video of the patient using the paretic limb run through OpenPose. Both videos must be of the same exercise but using different limbs. This class then calculates the differences in the metrics described in the arff module and outputs it in an arff file.

MultipleTrialsDataArffs.java (Main) - Takes multiple single trial arff files as input and calculates the averages of the differences (non-paretic  -   paretic) at each frame for each metric over multiple trials  of the same exercise. It outputs these averages in an arff file for each metric. These average arff files can be used in Support Vector Regression and other machine learning techniques and methods in order to quantify functionality.

### graphs - 
graphMaker.java (Main) -Used to output graphs of an arff directly from code 

### wekaTutorials  - 
Test classes for Weka


For questions please contact me at liall88@gmail.com
