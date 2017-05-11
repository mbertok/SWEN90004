package SWEN90004;

import java.util.Random;
public class Agent {
    public int color;
    public double ptr;
    private boolean coop_same;
    private boolean coop_diff;
    //Default constructor
    Agent(int col,double potential,boolean same,boolean diff){
        this.color=col;
        this.ptr=potential;
        this.coop_same=same;
        this.coop_diff=diff;
    }
    public boolean isCoopSame()
    {
    	return coop_same;
    }
    public boolean isCoopDiff()
    {
    	return coop_diff;
    }
    public String toString()
    {
    	String sameFlag = coop_same? "C":"D";
    	String diffFlag = coop_diff? "C":"D";
    	return(sameFlag+diffFlag+"-"+String.format("%0.2f", ptr));
    }
    public void give(double amount,Agent a){
        //this.ptr=-amount;
        a.setPtr(a.getPtr()+amount);
    }
    public void take(double amount,Agent a){
        //this.ptr=+amount;
        this.setPtr(a.getPtr()-amount);
    }
    public void cooperate(double cost,double gain,Agent a){
        take(cost,this);
        give(gain,a);
    }
    public void setPtr(double amount){
        this.ptr=amount;
    }
    public double getPtr(){
        return this.ptr;
    }
    public int getColor(){
        return this.color;
    }
    public boolean isAlturist(){
        return coop_same && coop_diff;
    }
    public boolean isEgoist(){
        return !coop_same && !coop_diff;
    }
    public boolean isEthnocentric(){
        return coop_same && !coop_diff;
    }
    public boolean isCosmopolitan(){
        return !coop_same && coop_diff;
    }
    public boolean check(double prob){
        Random r=new Random();
        if(r.nextDouble()<prob){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean die(double death_rate){
        return  check(death_rate);
    }
    public Agent reproduce(double mut_rate,double ptr,int min_col,int max_col) throws OutOfTheWorldException{
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
