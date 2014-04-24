package com.trevorharron.tsp.reader;

import java.io.File;
import java.util.List;

import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.node.Node;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

public class KMLReader implements DataReader{
	//Generalize later

	@Override
	public void readFile(final String file, Graph graph, final String state) {
		Kml kml = Kml.unmarshal(new File(file));
		Document doc = (Document) kml.getFeature();
		Folder folder = (Folder) doc.getFeature().get(0);
		List<Feature> features = folder.getFeature();
		int idCount = 0;
		for(Feature f : features){
			Placemark p = (Placemark) f;
			String s = p.getExtendedData().getSchemaData().get(0).getSimpleData().get(8).getValue();
			if(s.equals(state)){
				Point point = (Point) p.getGeometry();
				String name = p.getName();
				String id = name+"-"+idCount;
				Node city = new Node(name, 
						point.getCoordinates().get(0).getLongitude(), 
						point.getCoordinates().get(0).getLatitude(),
						id, idCount);
				graph.addCity(city);
				idCount++;
			}
		}
	}
}
