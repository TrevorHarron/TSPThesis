package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.GraphFactory;
import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.Node;

public class GreedySolver extends BasicSolver {

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
			for(String name: graph.getCities().keySet())
				pathGraph.addCity(graph.getCity(name));
			pathGraph.finalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public GreedySolver(){
		roads = new ArrayList<Edge>();
	}
	
	@Override
	public void setGraph(Graph graph) {
		super.setGraph(graph);
		try {
			pathGraph = new GraphFactory().getGraph(graph.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(String name: graph.getCities().keySet())
			pathGraph.addCity(graph.getCity(name));
		pathGraph.finalize();
		for(Edge r: graph.getRoads())
			roads.add(new Edge(r));
	}
	

	public ArrayList<String> solve() throws NoSolutionException {		
		long startTime = System.nanoTime();
		ArrayList<String> result = new ArrayList<String>();
		//sorting and ensuring there are no duplicates
		Collections.sort(roads);
		maxEdges = graph.getCities().keySet().size();
		route = new ArrayList<Edge>();
		//finding the route
		try{
			int numEdges = 0;
			while(numEdges < maxEdges){
				Edge road = roads.get(0);
				if(!moreThanTwoDegrees(road,route) && 
						!makesCycleLessThanN(road, route)){
					pathGraph.addEdge(road);
					route.add(road);
					numEdges++;
				}
				roads.remove(0);
			}
			//obtaining the result
			double distance = findRoute(result, route);
			//final preparing of the data
			result.add(""+((System.nanoTime()-startTime)*1.0e-9));
			System.gc();
			double usedKB = (Runtime.getRuntime().totalMemory() - 
		    		Runtime.getRuntime().freeMemory()) / 1024.0;
			result.add(""+usedKB);
			result.add(""+distance);
			return result;
		} catch(Exception e){
			throw new NoSolutionException(e.getMessage());
		}
	}

	private double findRoute(ArrayList<String> result, ArrayList<Edge> route) {
		double distance = 0.0;
		HashMap<String,Edge> roads = new HashMap<String,Edge>();
		for(Edge road: route)
			roads.put(road.getFrom(), road);
		String cityName = route.get(0).getFrom();
		while(result.size() <= maxEdges ){
			result.add(cityName);
			distance += roads.get(cityName).getDistance();
			cityName = roads.get(cityName).getTo();
		}
		return distance;
	}

	private boolean moreThanTwoDegrees(Edge road, ArrayList<Edge> route) {
		
		HashMap<String,Integer> fromCounts = new HashMap<String,Integer>();
		HashMap<String,Integer> toCounts = new HashMap<String,Integer>();
		
		fromCounts.put(road.getFrom(), 1);
		toCounts.put(road.getTo(), 1);
		
		for(Edge e: pathGraph.getRoads()){
			if(fromCounts.containsKey(e.getFrom()))
				fromCounts.put(e.getFrom(), fromCounts.get(e.getFrom())+1);
			else
				fromCounts.put(e.getFrom(), 1);
			
			if(toCounts.containsKey(e.getTo()))
				toCounts.put(e.getTo(), toCounts.get(e.getTo())+1);
			else
				toCounts.put(e.getTo(), 1);
		}
		
		for(String key: fromCounts.keySet())
			if(fromCounts.get(key) > 1) return true;
		
		for(String key: toCounts.keySet())
			if(toCounts.get(key) > 1) return true;
		
		return false;
	}

	private boolean makesCycleLessThanN(final Edge r, final ArrayList<Edge> cRoute){
		String to = r.getTo();
		String from =  r.getFrom();
		ArrayList<String> queue = linearSearch(r, to);
		
		return queue.contains(from) && !(queue.size() == maxEdges);
	}

	private ArrayList<String> linearSearch(Edge r, String start) {
		// this is assuming that there is one path to each node and one
		// path from each node (by design)
		Node current = pathGraph.getCity(start);
		
		ArrayList<String> queue = new ArrayList<String>();
		
		ArrayList<Edge> edges =  pathGraph.getRoadsByCity(current.getName());
		queue.add(start);
		while(!edges.isEmpty()){
			if(edges.get(0).getTo().equals(start)) break;
			Node next = pathGraph.getCity(edges.get(0).getTo());
			queue.add(next.getName());
			current = next;
			edges =  pathGraph.getRoadsByCity(current.getName());
		}
		
		return queue;
	}
	
}
