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
		this.graph = graph;
		try{
			pathGraph = new GraphFactory().getGraph(graph.getClass());
			for(String name: graph.getCities().keySet())
				pathGraph.addCity(graph.getCity(name));
			pathGraph.finalize();
			roads =  graph.getRoads();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@SuppressWarnings("finally")
	public ArrayList<String> solve() throws NoSolutionException {		
		long startTime = System.nanoTime();
		ArrayList<String> result = new ArrayList<String>();
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
		maxEdges = graph.getCities().keySet().size();
		route = new ArrayList<Edge>();
		//getting the routes
		try{
			int numEdges = 0;
			while(numEdges < maxEdges){
				Edge road = roads.get(0);
				
				if(!moreThanTwoDegrees(road,route) && 
						!makesCycleLessThanN(road, route)){
					pathGraph.addEdge(road);
					route.add(road);
					System.out.println(route.size());
					numEdges++;
				}
				roads.remove(0);
			}
		} catch(Exception e){
			throw new NoSolutionException(e.getMessage());
		} finally{
			
			//obtaining the result
			
			double distance = 0.0;
			System.out.println(route);
			findRoute(result,distance);
			//final preparing of the data
			result.add(result.get(0));
			result.add(""+((System.nanoTime()-startTime)*1.0e-9));
		    System.gc();
		    double usedMB = (Runtime.getRuntime().totalMemory() - 
		    		Runtime.getRuntime().freeMemory()) / 1024.0;
		    result.add(""+usedMB);
			result.add(""+distance);
		}
		return result;
	}

	private void findRoute(ArrayList<String> result, double distance) {
		String cityName = route.get(0).getFrom();
		for(int count = 0; count < maxEdges; count ++){
			result.add(cityName);
			distance += pathGraph.getRoadsByCity(cityName).get(0).getDistance();
		}
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
		return queue.contains(from) && queue.size() < maxEdges;
	}

	private ArrayList<String> linearSearch(Edge r, String start) {
		// this is assuming that there is one path to each node and one
		// path from each node (by design)
		Node current = pathGraph.getCity(start);
		
		ArrayList<String> queue = new ArrayList<String>();
		
		ArrayList<Edge> edges =  pathGraph.getRoadsByCity(current.getName());
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
