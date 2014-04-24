package com.trevorharron.tsp.graph.node;

import java.util.ArrayList;

import com.trevorharron.tsp.graph.edge.Edge;

public class Node {
	
	protected String name;
	
	protected double xCor;
	protected double yCor;
	protected String id;
	protected int readPos;
	protected boolean visited;
	
	public Node(final String name, final double xCor, final double yCor, 
			final String id, final int readPos){
		this.setName(name);
		this.setxCor(xCor);
		this.setyCor(yCor);
		this.setId(id);
		this.setReadPos(readPos);
		
	}
	
	public Node(Node city){

			name = city.getName();
			xCor = city.getxCor();
			yCor = city.getyCor();
			id = city.getId();
			readPos = city.getReadPos();
	}
	
	public void setVisited(final boolean visited){ 
		this.visited = visited;
	}
    public boolean isVisited(){ return visited; }
	
	public void setName(final String name){ this.name = name; }
	public String getName(){ return name; }
	
	public String getId(){ return id; }
	
	public void setId(final String id){ this.id = id; }

	
	public int getReadPos(){ return readPos; }
	
	
	public void setReadPos(int readPos){ this.readPos = readPos; }
	
	
	public double getY(){ return getyCor(); }
	
	
	public double getX(){ return getxCor(); }
	
	
	public String toString(){
		return name+","+id +","+getReadPos()+":"+getxCor()+","+getyCor();
	}
	
	
	public boolean equals(Node o){
		return o.getId() == id;
	}
	
	public double getxCor() {
		return xCor;
	}

	public void setxCor(double xCor) {
		this.xCor = xCor;
	}
	
	public double getyCor() {
		return yCor;
	}
	
	public void setyCor(double yCor) {
		this.yCor = yCor;
	}
	
	public void addRoad(ListNode n, double d){}
	
	public ArrayList<Edge> getRoads(){return null;}
}
