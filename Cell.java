
public class Cell {
int x;
int y;
double ptr;
Agent agent;

Cell(int x,int y,double ptr)
{
	this.x = x;
	this.y = y;
	this.ptr = ptr;
	agent = null;
}
Cell(int x,int y)
{
	this.x = x;
	this.y = y;
	this.ptr = 0;
}
//returns agent in the cell. Null if no agent in cell
public Agent GetAgentInCell()
{
	return agent;
}
public void PutAgentInCell(Agent a)
{
	agent = a;
}
}
