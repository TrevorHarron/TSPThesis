package com.trevorharron.tsp.solvers;

import java.util.ArrayList;

import com.trevorharron.tsp.graph.Graph;

public class MSTSolver implements Solver {

	Graph graph;
	
	public MSTSolver(){
		
	}
	public MSTSolver(Graph graph){
		this.graph = graph;
	}
	
	@Override
	public ArrayList<String> solve() throws NoSolutionException{
		// TODO Auto-generated method stub
		return null;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

}
