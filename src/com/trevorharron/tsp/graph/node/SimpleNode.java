package com.trevorharron.tsp.graph.node;

import java.util.ArrayList;

import com.trevorharron.tsp.graph.edge.Edge;

public class SimpleNode extends Node {
	private ArrayList<Pair> roads;
	
	public SimpleNode(final String name,
			final double xCor, final double yCor, 
			final String id, final int readPos){
		super(name,xCor,yCor,id,readPos);
		roads= new ArrayList<Pair>();
		
	}
	
	public SimpleNode(Node city) {
		super(city.getName(),city.getxCor(),
				city.getyCor(),city.getId(),city.getReadPos());
		//get all connections TODO
		roads= new ArrayList<Pair>();
	}

	public void addRoad(SimpleNode n, double d){
		Pair p =  new Pair(n,d);
		roads.add(p);
	}
	
	public ArrayList<Edge> getRoads(){
		ArrayList<Edge> rds = new ArrayList<Edge>();
		for(Pair p: roads){
			rds.add(new Edge(this.name, ((Node) p.getTo()).getName(), p.getDistance() ));
		}
		return rds;
	}
	
	private class Pair{
		private SimpleNode to;
		private double distance;
		
		public Pair(SimpleNode n, double d){
			to = n;
			distance = d;
		}
		
		public SimpleNode getTo(){ return to; }
		public double getDistance(){ return distance; }
	}
	
}

