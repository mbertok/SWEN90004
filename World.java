
import java.util.HashMap;
import java.util.*;
public class World {
	// The dimension of the world
	private int dimension;
    private int CCcount;
    private int CDcount;
    private int DCcount;
    private int DDcount;

	private int immigrantsPerDay;
	private double mutationRate;
	private double initialPtr;
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
		immigrantsPerDay = 1;
		mutationRate = 0.005;
		initialPtr = 0.12;
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
            if(a.die(deathRate)){
                allSpaces.put(i,' ');
                worldState.remove(i);
            }
        }

    }
    public void Immigrate(){
        Random r=new Random();
        List<Integer> coord=new ArrayList<Integer>(allSpaces.keySet());
        int key;
        for(int i=0;i<immigrantsPerDay;i++){
            key=coord.get(r.nextInt(coord.size()));
            worldState.put(key,new Agent(1,initialPtr,true,true));//Placeholder
            allSpaces.remove(key);
            coord.remove(key);
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
