package com.trevorharron.tsp.solvers;

import java.util.ArrayList;

import com.trevorharron.tsp.graph.Graph;

public class GeneticSolver implements Solver {
	
	Graph graph;
	double percentMutation;
	
	public GeneticSolver(){
		graph = null;
	}
	
	public GeneticSolver(Graph graph){
		setGraph(graph);
	}

	@Override
	public ArrayList<String> solve() throws NoSolutionException {
		
		ArrayList<ArrayList<String>> population =  makePopulation();
		
		return new ArrayList<String>();
	}

	@Override
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	private ArrayList<ArrayList<String>> makePopulation(){
		//TODO
		return new ArrayList<ArrayList<String>>();
	}
	
	private int[] darwinsTheory(ArrayList<String> population){
		//TODO
		return new int[2];
	}
	
	private ArrayList<String> breed(ArrayList<String> father, ArrayList<String> mother){
		//TODO
		return new ArrayList<String>();
	}
	
	


}
