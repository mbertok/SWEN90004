import java.util.HashMap;

public class World {
	// The dimension of the world
	private int dimension;
	
	private int immigrantsPerDay;
	private double mutationRate;
	private double initialPtr; 
	private double deathRate;
	private double costOfGiving;
	private double gainOfReceiving;
	private double immigrantChanceToCooperateWithSameColor;
	private double immigrantChanceToCooperateWithDifferentColor;
	
	//Default constructor. Sets values to defaults in NetLogo
	public World()
	{
		immigrantsPerDay = 1;
		mutationRate = 0.005;
		initialPtr = 0.12;
		deathRate = 0.10;
		costOfGiving = 0.01;
		gainOfReceiving = 0.03;
		immigrantChanceToCooperateWithSameColor =0.50;
		immigrantChanceToCooperateWithDifferentColor = 0.50;
	}
	 //the current world state storing the position of Agents
    private HashMap<Integer, Agent> worldState = new HashMap<>();

    //the current world state storing the position of all empty spaces
    private HashMap<Integer, Character> allSpaces = new HashMap<>();
    
    /* Returns the cell id corresponding to the x and y coordinates
     * of the 2D world matrix
     * @param x - the x coordinate of the world matrix
     * @param y - the y coordinate of the world matrix
     * @param return - the return cell id corresponding to the location
     * in the hash map. 
     * 
     */
    private int locate(int x, int y) {
    	return x * this.dimension + y;
    	}

    /* Returns the x and y coordinates corresponding to a id of the board
     * @param id - the id in the hash map
     * @param return - an array containing the corresponding x and the y 
     * coordinates to hash map id. 
     */
        private int[] locate(int id) {
            int x = id / this.dimension;
            int y = id % this.dimension;
            return new int[] {x, y};
        }


}
