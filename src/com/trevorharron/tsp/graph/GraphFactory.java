package com.trevorharron.tsp.graph;

public class GraphFactory {

	public GraphFactory(){}
	
	public Graph getGraph(Class<? extends Graph> class1) throws Exception{
		
		if(class1.equals(new GraphSymmetric().getClass())){
			return new GraphSymmetric();
		} else if(class1.equals(new GraphBasic().getClass())) {
			return new GraphBasic();
		} else {
			throw new Exception("The graph type " +class1+" was not found.");
		}
	}

}
