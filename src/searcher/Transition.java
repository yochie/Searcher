package searcher;

public class Transition implements Edge {
	private final int aLine;
	private final int aWeight;
	private final String aSrc;
	private final String aDest;
	private int aDepth;
		
	public Transition(int pLine, int pWeight, String pFrom, String pNeighbour)
	{
		this.aSrc = pFrom;
		this.aDest = pNeighbour;
		this.aWeight = pWeight;
		this.aLine = pLine;
	}

	public int getWeight() {
		return aWeight;
	}

	public int getLine() {
		return aLine;
	}

	public String getSrc() {		
		return aSrc;
	}

	public String getDest() {
		return aDest;
	}

	public int getDepth() {
		return aDepth;
	}

	public void setDepth(int pDepth) {
		this.aDepth = pDepth;
	}

}
