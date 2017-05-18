package SWEN90004;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
public class World {
	// The dimension of the world
	private int dimension;
    private int CCcount;
    private int CDcount;
    private int DCcount;
    private int DDcount;

	private double mutationRate;
	private double deathRate;
	private double costOfGiving;
	private double gainOfReceiving;
	private double immigrantChanceToCooperateWithSameColor;
	private double immigrantChanceToCooperateWithDifferentColor;

    //the current world state storing the position of Agents
    private HashMap<Integer, Agent> worldState = new HashMap<Integer, Agent>();

    //the current world state storing the position of all empty spaces
    private HashMap<Integer, Character> allSpaces = new HashMap<Integer, Character>();
	//Default constructor. Sets values to defaults in NetLogo

    /**
     * The default constructor
     */
    public World()
	{
		System.out.println("Creating World.");
		mutationRate = 0.005;
		deathRate = 0.10;
		costOfGiving = 0.01;
		gainOfReceiving = 0.03;
		immigrantChanceToCooperateWithSameColor =0.50;
        dimension =5;
        for(int i =0;i<dimension;i++)
        {
        	for(int j=0;j<dimension;j++)
        	{
        		allSpaces.put(i+j, ' ');
        	}
        }
	}

    /**
     * Returns the deathRate variable
     * @return - the value of deathRate
     */
	public double getDeathRate()
	{
		return deathRate;
	}

    /**
     * Returns the costOfGiving
     * @return - the value of the costOfGiving
     */
	public double getCostOfGiving()
	{
		return costOfGiving;
	}

    /**
     * Returns the gainOfReceiving
     * @return - the value of gainOfReceiving
     */
	public double getGainOfReceiving()
	{
		return gainOfReceiving;
	}


    /**
     * Returns the mutationRate
     * @return - the value of mutationRate
     */
	public double getMutationRate()
	{
		return mutationRate;
	}


    /**
     * Returns the immigrantChanceToCooperateWithSameColor.
     * @return - the value of immigrantChanceToCooperateWithSameColor
     */
    public double getImmigrantChanceToCooperateWithSameColorRate()
    {
        return immigrantChanceToCooperateWithSameColor;
    }


    /**
     * Returns the immigrantChanceToCooperateWithDifferentColor
     * @return - the value of immigrantChanceToCooperateWithDifferentColor
     */
    public double getImmigrantChanceToCooperateWithDifferentColor()
    {
        return immigrantChanceToCooperateWithDifferentColor;
    }


    /**
     * Returns the list of free spaces in the board
     * @return - the free spaces as an array of integers
     */
    public List<int[]> getSpaces()
	{
		List<int[]> spaces = new ArrayList<int[]>();
		for(int i:allSpaces.keySet())
		{
			spaces.add(locate(i));
		}
		return spaces;
	}


    /**
     * Returns the list of agents in the world
     * @return - the agents as a list
     */
	public List<int[]> getAgents(){
	    List<int[]> agents=new ArrayList<int[]>();
	    for(int i: worldState.keySet()){
	        agents.add(locate(i));
        }
        return agents;
    }


    /**
     * Checks whether a location is empty in the world
     * @param x - the x coordinate of the location
     * @param y - the y coordinate of the location
     * @return - true if the location is empty
     */
    public boolean isSpace(int x, int y){
	    return allSpaces.containsKey(locate(x,y));
    }

    /**
     * Checks whether a location is empty in the world
     * @param x - the id corresponding to the hashmap keyset
     * @return - true if the location is empty and false otherwise
     */
    public boolean isSpace(int x){
        return allSpaces.containsKey(x);
    }

    /**
     * Returns the agent on the specified location of board.
     * @param x - the x coordinate of the location
     * @param y - the y coordinate of the location
     * @return - the agent on the board.
     */
    public Agent getAgent(int x, int y){
        return worldState.get(locate(x,y));
    }

    /**
     * Returns the dimension of the board
     * @return - the dimension of the board
     */
    public int getDimension()
	{
		return dimension;
	}
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

    /**
     * Simulates the death in the world.
     */
    public void Death(){
        Agent a;
        for(Integer i:worldState.keySet()){
            a=worldState.get(i);
            if(a.check(deathRate)){
                allSpaces.put(i,' ');
                worldState.remove(i);
            }
        }
    }


    /**
     * Creates and returns a random agent
     * @param numberOfEthnicities - the number of ethnicities
     * @return -  the agent itself
     */
    public Agent makeRandomAgent(int numberOfEthnicities){
        Random r=new Random();
        int col=r.nextInt(numberOfEthnicities);
        boolean same=false;
        boolean diff=false;
        if(r.nextDouble()<(immigrantChanceToCooperateWithSameColor)){
            same=true;
        }
        if(r.nextDouble()<(immigrantChanceToCooperateWithDifferentColor)){
            diff=true;
        }
        return new Agent(col,0,same,diff);
    }
    /*
     * Adds agent to world
     * @param Agent agent - agent to be added to world
     * @param int x - x coordinate on the world
     * @param int y - y coordinate on the world
     */
    public void addAgent(Agent agent, int x, int y) throws OutOfTheWorldException
    {
		if(x<dimension&&y<dimension)
		{
    		worldState.put(locate(x,y), agent);
        	allSpaces.remove(locate(x,y));
		}
		else
		{
			throw new OutOfTheWorldException("Coordinate greater than dimension");
		}	
    }
    
