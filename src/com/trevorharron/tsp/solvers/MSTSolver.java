package com.trevorharron.tsp.solvers;

import java.util.ArrayList;
import java.util.Stack;

import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.node.Node;

public class MSTSolver implements Solver {

	Graph graph;
	
	public MSTSolver(){}
	
	public MSTSolver(Graph graph){
		this.graph = graph;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	@Override
	public ArrayList<String> solve() throws NoSolutionException{
		long startTime = System.nanoTime();
		ArrayList<String> result  = new ArrayList<String>();
		try{
			Tree mst = makeMST("");
			result = mst.treeWalk();
		} catch (Exception e){
			throw new NoSolutionException(e.getMessage());
		}
		double distance = getResult(result);
		result.add(""+((System.nanoTime()-startTime)*1.0e-9));
		System.gc();
		double usedMB = (Runtime.getRuntime().totalMemory() - 
	    		Runtime.getRuntime().freeMemory()) / 1024.0;
		result.add(""+usedMB);
		result.add(""+distance);
		return result;
	}
	
	private double getResult(ArrayList<String> result) {
		// TODO get the distances from routes
		
		//TODO get distance back to start
		return 0.0;
	}

	private Tree makeMST(String start){
		return new Tree(new TreeNode(graph.getCity(start)));
	}
	
	private class Tree{
		
		TreeNode root;

		Tree(TreeNode root){
			this.root = root;
		}
		
		public ArrayList<String> treeWalk(){
			
			ArrayList<String> route = new ArrayList<String>();
			TreeNode current = root;
			Stack<TreeNode> stack = new Stack<TreeNode>();
			stack.push(root);
			boolean keepWorking = true;
			while(!stack.isEmpty()){
				while(current.getLeft() != null){
					stack.push(current.getLeft());
					current = current.getLeft();
				}
				while(keepWorking){
					TreeNode node =  stack.pop();
					route.add(node.getSelf().getName());
					if(current.getRight() != null){
						stack.push(current.getRight());
						current = current.getRight();
						keepWorking = false;
					}
				}
				keepWorking = true;
			}
			return route;
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
