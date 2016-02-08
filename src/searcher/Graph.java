package searcher;
import java.util.*;
import java.util.Collections;

public class Graph{
	private ArrayList<Node> aNodes;
	private int aNodeNum = 0;
	
	public Graph(){
		this.aNodes = new ArrayList<Node>();
	}
	
	public void addNode(Node pNode)
	{
			this.aNodes.add(pNode.copy());
			aNodeNum++;			
	}
	
	//breadth first search
	public ArrayList<String> BFS(String pSrc, String pDest)
	{
		Queue<Node> s = new LinkedList<Node>();
		ArrayList<String> solutionPath = new ArrayList<String>();
		
		Node srcNode = aNodes.get(this.findNode(pSrc));
		s.add(srcNode);
		Node currentn = srcNode;
		
		while (!s.isEmpty() && (currentn = s.remove()).getName().compareTo(pDest) != 0)
		{
			currentn.setVisited(true);
			
			for (Edge e : currentn.getEdges())
			{
				Node child = aNodes.get(findNode(e.getDest()));
				if (!child.getVisited() && !s.contains(child))
				{
				
						s.add(child);
						child.setParent(currentn.getName());
						child.setLineFromParent(e.getLine());
				}
			}
		}
		
		//Build solution path from parent structure
		while (currentn.getName().compareTo(pSrc) != 0)
		{
			solutionPath.add(currentn.getName() + " Line : " + currentn.getLinefromParent());
			currentn = aNodes.get(findNode(currentn.getParent()));  
		}
		Collections.reverse(solutionPath);
		
		System.out.println("BFS :");
		for (String sp : solutionPath)
		{
			System.out.println(sp);
		}
		System.out.println("");
		this.resetVisited();
	
		return solutionPath;
	}
	
	//Depth first search
	public ArrayList<String> DFS(String pSrc, String pDest)
	{
		ArrayList<String> solutionPath = limitedDFS(pSrc, pDest, Integer.MAX_VALUE);
		 
		System.out.println("DFS :");

		for (String sp : solutionPath)
		{
			System.out.println(sp);
		}
		System.out.println("");
		this.resetVisited();
		return solutionPath;
	}
	
	//depth capped version of DFS
	public ArrayList<String> limitedDFS(String pSrc, String pDest, int pMaxDepth)
		{
			Stack<Node> s = new Stack<Node>();
			ArrayList<String> solutionPath = new ArrayList<String>();
			
			Node src = aNodes.get(this.findNode(pSrc));
			src.setDepth(0);
			s.push(src);
			Node currentn = src.copy();
			
			while (!s.isEmpty() && (currentn = s.pop()).getName().compareTo(pDest) != 0)
			{
				aNodes.get(findNode(currentn.getName())).setVisited(true);
				
				//reverse order of edge list so that they are explored in right order
				ArrayList<Edge> reversedEdges = currentn.getEdges();
				Collections.reverse(reversedEdges);
				for (Edge e : reversedEdges)
				{
					Node child = aNodes.get(findNode(e.getDest()));
					if (!child.getVisited())
					{
						if (currentn.getDepth() < pMaxDepth)
						{	
							child.setDepth(currentn.getDepth() + 1);
							s.push(child);
							child.setParent(currentn.getName());
							child.setLineFromParent(e.getLine());
						}
					}
				}
			}
			
			//Build solution path from parent structure
			while (currentn.getName().compareTo(pSrc) != 0)
			{
				solutionPath.add(currentn.getName() + " Line : " + currentn.getLinefromParent());
				currentn = aNodes.get(findNode(currentn.getParent()));  
			}
			Collections.reverse(solutionPath);
			this.resetVisited();
			
			return solutionPath;
		}
		
	//Uniform cost search
	public ArrayList<String> UCS(String pSrc, String pDest)
	{
		//priority queue for nodes to explore
		PriorityQueue<Node> r = new PriorityQueue<Node>(10, 
				new Comparator<Node>(){
					public int compare(Node one, Node two)
					{	
						return one.getCost() - two.getCost();
					}
				});
		
		ArrayList<String> solutionPath = new ArrayList<String>();
		
		Node src = aNodes.get(this.findNode(pSrc));
		r.add(src);
		Node currentn = src.copy();
		
		//while destination is not reached
		while ( !r.isEmpty() && (currentn = r.remove()).getName().compareTo(pDest) != 0)
		{
			aNodes.get(findNode(currentn.getName())).setVisited(true);
			
			for (Edge e : currentn.getEdges())
			{
				Node child = aNodes.get(findNode(e.getDest()));
			
				//if previous cost for child was higher (ie node is unvisited)
				if (child.getCost() > currentn.getCost() + e.getWeight())
				{
					//
					child.setCost(currentn.getCost() + e.getWeight());
					child.setParent(currentn.getName());
					child.setLineFromParent(e.getLine());
					if (!r.contains(child))
					{
						r.add(child);	
					}
				}
			}
			
		}
		

		while (currentn.getName().compareTo(pSrc) != 0)
		{
			solutionPath.add(currentn.getName() + " Line : " + currentn.getLinefromParent());
			currentn = aNodes.get(findNode(currentn.getParent()));  
		}
		Collections.reverse(solutionPath);
	
		System.out.println("UCS :");

		for (String sp : solutionPath)
			{
				System.out.println(sp);
			}
		System.out.println("");
		
		this.resetVisited();
		
		return solutionPath;
	}
	
	//Iterative DFS
	public ArrayList<String> IDFS(String pSrc, String pDest, int pStepSize)
	{
		int depth = 1;
		int i = 0;
		ArrayList<String> output = limitedDFS(pSrc, pDest, depth + i*pStepSize);

		while (!output.get(output.size() -1).contains(pDest))
		{
			output = limitedDFS(pSrc, pDest, depth + i*pStepSize);
			i++;
		}
		System.out.println("IDFS :");
		for (String sp : output)
		{
			System.out.println(sp);
		}
		System.out.println("");
		this.resetVisited();
		return output;

	}
	
	//returns the node object from graph given its name
	private int findNode(String name)
	{
		assert name != null;
		int i = 0;
		for (Node node : aNodes)
		{
			if (node.getName().contentEquals(name))
			{
				return i;
			}
			i++;
		}
		
		return -1;
	}
	
	//adds edge to appropriate node
	public void addEdge(Edge pEdge)
	{
		aNodes.get(findNode(pEdge.getSrc())).addEdge(pEdge);
	}

	//returns total number of nodes in graph
	public int getNodeNum() {
		return aNodeNum;
	}
	
	//resets visited state of all nodes in graph
	private void resetVisited(){
		for (Node n : aNodes)
		{
			n.setVisited(false);
		}
	}

}
