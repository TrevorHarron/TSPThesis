package com.trevorharron.tsp.data;

import java.util.HashMap;

public final class FileNames {
	
	private static final String path = "/home/gurutre/workspace/TSPThesis/src/com/trevorharron/tsp/data/";
	
	public static final String TEST_KML = path+"simple.kml";
	public static final String TEST_CSV = path+"simple.csv";
	
	public static final String CITIES = path+"cities.kml";
	
	public static final String RICSV = path+"ri.csv";
	public static final String DECSV = path+"de.csv";
	public static final String VTCSV = path+"vt.csv";
	public static final String MACSV = path+"ma.csv";
	public static final String UTCSV = path+"ut.csv";
	public static final String WYCSV = path+"wy.csv";
	public static final String TNCSV = path+"tn.csv";
	
	public static final String NN_RESULT = path+"nn-results.csv";
	public static final String GRDY_RESULT = path+"grdy-results.csv";
	public static final String MST_RESULT = path+"mst-results.csv";
	public static final String GEN_RESULT = path+"gen-results.csv";
	public static final String CHRS_RESULT = path+"chrs-results.csv";
	
	
	
	@SuppressWarnings("serial")
	public static HashMap<Integer, String> ROADS = new HashMap<Integer, String>(){
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
	
	@SuppressWarnings("serial")
	public static HashMap<Integer, String> RESULTS = new HashMap<Integer, String>(){
		{
			put(0, NN_RESULT);
			put(1, GRDY_RESULT);
			put(2, MST_RESULT);
			put(3, GEN_RESULT);
			put(4, CHRS_RESULT);
		}
	};
	
}
