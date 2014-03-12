package com.trevorharron.tsp.data;

public class Pair<T1,T2> {
	private T1 left;
	private T2 right;
	
	public Pair(T1 left, T2 right){
		this.left = left;
		this.right = right;
	}
	public T2 getRight(){
		return right;
	}
	public T1 getLeft(){
		return left;
	}
	public String toString(){
		return left+","+right;
	}
}
