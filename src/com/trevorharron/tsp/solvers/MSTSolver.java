package com.trevorharron.tsp.solvers;

import java.util.ArrayList;

import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.node.Node;

public class MSTSolver implements Solver {

	Graph graph;
	
	public MSTSolver(){
		
	}
	
	public MSTSolver(Graph graph){
		this.graph = graph;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	@Override
	public ArrayList<String> solve() throws NoSolutionException{
		
		
		ArrayList<String> result = new ArrayList<String>();
		return result;
	}
	
	private class Tree{
		
		TreeNode root;

		Tree(TreeNode root){
			this.root = root;
		}
		
		ArrayList<String> treeWalk(){
			TreeNode current = root;
			return new ArrayList<String>();
		}

	}
	
	private class TreeNode{
		
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



}
