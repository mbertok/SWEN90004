package SWEN90004;

import java.util.*;
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
	public World()
	{
		mutationRate = 0.005;
		deathRate = 0.10;
		costOfGiving = 0.01;
		gainOfReceiving = 0.03;
		immigrantChanceToCooperateWithSameColor =0.50;
		immigrantChanceToCooperateWithDifferentColor = 0.50;
        CCcount=0;
        CDcount=0;
        DCcount=0;
        DDcount=0;
        dimension = 51;
        for(int i =0;i<dimension;i++)
        {
        	for(int j=0;j<dimension;j++)
        	{
        		allSpaces.put(i+j, ' ');
        	}
        }
	}
	public double getMutationRate()
	{
		return mutationRate;
	}
    public double getImmigrantChanceToCooperateWithSameColorRate()
    {
        return immigrantChanceToCooperateWithSameColor;
    }
    public double getImmigrantChanceToCooperateWithDifferentColor()
    {
        return immigrantChanceToCooperateWithDifferentColor;
    }
	public List<int[]> getSpaces()
	{
		List<int[]> spaces = new ArrayList<int[]>();
		for(int i:allSpaces.keySet())
		{
			spaces.add(locate(i));
		}
		return spaces;
	}
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
        /*
         * 
         * 
         * 
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
    public Agent makeRandomAgent(int col){
        Random r=new Random();
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
	    	worldState.remove(locate(x,y));
	    	allSpaces.put(locate(x,y),' ');
		}
    	else
		{
			throw new OutOfTheWorldException("Coordinate greater than dimension");
		}
    	
    }
        
    //print world
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
    public int[] export(){
        return new int[]{this.CCcount,this.CDcount,this.DCcount,this.DDcount};


    }



}
