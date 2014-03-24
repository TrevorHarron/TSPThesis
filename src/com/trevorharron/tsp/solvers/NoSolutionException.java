package com.trevorharron.tsp.solvers;

@SuppressWarnings("serial")
public class NoSolutionException extends Exception {

	/**
	 * @param args
	 */
	public NoSolutionException(){
		super();
	}
	
	public NoSolutionException(String reason){
		super(reason);
	}

}
