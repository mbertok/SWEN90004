import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class World {
	// The dimension of the world
	private int dimension;
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
		System.out.println("Creating World.");
		mutationRate = 0.005;
		deathRate = 0.10;
		costOfGiving = 0.01;
		gainOfReceiving = 0.03;
		immigrantChanceToCooperateWithSameColor =0.50;
        dimension =5;
        double[][] Map;
		int Dimension;
		try {
			Scanner sc = new Scanner(new File("C:\\Users\\abhimanyu\\workspace\\sillinesssake\\src\\sillinesssake\\WorldMap.txt"));
			Dimension = Integer.parseInt(sc.nextLine());
			System.out.println("Dimension:"+Dimension);
			Map = new double[Dimension][Dimension];
			int i =0;
			 while (sc.hasNextLine()) {
		         List<Double> row = new ArrayList<Double>();
		         int j = 0;
				 for(String s: sc.nextLine().split("\t"))
		          {
					 Map[i][j]=Double.parseDouble(s);
					 j++;
		          }
				 i++;
				 //rows.add(row);
					
			 }
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public double getDeathRate()
	{
		return deathRate;
	}
	public double getCostOfGiving()
	{
		return costOfGiving;
	}
	public double getGainOfReceiving()
	{
		return gainOfReceiving;
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
	public List<int[]> getAgents(){
	    List<int[]> agents=new ArrayList<int[]>();
	    for(int i: worldState.keySet()){
	        agents.add(locate(i));
        }
        return agents;
    }
    public boolean isSpace(int x, int y){
	    return allSpaces.containsKey(locate(x,y));
    }
    public boolean isSpace(int x){
        return allSpaces.containsKey(x);
    }
    public Agent getAgent(int x, int y){
        return worldState.get(locate(x,y));
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
    public boolean isAgentInPosition(int x, int y)
    {
    	return(worldState.get(locate(x,y))==null?false:true);
    }
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

    public int selectNeighbour(int[] neighbours){
        Random r=new Random();
        for(int i: neighbours){
            if(isSpace(i)){
                return i;
            }
        }
        return -1;
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




}
