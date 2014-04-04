package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Stack;

public class Tree{
	
	TreeNode root;

	Tree(TreeNode root){
		this.root = root;
	}
	
	void setRoot(TreeNode root){
		this.root =  root;
	}
	
	public ArrayList<String> treeWalk(){
		
		ArrayList<String> route = new ArrayList<String>();
		TreeNode current = root;
		Stack<TreeNode> stack = new Stack<TreeNode>();
		while(!stack.isEmpty()||current !=null){
			if(current != null){
				stack.push(current);
				current = current.getLeft();
			}else{
				route.add(stack.peek().getSelf().getName());
				current = stack.pop().getRight();
			}
		}
		return route;
	}

}