package com.trevorharron.tsp.gui;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.trevorharron.tsp.reader.CSVReader;
import com.trevorharron.tsp.reader.DataReader;
import com.trevorharron.tsp.reader.KMLReader;
import com.trevorharron.tsp.solvers.Solver;
import com.trevorharron.tsp.solvers.SolverFactory;
import com.trevorharron.tsp.data.FileNames;
import com.trevorharron.tsp.graph.GraphBasic;
import com.trevorharron.tsp.graph.GraphSymmetric;

public class Experimenter {
	
	public static void main(String[] args) {
		DataReader kml = new KMLReader();
		DataReader csv = new CSVReader();
		GraphSymmetric sGraph = new GraphSymmetric();
		GraphBasic bGraph = new GraphBasic();
		SolverFactory factory = new SolverFactory();
		
		try {
			kml.readFile(FileNames.citiesKML,sGraph,"RI");
			kml.readFile(FileNames.citiesKML,bGraph,"RI");
			csv.readFile(FileNames.RICSV,sGraph,"RI");
			FileWriter fileWriter = new FileWriter(FileNames.RIRESULT);
			@SuppressWarnings("resource")
			PrintWriter printW = new PrintWriter(fileWriter);
			
			sGraph.makeRoadMatrix();
			for(int solverNum = 0; solverNum<2;solverNum++){
				factory.setChoice(solverNum);
				for(int numTimes = 0; numTimes < 1; numTimes++){
					sGraph.resetGraph();
					Solver solver = factory.getSolver(); //NN test
					solver.setGraph(sGraph);
					
					ArrayList<String> result = solver.solve();
					
					for(String item : result){
						printW.print(item);
						if(result.indexOf(item)!=result.size()-1)
							printW.print(",");
					}
					printW.print("\n");
					printW.flush();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
	}
	
}
