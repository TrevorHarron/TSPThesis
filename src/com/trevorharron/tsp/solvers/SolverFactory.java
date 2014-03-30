package com.trevorharron.tsp.solvers;

public class SolverFactory {
	public static int NN = 0;
	public static int GREEDY = 1;
	public static int MST = 2;
	
	private int solver;

	public SolverFactory(){
		this.solver = 0;
	}
	
	public SolverFactory(final int solverChoice){
		setChoice(solverChoice);
	}
	
	public void setChoice(final int solverChoice){
		this.solver = solverChoice;
	}
	
	public Solver getSolver(){
		if(solver == NN)
			return new NNSolver();
		else if(solver == GREEDY)
			return new GreedySolver();
		else if(solver == MST)
			return new MSTSolver();
		else
			return null;
	}

}
