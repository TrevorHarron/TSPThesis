package com.trevorharron.tsp.graph;

import java.util.ArrayList;
import java.util.HashMap;

import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.Node;
import com.trevorharron.tsp.graph.node.SimpleNode;

public class GraphBasic implements Graph {
	
	private HashMap<String,Node> cities;

	public GraphBasic(){
		cities =  new HashMap<String,Node>();
	}
	
	@Override
	public void addCity(Node city) {
		cities.put(city.getName(), new SimpleNode(city));
	}

	@Override
	public HashMap<String, Node> getCities() {
		return cities;
	}

	@Override
	public void addEdge(Edge road) {
		SimpleNode c = (SimpleNode) cities.get(road.getFrom());
		c.addRoad((SimpleNode)cities.get(road.getTo()), road.getDistance());
	}

	@Override
	public ArrayList<Edge> getRoads() {
		SimpleNode c;
		ArrayList<Edge> rds = new ArrayList<Edge>();
		for(String key: cities.keySet()){
			c = (SimpleNode)cities.get(key);
			for(Edge e: c.getRoads())
				if(!rds.contains(e))
					rds.add(e);
		}
		return rds;
	}

	@Override
	public void resetGraph() {
		for(String key: cities.keySet())
			cities.get(key).setVisited(false);
	}

	@Override
	public void setRoadVisited(boolean visited, Edge r) {
		cities.get(r.getFrom()).setVisited(visited);
	}

	@Override
	public ArrayList<Edge> getRoadsByCity(String name) {
		SimpleNode n =(SimpleNode) cities.get(name);
		return n.getRoads();
	}

	@Override
	public Node getCity(String name) {
		return cities.get(name);
	}

	@Override
	public Edge getRoad(String to, String from) {
		SimpleNode n =(SimpleNode) cities.get(from);
		for(Edge e: n.getRoads()){
			if(e.getFrom().equals(to))
				return e;
		}
		return null;
	}

	@Override
	public void finalize(){}

}
