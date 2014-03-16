package com.trevorharron.tsp.graph.node;

import java.util.ArrayList;

import com.trevorharron.tsp.data.Pair;
import com.trevorharron.tsp.graph.edge.Edge;

public class ListNode extends Node {

	private ArrayList<Pair<ListNode, Double>> roads;
	
	public ListNode(final String name,
			final double xCor, final double yCor, 
			final String id, final int readPos){
		
		super(name, xCor, yCor, id, readPos);
		//get all connections TODO
		roads= new ArrayList<Pair<ListNode, Double>>();	
	}
	
	public ListNode(Node city) {
		super(city);
		//get all connections TODO
		roads= new ArrayList<Pair<ListNode, Double>>();
	}

	@Override
	public void addRoad(ListNode n, double d){
		Pair<ListNode, Double> p =  new Pair<ListNode, Double>(n,d);
		roads.add(p);
	}
	
	@Override
	public ArrayList<Edge> getRoads(){
		ArrayList<Edge> rds = new ArrayList<Edge>();
		for(Pair<ListNode, Double> p: roads){
			rds.add(new Edge(this.name, ((Node) p.getLeft()).getName(), p.getRight() ));
		}
		return rds;
	}

	public void setVisited(final boolean visited){ 
		this.visited = visited;
	}
	public boolean isVisited(){ return visited; }
	
	public void setName(final String name){ this.name = name; }
	public String getName(){ return name; }
	@Override
	public String getId(){ return id; }
	@Override
	public void setId(final String id){ this.id = id; }

	@Override
	public int getReadPos(){ return readPos; }
	
	@Override
	public void setReadPos(int readPos){ this.readPos = readPos; }
	
	@Override
	public double getY(){ return getyCor(); }
	
	@Override
	public double getX(){ return getxCor(); }
	
	@Override
	public String toString(){
		return name+","+id +":"+getxCor()+","+getyCor();
	}
	
	@Override
	public boolean equals(Node o){
		return o.getId() == id;
	}
	@Override
	public double getxCor() {
		return xCor;
	}
	@Override
	public void setxCor(double xCor) {
		this.xCor = xCor;
	}
	@Override
	public double getyCor() {
		return yCor;
	}
	@Override
	public void setyCor(double yCor) {
		this.yCor = yCor;
	}
	
}

