package com.trevorharron.tsp.graph.edge;

public class Edge implements Comparable<Edge>{
	
	protected String to;
	protected String from;
	
	protected double distance;
	protected boolean visited;
	
	public Edge(String to, String from, double distance){
		this.to = to;
		this.from = from;
		this.distance = distance;
		this.visited = false;
	}
	
	public Edge(Edge e){
		this.to = e.getTo();
		this.from = e.getFrom();
		this.distance = e.getDistance();
		this.visited = e.isVisited();
	}
	
	public String toString(){
		return from+","+to+":"+distance;
	}
	
	public void setVisited(final boolean visited){
		this.visited = visited;
	}
	public boolean isVisited(){
		return visited;
	}
	
	public String getTo(){
		return to;
	}
	public String getFrom(){
		return from;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public boolean equals(Edge o){
		return o!=null&&(to.equals(o.getTo()) && 
				from.equals(o.getFrom()) ||
				to.equals(o.getFrom()) && 
				from.equals(o.getTo()));
	}
	
	@Override
	public int compareTo(Edge o) {
		if(distance > o.getDistance())
			return 1;
		else if(distance == o.getDistance()){
			if(to.equals(o.getFrom()))
				return 1;
			else if(from.equals(o.getTo()))
				return -1;
			else
				return 0;
		} else
			return -1;
	}

}
