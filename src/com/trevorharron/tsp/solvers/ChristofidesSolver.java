package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.trevorharron.tsp.aux.Pair;
import com.trevorharron.tsp.aux.Tree;
import com.trevorharron.tsp.aux.TreeNode;
import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.GraphFactory;
import com.trevorharron.tsp.graph.edge.Edge;

public class ChristofidesSolver extends BasicSolver {

	Graph eulerGraph;
	
	public ChristofidesSolver(){
		
	}
	
	public ChristofidesSolver(Graph graph){
		setGraph(graph);
	}
	
	@Override
	public ArrayList<String> solve(){
		
		ArrayList<String> V = new ArrayList<String>();
		for(String v: graph.getCities().keySet())
			V.add(v);
		long seed = System.nanoTime();
		Collections.shuffle(V,new Random(seed));//random start
		//make the Tree
		Tree mst = makeMST(V.get(0));
		
		ArrayList<String> oddNodes = mst.getOddNodes();
		System.out.println(eulerGraph.getRoads());
		
		ArrayList<Pair<String,String>> pairs = getMinimalPairs(oddNodes);
		System.out.println(pairs);
		
		unionWithTree(pairs);
		
		ArrayList<String> cycle =  eurlerCycle(eulerGraph, V.get(0));
		
		ArrayList<String> result = takeShortcuts(cycle);
		
		return result;
		
	}

	private void unionWithTree(ArrayList<Pair<String, String>> pairs) {
		for(Pair<String,String> pair: pairs){
			Edge e =  graph.getRoad(pair.getLeft(), pair.getRight());
			eulerGraph.addEdge(e);
			e = graph.getRoad(pair.getRight(),pair.getLeft());
			eulerGraph.addEdge(e);
		}
	}

	private ArrayList<String> eurlerCycle(Graph euler, String start) {
		
		ArrayList<Edge> used = new ArrayList<Edge>();
		ArrayList<Edge> edges = eulerGraph.getRoads();
		String current = start;
		ArrayList<String> cycle = new ArrayList<String>();
		cycle.add(current);
		while(used.size() < edges.size()){
			ArrayList<Edge> roads = euler.getRoadsFromCity(current);
			for(Edge e: roads){
				if(!used.contains(e)){
					used.add(e);
					used.add(euler.getRoad(e.getTo(), e.getFrom()));
					cycle.add(e.getTo());
					current = e.getTo();
					break;
				}
			}
			System.out.println(current);
			System.out.println(used.size());
			System.out.println(edges.size());
		}
		return cycle;	
	}
	
	private ArrayList<Pair<String, String>> getMinimalPairs(
			ArrayList<String> oddNodes) {
		
		ArrayList<Edge> edges = new ArrayList<Edge>();	
		for(String node: oddNodes){
			double dist = 900000000000.0;
			Edge e = null;
			for(String otherNode:oddNodes){
				Edge r = graph.getRoad(node, otherNode);
				if(!node.equals(otherNode) && r.getDistance() < dist){
					dist =  r.getDistance();
					e = r;
				}
			}
			if(e!=null){
				edges.add(e);
				edges.add(graph.getRoad(e.getTo(), e.getFrom()));
			}
			
		}
		ArrayList<String> used = new ArrayList<String>();
		ArrayList<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>();
		ArrayList<Edge> graphEdges = eulerGraph.getRoads();
		for(Edge e: edges){
			if(!used.contains(e.getTo()) && !used.contains(e.getFrom())
					&& !graphEdges.contains(e)){
				pairs.add(new Pair<String,String>(e.getFrom(), e.getTo()));
				used.add(e.getFrom());
				used.add(e.getTo());
			}
		}	
		return pairs;
	}
	
	private ArrayList<String> takeShortcuts(ArrayList<String> cycle) {
		ArrayList<String> route  = new ArrayList<String>();
		for(String city: cycle){
			if(!route.contains(city))
				route.add(city);
		}
		return route;
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
				frontierRoads.addAll(graph.getRoadsFromCity(node));
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
			eulerGraph.addEdge(e);
			eulerGraph.addEdge(graph.getRoad(e.getTo(), e.getFrom()));
		}
		return t;
	}

	@Override
	public void setGraph(Graph graph){
		super.setGraph(graph);
		try {
			eulerGraph = new GraphFactory().getGraph(graph.getClass());
			for(String name: graph.getCities().keySet())
				eulerGraph.addCity(graph.getCity(name));
			eulerGraph.finalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
