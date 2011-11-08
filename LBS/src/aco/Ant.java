package aco;

import java.util.Random;

/**
 * @author Phili
 *
 */
public class Ant {
	
	/**
	 * ACS Settings
	 */
	private ACS acs;
	
	/**
	 * Probability-Array for city transitions
	 */
	private double prob[];
		
	/**
	 * Number of citys visited
	 */
	private int cityCount;
	
	/**
	 * >0 if Allowed to visit city, index is number of city
	 */
	private int allowedCity[];
		
	/**
	 * Already visited cities, also stores order in which citys have been visited
	 */
	public int tabu[];
		
	/**
	 * Length of the current Tour
	 */
	public double currentTourLength;
		
	/**
	 * Length of shortest Tour found so far
	 */
	double shortestTourLength;
	
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
	

	private int chooseNextCity()
	{
        if (ACS.random.nextDouble() <= acs.q0)
        {
        	//exploitation
            return pickGreedyNode();
        }
        else
        {
        	//exploration
            return pickRandomNode();
        }
	}
	
	private int pickGreedyNode() {

		double highest = Double.MIN_VALUE;
		int highestCity = -1;
		int curCity=tabu[cityCount-1];
		 
		for (int i = 0; i < acs.cityCount; i++) {
			  if((allowedCity[i]==1))    
			  {   
				  double temp =acs.eta[curCity][i]*Math.pow((acs.dTrail[curCity][i]),acs.alpha);
				  //double temp =Math.pow((double)(1.0/acs.distance[curCity][i]),acs.beta)*Math.pow((acs.dTrail[curCity][i]),acs.alpha);
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
				sum+=acs.eta[curCity][i]*Math.pow((acs.dTrail[curCity][i]),acs.alpha);
				//sum+=Math.pow((double)(1.0/acs.distance[curCity][i]),acs.beta)*Math.pow((acs.dTrail[curCity][i]),acs.alpha);
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
	
	/**
	 * Update tour length when ant has completed a tour
	 */
	public void updateResult()
	{
		 // Update the length of tour   
		 int i;   
		 for(i=0;i<acs.cityCount-1;i++)   
		  currentTourLength+=acs.distance[tabu[i]][tabu[i+1]];   
		 currentTourLength+=acs.distance[tabu[acs.cityCount-1]][tabu[0]];   
	}
	

	/**
	 * Move ant to next city and add city-id to tabu
	 */
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
