package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.GraphSymmetric;
import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.Node;

public class NNSolver implements Solver {
	
	private static final double INF = 1000000000.0;
	
	private Graph graph;
	private ArrayList<String> V;
	private ArrayList<String> U;
	
	public NNSolver(final Graph graph){
		this.graph = graph;
		this.V = new ArrayList<String>();
		this.U = new ArrayList<String>();
	}
	
	public NNSolver(){
		this.V = new ArrayList<String>();
		this.U = new ArrayList<String>();
	}
	
	public void setGraph(final Graph graph){
		this.graph = graph;
	}


	@Override
	public ArrayList<String> solve() {
		//Initializing variables
		long startTime = System.nanoTime();
		
		for(String v: graph.getCities().keySet())
			V.add(v);
		long seed = System.nanoTime();
		Collections.shuffle(V,new Random(seed));//random start
		U.add(V.get(0));
		V.remove(0);
		
		//The algorithm
		double distance = 0.0;
		while(!V.isEmpty()){
			String u = U.get(U.size()-1);
			Edge e = findMinVertex(graph.getCity(u),graph);
			graph.setRoadVisited(true,e);
			String nextCity = e.getTo();
			distance += e.getDistance();
			U.add(nextCity);
			V.remove(nextCity);
		}
		
		Edge road = findRouteToStart(U);
		U.add(road.getTo());
		distance += road.getDistance();
		U.add(""+((System.nanoTime()-startTime)*1.0e-9));
	    System.gc();
	    double usedMB = (Runtime.getRuntime().totalMemory() - 
	    		Runtime.getRuntime().freeMemory()) / 1024.0;
	    U.add(""+(usedMB));
		U.add(""+distance);
		return U;
	}
	
	private Edge findRouteToStart(ArrayList<String> U) {
		Node start = graph.getCity(U.get(0));
		Node last = graph.getCity(U.get(U.size()-1));
		
		return ((GraphSymmetric) graph).getRoad(last.getReadPos(),start.getReadPos());
	}


	private Edge findMinVertex(Node node, Graph graph){
		ArrayList<Edge> roads = ((GraphSymmetric) graph).getRoadsByCity(node.getName());
		double minDis = INF;
		Edge minRoad = null;
		for(Edge road: roads){
			if(!U.contains(road.getTo()) && V.contains(road.getTo()) &&
					minDis > road.getDistance()){
				minRoad = road;
				minDis = minRoad.getDistance();
			}
		}
		return minRoad;
	}

}
