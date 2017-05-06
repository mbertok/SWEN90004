/**
 * Created by marci on 6/05/2017.
 */
public class Agent {
    private int color;			//color of ethnicity
    private double ptr;			//agent's potential to reproduce
    private boolean coop_same;	//if agent will cooperate with same ethnicity
    private boolean coop_diff;	//if agent will cooperate with different ethnicity
    Agent(int col,double potential,boolean same,boolean diff){
        this.color=col;
        this.ptr=potential;
        this.coop_same=same;
        this.coop_diff=diff;
    }
    public void give(){
        this.ptr=-amount;
        a.setPtr(a.getPtr()+amount);
    }
    public void setPtr(double amount){
        this.ptr=amount;
    }
    public double getPtr(){
        return this.ptr;
    }
}
