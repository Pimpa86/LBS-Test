package aco;
import java.util.Random;



/**
 * @author Phili
 * Main Class for Ant Colony System
 * TODO: Abstandsmatrix vorberechnen und speichern (Math.pow aufruf vermeiden ) (DONE)
 * TODO: Pheromonematrix durch NN-Search vorinitialisieren (DONE)
 * TODO: Global Update Rule noch nicht korrekt => nur falls die global beste Tour gefunden wurde Pheromatrix updaten nicht immer für jede Ameise
 */
public class ACS {

	/**
	 * Number of Ants
	 */
	public int antCount; 
	
	/**
	 * Number of Nodes of TSP
	 */
	public int cityCount; 
	
	/**
	 * Number of iterations (default=300)
	 */
	public int iterationCount;

	/**
	 * Amount of pheromone each ant deposits when a tour is completed
	 */
	public double Q;
		
	/**
	 * Value for Alpha (default 1.0)
	 */
	public double alpha;
		
	/**
	 * Value for Beta (default 2.0)
	 */
	public double beta;
	
	/**
	 * Pheromone evaporation-factor (]0,1[, default: 0.1)
	 */
	public double evap_rate;
		
	/**
	 * Initial Pheromone
	 */
	public double d0;
	
	/**
	 * Exploration or Exploitation for Ant::chooseNextCity()
	 */
	public double q0;
	
	/**
	 * Initial Pheromone Value
	 */
	public double tau0;
	
	
	public int bestTour[]; //bis jetzt kürzeste gefundene Tour	
	
	
	public double dTrail[][]; //Pheromon-Matrix
	public double dDeltaTrail[][]; //Delta-Pheromon-Matrix
	public double distance[][]; // Distanzmatrix
	public double eta[][]; //precalculated distance matrix
	
	
	static final Random random = new Random();
 
	public AntColony antColony;
	
	/** Default Constructor for ACS
	 * @param distances Distance-Matrix
	 * Default Values: 	Iteration: 300
	 * 					Alpha: 1.0
	 * 					Beta: 2.0
	 * 					evap_rate: 0.1
	 * 					Q: 100
	 */
	public ACS(double[][] distances){
		

		this(distances,200,1.0,2.0,0.1,100.0,0.9);

	}
	
	/** Main-Constructor
	 * @param distances Distance-Matrix
	 * @param iteration_count Iteration Count
	 * @param alpha Alpha
	 * @param beta Beta
	 * @param evap_rate Evaporation Rate
	 * @param Q Pheromone Deposit
	 */
	public ACS(double[][] distances, int iteration_count, double alpha, double beta, double evap_rate, double Q,double q0){
		
		this.cityCount = distances[0].length;
		this.distance  = new double[cityCount][cityCount];
		this.distance = distances;

		this.tau0 = 1.0/(cityCount*nearestNeighborSearch());
		this.dTrail = new double[cityCount][cityCount];
		this.dDeltaTrail  = new double[cityCount][cityCount];
		
		
		this.bestTour  = new int[cityCount];
		
		this.antCount = 10;
		
		this.alpha = alpha;
		this.beta = beta;
		this.iterationCount = iteration_count;
		this.evap_rate = evap_rate;
		this.Q=Q;
		this.q0=q0;
		
		//precalculate eta-Matrix
		this.eta = new double[cityCount][cityCount];
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances[0].length; j++) {
				eta[i][j]=Math.pow((double)(1.0/distance[i][j]),beta);
			}
		}
		
		this.antColony = new AntColony(this);
		
	}
	
	
	/**
	 * @return Returns Array of best tour found
	 */
	public int[] getTour(){

		antColony.initAnts();
		antColony.startSearch();

		return bestTour;
	}
	
	
	public double nearestNeighborSearch(){
		int[] temp = new int[cityCount];
		boolean[] visited = new boolean[cityCount];
		double sum=0.0;
		
		//Erste Stadt
		temp[0]=0;
		visited[0]=true;
		
		//NN-Suche über restliche Städte
		double dmin = Float.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < cityCount-1; i++) {
			for (int j = 0; j < cityCount; j++) {
				if(i!=j && !visited[j] && distance[temp[i]][j]<dmin){
					dmin = distance[temp[i]][j];
					minIndex = j;
				}
					
			}
			
			//--
			temp[i+1]=minIndex;
			visited[minIndex]=true;
			sum+=dmin;
			dmin = Float.MAX_VALUE;
			minIndex = -1;
		}

		return sum;
	}

}
