import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Driver {
	private World Map;
	public int noOfEthnicities;
	private double initialPtr;
	private int immigrantsPerDay;
	public Driver()
	{
		noOfEthnicities = 4;
		initialPtr = 0.12;
		immigrantsPerDay = 1;
		Map  = new World();
	}
	//Immigration Phase
	public void Immigration()
	{
	    Random r=new Random();
        List<int[]> coord = Map.getSpaces();
        int[] key;
        try{
	        for(int i=0;i<immigrantsPerDay;i++)
	        {
	            key=coord.get(r.nextInt(coord.size()));
	            Map.addAgent(new Agent(1,initialPtr,true,true),key[0],key[1]);
	            coord.remove(key);
	        }
        }
        catch(OutOfTheWorldException e)
        {
        	System.out.println("OutOfTheWorldException. Terminating Program.");
        }
	}
	public void Interaction()
	{
		
	}
	public void Reproduction()
	{
		
	}
	public void Death()
	{
		
	}
	public void drive() {
		while (true)
		{
			Immigration();
			Interaction();
			Reproduction();
			Death();
		}
	}
}
