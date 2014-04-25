package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.GraphSymmetric;
import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.Node;

public class NNSolver extends BasicSolver {
	
	private static final double INF = 1000000000.0;
	
	private ArrayList<String> V;
	private ArrayList<String> U;
	
	public NNSolver(final Graph graph){
		this.V = new ArrayList<String>();
		this.U = new ArrayList<String>();
		setGraph(graph);
		for(String v: graph.getCities().keySet())
			V.add(v);
	}
	
	public NNSolver(){
		this.V = new ArrayList<String>();
		this.U = new ArrayList<String>();
	}
	
	public void setGraph(final Graph graph){
		super.setGraph(graph);
		for(String v: graph.getCities().keySet())
			V.add(v);
	}


	@Override
	public ArrayList<String> solve() {
		//Initializing variables
		long startTime = System.nanoTime();
		
		long seed = System.nanoTime();
		Collections.shuffle(V,new Random(seed));//random start
		double distance = 0.0;
		U.add(V.get(0));
		V.remove(0);
			
		//The algorithm
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
		
		U = getMetrics(U, startTime,distance);
		
		return U;
	}
	
	private Edge findRouteToStart(ArrayList<String> U) {
		Node start = graph.getCity(U.get(0));
		Node last = graph.getCity(U.get(U.size()-1));
		
		return ((GraphSymmetric) graph).getRoad(last.getReadPos(),start.getReadPos());
	}


	private Edge findMinVertex(Node node, Graph graph){
		ArrayList<Edge> roads = graph.getRoadsFromCity(node.getId());
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