    /*
     * Removes agent from the world
     * @param int x - x coordinate on the world
     * @param int y - y coordinate on the world
     */
    public void removeAgent(int x, int y) throws OutOfTheWorldException
    {
    	if(x<dimension&&y<dimension)
		{
	    	worldState.get(locate(x,y)).kill();
            worldState.remove(locate(x,y));
	    	allSpaces.put(locate(x,y),' ');
		}
    	else
		{
			throw new OutOfTheWorldException("Coordinate greater than dimension");
		}
    	
    }

    /**
     * Displays the current world state.
     */
    public void printWorld()
    {
    	 for(int i =0;i<dimension;i++)
         {
         	for(int j=0;j<dimension;j++)
         	{
         		if(worldState.containsKey(i+j))
         		{
         			System.out.print(worldState.get(i+j));
         		}
         		else
         		{
         			System.out.println(allSpaces.get(i+j));
         		}
         	}
         	System.out.println();
         }
    }

    /**
     * Checks if there is an agent on the location
     * @param x -  the x coordinate of the location
     * @param y - the y coordinate of location
     * @return - returns true if there is an agent on the location
     */
    public boolean isAgentInPosition(int x, int y)
    {

        return(worldState.get(locate(x,y))==null?false:true);
    }


    /**
     * Returns the list of neighbours of an agent
     * @param i - the x coordiante of the agent location
     * @param j - the y coordinate of the agent location
     * @param radius - the neighbourhood radius
     * @return - a list of neighbours of the agent
     */
    public List<Agent> findNeighbours(int i, int j, int radius){
        int k=locate(i,j);
        System.out.println("point:"+i+","+j);
        List<int[]> neighborhood = new ArrayList<int[]>();
        List<Agent> neighbors=new ArrayList<Agent>();
        for(int x=i-radius;x<dimension&&x<=i+radius;x++)	//add all points in the 
        {
        
        	//Von Neumann neighborhood. 	
        	for(int y=j-radius;y<=j+radius;y++)
        	{
        		
        		if(Math.abs(x-i)+Math.abs(y-j)<=radius)
        		{
        			if(x>=0&&x<dimension&&y>=0&&y<dimension)
        			{
	        			neighborhood.add(new int[]{x,y});
        			}
        		}
        	}
        }
        neighborhood.remove(new int[]{i,j});			//remove initial point from the list
        for(int[]point :neighborhood)
        {
        	if(isAgentInPosition(point[0],point[1]))
        	{
        		neighbors.add(worldState.get(locate(point[0],point[1])));
        	}
        }
        return neighbors;

    }

    /**
     * Returns the list of spaces in the neighbourhood of an agent
     * @param i - the x coordiante of the agent location
     * @param j - the y coordinate of the agent location
     * @param radius - the neighbourhood radius
     * @return - a list of neighbourhood spaces of the agent
     */
    public List<int[]> findNeighboringSpaces(int i, int j, int radius){
        int k=locate(i,j);
        List<int[]> neighborhood = new ArrayList<int[]>();
        List<Agent> neighboringSpaces;
        for(int x=i-radius;x<=i+radius;x++)	//add all points without agent in the 
        {													//Von Neumann neighborhood. 	
        	for(int y=j-radius;y<=j+radius;y++)
        	{
        		if(Math.abs(x-i)+Math.abs(y-j)<=radius)
        		{
        			if(!isAgentInPosition(x, y))
        			neighborhood.add(new int[]{x,y});
        		}
        	}
        }
        return neighborhood;

    }


    /**
     * Selects a random neighbour from the list of neighbours
     * @param neighbours - the list of neighbours
     * @return - the selected neighbour
     */
    public int selectNeighbour(int[] neighbours){
        Random r=new Random();
        for(int i: neighbours){
            if(isSpace(i)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Updates the agent classifications.
     */
    public void count(){
        CCcount=0;
        CDcount=0;
        DCcount=0;
        DDcount=0;
        Agent a;
        for(Integer i : worldState.keySet()){
            a=worldState.get(i);
            if(a.isAlturist()){
                CCcount++;
            }
            else if (a.isEgoist()){
                DDcount++;
            }
            else if(a.isCosmopolitan()){
                DCcount++;
            }
            else if(a.isEthnocentric()){
                CDcount++;
            }

        }
    }




}
