package com.trevorharron.tsp.gui;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.trevorharron.tsp.reader.CSVReader;
import com.trevorharron.tsp.reader.DataReader;
import com.trevorharron.tsp.reader.KMLReader;
import com.trevorharron.tsp.solvers.Solver;
import com.trevorharron.tsp.solvers.SolverFactory;

import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.GraphSymmetric;

import static com.trevorharron.tsp.data.FileNames.RESULTS;
import static com.trevorharron.tsp.data.FileNames.ROADS;
import static com.trevorharron.tsp.data.FileNames.STATES;
import static com.trevorharron.tsp.data.FileNames.CITIES;

public class Experimenter {
	
	
	
	public static void main(String[] args) {
		DataReader kml = new KMLReader();
		DataReader csv = new CSVReader();
		Graph graph;
		SolverFactory factory = new SolverFactory();
		FileWriter fileWriter;
		PrintWriter printW;
		int maxTimes = 100;
		try {
		
			for(int solverNum = 0; solverNum < 4;solverNum ++){
				fileWriter = new FileWriter(RESULTS.get(solverNum));
				printW = new PrintWriter(fileWriter);
				factory.setChoice(solverNum);
					
				printW.print("STATE,TIME,MEMORY,DISTANCE\n");
				printW.flush();
				for(int fileNum = 0; fileNum<6;fileNum++){
					if(fileNum != 4){
					graph = new GraphSymmetric();
					System.out.println(STATES.get(fileNum));
					kml.readFile(CITIES,graph,STATES.get(fileNum));
					csv.readFile(ROADS.get(fileNum),graph,"");
					graph.finalize();
					
					@SuppressWarnings("serial")
					HashMap<Integer,Double> avgs 
						= new HashMap<Integer,Double>(){{
						put(0,0.0);
						put(1,0.0);
						put(2,0.0);
					}};
					try{
					for(int numTimes = 0; numTimes < maxTimes; numTimes++){
						graph.resetGraph();
						Solver solver = factory.getSolver();
						solver.setGraph(graph);
						
						ArrayList<String> result = solver.solve();
							
						printW.print(STATES.get(fileNum)+",");
						int size = result.size();
						for(int index = result.size()-3; index < result.size(); index++){
							int currentNum = size-1-index;
							printW.print(result.get(index));
							avgs.put(currentNum, avgs.get(currentNum)+
									Double.parseDouble(result.get(index)));
							if(index!=size-1) printW.print(",");
						}
						printW.print("\n");
						printW.flush();
					}
					printW.print("AVG-"+STATES.get(fileNum)+",");
					for(int key = 2; key >=0; key--){
						printW.print(avgs.get(key)/(double)maxTimes);
						if(key!=3) printW.print(",");
					}
					printW.print("\n");
					printW.flush();
					} catch( Exception e){
						System.out.println("Solver number: "+solverNum);
						e.printStackTrace();
						printW.print(STATES.get(fileNum)+",NO solution\n");
					}
				}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
	}

}
