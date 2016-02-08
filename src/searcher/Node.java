package searcher;
import java.util.*;

public interface Node {

	public String getName();
	public Node copy();
	public ArrayList<Edge> getEdges();
	public void addEdge(Edge pEdge);
	public boolean getVisited();
	public void setVisited(boolean pVisited);
	public void setParent(String name);
	public String getParent();
	public int getCost();
	public void setCost(int pCost);
	public void setDepth(int pDepth);
	public int getDepth();
	public void setLineFromParent(int line);
	public int getLinefromParent();
}
