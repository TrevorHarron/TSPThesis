package com.trevorharron.tsp.solvers;

import com.trevorharron.tsp.graph.node.Node;

public class TreeNode {
	
	private Node self;
	private TreeNode left;
	private TreeNode right;
	
	public TreeNode(Node self){
		this.self = self;
		this.left = null;
		this.right = null;
	}
	
	public void setLeft(TreeNode left){this.left = left;}
	
	public void setRight(TreeNode right){this.right =  right;}
	
	public TreeNode getLeft(){	return left;}
	
	public TreeNode getRight(){ return right;}
	
	public Node getSelf(){ return self;}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof TreeNode)) return false;
		return this.self.equals(((TreeNode)o).getSelf());		
	}
	
	@Override
	public String toString(){
		String rep = "Self: "+self.getName()+" Left: ";
		if(left!=null)
			rep += ""+left.getSelf().getName();
		rep+=" Right: ";
		if(right!=null)
			rep += ""+right.getSelf().getName();
		return rep;
	}
	
}
