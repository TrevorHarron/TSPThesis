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
		public boolean equals(Edge o){
			boolean isDistEqual = distance == o.getDistance();
			
			return o != null && 
					(isDistEqual && 
					(to.equals(o.getTo()) || to.equals(o.getFrom())) && 
					(from.equals(o.getTo()) || from.equals(o.getFrom())));
			
		}
		
		public boolean equals(UndirectedEdge o){
			boolean isDistEqual = distance == o.getDistance();		
			return o != null && 
					(isDistEqual && 
					(to.equals(o.getTo()) || to.equals(o.getFrom())) && 
					(from.equals(o.getTo()) || from.equals(o.getFrom())));
			
		}
	}