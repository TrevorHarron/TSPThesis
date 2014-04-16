package com.trevorharron.tsp.aux;

import java.util.ArrayList;
import java.util.Stack;

public class Tree{
	
	private TreeNode root;

	public Tree(TreeNode root){
		this.root = root;
	}
	
	public void setRoot(TreeNode root){
		this.root =  root;
	}
	
	public TreeNode getRoot(){
		return this.root;
	}
	
	public ArrayList<String> treeWalk(){
		
		ArrayList<String> route = new ArrayList<String>();
		TreeNode current = root;
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(current);
		while(!stack.isEmpty()){
			current = stack.peek();
			route.add(current.getSelf().getName());
			stack.pop();
			if(current.getRight() != null)
				stack.push(current.getRight());
				
			if(current.getLeft() != null)
				stack.push(current.getLeft());
		}
		return route;
	}

}