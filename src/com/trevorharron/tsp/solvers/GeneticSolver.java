package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import com.trevorharron.tsp.graph.Graph;

public class GeneticSolver extends BasicSolver {

	ArrayList<String> cities;
	double percentMutation;
	Random rng;
	int numMutations;
	
	public GeneticSolver(){
		cities = new ArrayList<String>();
		graph = null;
		rng =  new Random(System.currentTimeMillis());
		percentMutation = .25;
		numMutations =  rng.nextInt(20)+1;
	}
	
	public GeneticSolver(Graph graph){
		cities = new ArrayList<String>();
		setGraph(graph);
		rng =  new Random(System.currentTimeMillis());
		percentMutation = .25;
		numMutations =  rng.nextInt(20)+1;
	}

	@Override
	public ArrayList<String> solve() {
		long startTime = System.nanoTime();
		ArrayList<ArrayList<String>> population =  makePopulation();
		for(int k = 0; k < 500; k++){
			ArrayList<ArrayList<String>> parents = darwinsTheory(population);
			population = breed(parents, population.size());
			population =  mutate(population);
		}
		ArrayList<String> result = findBest(population);
		double distance = getRouteDistance(result);
		result.add(""+((System.nanoTime()-startTime)*1.0e-9));
	    System.gc();
	    double usedKB = (Runtime.getRuntime().totalMemory() - 
	    		Runtime.getRuntime().freeMemory()) / 1024.0;
	    result.add(""+(usedKB));
		result.add(""+distance);
		return result;
	}

	private ArrayList<ArrayList<String>> makePopulation(){
		ArrayList<ArrayList<String>> pop = new ArrayList<ArrayList<String>>();
		while(pop.size()< 20){
			Collections.shuffle(cities, rng);
			ArrayList<String> indiv = new ArrayList<String>();
			for(String name: cities)
				indiv.add(name);
			indiv.add(indiv.get(0));
			if(!pop.contains(indiv))
				pop.add(indiv);
		}
		return pop;
	}
	
	private ArrayList<ArrayList<String>> darwinsTheory( ArrayList<ArrayList<String>> population){
		
		ArrayList<ArrayList<String>> pool = new ArrayList<ArrayList<String>>();
		for( ArrayList<String> indiv: population)
			pool.add(indiv);
		int numCanidates = 10;
		HashMap<Integer, Double> scores = new HashMap<Integer, Double>();
		for(int index = 0; index < pool.size();index++){
			ArrayList<String> indiv = pool.get(index);
			if(scores.keySet().size() < numCanidates)
				scores.put(index, getRouteDistance(indiv));
			else {
				double dist =  getRouteDistance(indiv);
				for(Integer key: scores.keySet()){
					if(dist < scores.get(key)){
						scores.put(index,dist);
						scores.remove(key);
						break;
					}
				}
			}
		}
		ArrayList<ArrayList<String>> bestParents = new ArrayList<ArrayList<String>>();
		for(Integer key:scores.keySet())
			bestParents.add(population.get(key));
		return bestParents;
	}

	private ArrayList<ArrayList<String>> breed(ArrayList<ArrayList<String>> parents, int size){
		for(ArrayList<String>parent:parents)
			parent.remove(parent.size()-1);
		ArrayList<ArrayList<String>> pop = new ArrayList<ArrayList<String>>();
		for(ArrayList<String>father:parents){
			for(ArrayList<String>mother:parents){
				ArrayList<String> indiv = new ArrayList<String>();
				for(int index = 0; index < father.size()/2 -1; index++)
					indiv.add(father.get(index));
				for(String name: mother)
					if(!indiv.contains(name))
						indiv.add(name);
				pop.add(indiv);
			}
			pop.add(father);
		}
		return pop;
	}
	
	private ArrayList<ArrayList<String>> mutate(ArrayList<ArrayList<String>> pop){
		for(ArrayList<String> indiv: pop){
			for(int count = 0; count < numMutations; count++){
				if(rng.nextDouble() < percentMutation){
					int index = rng.nextInt(indiv.size());
					String tmp = indiv.get(index);
					int otherIndex = rng.nextInt(indiv.size());
					indiv.set(index, indiv.get(otherIndex));
					indiv.set(otherIndex, tmp);	
				}
			}
			indiv.add(indiv.get(0));
		}
		return pop;
	}
	
	private ArrayList<String> findBest(ArrayList<ArrayList<String>> population) {
		double bestDist = 9000000000.0;
		ArrayList<String> bestRoute = null;
		for(ArrayList<String> indiv:population){
			double distance =  getRouteDistance(indiv);
			if(distance < bestDist)
				bestRoute = indiv;
		}
		return bestRoute;
	}
	
	@Override
	public void setGraph(Graph graph) {
		super.setGraph(graph);
		for(String name : graph.getCities().keySet())
			cities.add(name);
	}
}
