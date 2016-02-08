package searcher;

import java.util.ArrayList;


public final class Station implements Node {
	private ArrayList<Edge> aEdges = new ArrayList<Edge>();
	private String aName;
	private boolean aVisited = false;
	private String aParent = null;
	private int aCost = Integer.MAX_VALUE;
	private int aDepth = 0;
	private int aLineFromParent = 0;
	
	public int getCost() {
		return aCost;
	}

	public void setCost(int pCost) {
		this.aCost = pCost;
	}

	public Station(String pName)
	{
		aName = pName;
	}	
	
	//copy constructor, Edges are immutable
	public Node copy()
	{
		Station cp = new Station(this.aName);
		for (Edge e : this.getEdges())
		{
			cp.aEdges.add(e); 
		}
		
		return cp;
	}

	public ArrayList<Edge> getEdges() {
		ArrayList<Edge> toreturn = new ArrayList<Edge>();
		for (Edge e : aEdges)
		{
			toreturn.add(e);
		}
		return toreturn;
	}

	public void addEdge(Edge pEdge)
	{
		this.aEdges.add(pEdge);
	}

	public String getName() {
		return aName;
	}

	public boolean getVisited() {
		return aVisited;
	}

	public void setVisited(boolean pVisited) {
		this.aVisited = pVisited;
	}

	public String getParent() {
		return aParent;
	}

	public void setParent(String pParent) {
		this.aParent = pParent;
	}

	public void setDepth(int pDepth) {
		aDepth = pDepth;
	}
	public int getDepth() {
		return aDepth;
	}

	public void setLineFromParent(int aLineFromParent) {
		this.aLineFromParent = aLineFromParent;
	}

	public int getLinefromParent() {
		return aLineFromParent;

	}

}
