/**
 * Created by marci on 6/05/2017.
 */
package SWEN90004;

import java.util.Random;

public class Agent {
    public int color;
    public double ptr;
    private boolean coop_same;
    private boolean coop_diff;
    Agent(int col,double potential,boolean same,boolean diff){
        this.color=col;
        this.ptr=potential;
        this.coop_same=same;
        this.coop_diff=diff;
    }
    public void give(double amount,Agent a){
        this.ptr=-amount;
        a.setPtr(a.getPtr()+amount);
    }
    public void take(double amount,Agent a){
        this.ptr=+amount;
        a.setPtr(a.getPtr()-amount);
    }
    public void setPtr(double amount){
        this.ptr=amount;
    }
    public double getPtr(){
        return this.ptr;
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

}
