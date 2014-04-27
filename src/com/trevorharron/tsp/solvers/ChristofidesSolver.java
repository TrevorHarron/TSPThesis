package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.trevorharron.tsp.aux.Pair;
import com.trevorharron.tsp.aux.Tree;
import com.trevorharron.tsp.aux.TreeNode;
import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.GraphFactory;
import com.trevorharron.tsp.graph.edge.Edge;

public class ChristofidesSolver extends BasicSolver {

	Graph eulerGraph;
	
	public ChristofidesSolver(){}
	
	public ChristofidesSolver(Graph graph){
		setGraph(graph);
	}
	
	@Override
	public ArrayList<String> solve(){
		
		long startTime = System.nanoTime();
		ArrayList<String> V = new ArrayList<String>();
		for(String v: graph.getCities().keySet()) V.add(v);
		long seed = System.nanoTime();
		Collections.shuffle(V,new Random(seed));//random start
		//make the Tree
		Tree mst = makeMST(V.get(0));
		ArrayList<String> oddNodes = mst.getOddNodes();
		ArrayList<Pair<String,String>> pairs = getMinimalPairs(oddNodes);
		
		eulerGraph = unionWithTree(pairs, eulerGraph);
		
		ArrayList<String> cycle =  eulerCycle(eulerGraph,V.get(0));
		ArrayList<String> result = takeShortcuts(cycle);
		double distance = getRouteDistance(result);
		result = getMetrics(result, startTime, distance);
		return result;
	}

	private ArrayList<String> takeShortcuts(ArrayList<String> cycle) {
		ArrayList<String> route  = new ArrayList<String>();
		String start = cycle.get(0);
		for(String city: cycle){
			if(!route.contains(city))
				route.add(city);
		}
		route.add(start);
		return route;
	}
	
	private ArrayList<String> eulerCycle(Graph euler, String start){
		Stack<String> stack = new Stack<String>();
		stack.push(start);
		ArrayList<String> route = new ArrayList<String>();
		
		while(!stack.isEmpty()) {
			String from = stack.peek();
			ArrayList<Edge> edges = euler.getRoadsFromCity(from);
			if(!edges.isEmpty()){
				
				String to = edges.get(0).getTo();
				stack.push(to);
				
				Edge inverse = euler.getRoad(to, edges.get(0).getFrom());
				euler.deleteEdge(edges.get(0));
				euler.deleteEdge(inverse);
			} else
				route.add(stack.pop());			
		}		
		return route;
	}
	
	private ArrayList<Pair<String, String>> getMinimalPairs(ArrayList<String> oddNodes) {
		
		ArrayList<Edge> edges = new ArrayList<Edge>();
		ArrayList<Edge> graphEdges = eulerGraph.getRoads();
		for(String node: oddNodes){
			Edge e = null;
			for(String otherNode:oddNodes){
				Edge r = graph.getRoad(node, otherNode);
				if(!node.equals(otherNode)){
					e = r;						
					if(!graphEdges.contains(e)){
						edges.add(e);
						edges.add(graph.getRoad(e.getTo(), e.getFrom()));
					}
				}
			}	
		}
		ArrayList<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>();
		Collections.sort(edges);

		ArrayList<String> used = new ArrayList<String>();
		for(Edge e: edges){
			String to = e.getTo();
			String from = e.getFrom();
			if(!(used.contains(to) || used.contains(from))){
				used.add(from);
				used.add(to);
				pairs.add(new Pair<String,String>(from,to));
			}
		}
		
		return pairs;
	}
	
	private Graph unionWithTree(ArrayList<Pair<String, String>> pairs, Graph g) {
		for(Pair<String,String> pair: pairs){
			Edge e =  graph.getRoad(pair.getLeft(), pair.getRight());
			g.addEdge(e);
			e = graph.getRoad(pair.getRight(),pair.getLeft());
			g.addEdge(e);
		}
		return g;
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
			eulerGraph.getCitiesAndSize(graph);
			eulerGraph.finalize();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
