/**
 * Author: Liall Arafa
   Imperial College London
   22 Jun 2018
2DFuglMeyer
	
 */
package dataExtraction;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import arff.Arff;
import arff.ArffReader;

/**
 * @author la2817
 *
 *This class is used to generate simulations of the different WMFT classes by taking the patient parietic trial
 *as WMFT 1 and  WMFT 5 by calculating the reference trajectory and dividing the positions within the interval
 
 *It returns an ArrayList<Skeleton> of simulated positions
 *TODO: clean up crefTraj method and class variables
 */
public class WMFTSimulator {
	
	private static ArrayList<Keypoint> simWMFT5Key0= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> simWMFT5Key1= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> simWMFT5Key2= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> simWMFT5Key3= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> simWMFT5Key4= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> simWMFT5Key5= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> simWMFT5Key6= new ArrayList<Keypoint>();
	private static ArrayList<Keypoint> simWMFT5Key7= new ArrayList<Keypoint>();
	
	
	//method that takes in a paretic limb SK list of WMFT 1 and a np limb SK list of WMFT 5 and returns an
	//arrayList of skeletons simulating either WMFT2, WMFT3, WMFT4
	
	public static ArrayList<Skeleton> getSimulatedWMFTSKList( int WMFTClass, ArrayList<Skeleton> pSkScaled, ArrayList <Skeleton> npSkScaled) throws IOException, ParseException{
		
		ArrayList <Skeleton> simWMFTSkList= new ArrayList<Skeleton>();
	                                      
		ArrayList<Skeleton> refTraj = getReferenceTrajectory(npSkScaled);
		
		if (WMFTClass==5){
			
			simWMFTSkList = refTraj;

		}
		
		else{
	
		for(int i=0; i < pSkScaled.size(); i++){
			
			Keypoint simK0=getSimKeypoint(WMFTClass, pSkScaled.get(i).key0,refTraj.get(i).key0);
			Keypoint simK1=getSimKeypoint(WMFTClass, pSkScaled.get(i).key1,refTraj.get(i).key1);
			Keypoint simK2=getSimKeypoint(WMFTClass, pSkScaled.get(i).key2,refTraj.get(i).key2);
			Keypoint simK3=getSimKeypoint(WMFTClass, pSkScaled.get(i).key3,refTraj.get(i).key3);
			Keypoint simK4=getSimKeypoint(WMFTClass, pSkScaled.get(i).key4,refTraj.get(i).key4);
			Keypoint simK5=getSimKeypoint(WMFTClass, pSkScaled.get(i).key5,refTraj.get(i).key5);
			Keypoint simK6=getSimKeypoint(WMFTClass, pSkScaled.get(i).key6,refTraj.get(i).key6);
			Keypoint simK7=getSimKeypoint(WMFTClass, pSkScaled.get(i).key7,refTraj.get(i).key7);
			
			Skeleton simSk= new Skeleton(simK0,simK1,simK2,simK3,simK4,simK5,simK6,simK7);		
			simWMFTSkList.add(simSk);
		}

		System.out.println("DEBUG: simWMFTskList: " +simWMFTSkList.size());
		}		
		return simWMFTSkList;

	}
	
	private static Keypoint getSimKeypoint (int WMFTClass, Keypoint wmft1Key, Keypoint wmft5Key){
		
		double xVal = getSimVal(WMFTClass, wmft1Key.x,wmft5Key.x);
		double yVal=  getSimVal(WMFTClass, wmft1Key.y,wmft5Key.y);
		return new Keypoint(xVal, yVal);
		
	}
	
	private static double getSimVal( int WMFTClass, double wmft1Val, double wmft5Val){
		double range =wmft5Val - wmft1Val;
		double boundaryStep=range/4; 
		double simVal= wmft1Val +(boundaryStep*(WMFTClass-1));
		System.out.println("DEBUG: wmftClass: "+WMFTClass);
		System.out.println("DEBUG: wmft1Val: "+wmft1Val);
		System.out.println("DEBUG: wmft5Val: "+wmft5Val);

		System.out.println("DEBUG: boundaryStep: "+boundaryStep);
		System.out.println("DEBUG: SimVal: "+simVal);
		return simVal;
		
	}
	
	//sets reference trajectory of the parietic arm (simulates WMFT5 for parietic side)
	private static ArrayList<Skeleton> getReferenceTrajectory (ArrayList <Skeleton> skList) throws IOException, ParseException{
		
		ArrayList <Skeleton> refTraj= new ArrayList<Skeleton>();
		Keypoint refKey;
		
		for(int i= 0; i <skList.size();i++){
			Keypoint K0 = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key0.x, skList.get(i).key0.y);
			Keypoint K1 = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key1.x, skList.get(i).key1.y);
			Keypoint K2  = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key2.x, skList.get(i).key2.y);
			Keypoint K3= calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key3.x, skList.get(i).key3.y);
			Keypoint K4 = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key4.x, skList.get(i).key4.y);
			Keypoint K5= calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key5.x, skList.get(i).key5.y);
			Keypoint K6 = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key6.x, skList.get(i).key6.y);
			Keypoint K7 = calculateReferenceTrajectory(skList.get(i).key1, skList.get(i).key7.x, skList.get(i).key7.y);
			refTraj.add(new Skeleton (K0,K1,K2,K3,K4,K5,K6,K7));
		}
		return refTraj;

		}
	
	private static Keypoint calculateReferenceTrajectory (Keypoint key1, double keyNx, double keyNy){
		Keypoint k1; //uses keypoint 1 as reference point as the neck does not move much
		//get displacement in x and y  of each Keypoint from Key1 as reference
		double dispFromKey1X, dispFromKey1Y, refX, refY;
			k1 = key1;
			dispFromKey1X = key1.x - keyNx;
			dispFromKey1Y=  key1.y - keyNy; 
			refX = k1.x +dispFromKey1X; 
			refY = k1.y +dispFromKey1Y;
			Keypoint refKey = new Keypoint (refX,refY);
			return refKey;
	}
	
	
	
}
