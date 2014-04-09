package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.trevorharron.tsp.aux.Tree;
import com.trevorharron.tsp.aux.TreeNode;
import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.edge.Edge;

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

		ArrayList<String> V = new ArrayList<String>();
		for(String v: graph.getCities().keySet())
			V.add(v);
		try{
			long seed = System.nanoTime();
			Collections.shuffle(V,new Random(seed));//random start
			//make the Tree
			Tree mst = makeMST(V.get(0));
			//get the result
			ArrayList<String> result = mst.treeWalk();
			
			//get the metrics
			result.add(result.get(0));
			double distance = getRouteDistance(result);
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
		//A variation of Primm's Algorithm
		Tree t = new Tree(new TreeNode(graph.getCity(start)));
		HashMap<String,TreeNode> inTree =  new HashMap<String,TreeNode>();
		inTree.put(start,t.getRoot());
		Set<String> frontier = new HashSet<String>();
		frontier.add(start);
		Set<String> unvisited =  new HashSet<String>();
		for(String v: graph.getCities().keySet())
			if(!v.equals(start))
				unvisited.add(v);

		while(!unvisited.isEmpty()){
			ArrayList<Edge> frontierRoads = new ArrayList<Edge>();
			for(String node: frontier)
				frontierRoads.addAll(graph.getRoadsByCity(node));
			ArrayList<Edge> roads = new ArrayList<Edge>();
			for(Edge e: frontierRoads)
				if(unvisited.contains(e.getTo()))
					roads.add(new Edge(e));
			Collections.sort(roads);
			Edge e =  roads.get(0);
			
			TreeNode tNode =  new TreeNode(graph.getCity(e.getTo()));
			TreeNode fNode = inTree.get(e.getFrom());
			if(fNode.getLeft() == null)
				fNode.setLeft(tNode);
			else{
				fNode.setRight(tNode);
				frontier.remove(e.getFrom());
			}
			frontier.add(e.getTo());
			inTree.put(e.getFrom(), fNode);
			inTree.put(e.getTo(), tNode);
			unvisited.remove(e.getTo());
		}
		return t;
	}

}
