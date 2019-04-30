package de.uos.inf.ko.ga.graph.impl;

import java.util.ArrayList;
import java.util.List;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Implementation of a undirected graph with a matrix representation of the edges.
 *
 */
public class UndirectedGraphMatrix extends DirectedGraphMatrix implements Graph {

	public void addEdge(int start, int end) {
		addEdge(start, end, 1);
	}


	public void addEdge(int start, int end, double weight) {
		if(!checkNotInBounds(start,end)){
			//only create edge if its not a loop
			if(start != end){
				//store the weight at the proper place
				matrix[start][end] = weight;
				matrix[end][start] = weight;
			}
		}
	}


	public List<Integer> getNeighbors(int v) {
		//check if the vertex exists
		if(matrix.length < v+1) throw new IllegalArgumentException();
		//create list which is returned
		List<Integer> ret = new ArrayList<Integer>();
		//go throw the line of v and add all the conneced vertices
		for(int i=0;i<matrix[v].length;i++){
			if(matrix[v][i] != Double.POSITIVE_INFINITY){
				ret.add(i);
			}
		}	
		return ret;
	}


	public List<Integer> getPredecessors(int v) {
		//every neighbor is a predecessor
		return getNeighbors(v);
	}

	
	public List<Integer> getSuccessors(int v) {
		//every neighbor is a successor
		return getNeighbors(v);
	}

	
	
	public void removeEdge(int start, int end) {
		//set the field in the start-line to 0
		matrix[start][end] = Double.POSITIVE_INFINITY;
		//set the field in the end-line to 0
		matrix[end][start] = Double.POSITIVE_INFINITY;
		
	}

	
	public boolean isDirected() {
		return false;
	}

	/**
	 * Checks array bounds. For e.g if vertex x for new edge exist's
	 * @param start Start vertex
	 * @param end End vertex
	 * @return true if out of bounds else false
	 */
	private boolean checkNotInBounds(int start, int end) {
		//			throw new NoVertexExcept("Es gibt keinen Vertex mit:" + v );
		if (matrix.length <= start || start < 0)
			return true;
		return matrix[start].length <= end || end < 0;
	}
}
