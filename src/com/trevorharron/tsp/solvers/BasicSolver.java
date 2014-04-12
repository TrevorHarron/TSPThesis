package com.trevorharron.tsp.solvers;

import java.util.ArrayList;

import com.trevorharron.tsp.graph.Graph;

public class BasicSolver implements Solver {
	
	protected Graph graph;

	@Override
	public ArrayList<String> solve() throws NoSolutionException {
		return new ArrayList<String>();
	}

	@Override
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	protected double getRouteDistance(ArrayList<String> result) {
		//get the distances from routes
		double distance = 0.0;
		for(int index = 0; index < result.size()-2; index++){
			String  to = result.get(index);
			String from =  result.get(index+1);
			distance += graph.getRoad(from, to).getDistance();
		}
		return distance;
	}
}
