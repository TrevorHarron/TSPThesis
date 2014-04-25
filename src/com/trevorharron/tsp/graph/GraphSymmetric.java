package com.trevorharron.tsp.graph;

import java.util.ArrayList;
import java.util.HashMap;

import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.Node;

public class GraphSymmetric implements Graph{
	
	private ArrayList<Edge> roads;
	private HashMap<String, Node> cities;
	
	private Edge[][] roadMatrix;
	private int originalSize;
	
	public GraphSymmetric(){
		cities =  new HashMap<String,Node>();
		roads = new ArrayList<Edge>();
		originalSize = -1;
	}
	
	public GraphSymmetric(Graph g){
		cities = g.getCities();
		roads =  g.getRoads();
		originalSize = g.getOriginalSize();
		finalize();
	}
	
	@Override
	public void resetGraph(){
		for(Edge road: roads)
			road.setVisited(false);		
		makeRoadMatrix();
	}
	
	@Override
	public void addCity(final Node city){
		cities.put(city.getId(),city);
	}
	
	@Override
	public void addEdge(final Edge road){	
		Edge e =  new Edge(road);
		Node from = cities.get(road.getFrom());
		if(from == null){
			e = fixRoad(road, true);
			from = cities.get(e.getFrom());
		}
		Node to = cities.get(road.getTo());
		if(to == null){
			e = fixRoad(road, false);
			to = cities.get(e.getTo());
		}
		roads.add(e);
		if (roadMatrix != null) 
			roadMatrix[from.getReadPos()][to.getReadPos()] = e;
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
		roadMatrix = new Edge[originalSize][originalSize];
		for(Edge road : roads){
			
			Node from =cities.get(road.getFrom());
			Edge e = road;
			if(from == null){
				e = fixRoad(e,true);
				from = cities.get(e.getFrom());
			}
			Node to = cities.get(road.getTo());
			if(to == null){
				e = fixRoad(e,false);
				to = cities.get(e.getTo());
			}
			roadMatrix[from.getReadPos()][to.getReadPos()] = e;
		}
		setDiagonal();
	}
	
	private Edge fixRoad(Edge road, boolean isFrom) {
		String[] parts = new String[2];
		int idNum = -1;
		Edge e =  new Edge(road);
		if(isFrom)
			parts = road.getFrom().split("-");
		else
			parts = road.getTo().split("-");
		idNum = Integer.parseInt(parts[1]);
		while(idNum < originalSize){
			if(cities.containsKey(parts[0]+"-"+idNum)){
				if(isFrom)
					e.setFrom(parts[0]+"-"+idNum);
				else
					e.setTo(parts[0]+"-"+idNum);
				break;
			}
			idNum++;
		}
		return e;
	}

	@Override
	public ArrayList<Edge> getRoadsFromCity(String name){
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
	public Edge getRoad(String from, String to) {
		return getRoad(cities.get(from).getReadPos(),
				cities.get(to).getReadPos());
	}
	
	@Override
	public void finalize(){
		if(originalSize == -1){
			originalSize = getCities().keySet().size();
		}
		makeRoadMatrix();
		HashMap<String, Node> newCities = new HashMap<String, Node>();
		for(String city: getCities().keySet()){
			boolean add = true;
			ArrayList<Edge> cityRoads = getRoadsFromCity(city);
			for(Edge e: cityRoads){
				if(e != null){
					add = true;
					break;
				}
			}
			if((add && !cityRoads.isEmpty()) || getRoads().isEmpty())
				newCities.put(city, cities.get(city));
		}
		cities =  newCities;
	}

	@Override
	public ArrayList<Edge> getRoadsToCity(String name) {
		ArrayList<Edge> currentRoads = new ArrayList<Edge>();
		int cityPos = cities.get(name).getReadPos();
		for(int i = 0; i < roadMatrix[cityPos].length; i++){
			Edge r =  roadMatrix[i][cityPos];
			if(r != null && !r.isVisited())
				currentRoads.add(r);
		}
		return currentRoads;
	}

	@Override
	public int getOriginalSize() { return originalSize; }

	@Override
	public void getCitiesAndSize(Graph graph) {
		cities = graph.getCities();
		originalSize = graph.getOriginalSize();
		finalize();
	}

}
