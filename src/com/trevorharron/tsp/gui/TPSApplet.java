package com.trevorharron.tsp.gui;

import java.applet.Applet;

import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.trevorharron.tsp.data.FileNames;
import com.trevorharron.tsp.data.Pair;
import com.trevorharron.tsp.graph.Graph;
import com.trevorharron.tsp.graph.GraphSymmetric;
import com.trevorharron.tsp.graph.edge.Edge;
import com.trevorharron.tsp.graph.node.Node;
import com.trevorharron.tsp.reader.CSVReader;
import com.trevorharron.tsp.reader.DataReader;
import com.trevorharron.tsp.reader.KMLReader;
import com.trevorharron.tsp.solvers.Solver;
import com.trevorharron.tsp.solvers.SolverFactory;

@SuppressWarnings("serial")
public class TPSApplet extends Applet{
	Graphics buffer;
	Image offscreen;
	Dimension dim; 
	
	SolverFactory factory;
	Graph graph;
	Button solveButton, resetButton;
	Choice solvers;
	
	private static String[] solverList = {"Nearest Neighbor", "Greedy", "Minimum Spanning Tree"};
	private static int MAX_X = 1024;
	private static int MAX_Y = 512;
	private static int SCREEN_X = 1250;
	private static int SCREEN_Y = 750;
	private static int START_X = 30;
	private static int START_Y = 50;
	
	double minX, maxX, minY, maxY;
	
	ArrayList<Edge> route;
	ArrayList<String> result;
	
	HashMap<String,Pair<Double,Double>> points;
	
	public void init(){
		result = new ArrayList<String>();
		setBackground(Color.WHITE);
		
		DataReader kml = new KMLReader();
		DataReader csv = new CSVReader();
		graph = new GraphSymmetric();
		factory = new SolverFactory();
		points = new HashMap<String,Pair<Double,Double>>();
		
		try {
			kml.readFile(FileNames.citiesKML,graph,"RI");
			csv.readFile(FileNames.RICSV,graph,"RI");	
		} catch(Exception e) {
			e.printStackTrace();
		}
		graph.finalize();
		setSize(SCREEN_X,SCREEN_Y);
		setLayout(null);
		
		minX = findMin(graph,true);
		maxX = findMax(graph,true);
		
		minY = findMin(graph,false);
		maxY = findMax(graph,false);

		resetButton = new Button("Reset");
		resetButton.setBounds(SCREEN_X-150,START_Y,100,30);
		add(resetButton);
		
		solvers = new Choice();
		for(String solver: solverList)
			solvers.add(solver);
		solvers.setBounds(SCREEN_X-150, START_Y+30, 150,30);
		add(solvers);
		
		solveButton = new Button("Solve");
		solveButton.setBounds(SCREEN_X-150,START_Y+60,100,30); 
		add(solveButton);

		HashMap<String,Node> cities = graph.getCities();
		for(String name: graph.getCities().keySet()){
			points.put(name, new Pair<Double, Double>(
					normalize(cities.get(name).getxCor(), minX, maxX),
					1-normalize(cities.get(name).getyCor(), minY, maxY)));
		}
		dim = getSize(); 
		offscreen = createImage(dim.width,dim.height); 
		buffer = offscreen.getGraphics(); 
		
		repaint();
	}
	
	public void paint(Graphics g){
		
		buffer.clearRect(0,0,dim.width,dim.width);
		
		buffer.setColor(Color.LIGHT_GRAY);
		drawRoads(buffer, graph.getRoads());
		if(!result.isEmpty()){
			drawRoads(buffer, graph.getRoads());
			buffer.setColor(Color.RED);
			Graphics2D g2 = (Graphics2D) buffer;
			g2.setStroke(new BasicStroke(3));
			for(int index = 0; index < result.size()-4;index++){
				Edge e = graph.getRoad(result.get(index), result.get(index+1));
				g2.drawLine((int)(START_X+3+points.get(e.getTo()).getLeft()*MAX_X),
						(int)(START_Y+3 +(points.get(e.getTo()).getRight())*MAX_Y),
						(int)(START_X+3+points.get(e.getFrom()).getLeft()*MAX_X),
						(int)(START_Y+3+(points.get(e.getFrom()).getRight())*MAX_Y));
			}
			g2.setStroke(new BasicStroke(1));
		}
		drawCities(buffer);
		buffer.setColor(Color.LIGHT_GRAY);
		g.drawImage(offscreen,0,0,this);
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	private void drawCities(Graphics g) {
		for(String key:points.keySet()){
			g.setColor(Color.BLACK);
			g.drawOval((int)(START_X+points.get(key).getLeft()*MAX_X),
					(int)(START_Y +(points.get(key).getRight())*MAX_Y), 6, 6);
			g.fillOval((int)(START_X+points.get(key).getLeft()*MAX_X),
					(int)(START_Y +(points.get(key).getRight())*MAX_Y), 6, 6);
			g.drawString(key, (int)(START_X+points.get(key).getLeft()*MAX_X)-10, 
					(int)(START_Y +(points.get(key).getRight())*MAX_Y) +15);
		}
		
	}

	private void drawRoads(Graphics g, List<Edge> list) {
		for(Edge e: list){
			g.drawLine((int)(START_X+3+points.get(e.getTo()).getLeft()*MAX_X),
					(int)(START_Y+3 +(points.get(e.getTo()).getRight()*MAX_Y)),
					(int)(START_X+3+points.get(e.getFrom()).getLeft()*MAX_X),
					(int)(START_Y+3+(points.get(e.getFrom()).getRight()*MAX_Y)));
		}	
	}

	private double normalize(double i, double min, double max){
		return (i-min)/(max-min);
	}
	
	private double findMin(final Graph g, final boolean isX){
		double minCor = 1000000000.0;
		for(String key: g.getCities().keySet()){
			if(isX){
				double x =  g.getCity(key).getxCor();
				if(x < minCor)
					minCor = x;
			} else {
				double y =  g.getCity(key).getyCor();
				if(y < minCor)
					minCor = y;
			}
		}
		return minCor;
	}
	
	private double findMax(final Graph g, final boolean isX){
		double maxCor = -1000000000.0;
		for(String key: g.getCities().keySet()){
			if(isX){
				double x =  g.getCity(key).getxCor();
				if(x > maxCor)
					maxCor = x;
			} else {
				double y =  g.getCity(key).getyCor();
				if(y > maxCor)
					maxCor = y;
			}
		}
		return maxCor;
	}
	
	public boolean action(Event e, Object args){
		String choice = solvers.getSelectedItem();
		if(e.target == solveButton){
			if(choice.equals("Nearest Neighbor")){
				graph =  new GraphSymmetric(graph);
				factory.setChoice(0);
			} else if(choice.equals("Greedy")){}
				//factory.setChoice(1);
			else if(choice.equals("Minimum Spanning Tree")){}
				//factory.setChoice(2);
			
			Solver s = factory.getSolver();
			graph.resetGraph();
			s.setGraph(graph);
			result = s.solve();
		}
		if(e.target ==resetButton){
			graph.resetGraph();
			result = new ArrayList<String>();
		}
		if(e.target==solvers){
			choice = solvers.getSelectedItem();
		}
		repaint();
		return true;
	}


}
