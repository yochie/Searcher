package searcher;

import java.io.*;
//Uses 3rd party library that can be found at https://github.com/douglascrockford/JSON-java
import org.json.*;

public class Searcher {
	
	
	public static void main(String[] args) {
	    String jsonData = readFile("brussels_metro_v2.json");
	    JSONObject jobj = new JSONObject(jsonData);
	    JSONArray jstations = new JSONArray(jobj.getJSONArray("stations").toString());
    	Graph g = new Graph();
    	
    	//SETTING FOR QUESTION 1 b)
    	//boolean constantCost = false;
    	boolean constantCost = true;
    	
    	//First pass to create all nodes
	    for(int i = 0; i < jstations.length(); i++)
	    {
	    	Node n = new Station(jstations.getJSONObject(i).getString("name"));
	    	g.addNode(n);
	    }
	    
	    //Second pass to add their edges
	   
	    for( int i = 0; i < g.getNodeNum(); i++)
	    {
	    	JSONArray jedges = new JSONArray(jstations.getJSONObject(i).getJSONArray("neighbours").toString());

	    	for (int j = 0; j < jedges.length(); j++)
	    	{
	    		int line = jedges.getJSONObject(j).getInt("line");
	    		//CONSTANT COST
	    		int cost = getLineCost(jedges.getJSONObject(j).getInt("line"), constantCost);
	    		String neighbourName =  jedges.getJSONObject(j).getString("name");
	    		g.addEdge(new Transition(line, cost, jstations.getJSONObject(i).getString("name"), neighbourName));
	    	}
	    }
	    
	    //Run all algorithms with constant cost transition
	    g.BFS("Gare du Nord", "Roi Baudouin");
	    g.DFS("Gare du Nord", "Roi Baudouin");
	    g.UCS("Gare du Nord", "Roi Baudouin");
	    g.IDFS("Gare du Nord", "Roi Baudouin", 1);
	}
	
	public static int getLineCost(int pLine, boolean pConstantCost)
	{
		if (!pConstantCost)
		{
			switch (pLine) {
				
				case 1 : return 2;
				case 2 : return 2;
				case 3 : return 2;
				case 4 : return 1;
				case 5 : return 1;
				case 6 : return 1;
			}
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	//Method copied from https://blog.nraboy.com/2015/03/parse-json-file-java/
	public static String readFile(String filename) {
	    String result = "";
	    try {
	        @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(filename));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        result = sb.toString();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
		
	
}
