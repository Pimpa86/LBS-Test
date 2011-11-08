package aco;

public class AntColony {
	
	private ACS acs;
	
	public double bestTourLength;
	public Ant ants[];
	
	public AntColony(ACS a){
		this.acs = a;
		initMap();
		ants = new Ant[acs.antCount];
		for (int i = 0; i < acs.antCount; i++) {
			ants[i] = new Ant(acs);
		}
		bestTourLength=Double.MAX_VALUE;
		for (int i = 0; i < acs.cityCount; i++) {
			acs.bestTour[i]=0;
		}
		
	}
	
	public void globalUpdate(){
		int i;   
		int j;   
		   
		for(i=0;i<acs.antCount;i++){   
			for (j=0;j<acs.cityCount-1;j++){   
				acs.dDeltaTrail[ants[i].tabu[j]][ants[i].tabu[j+1]]+=acs.Q/ants[i].currentTourLength;   
				acs.dDeltaTrail[ants[i].tabu[j+1]][ants[i].tabu[j]]+=acs.Q/ants[i].currentTourLength;   
			}
			
			acs.dDeltaTrail[ants[i].tabu[acs.cityCount-1]][ants[i].tabu[0]]+=acs.Q/ants[i].currentTourLength;   
			acs.dDeltaTrail[ants[i].tabu[0]][ants[i].tabu[acs.cityCount-1]]+=acs.Q/ants[i].currentTourLength;   
		}
		
		for (i=0;i<acs.cityCount;i++){   
			for (j=0;j<acs.cityCount;j++){   
				acs.dTrail[i][j]=(acs.evap_rate*acs.dTrail[i][j]+acs.dDeltaTrail[i][j] );   
				acs.dDeltaTrail[i][j]=0;   
			}   
		}
		 
	
	}
	
	public void globalUpdate(double length, int[] tour){
		
		for (int j=0;j<acs.cityCount-1;j++){
			
			acs.dDeltaTrail[tour[j]][tour[j+1]] += 1.0/length;
			acs.dDeltaTrail[tour[j+1]][tour[j]] += 1.0/length;
		}
		
		acs.dDeltaTrail[tour[acs.cityCount-1]][0] += 1.0/length;
		acs.dDeltaTrail[tour[0]][acs.cityCount-1] += 1.0/length;

		for (int i = 0; i < acs.cityCount; i++) {
			for (int j = 0; j < acs.cityCount; j++) {
				acs.dTrail[i][j]= (1.0-acs.evap_rate) * acs.dTrail[i][j] + acs.evap_rate * acs.dDeltaTrail[i][j];
				acs.dTrail[j][i]= (1.0-acs.evap_rate) * acs.dTrail[j][i] + acs.evap_rate * acs.dDeltaTrail[j][i];
				acs.dDeltaTrail[i][j]=0;
			}
			
		}
	}
	
	public void initMap(){
		
		for(int i=0;i<acs.cityCount;i++)
			for (int j=0;j<acs.cityCount;j++){
			acs.dTrail[i][j]=acs.tau0;
			acs.dDeltaTrail[i][j]=0;
		}
		
	}
	

	
	
	/**
	 * Distribute Ants randomly across citys
	 */
	public void initAnts(){ 
		 int i=0;   
		 int city;   

		 for (i=0;i<acs.antCount;i++)   
		 {   
			 //pick random city
			 city=ACS.random.nextInt(acs.cityCount);
			 //mark city as visited
			 ants[i].addCity(city);   
		 }   
	}
	

	public void startSearch(){
		 //begin to find best solution   
		 int max=0;//iteration counter 
		 double temp;  //temp tour length
		 int temptour[] = new int[acs.cityCount]; //temp tour array
		 int i;
		 int j;
		 while (max<acs.iterationCount)   
		 {     
			 //Jede Ameise jede Stadt einmal besuchen
			 for(j=0;j<acs.antCount;j++){ //for every ant   
				   for (i=0;i<acs.cityCount-1;i++){  //visit every city
					   
					   ants[j].move();
					   
					   //local pheromon update nach ACS
					   acs.dTrail[ants[j].tabu[i]][ants[j].tabu[i+1]]=(1-acs.evap_rate)*acs.dTrail[ants[j].tabu[i]][ants[j].tabu[i+1]]+acs.evap_rate*acs.tau0;
				   }
			  }   

			 
			 //Zurück zur Startstadt
			 for(j=0;j<acs.antCount;j++){   
				  ants[j].move2last();   
				  ants[j].updateResult();   
			 }   
			   
			 //Suche beste Lösung von allen Ameisen und in temp[] speichern
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
			 
			 if(temp<bestTourLength){   
				 bestTourLength=temp;
				 globalUpdate(bestTourLength,temptour);  
				 for ( t=0;t<acs.cityCount;t++)   
					 acs.bestTour[t]=temptour[t];   
			 }   
			  
			 //Global Pheromone Update
			 //globalUpdate2();    
			 
			 
			 for(j=0;j<acs.antCount;j++)    
				 ants[j].clear();   
			   
			 max++;   
		   
		 }
		 
		 
		 System.out.println("The shortest tour is : " + bestTourLength);   
		   
		 for ( int t=0;t<acs.cityCount;t++)   
			 System.out.print(acs.bestTour[t]+",");   
	}
	
	public void reset(){
		initMap();
		ants = new Ant[acs.antCount];
		for (int i = 0; i < acs.antCount; i++) {
			ants[i] = new Ant(acs);
		}
		bestTourLength=Double.MAX_VALUE;
		for (int i = 0; i < acs.cityCount; i++) {
			acs.bestTour[i]=0;
		}
	}
	
//    protected void globalUpdatingRule()
//    {
//        double dEvaporation = 0;
//        double dDeposition  = 0;
//        
//        for(int r = 0; r < m_graph.nodes(); r++)
//        {
//            for(int s = 0; s < m_graph.nodes(); s++)
//            {
//                if(r != s)
//                {
//                    // get the value for deltatau
//                    double deltaTau = //Ant4TSP.s_dBestPathValue * (double)Ant4TSP.s_bestPath[r][s];
//                        ((double)1 / Ant4TSP.s_dBestPathValue) * (double)Ant4TSP.s_bestPath[r][s];
//                                    
//                    // get the value for phermone evaporation as defined in eq. d)
//                    dEvaporation = ((double)1 - A) * m_graph.tau(r,s);
//                    // get the value for phermone deposition as defined in eq. d)
//                    dDeposition  = A * deltaTau;
//                    
//                    // update tau
//                    m_graph.updateTau(r, s, dEvaporation + dDeposition);
//                }
//            }
//        }
//    }
	
}
