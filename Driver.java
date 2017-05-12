import java.io.FileWriter;
import java.util.List;
import java.util.Random;
import java.io.IOException;

public class Driver {
	private World Map;
	public int noOfEthnicities;
	private double initialPtr;
	private int immigrantsPerDay;
	public Driver()
	{
		System.out.println("Initializing Driver");
		noOfEthnicities = 4;
		initialPtr = 0.12;
		immigrantsPerDay = 1;
		Map  = new World();
	}
	
	//Immigration Phase
	public void Immigration()
	{
		System.out.println("Immigration Phase.");
	    Random r=new Random();
        List<int[]> coord = Map.getSpaces();
        int[] key;
        Agent a;
        try{
	        for(int i=0;i<immigrantsPerDay;i++)
	        {
                if(coord.size()>0){
                    key=coord.get(r.nextInt(coord.size()));
                    a=Map.makeRandomAgent(noOfEthnicities);
                    a.setPtr(initialPtr);
                    Map.addAgent(a,key[0],key[1]);
                    coord.remove(key);
                }
	        }
        }
        catch(OutOfTheWorldException e)
        {
        	System.out.println("OutOfTheWorldException. Terminating Program.");
        }
	}
	
	public void Interaction()
	{
		System.out.println("Interaction Phase");
		for(int[] i : Map.getAgents())
		{
			for(Agent neighbor : Map.findNeighbours(i[0], i[1],1))
			{
				Interact(Map.getAgent(i[0], i[1]),neighbor);
			}
		}
	}

	public void Interact(Agent a, Agent b)
	{
		if(a.getColor()==b.getColor()&&a.isCoopSame())
		{
				a.cooperate(Map.getCostOfGiving(),Map.getGainOfReceiving(),b);
		}
		if(a.getColor()!=b.getColor()&&a.isCoopDiff())
		{
				a.cooperate(Map.getCostOfGiving(),Map.getGainOfReceiving(),b);
		}
	}

	public void Reproduction()
	{
		System.out.println("Reproduction Phase");
		try {
			//Get list of agents
			List<int[]> coord = Map.getAgents();
			Agent a;
			Random r = new Random();
			for (int[] i : coord) {
				a = Map.getAgent(i[0], i[1]);
				//check if ready to reproduce
				if (a.check(a.getPtr())) {
					//selecting cell to put reproduce in
					List<int[]> neighboringSpaces = Map.findNeighboringSpaces(i[0], i[1],1);
                    if(neighboringSpaces.size()>0){
                        int[] point = neighboringSpaces.get(r.nextInt(neighboringSpaces.size()));
                        Map.addAgent(a.reproduce(Map.getMutationRate(), initialPtr, 0, noOfEthnicities), point[0], point[1]);
                    }
				}
				//Initialize ptr
				a.setPtr(initialPtr);
			}
		}
		catch(OutOfTheWorldException e)
		{
			System.out.println("OutOfTheWorldException. Terminating Program.");
		}

	}

	public void Death()
	{
		System.out.println("Death Phase");
		Random r = new Random();
		for(int[] i : Map.getAgents())
		{
			if( Map.getDeathRate() <  r.nextDouble())
			{
				try{
				Map.removeAgent(i[0], i[1]);
				}
				catch (OutOfTheWorldException e)
				{
					System.out.println("Out of the World Exception caught. Terminating.");
				}
			}
		}
	}
    public void PrintCount(){
    	System.out.println("\t\t\t\tCC:"+Agent.CCcount+";CD:"+Agent.CDcount+
    			"DC:"+Agent.DCcount+";DD:"+Agent.DDcount);
    }
    //Exports the current number of agents per category
    public String Status(){
        int[] result=Map.export();
        String s="";
        for(int i=0;i<result.length;i++){
            s=s+Integer.toString(result[i]);
            if(i==result.length-1){
                s=s+"\n";
            }
            else{
                s=s+",";
            }
        }
        return s;
    }
	public void drive(int ticks){
		try{
        FileWriter f=new FileWriter("ethno.csv");
        f.write("Tick,CC,CD,DC,DD \n");
		for(int i=1;i<=ticks;i++)
		{
			System.out.println("Tick:"+i);
			Immigration();
			Interaction();
			Reproduction();
			Death();
            PrintCount();
            f.write(""+i+",");
            f.write(Status());
		}
        f.close();
		}
        catch(IOException e)
        {
        	System.out.println("IOException caught. terminating program");
        }
	}
}
