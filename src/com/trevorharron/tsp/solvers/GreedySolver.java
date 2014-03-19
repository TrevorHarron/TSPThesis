package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.GraphFactory;
import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.Node;

public class GreedySolver implements Solver {

	private Graph graph;
	private Graph pathGraph;
	private ArrayList<Edge> roads;
	private ArrayList<Edge> route;
	private int maxEdges;
	
	public GreedySolver(final Graph graph){
		this.setGraph(graph);
		for(Edge e: graph.getRoads())
			roads.add(e);
		try{
			pathGraph = new GraphFactory().getGraph(graph.getClass());
		}catch (Exception e) {
			e.printStackTrace();
		}
		for(String name : graph.getCities().keySet())
			pathGraph.addCity(graph.getCity(name));
	}
	
	public GreedySolver(){
		roads = new ArrayList<Edge>();
	}
	
	@Override
	public void setGraph(Graph graph) {
		this.graph = graph;
		try{
			pathGraph = new GraphFactory().getGraph(graph.getClass());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<String> solve() {		
		long startTime = System.nanoTime();
		//sorting and ensuring there are no duplicates
		Collections.sort(roads);
		Edge prev = null;
		ArrayList<Edge> sRoads = new ArrayList<Edge>();
		for(Edge road:roads){
			if(!road.equals(prev))
				sRoads.add(road);
			prev = road;
		}
		roads = sRoads;
		//getting the routes
		maxEdges = graph.getCities().keySet().size();
		route = new ArrayList<Edge>();
		int numEdges = 0;
		while(numEdges < maxEdges){
			Edge road = roads.get(0);
			if(!(makesCycleLessThanN(road, route) || moreThanTwoDegrees(road,route))){
				route.add(road);
				numEdges++;
			}
			roads.remove(0);
		}
		//obtaining the result
		ArrayList<String> result = new ArrayList<String>();
		double distance = 0.0;
		findRoute(result,distance);
		//final preparing of the data
		result.add(result.get(0));
		result.add(""+((System.nanoTime()-startTime)*1.0e-9));
	    System.gc();
	    double usedMB = (Runtime.getRuntime().totalMemory() - 
	    		Runtime.getRuntime().freeMemory()) / 1024.0;
	    result.add(""+usedMB);
		result.add(""+distance);
		return result;
	}

	private void findRoute(ArrayList<String> result, double distance) {
		HashMap<String,Edge> fromCities = new HashMap<String,Edge>();
		for(Edge road: route){
			fromCities.put(road.getTo(), road);
		}
		String city = route.get(0).getTo();
		int edgeCount = 0;
		System.out.println(route);
		while(edgeCount < route.size()){
			result.add(city);
			Edge road = fromCities.get(city);
			distance += road.getDistance();
			city = fromCities.get(city).getFrom();
			edgeCount++;
		}
		//Node start = graph.getCity(route.get(0).getFrom());
		//Node last = graph.getCity(city);
		//distance += graph.getRoad(last.getReadPos(),start.getReadPos()).getDistance();
		//result.add(route.get(0).getFrom());
	}

	private boolean moreThanTwoDegrees(Edge road, ArrayList<Edge> route) {
		
		HashMap<String,Integer> toCount = new HashMap<String,Integer>();
		HashMap<String,Integer> fromCount = new HashMap<String,Integer>();
		
		String from = road.getFrom();
		String to = road.getTo();
		fromCount.put(from, 1);
		toCount.put(to,1);
		for(Edge r: route){
			from = r.getFrom();
			to = r.getTo();
			
			if(fromCount.containsKey(from))
				fromCount.put(from, fromCount.get(from)+1);
			else
				fromCount.put(from, 1);
			
			if(toCount.containsKey(to))
				toCount.put(to, toCount.get(to)+1);
			else
				toCount.put(to, 1);
		}
		for(String r: toCount.keySet())
			if(toCount.get(r) >= 2)
				return true;
		for(String r: fromCount.keySet())
			if(fromCount.get(r) >= 2)
				return true;
		return false;
	}

	private boolean makesCycleLessThanN(final Edge r, final ArrayList<Edge> cRoute){
		String to = r.getTo();
		String from =  r.getFrom();
		ArrayList<String> queue = breadthFirstSearch(r, from);
		return true;
	}

	private ArrayList<String> breadthFirstSearch(Edge r, String start) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
