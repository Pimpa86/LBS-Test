package aco;

public class AntColony {
	
	private ACS acs;
	
	public double m_dLength;
	public Ant ants[];
	
	public AntColony(ACS a){
		this.acs = a;
		initMap();
		ants = new Ant[acs.antCount];
		for (int i = 0; i < acs.antCount; i++) {
			ants[i] = new Ant(acs);
		}
		m_dLength=Double.MAX_VALUE;
		for (int i = 0; i < acs.cityCount; i++) {
			acs.bestTour[i]=0;
		}
		
	}
	
	public void updateTrail(){
		 int i;   
		 int j;   
		   
		 for(i=0;i<acs.antCount;i++)   
		 {   
			  for (j=0;j<acs.cityCount-1;j++)   
			  {   
				  acs.dDeltaTrail[ants[i].tabu[j]][ants[i].tabu[j+1]]+=acs.Q/ants[i].currentTourLength ;   
				  acs.dDeltaTrail[ants[i].tabu[j+1]][ants[i].tabu[j]]+=acs.Q/ants[i].currentTourLength;   
			  }   
			  acs.dDeltaTrail[ants[i].tabu[acs.cityCount-1]][ants[i].tabu[0]]+=acs.Q/ants[i].currentTourLength;   
			  acs.dDeltaTrail[ants[i].tabu[0]][ants[i].tabu[acs.cityCount-1]]+=acs.Q/ants[i].currentTourLength;   
			 }   
			 for (i=0;i<acs.cityCount;i++)   {   
				 for (j=0;j<acs.cityCount;j++)   {   
					 acs.dTrail[i][j]=(acs.evap_rate*acs.dTrail[i][j]+acs.dDeltaTrail[i][j] );   
					 acs.dDeltaTrail[i][j]=0;   
				 }   
			 }
		 
	}
	
	public void initMap(){
		int i;   
		 int j;   
		 for(i=0;i<acs.cityCount;i++)   
		  for (j=0;j<acs.cityCount;j++)   
		  {   
		   acs.dTrail[i][j]=1;   
		   acs.dDeltaTrail[i][j]=0;   
		  }   
	}
	
	public void getAnts(){
		 //randomly put ant into map   
		 int i=0;   
		 int city;   

		 for (i=0;i<acs.antCount;i++)   
		 {   
			 //Zufällige Startstadt
			 city=ACS.random.nextInt(acs.cityCount);
			 //gewählte Stadt als besucht markieren
			 ants[i].addCity(city);   
		 }   
	}
	

	public void startSearch(){
		 //begin to find best solution   
		 int max=0;//every ant tours times   
		 int i;   
		 int j;   
		 double temp;   
		 int temptour[] = new int[acs.cityCount];
		 while (max<acs.iterationCount)   
		 {     
			 for(j=0;j<acs.antCount;j++){    
				   for (i=0;i<acs.cityCount-1;i++){   
					   ants[j].move();
					   //local pheromon update
					   acs.dTrail[ants[j].tabu[i]][ants[j].tabu[i+1]]=(1-acs.evap_rate)*acs.dTrail[ants[j].tabu[i]][ants[j].tabu[i+1]]+acs.evap_rate*1.0;
				   }
			  }   
		   
			  for(j=0;j<acs.antCount;j++)    
			  {   
			   ants[j].move2last();   
			   ants[j].updateResult ();   
			  }   
			   
			  //find out the best solution of the step and put it into temp   
			  int t;   
			  temp=ants[0].currentTourLength;   
			  for (t=0;t<acs.cityCount;t++)   
				  temptour[t]=ants[0].tabu[t];   
			  for(j=0;j<acs.antCount;j++){
				   if (temp>ants[j].currentTourLength) {   
					    temp=ants[j].currentTourLength;   
					    for ( t=0;t<acs.cityCount;t++)   
					    	temptour[t]=ants[j].tabu[t];   
				   }   
			  }
			   
			  if(temp<m_dLength){   
				   m_dLength=temp;   
				   for ( t=0;t<acs.cityCount;t++)   
					   acs.bestTour[t]=temptour[t];   
			  }   
			  
			  //update pheromone matrix
			  updateTrail();    
			   
			  for(j=0;j<acs.antCount;j++)    
				  ants[j].clear();   
			   
			  max++;   
		   
		 }   
		 System.out.println("The shortest tour is : " + m_dLength);   
		   
		 for ( int t=0;t<acs.cityCount;t++)   
			 System.out.print(acs.bestTour[t]+",");   
	}
	
	public void reset(){
		initMap();
		ants = new Ant[acs.antCount];
		for (int i = 0; i < acs.antCount; i++) {
			ants[i] = new Ant(acs);
		}
		m_dLength=Double.MAX_VALUE;
		for (int i = 0; i < acs.cityCount; i++) {
			acs.bestTour[i]=0;
		}
	}
	
}
