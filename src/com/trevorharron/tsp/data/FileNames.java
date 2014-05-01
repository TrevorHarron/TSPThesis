package com.trevorharron.tsp.data;

import java.io.File;
import java.util.HashMap;

public class FileNames {
	private final String postfix = "/src/com/trevorharron/tsp/data";
	private String path;
	
	public final String TEST_KML;
	public final String TEST_CSV;
	public final String CITIES;
	public final String RICSV;
	public final String DECSV;
	public final String VTCSV;
	public final String MACSV;
	public final String UTCSV;
	public final String WYCSV;
	public final String TNCSV;
	
	public final String NN_RESULT;
	public final String GRDY_RESULT;
	public final String MST_RESULT;
	public final String GEN_RESULT;
	public final String CHRS_RESULT;
	
	public final String RESULTS_CSV;
	
	public HashMap<Integer, String> ROADS;
	public static HashMap<Integer, String> RESULTS;
	
	public FileNames(){
		path = new File("").getAbsolutePath();
		
		int indexOfLast = path.lastIndexOf("/");
		if(indexOfLast >= 0) path = path.substring(0, indexOfLast);
		path = path + postfix;
		
		TEST_KML = path+"/simple.kml";
		TEST_CSV = path+"/simple.csv";
		
		CITIES = path+"/cities.kml";
		
		RICSV = path+"/ri.csv";
		DECSV = path+"/de.csv";
		VTCSV = path+"/vt.csv";
		MACSV = path+"/ma.csv";
		UTCSV = path+"/ut.csv";
		WYCSV = path+"/wy.csv";
		TNCSV = path+"/tn.csv";
		
		
		this.ROADS = new HashMap<Integer, String>(){
			{
				put(0, RICSV);
				put(1, DECSV);
				put(2, VTCSV);
				put(3, WYCSV);
				put(4, MACSV);
				put(5, UTCSV);
				put(6, TNCSV);
			}
		};

		NN_RESULT = path+"/nn-results.csv";
		GRDY_RESULT = path+"/grdy-results.csv";
		MST_RESULT = path+"/mst-results.csv";
		GEN_RESULT = path+"/gen-results.csv";
		CHRS_RESULT = path+"/chrs-results.csv";
		
		RESULTS = new HashMap<Integer, String>(){
			{
				put(0, NN_RESULT);
				put(1, GRDY_RESULT);
				put(2, MST_RESULT);
				put(3, GEN_RESULT);
				put(4, CHRS_RESULT);
			}
		};
		
		RESULTS_CSV =  path+"results.csv";
	}

	
	@SuppressWarnings("serial")
	public static HashMap<Integer, String> STATES = new HashMap<Integer, String>(){
		{
			put(0, "RI");
			put(1, "DE");
			put(2, "VT");
			put(3, "WY");
			put(4, "MA");
			put(5, "UT");
			put(6, "TN");
		}
	};
	
}
