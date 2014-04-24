package com.trevorharron.tsp.aux;

import java.util.ArrayList;
import java.util.Stack;

public class Tree{
	
	private TreeNode root;

	public Tree(TreeNode root){ this.root = root; }
	
	public void setRoot(TreeNode root){ this.root =  root; }
	
	public TreeNode getRoot(){ return this.root; }
	
	public ArrayList<String> treeWalk(){
		
		ArrayList<String> route = new ArrayList<String>();
		TreeNode current = root;
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(current);
		while(!stack.isEmpty()){
			current = stack.peek();
			route.add(current.getSelf().getId());
			stack.pop();
			
			if(current.getRight() != null) stack.push(current.getRight());	
			if(current.getLeft() != null) stack.push(current.getLeft());
		}
		return route;
	}


	public ArrayList<String> getOddNodes() {
		
		ArrayList<String> oddNodes = new ArrayList<String>();
		
		TreeNode current = root;
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(current);
		while(!stack.isEmpty()){
			current = stack.peek();
			if(isOddDegree(current, root) 
					&& !oddNodes.contains(current.getSelf().getId()))
				oddNodes.add(current.getSelf().getId());
			stack.pop();
			
			if(current.getRight() != null) stack.push(current.getRight());	
			if(current.getLeft() != null) stack.push(current.getLeft());
		}
		return oddNodes;
	}

	private boolean isOddDegree(TreeNode current, TreeNode root) {
		
		int count = 0;
		if(current.getLeft() != null) count += 1;
		if(current.getRight() !=null) count += 1;
		
		if(!current.equals(root))
			return count % 2 == 0;
		else 
			return !(count % 2 == 0);
	}

}