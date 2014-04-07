package com.trevorharron.tsp.graph;

public class GraphFactory {

	public GraphFactory(){}
	
	public Graph getGraph(Class<? extends Graph> clazz) throws Exception{
		
		if(clazz.equals(new GraphSymmetric().getClass())){
			return new GraphSymmetric();
		} else if(clazz.equals(new GraphBasic().getClass())) {
			return new GraphBasic();
		} else {
			throw new Exception("The graph type " +clazz+" was not found.");
		}
	}

}
