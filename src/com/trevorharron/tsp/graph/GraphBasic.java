package com.trevorharron.tsp.graph;

import java.util.ArrayList;
import java.util.HashMap;

import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.ListNode;
import com.trevorharron.tsp.graph.node.Node;

public class GraphBasic implements Graph {
	
	private HashMap<String,Node> cities;
	private int originalSize;

	public GraphBasic(){
		cities =  new HashMap<String,Node>();
		originalSize = -1;
	}
	
	public GraphBasic(Graph g){
		cities = g.getCities();
		for(Edge e: g.getRoads()){
			addEdge(e);
		}
		originalSize = g.getOriginalSize();
		finalize();
	}
	
	@Override
	public void addCity(Node city) {
		cities.put(city.getName(), (Node) new ListNode(city));
	}

	@Override
	public HashMap<String, Node> getCities() {
		return cities;
	}

	@Override
	public void addEdge(Edge road) {
		ListNode c = new ListNode(cities.get(road.getFrom()));
		c.addRoad(new ListNode(cities.get(road.getTo())), road.getDistance());
	}

	@Override
	public ArrayList<Edge> getRoads() {
		ListNode c;
		ArrayList<Edge> rds = new ArrayList<Edge>();
		for(String key: cities.keySet()){
			c = (ListNode)cities.get(key);
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
	public ArrayList<Edge> getRoadsFromCity(String name) {
		ListNode n =(ListNode) cities.get(name);
		return n.getRoads();
	}

	@Override
	public Node getCity(String name) {
		return cities.get(name);
	}

	@Override
	public Edge getRoad(String from, String to) {
		ListNode n =(ListNode) cities.get(from);
		for(Edge e: n.getRoads()){
			if(e.getFrom().equals(to))
				return e;
		}
		return null;
	}

	@Override
	public void finalize(){
		for(String city: getCities().keySet()){
			if(getRoadsFromCity(city).isEmpty() && !getRoads().isEmpty())
				cities.remove(city);
		}
		if(originalSize == -1)
			originalSize = cities.keySet().size();
	}


	@Override
	public ArrayList<Edge> getRoadsToCity(String name) {
		return new ArrayList<Edge>();
	}

	@Override
	public int getOriginalSize() {
		return originalSize;
	}

	@Override
	public void getCitiesAndSize(Graph graph) {
		originalSize = graph.getOriginalSize();
		cities =  graph.getCities();
	}

	@Override
	public void deleteEdge(Edge edge) {
		// TODO Auto-generated method stub
		
	}

}
