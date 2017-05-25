package Ext;

import java.util.Random;

public class Agent {
	//the color of the Agent
	public int color;
	//the potential to reproduce
	public double ptr;
	//whether the agent cooperates with same color
	private boolean coop_same;
	//whether the agent cooperates with different color
	private boolean coop_diff;
	
    /**
     * Default constructor
     * @param col - the color of the agent
     * @param potential - the potential to reproduce of the agent
     * @param same - whether the agent cooperates with same color
     * @param diff - whether the agent cooperates with different colors
     */
    Agent(int col,double potential,boolean same,boolean diff){
//        System.out.println("\t\t\t\tMaking Agent");
    		this.color=col;
        this.ptr=potential;
        this.coop_same=same;
        this.coop_diff=diff;
    }

    /**
     * Returns isCoopSame value
     * @return - isCoopSame variable
     */
    public boolean isCoopSame()
    {
    	return coop_same;
    }


    /**
     * Returns isCoopDiff value
     * @return - isCoopDiff variable
     */
    public boolean isCoopDiff()
    {
    	return coop_diff;
    }


    /**
     * Returns the agent as a string
     * @return - a string corresponding to the agent
     */
    public String toString()
    {
    	String sameFlag = coop_same? "C":"D";
    	String diffFlag = coop_diff? "C":"D";
    	return(sameFlag+diffFlag+"-"+String.format("%0.2f", ptr));
    }


    /**
     * The agent gives from it's ptr to another agent
     * @param amount - the amount to give
     * @param a - the agent to give the amount to
     */
    public void give(double amount,Agent a){
        //this.ptr=-amount;
        a.setPtr(a.getPtr()+amount);
    }
    
    
    /**
     * The agent takes from an agent's ptr
     * @param amount - the amount to take
     * @param a - the agent to take from
     */
    public void take(double amount,Agent a){
        //this.ptr=+amount;
        this.setPtr(a.getPtr()-amount);
    }


    /**
     * Simulates agent interaction.
     * @param cost - the cost of interaction for an agent
     * @param gain - the gain by an interaction to an agent
     * @param a - the agent to cooperate
     */
    public void cooperate(double cost,double gain, Agent a){
        take(cost,this);
        give(gain,a);

    }


    /**
     * Sets the Ptr value
     * @param amount - the value of the Ptr
     */
    public void setPtr(double amount){
        this.ptr=amount;
    }


    /**
     * Gets the Ptr value
     * @return - the value of the Ptr
     */
    public double getPtr(){
        return this.ptr;
    }


    /**
     * Gets the color value
     * @return - the value of the color
     */
    public int getColor(){
        return this.color;
    }


    /**
     * Checks if the agent is Altruist
     * @return - true if agent is altruist and false otherwise
     */
    public boolean isAlturist(){
        return coop_same && coop_diff;
    }


    /**
     * Checks if the agent is Egoist
     * @return - true if agent is egoist and false otherwise.
     */
    public boolean isEgoist(){
        return !coop_same && !coop_diff;
    }


    /**
     * Checks if agent is Ethnocentric
     * @return - true if agent is ethnocentric and false otherwise.
     */
    public boolean isEthnocentric(){
        return coop_same && !coop_diff;
    }


    /**
     * Checks if agent is Cosmopolitan
     * @return - true if agent is cosmopolitan and false otherwise.
     */
    public boolean isCosmopolitan(){
        return !coop_same && coop_diff;
    }


    /**
     * Returns true or false according to a probability
     * @param prob - the probabilty to check against
     * @return - true if probable and false otherwise
     */
    public boolean check(double prob){
        Random r=new Random();
        if(r.nextDouble()<prob){
            return false;
        }
        else {
            return true;
        }
    }


    /**
     * Checks if an agent can die according to the death rate
     * @param death_rate - the death rate to check against
     * @return - true if an agent can die and false otherwise
     */
    public boolean die(double death_rate){
        return  check(death_rate);
    }

    /**
     * Simulates the reproduction of an agent
     * @param mut_rate - the mutation rate of the agent
     * @param ptr - the potential to reproduce of an agent
     * @param min_col - the minimum color
     * @param max_col - the maximum color
     * @return - the child agent after reproduction
     * @throws Ext.OutOfTheWorldException
     */
    public Agent reproduce(double mut_rate,double ptr,int min_col,int max_col) throws OutOfTheWorldException {
        Random r=new Random();
        int col=this.color;
        boolean same=this.coop_same;
        boolean diff =this.coop_diff;
        if(check(mut_rate)){
            int c=r.nextInt();
            if(c%2==0){
                col=+1;
            }
            else{
                col=-1;
            }
        }
        if(col>max_col)
            col=max_col;
        if(col<min_col)
            col=min_col;
        if(check(mut_rate)){
            same=!same;
        }
        if (check(mut_rate)){
            diff=!diff;
        }
        return new Agent(col,ptr,same,diff);
    }

}
