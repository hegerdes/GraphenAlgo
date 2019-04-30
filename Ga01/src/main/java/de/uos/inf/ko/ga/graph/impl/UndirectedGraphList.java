package de.uos.inf.ko.ga.graph.impl;

import java.util.ArrayList;
import java.util.List;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Implementation of an undirected graph with a list representation of the edges.
 *
 */
public class UndirectedGraphList extends DirectedGraphList implements Graph  {


	public void addEdge(int start, int end) {
		//only create edge if its not a loop
		if(start != end){
			//call the eddEdge method which has the weight as an additional parameter
			this.addEdge(start, end, 1);	
		}
	}


	public void addEdge(int start, int end, double weight) {
		//only create edge if its not a loop
		if(start != end){
			//Create two edges. First from start to end
			Edge e1 = new Edge(end, weight);
			//Second from end to start
			Edge e2 = new Edge(start, weight);
			//add the edges
			array[start].add(e1);
			array[end].add(e2);
		}	
	}

	
	public List<Integer> getNeighbors(int v) {	
		//check if the vertex exists
		if(array.length < v+1) throw new IllegalArgumentException();
		//Create list which is returned
		List<Integer> ret = new ArrayList<Integer>();
		//go throw the list and add all the vertices
		for(int i=0;i<array[v].size();i++){
			ret.add(array[v].get(i).getNode());
		}
		return ret;
	}

	
	public List<Integer> getPredecessors(int v) {
		//Every neighbor is a predecessor
		return getNeighbors(v);
	}

	
	public List<Integer> getSuccessors(int v) { 
		//every neighbor is a successor
		return getNeighbors(v);
	}

	
	public void removeEdge(int start, int end) {
		//look if the edge exists and can be removed
		if(hasEdge(start,end)){
			//go throw the list of start vertex
			for(int i=0;i<array[start].size();i++){
				//search for end and remove end from the start-list
				if(array[start].get(i).getNode() == end){
					array[start].remove(i);
				}
			}
			//go throw the list of end vertex
			for(int i=0;i<array[end].size();i++){
				//search for start end remove start from the end-list
				if(array[end].get(i).getNode() == start){
					array[end].remove(i);
				}
			}			
		}
	}


	public boolean isDirected() {
		return false;
	}
}
