package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.Node;

public class MSTSolver implements Solver {

	Graph graph;
	
	public MSTSolver(){}
	
	public MSTSolver(Graph graph){
		this.graph = graph;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	@Override
	public ArrayList<String> solve() throws NoSolutionException{
		long startTime = System.nanoTime();
		ArrayList<String> result  = new ArrayList<String>();
		//try{
		ArrayList<String> V = new ArrayList<String>();
		for(String v: graph.getCities().keySet())
			V.add(v);
		long seed = System.nanoTime();
		Collections.shuffle(V,new Random(seed));//random start
			Tree mst = makeMST(V.get(0));
			result = mst.treeWalk();
		//} catch (Exception e){
		//	throw new NoSolutionException(e.getMessage());
		//}
		result.add(result.get(0));
		double distance = getRouteDistance(result);
		result.add(""+((System.nanoTime()-startTime)*1.0e-9));
		System.gc();
		double usedMB = (Runtime.getRuntime().totalMemory() - 
	    		Runtime.getRuntime().freeMemory()) / 1024.0;
		result.add(""+usedMB);
		result.add(""+distance);
		return result;
	}
	
	private double getRouteDistance(ArrayList<String> result) {
		//get the distances from routes
		double distance = 0.0;
		for(int index = 0; index < result.size()-2; index++){
			String  to = result.get(index);
			String from =  result.get(index+1);
			distance += graph.getRoad(from, to).getDistance();
		}
		return distance;
	}

	private Tree makeMST(String start){
		//based off of Primm's Algorithm
		Set<String> V =  new HashSet<String>();
		graph.getCity(start).setVisited(true);
		V.add(start);
		Set<String> cities = graph.getCities().keySet();
		Set<Edge> E =  new HashSet<Edge>();
		ArrayList<Edge> roads = new ArrayList<Edge>();
		for(Edge r: graph.getRoads())
			roads.add(new Edge(r));
		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		counts.put(start, 0);
		Collections.sort(roads);
		while(!V.equals(cities)){
			Edge u = roads.get(0);
			String from = u.getFrom();
			if(!V.contains(u.getTo()) && 
					!(counts.containsKey(from) && counts.get(from) >= 2) ){
				E.add(u);
				V.add(u.getTo());
				if(!counts.containsKey(from))
					counts.put(from, 0);
				counts.put(from, counts.get(from)+1);
			}
			roads.remove(0);
		}
		return buildTree(new Tree(new TreeNode(graph.getCity(start))), V, E);
	}
	
	private Tree buildTree(Tree t, Set<String> V, Set<Edge> E) {
		//given a set of Vertices and a set of edges build a MST
		//TODO check test
		System.out.println(V);
		System.out.println(E);
		HashMap<String, ArrayList<Edge>> edges =  new HashMap<String, ArrayList<Edge>>();
		for(Edge e: E){
			String from = e.getFrom();
			if(!edges.containsKey(e.getFrom()))
				edges.put(from, new ArrayList<Edge>());
			edges.get(from).add(e);
		}
		HashMap<String, TreeNode> nodes = new HashMap<String,TreeNode>();
		for(String v: V){
			nodes.put(v, new TreeNode(graph.getCity(v)));
		}
		for(String key: nodes.keySet()){
			if(edges.containsKey(key)){
				nodes.get(key).setLeft(nodes.get((edges.get(key).get(0).getTo())).getSelf());
				if ( edges.get(key).size() >1)
					nodes.get(key).setRight(nodes.get((edges.get(key).get(1).getTo())).getSelf());
			}
		}
		t.setRoot(nodes.get(t.root.self.getName())); //TODO check this and ^^
		return t;
	}

}
