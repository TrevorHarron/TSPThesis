package com.trevorharron.tsp.graph.edge;


public class UndirectedEdge extends Edge{
		public UndirectedEdge(Edge e){
			super(e);
		}
		
		@Override
		public int compareTo(Edge o) {
			if(distance > o.getDistance())
				return 1;
			else if(distance == o.getDistance())
				return 0;
			else
				return -1;
		}
		
		@Override
		public boolean equals(Object o){
			if(o instanceof Edge){
				boolean isDistEqual = (distance == ((Edge) o).getDistance());
				return o != null && 
						(isDistEqual && 
						(to.equals(((Edge) o).getTo()) || to.equals(((Edge) o).getFrom())) && 
						(from.equals(((Edge) o).getTo()) || from.equals(((Edge) o).getFrom())));
			} else
				return false;
			
		}
	}