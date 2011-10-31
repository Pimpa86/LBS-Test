package aco;

import java.util.Random;


/**
 * @author Phili
 *
 */
/**
 * @author Phili
 *
 */
public class Ant {
	
	private ACS acs; //Settings
	private double prob[]; //übergangswahrscheinlichkeiten von einer stadt zu deren nachfolgern
	private int cityCount; //anzahl besuchter städte
	private int allowedCity[]; //stadt noch nicht besucht
	public int tabu[]; //besuchte städte->speichert auch reihenfolge in der die städte besucht wurden
	public double currentTourLength; //länge der aktuellen tour
	double shortestTourLength; //länge der kürzesten tour
	
	static final Random random = new Random();
	
	public Ant(ACS a){
		
		currentTourLength = shortestTourLength = 0; //Länge kürzester Weg und aktuelle Länge = 0
		cityCount = 0; //Es wurden noch keine Städte besucht
		acs = a;
		allowedCity  = new int[acs.cityCount];
		tabu  = new int[acs.cityCount];
		prob= new double[acs.cityCount];
		for (int i = 0; i < acs.cityCount; i++) {
			prob[i] = 0;
			allowedCity[i]=1;
		}

	}
	

	/** Choose next city to visit
	 * @return Index of next city
	 */
	private int chooseNextCity()
	{
        if (ACS.random.nextDouble() < 0.1)
        {
            return pickRandomNode();
        }
        else
        {
            return pickGreedyNode();
        }
	}
	
	private int pickGreedyNode() {

		double highest = Double.MIN_VALUE;
		int highestCity = -1;
		int curCity=tabu[cityCount-1];
		 
		for (int i = 0; i < acs.cityCount; i++) {
			  if((allowedCity[i]==1))    
			  {   
			   double temp =Math.pow((double)(1.0/acs.distance[curCity][i]),acs.beta)*Math.pow((acs.dTrail[curCity][i]),acs.alpha);
			   if(temp>highest){
				   highest=temp;
				   highestCity = i;
			   }
			  }  
		}
		
		return highestCity;
	}
	
	private int pickRandomNode() {
		int i;   
		int j=-1;   
		double sum=0;   
		int curCity=tabu[cityCount-1];   
		for (i=0;i<acs.cityCount;i++)   
		{   
			if((allowedCity[i]==1))    
			{   
				sum+=Math.pow((double)(1.0/acs.distance[curCity][i]),acs.beta)*Math.pow((acs.dTrail[curCity][i]),acs.alpha);
				prob[i]=sum;
			}else
				prob[i]=0;
		}   
		double x= random.nextDouble()*sum;
		
		for (i=0;i<acs.cityCount;i++)   
		{   
			if((allowedCity[i]==1))    
			{   
				if(x<=prob[i])
					j=i;
			}   
		}  
		return j;
	}
	
	//Liefert die nächste Stadt, die noch nicht besucht wurde
//	private int chooseNextCity()
//	{
//
//		   
//		 int i;   
//		 int j=10000;   
//		 double temp=0;   
//		 int curCity=tabu[m_iCitycount-1];   
//		 for (i=0;i<acs.cityCount;i++)   
//		 {   
//		  if((allowedCity[i]==1))    
//		  {   
//		   temp+=Math.pow((double)(1.0/acs.distance[curCity][i]),acs.beta)*Math.pow((acs.dTrail[curCity][i]),acs.alpha);   
//		  }   
//		 }   
//
//		 double sel=0;   
//		 for (i=0;i<acs.cityCount;i++)   
//		 {     
//		  if((allowedCity[i]==1))   
//		  {   
//		   prob[i]=Math.pow((1.0/acs.distance[curCity][i]),acs.beta)*Math.pow((acs.dTrail[curCity][i]),acs.alpha)/temp;   
//		   sel+=prob[i];   
//		  }   
//		  else    
//		   prob[i]=0;   
//		 }   
//		 double mRate= ACS.random.nextDouble();//*sel;
//		 double mSelect=0;   
//		   
//		 for ( i=0;i<acs.cityCount;i++)   
//		 {   
//		  if((allowedCity[i]==1))   
//		   mSelect+=prob[i] ;   
//		  if (mSelect>=mRate) {j=i;break;}   
//		 }   
//		   
//		 if (j==10000)   
//		 {   
//		  temp=-1;   
//		  for (i=0;i<acs.cityCount;i++)   
//		  {    
//		   if((allowedCity[i]==1))   
//		    if (temp<Math.pow((1.0/acs.distance[curCity][i]),acs.beta)*Math.pow((acs.dTrail[curCity][i]),acs.alpha))        
//		    {   
//		     temp=Math.pow((1.0/acs.distance[curCity][i]),acs.beta)*Math.pow((acs.dTrail[curCity][i]),acs.alpha);   
//		     j=i;   
//		    }   
//		  }   
//		 }   
//		   
//		 return j;   
//	}
	
	//Stadt wurde besucht
	public void addCity(int city)
	{
		tabu[cityCount]=city; //besuchte stadt zu tabuliste hinzufügen
		cityCount++; //Es wurde eine stadt besucht counter++
		allowedCity[city]=0;
		
	}
	
	//Reset Ant
	public void clear(){
		currentTourLength = 0;
		for (int i = 0; i < acs.cityCount; i++) {		
			prob[i]=0;
			allowedCity[i]=1;
		}
		int t = tabu[acs.cityCount-1];
		cityCount = 0;
		addCity(t);
	}
	
	//update tour length
	public void updateResult()
	{
		 // Update the length of tour   
		 int i;   
		 for(i=0;i<acs.cityCount-1;i++)   
		  currentTourLength+=acs.distance[tabu[i]][tabu[i+1]];   
		 currentTourLength+=acs.distance[tabu[acs.cityCount-1]][tabu[0]];   
	}
	
	//move ant to next city and add city-id to tabu
	public void move(){
		 int j;   
		 j=chooseNextCity();   
		 addCity(j); 
	}
	
	//back to the roots
	public void move2last(){
		int i;
		for(i=0;i<acs.cityCount;i++){
			if(allowedCity[i]==1){
				addCity(i);
				break;
			}
		}
	}
	
	
	
}
