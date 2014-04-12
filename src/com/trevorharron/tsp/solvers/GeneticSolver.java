package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.trevorharron.tsp.graph.Graph;

public class GeneticSolver extends BasicSolver {

	ArrayList<String> cities;
	double percentMutation;
	Random rand;
	
	public GeneticSolver(){
		cities = new ArrayList<String>();
		graph = null;
		rand =  new Random(System.currentTimeMillis());
	}
	
	public GeneticSolver(Graph graph){
		cities = new ArrayList<String>();
		setGraph(graph);
		rand =  new Random(System.currentTimeMillis());
	}

	@Override
	public ArrayList<String> solve() throws NoSolutionException {
		
		ArrayList<ArrayList<String>> population =  makePopulation();
		for(int k = 0; k < 100; k++){
			int[] parents = darwinsTheory(population);
			population = breed(population.get(parents[0]), population.get(parents[1]));
		}
		return new ArrayList<String>();
	}

	@Override
	public void setGraph(Graph graph) {
		super.setGraph(graph);
		for(String name : graph.getCities().keySet())
			cities.add(name);
	}
	
	private ArrayList<ArrayList<String>> makePopulation(){
		ArrayList<ArrayList<String>> pop = new ArrayList<ArrayList<String>>();
		//make a population of size 20
		for(int time = 0; time < 20; time++){
			Collections.shuffle(cities, rand);
			ArrayList<String> indiv = new ArrayList<String>();
			for(String name: cities)
				indiv.add(name);
			pop.add(indiv);
		}
		return pop;
	}
	
	private int[] darwinsTheory( ArrayList<ArrayList<String>> population){
		int[] bestParents = new int[2];
		double bestScoreA = 900000000.0;
		double bestScoreB = 900000000.0;
		
		for( ArrayList<String> indiv: population){
			double dist =  getRouteDistance(indiv);
			if(dist < bestScoreA){
				bestScoreA = dist;
				bestParents[0] = population.indexOf(indiv);
			} else if(dist < bestScoreB){
				bestScoreB = dist;
				bestParents[1] = population.indexOf(indiv);
			}		
		}
		return bestParents;
	}

	private ArrayList<ArrayList<String>> breed(ArrayList<String> father, ArrayList<String> mother){
		//TODO
		return new ArrayList<ArrayList<String>>();
	}
	
	private ArrayList<ArrayList<String>> mutate(ArrayList<ArrayList<String>> pop){
		return null;
		
	}
}
