package com.trevorharron.tsp.solvers;

import java.util.ArrayList;

import com.trevorharron.tsp.graph.Graph;

public interface Solver {
	
	public ArrayList<String> solve();
	
	public void setGraph(final Graph graph);

}
