package test;

import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;

import util.Point;
import aco.ACS;

public class Test {
	
	public Point[] points;
	static final Random random = new Random();
	
	final int COORD_MAX = 1000;
	public int[] bestTour;
	ACS acs;
	public Test(int n){
		points = new Point[n];

		

	}
	
	public void init(){

		for (int i = 0; i < points.length; i++) {
			points[i]= getPoint(COORD_MAX);
		}
	}
	public void run(){
		
		for (int i = 0; i < 10; i++) {
			
		
		acs = new ACS(buildMatrix(points));
		

		bestTour = acs.getTour();
		System.out.print(getDistance(bestTour));
		}
	}
	private double getDistance(int[] bestTour2) {
		 int i;
		 double ret=0;
		 for(i=0;i<acs.cityCount-1;i++)   
			 ret+=acs.distance[bestTour[i]][bestTour[i+1]];   
		 ret+=acs.distance[bestTour[acs.cityCount-1]][bestTour[0]];   
		 return ret;
	}

	public void draw(){
        JFrame F=new JFrame();
        F.setTitle("Test");
        F.setSize(300,300);
        F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        F.add(new TestCanvas(this));
        F.setVisible(true);
	}
	
	public static double[][] buildMatrix(Point[] points){
		double[][] temp = new double[points.length][points.length];
		
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points.length; j++) {
				Point pi = points[i];
				Point pj = points[j];
				temp[i][j]=temp[j][i]=Math.sqrt((Math.pow((pi.x-pj.x),2)+Math.pow((pi.y-pj.y),2)));
				//System.out.print("  " + temp[i][j]+"  ,");
			}
			//System.out.println("");
		}
		
		return temp;
	}
	
	public static Point getPoint(int max){
		
		return new Point(random.nextDouble()*max,random.nextDouble()*max);
	}

}
