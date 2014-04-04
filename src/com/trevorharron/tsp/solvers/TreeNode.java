package com.trevorharron.tsp.solvers;

import com.trevorharron.tsp.graph.node.Node;

public class TreeNode {
	
	Node self;
	TreeNode left;
	TreeNode right;
	
	TreeNode(Node self){
		this.self = self;
		this.left = null;
		this.right = null;
	}
	
	void setLeft(Node left){this.left =  new TreeNode(left);}
	
	void setRight(Node right){this.right =  new TreeNode(right);}
	
	TreeNode getLeft(){	return left;}
	
	TreeNode getRight(){ return right;}
	
	Node getSelf(){ return self;}
	
}
