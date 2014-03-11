package com.trevorharron.tsp.gui;

import com.trevorharron.tsp.data.FileNames;
import com.trevorharron.tsp.graph.GraphSymmetric;
import com.trevorharron.tsp.reader.CSVReader;
import com.trevorharron.tsp.reader.DataReader;
import com.trevorharron.tsp.reader.KMLReader;
import com.trevorharron.tsp.solvers.NNSolver;
import com.trevorharron.tsp.solvers.Solver;

public class ReaderTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataReader kml = new KMLReader();
		DataReader csv = new CSVReader();
		GraphSymmetric graph = new GraphSymmetric();
		try{
			kml.readFile(FileNames.testKML,graph,"A");
			csv.readFile(FileNames.testCSV,graph,"A");
			graph.makeRoadMatrix();
			Solver nn = new NNSolver(graph);
			System.out.println(nn.solve());
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}
