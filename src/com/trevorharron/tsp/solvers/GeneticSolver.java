package com.trevorharron.tsp.solvers;

import java.util.ArrayList;

import com.trevorharron.tsp.graph.Graph;

public class GeneticSolver implements Solver {
	
	Graph graph;
	double percentMutation;
	
	public GeneticSolver(){
		
	}
	
	public GeneticSolver(Graph graph){
		setGraph(graph);
	}

	@Override
	public ArrayList<String> solve() throws NoSolutionException {
		// TODO 
		return new ArrayList<String>();
	}

	@Override
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	


}
