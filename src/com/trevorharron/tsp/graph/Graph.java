package com.trevorharron.tsp.graph;

import java.util.ArrayList;
import java.util.HashMap;

import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.Node;

public interface Graph {
	
	public void addCity(final Node city);
	public HashMap<String, Node> getCities();
	public Node getCity(String name);
	
	public void addEdge(final Edge edge);
	public ArrayList<Edge> getRoads();
	
	public void resetGraph();
	
	public void setRoadVisited(final boolean visited, final Edge r);
	
	public ArrayList<Edge> getRoadsFromCity(String name);
	
	public ArrayList<Edge> getRoadsToCity(String name);
	
	public Edge getRoad(String from, String to);
	
	public void deleteEdge(Edge edge);
	
	public void finalize();
	public int getOriginalSize();
	public void getCitiesAndSize(Graph graph);
}
