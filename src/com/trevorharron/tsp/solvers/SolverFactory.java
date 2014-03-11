package com.trevorharron.tsp.solvers;

public class SolverFactory {
	private static int nn = 0;
	private static int greedy = 1;
	private static int mst = 2;
	
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
		if(solver == nn)
			return new NNSolver();
		else if(solver == greedy)
			return new GreedySolver();
		else if(solver == mst)
			return new MSTSolver();
		else
			return null;
	}

}
