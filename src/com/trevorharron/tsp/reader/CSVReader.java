package com.trevorharron.tsp.reader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.edge.Edge;

public class CSVReader implements DataReader{

	public void readFile(final String file, Graph graph, String state) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(file));
		String csv = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
		String[] rows = csv.split("\n");
		//Assuming pattern #,city1 - city2,...,distance
		String row;
		int originId = -1;
		int destId = -1;
		for(int index = 1; index <rows.length; index++){
			row = rows[index];
			String[] rowContents = new String(row).split("\"");
			//System.out.println(rowContents);
			int idANum = 2;
			int idBNum = 3;
			if(rowContents.length > 1){
				idANum = 4;
				idBNum = 5;
			}
			String[] contents = row.split(",");
			if(index == 1){
				originId = Integer.parseInt(contents[idANum]);
				destId = Integer.parseInt(contents[idBNum]);
			}//TODO
			String ci = (contents[1].split(" - ")[0])+"-"+(Integer.parseInt(contents[idANum])-originId);
			String cj = (contents[1].split(" - ")[1])+"-"+(Integer.parseInt(contents[idBNum])-destId);
			
			double distance = Double.parseDouble((contents[contents.length-1]));
			if(!ci.equals(cj)){
				Edge r = new Edge(ci, cj, distance);
				graph.addEdge(r);
				r = new Edge(cj, ci, distance);
				graph.addEdge(r);
			}
		}
		
	}

}
