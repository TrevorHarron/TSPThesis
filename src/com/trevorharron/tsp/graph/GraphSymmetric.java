package com.trevorharron.tsp.graph;

import java.util.ArrayList;
import java.util.HashMap;

import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.Node;

public class GraphSymmetric implements Graph{
	
	private ArrayList<Edge> roads;
	private HashMap<String, Node> cities;
	
	private Edge[][] roadMatrix;
	
	public GraphSymmetric(){
		cities =  new HashMap<String,Node>();
		roads = new ArrayList<Edge>();
	}
	
	public GraphSymmetric(Graph g){
		cities = g.getCities();
		roads =  g.getRoads();
		finalize();
	}
	
	@Override
	public void resetGraph(){
		for(Edge road: roads){
			road.setVisited(false);		
		}
		makeRoadMatrix();
	}
	
	@Override
	public void addCity(final Node city){
		cities.put(city.getId(),city);
	}
	
	@Override
	public void addEdge(final Edge road){	
		roads.add(road);
		if (roadMatrix != null) 
			roadMatrix[cities.get(road.getFrom()).getReadPos()][cities.get(road.getTo()).getReadPos()] = road;
	}
	
	private void setDiagonal(){
		for(Node city : cities.values())
			roadMatrix[city.getReadPos()][city.getReadPos()] = null;
	}
	
	public void setRoadVisited(final boolean visited, final Edge r){
		r.setVisited(visited);
		roadMatrix[cities.get(r.getTo()).getReadPos()]
				[cities.get(r.getFrom()).getReadPos()].setVisited(visited);
	}
	
	public void makeRoadMatrix(){
		int numCities = cities.size();
		roadMatrix = new Edge[numCities][numCities];
		for(Edge road : roads)
			roadMatrix[cities.get(road.getFrom()).getReadPos()][cities.get(road.getTo()).getReadPos()] = road;

		setDiagonal();
	}
	
	@Override
	public ArrayList<Edge> getRoadsByCity(String name){
		ArrayList<Edge> currentRoads = new ArrayList<Edge>();
		int cityPos = cities.get(name).getReadPos();
		for(Edge r: roadMatrix[cityPos]){
			if(r != null && !r.isVisited())
				currentRoads.add(r);
		}

		return currentRoads;	
	}
	
	public HashMap<String,Node>  getCities(){
		return cities;
	}
	
	public Edge[][] getRoadMatrix(){
		return roadMatrix;
	}
	
	public ArrayList<Edge> getRoads(){
		return roads;
	}
	
	public Node getCity(String key){
		return cities.get(key);
	}
	
	public Edge getRoad(int i, int j){
		return roadMatrix[i][j];
	}

	@Override
	public Edge getRoad(String to, String from) {
		return getRoad(cities.get(from).getReadPos(),
				cities.get(to).getReadPos());
	}
	
	@Override
	public void finalize(){
		makeRoadMatrix();
	}

}
