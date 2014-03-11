package com.trevorharron.tsp.graph.edge;

public class Edge implements Comparable<Edge>{
	
	protected String toCity;
	protected String fromCity;
	
	protected double distance;
	protected boolean visited;
	
	public Edge(String to, String from, double distance){
		this.toCity = to;
		this.fromCity = from;
		this.distance = distance;
	}
	
	public String toString(){
		return fromCity+","+toCity+":"+distance;
	}
	
	public void setVisited(final boolean visited){
		this.visited = visited;
	}
	public boolean isVisited(){
		return visited;
	}
	
	public String getTo(){
		return toCity;
	}
	public String getFrom(){
		return fromCity;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public boolean equals(Edge o){
		return o!=null&&(toCity.equals(o.getTo()) && 
				fromCity.equals(o.getFrom()) ||
				toCity.equals(o.getFrom()) && 
				fromCity.equals(o.getTo()));
	}
	
	@Override
	public int compareTo(Edge o) {
		if(distance > o.getDistance())
			return 1;
		else if(distance == o.getDistance()){
			if(toCity.equals(o.getFrom()))
				return 1;
			else if(fromCity.equals(o.getTo()))
				return -1;
			else
				return 0;
		} else
			return -1;
	}

}
