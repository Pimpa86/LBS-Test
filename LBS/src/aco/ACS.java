package aco;
import java.util.Random;


/**
 * @author Phili
 * Main-Class for ACO
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
	 * Pherome amount each ant deposits when a tour is completed
	 */
	public double Q;
	
	public double alpha;//Alpha   
	public double beta;//Beta
	public double evap_rate;//Verdunstungsfaktor
	public double d0; //init phero-value
	
	public int bestTour[]; //bis jetzt kürzeste gefundene Tour
	
	public double dTrail[][]; //Pheromon-Matrix
	public double dDeltaTrail[][]; //Delta-Pheromon-Matrix
	public double distance[][]; // Distanzmatrix
	
	static final Random random = new Random();
 
	public AntColony antColony;
	
	/**
	 * @param distances Distance-Matrix
	 */
	public ACS(double[][] distances){
		

		this(distances,300,1.0,1,0.1,100.0);

	}
	
	/** Main-Construcor
	 * @param distances Distance-Matrix
	 * @param iteration_count Iteration Count
	 * @param alpha Alpha
	 * @param beta Beta
	 * @param evap_rate Evaporation Rate
	 * @param Q Pheromone Deposit
	 */
	public ACS(double[][] distances, int iteration_count, double alpha, double beta, double evap_rate, double Q){
		
		this.cityCount = distances[0].length;
		this.distance  = new double[cityCount][cityCount];
		this.distance = distances;

		this.dTrail = new double[cityCount][cityCount];
		this.dDeltaTrail  = new double[cityCount][cityCount];
		
		
		this.bestTour  = new int[cityCount];
		
		this.antCount = cityCount;
		
		this.alpha = alpha;
		this.beta = beta;
		this.iterationCount = iteration_count;
		this.evap_rate = evap_rate;
		this.Q=Q;
		
		this.antColony = new AntColony(this);
	}
	
//	/**
//	 * asdfasdf
//	 */
//	public void init(){
//		this.antColony = new AntColony(this);
//		this.dTrail = new double[cityCount][cityCount];
//		this.dDeltaTrail  = new double[cityCount][cityCount];
//	}
	
	public int[] getTour(){

		antColony.getAnts();
		antColony.startSearch();

		return bestTour;
	}
	

}
