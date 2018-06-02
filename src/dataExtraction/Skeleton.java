package dataExtraction;

/**
 * Author: Liall Arafa
   Imperial College London
   16 Apr 2018
	
 */

/**
 * @author la2817
 *Class which represents the skeleton detected by openPose frame by frame
//ordering and location of the keypoints can be found in openPose documentation
 *
 *Since some keypoints are not detected in the frame, it can creates zero vectors,
 *making it impossible to find the angle between the vectors.It Returns NAN
 * 
 *
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Skeleton {
	
	ArrayList <Double> doubleKeypointsArr = new ArrayList<Double>();
	JSONParser parser = new JSONParser();
	String path;
	
	//L and R are defined from the first person of the skeleton 
	Keypoint key0 ;//nose
	Keypoint key1 ;//neck
	Keypoint key2 ;//Rshoulder
	Keypoint key3 ;//Relbow
	Keypoint key4 ;//RWrist
	Keypoint key5 ;//LShoulder
	Keypoint key6 ;//LElbow
	Keypoint key7 ;//LWrist
	
	Keypoint[] keyArr={key0,key1,key2,key3,key4,key5,key6,key7};
	
	double ang0;//nose-neck-Rshoulder
	double ang1;//neck-Rshoulder-Relbow
	double ang2;//Rshoulder-Relbow-RWrist
	double ang3;//nose-neck-Lshoulder
	double ang4;//neck-Lshoulder-Lelbow
	double ang5;//Lshoulder-Lelbow-Lwrist
	
	
	public Skeleton (String path ) throws IOException, ParseException {
		
		this.setKeypoints(path);
		this.setAngles(key0, key1, key2, key3, key4, key5, key6, key7);
	}

	//import JSON
	public  void setKeypoints(String path) throws IOException, ParseException{

		try {
			 
			Object obj = parser.parse(new FileReader(path ));
			JSONObject jsonObject= (JSONObject) obj;
			JSONArray peopleArr = (JSONArray) jsonObject.get("people");
			
			//System.out.println("SKELETONDEBUG: people size " + peopleArr.size() );
			
			/*try{
			System.out.println("SKELETONDEBUG: peopleArr" + peopleArr.get(0));}
			catch (IndexOutOfBoundsException e){
				System.out.println("SKELETONDEBUG: peopleArr.get(0) exception : " + e);}*/

		
			//if no people detected in frame			
			
			if  (peopleArr.size()==0){
				//System.out.println("SKELETONDEBUG: people size is 0" + peopleArr.size() );

				key0= new Keypoint( 0.0, 0.0, 0.0);
				key1= new Keypoint( 0.0, 0.0, 0.0);
				key2= new Keypoint( 0.0, 0.0, 0.0);
				key3= new Keypoint( 0.0, 0.0, 0.0);
				key4= new Keypoint( 0.0, 0.0, 0.0);
				key5= new Keypoint( 0.0, 0.0, 0.0);
				key6= new Keypoint( 0.0, 0.0, 0.0);
				key7= new Keypoint( 0.0, 0.0, 0.0);
			}
			else{
				for (int i = 0; i <peopleArr.size(); i++){
					JSONObject poseKeyPoints=new JSONObject();
					
					try {
						//JSONObject poseKeyPoints = (JSONObject) peopleArr.get(0);
						poseKeyPoints = (JSONObject) peopleArr.get(0);
					}
					catch (NullPointerException e) {
						System.out.println("NULL POINTER EXCEPTION THROWN");
					}
					try {
						//JSONObject poseKeyPoints = (JSONObject) peopleArr.get(0);
						poseKeyPoints = (JSONObject) peopleArr.get(0);
					}
					catch (NullPointerException e) {
						System.out.println("NULL POINTER EXCEPTION THROWN");
					}
					JSONArray  posePointsArr = (JSONArray) poseKeyPoints.get("pose_keypoints_2d"); 
					
					
					//System.out.println(posePointsArr);
					
					//test
					//Double test = (Double)posePointsArr.get(0);
					//System.out.println("double test is " + test);
					
					for (int j=0; j<24; j++){ //24 as only need keypoints 0-7 (upper limbs)
						if (!(posePointsArr.get(j) instanceof Double )){ 
							Long l= (Long) posePointsArr.get(j);
							doubleKeypointsArr.add((Double) l.doubleValue());
						}
						
						
						else { //is a double
							doubleKeypointsArr.add((Double)posePointsArr.get(j));
						}
						
						//System.out.println(doubleKeypoints.get(j));
					}	
					//instantiate all keypoints
					key0= new Keypoint((Double) doubleKeypointsArr.get(0), doubleKeypointsArr.get(1), doubleKeypointsArr.get(2));
					key1= new Keypoint((Double) doubleKeypointsArr.get(3), doubleKeypointsArr.get(4), doubleKeypointsArr.get(5));
					key2= new Keypoint((Double) doubleKeypointsArr.get(6), doubleKeypointsArr.get(7), doubleKeypointsArr.get(8));
					key3= new Keypoint((Double) doubleKeypointsArr.get(9), doubleKeypointsArr.get(10), doubleKeypointsArr.get(11));
					key4= new Keypoint((Double) doubleKeypointsArr.get(12), doubleKeypointsArr.get(13), doubleKeypointsArr.get(14));
					key5= new Keypoint((Double) doubleKeypointsArr.get(15), doubleKeypointsArr.get(16), doubleKeypointsArr.get(17));
					key6= new Keypoint((Double) doubleKeypointsArr.get(18), doubleKeypointsArr.get(19), doubleKeypointsArr.get(20));
					key7= new Keypoint((Double) doubleKeypointsArr.get(21), doubleKeypointsArr.get(22), doubleKeypointsArr.get(23));
				}
			}
			
		
		}
		catch (FileNotFoundException e){
            System.out.println("file not found exception thrown");

		}
		
		
	}
	
	
	public void setAngles(Keypoint key0, Keypoint key1, Keypoint key2, Keypoint key3, Keypoint key4, Keypoint key5, Keypoint key6, Keypoint key7){
		//u vector always points towards middle keypoint (0-->1)
		//v vector always points towards final keypoint (1-->2) 
	
		//R side angles
		Vec u = new Vec((key1.x -key0.x), (key1.y - key0.y));
		Vec v = new Vec((key2.x -key1.x), (key2.y - key1.y));
		ang0 = Vec.getCosAngle(u,v);
		u = new Vec((key2.x -key1.x), (key2.y - key2.y));
		v = new Vec((key3.x -key2.x), (key3.y - key2.y));
		ang1 = Vec.getCosAngle(u,v);
		u = new Vec((key3.x -key2.x), (key3.y - key2.y));
		v = new Vec((key4.x -key3.x), (key4.y - key3.y));
		ang2 = Vec.getCosAngle(u,v);
		
		//L side angles
		u = new Vec((key1.x -key0.x), (key1.y - key0.y));
		v = new Vec((key5.x -key1.x), (key5.y - key1.y));
		ang3= Vec.getCosAngle(u,v);
		u = new Vec((key5.x -key1.x), (key5.y - key1.y));
		v = new Vec((key6.x -key5.x), (key6.y - key5.y));
		ang4= Vec.getCosAngle(u,v);
		u = new Vec((key6.x -key5.x), (key6.y - key5.y));
		v = new Vec((key7.x -key6.x), (key7.y - key6.y));
		ang5= Vec.getCosAngle(u,v);
	
	}
	
	
	 public static void main(String[] args) throws IOException, ParseException {
	        // test program 
		 	String jsonFrame ="/homes/la2817/Desktop/openPose_outputs/nptest1.json/000000000000_keypoints.json"; //path to frame
		 	
		 	Skeleton spooky = new Skeleton(jsonFrame);
		 	
		 	
		 /*	double ang0 = Math.toDegrees(spooky.setAngles(spooky.key0, spooky.key1, spooky.key2));
		 	double ang1 = Math.toDegrees(spooky.setAngles(spooky.key1, spooky.key2, spooky.key3));
		 	double ang2 = Math.toDegrees(spooky.setAngles(spooky.key2, spooky.key3, spooky.key4));
			double ang3 = Math.toDegrees(spooky.setAngles(spooky.key1, spooky.key5, spooky.key6));
			double ang4 = Math.toDegrees(spooky.setAngles(spooky.key5, spooky.key6, spooky.key7));
		*/
			System.out.println("skeletal keypoints:");
			System.out.println("key 0 :" + spooky.key0.x + " " + spooky.key0.y+ " "+ spooky.key0.con);
			System.out.println("key 1 :" + spooky.key1.x + " " + spooky.key1.y + " "+ spooky.key1.con);
			System.out.println("key 2 :" + spooky.key2.x + " " + spooky.key2.y+ " "+ spooky.key2.con);
			System.out.println("key 3 :" + spooky.key3.x + " " + spooky.key3.y + " "+ spooky.key3.con);
			System.out.println("key 4 :" + spooky.key4.x+ " " + spooky.key4.y + " "+ spooky.key4.con);
			System.out.println("key 5 :" + spooky.key5.x + " " + spooky.key5.y + " "+ spooky.key5.con);
			System.out.println("key 6 :" + spooky.key6.x + " " + spooky.key6.y + " "+ spooky.key6.con);
			System.out.println("key 7 :" + spooky.key7.x+ " " + spooky.key7.y+ " "+ spooky.key7.con);
		 	
			System.out.println("Angle0:");
			System.out.println(spooky.ang0);
			System.out.println("Angle1:");
			System.out.println(spooky.ang1);
			System.out.println("Angle2:");
			System.out.println(spooky.ang2);
			System.out.println("Angle3:");
			System.out.println(spooky.ang3);
			System.out.println("Angle4:");
			System.out.println(spooky.ang4);
			System.out.println("Angle5:");
			System.out.println(spooky.ang5);
				 	
	 }
	
}

