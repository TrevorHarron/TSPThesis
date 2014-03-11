package com.trevorharron.tsp.reader;

import java.io.IOException;

import com.trevorharron.tsp.graph.Graph;

public interface DataReader {
	
	/**
	 * For a given reader it reads the file and 
	 * helps construct a graph
	 * @param file
	 */
	void readFile(String file, Graph graph , String state) throws IOException;

	
}
