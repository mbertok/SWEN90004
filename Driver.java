package SWEN90004;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;

public class Driver {
	private World Map;
	public int noOfEthnicities;
	private double initialPtr;
	private int immigrantsPerDay;
    private int ticks;
	public Driver(int t)
	{
		noOfEthnicities = 4;
		initialPtr = 0.12;
		immigrantsPerDay = 1;
		Map  = new World();
        ticks=t;
	}
	
	//Immigration Phase
	public void Immigration()
	{
	    Random r=new Random();
        List<int[]> coord = Map.getSpaces();
        int[] key;
        Agent a;
        boolean same=false;
        boolean diff=false;
        try{
	        for(int i=0;i<immigrantsPerDay;i++)
	        {
	            key=coord.get(r.nextInt(coord.size()));
                a=Map.makeRandomAgent(noOfEthnicities);
                a.setPtr(initialPtr);
	            Map.addAgent(a,key[0],key[1]);
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
		try {
			//Get list of agents
			List<int[]> coord = Map.getAgents();
			Agent a;
			Random r = new Random();
			int selected;
			for (int[] i : coord) {
				a = Map.getAgent(i[0], i[1]);
				//check if ready to reproduce
				if (a.check(a.getPtr())) {
					//selecting cell to put reproduce in
					int[] neighbours = Map.findNeighbours(i[0], i[1]);
					selected = Map.selectNeighbour(neighbours);
					//If space
					if (selected != -1) {
						//create child
						Map.addAgent(a.reproduce(Map.getMutationRate(), initialPtr, 0, noOfEthnicities), i[0], i[1]);
					}
				}
				//intialise ptr
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
		Map.Death();
	}
    public void Count(){
        Map.count();
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
	public void drive() throws IOException{
        FileWriter f=new FileWriter("ethno.csv");
        f.write("Tick,CC,CD,DC,DD \n");
		for(int i=1;i<=ticks;i++)
		{
			Immigration();
			Interaction();
			Reproduction();
			Death();
            Count();
            f.write(i);
            f.write(Status());
		}
        f.close();
	}
}
