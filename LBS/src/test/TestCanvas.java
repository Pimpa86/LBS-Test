package test;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import util.Point;

	class TestCanvas extends Canvas
	{

		private static final long serialVersionUID = 1L;
		Test t;
		
		public TestCanvas(Test t){

			this.t=t;
		}
		

		
		public void paint (Graphics g)
	    {   
			g.drawString(new Double(t.acs.antColony.bestTourLength).toString(),30, 30);

	        for(int i = 0;i<t.points.length;i++){
	        	
	        	int[] c = getCoord(t.points[i]);
	        	g.drawOval(c[0]-10, c[1]-10, 20, 20);
	        	g.drawString(new Integer(i).toString(), c[0]-5, c[1]+5);
	        }
	        

	        for (int i = 0; i < t.bestTour.length-1; i++) {
	        	int[] p1 = getCoord(t.points[t.bestTour[i]]);
	        	int[] p2 = getCoord(t.points[t.bestTour[i+1]]);
	        	g.drawLine(p1[0],p1[1], p2[0], p2[1]);
				
			}
	   
        	int[] p1 = getCoord(t.points[t.bestTour[0]]);
        	int[] p2 = getCoord(t.points[t.bestTour[t.bestTour.length-1]]);
        	g.drawLine(p1[0],p1[1], p2[0], p2[1]);
	    }
		
		private int[] getCoord(Point p){
			Dimension d=getSize();
	        int w=d.width,h=d.height;
			
			int[] r = new int[2];
			r[0]= (int)((t.COORD_MAX-p.x)/t.COORD_MAX*w);
			r[1]= (int)((t.COORD_MAX-p.y)/t.COORD_MAX*h);
			return r;
		}
	}