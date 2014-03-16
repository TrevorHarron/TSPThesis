package com.trevorharron.tsp.graph.node;

public class MatrixNode extends Node{
	
	
	public MatrixNode(String name, double xCor, double yCor, String id,
			int readPos) {
		super(name, xCor, yCor, id, readPos);
	}
	
	public MatrixNode(Node city){
		super(city);
	}

}
