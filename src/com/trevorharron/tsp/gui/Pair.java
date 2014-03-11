package com.trevorharron.tsp.gui;

public class Pair<T1,T2> {
	private double x;
	private double y;
	
	public Pair(double x, double y){
		this.x = x;
		this.y = y;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public String toString(){
		return x+","+y;
	}
}
