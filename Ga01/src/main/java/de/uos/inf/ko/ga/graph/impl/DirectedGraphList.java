package de.uos.inf.ko.ga.graph.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Implementation of a directed graph with a list representation of the edges.
 *
 */
public class DirectedGraphList implements Graph {
	
	//list representation of the edges
	public List<Edge>[] array;
	
	/**
	 * Constructor, which initializes the array of lists
	 * 
	 */
	public DirectedGraphList(){
		array = new List[0];		
	}
	
	
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
			//Create new edge
			Edge e = new Edge(end, weight);	
			// store the edge in the appropriate list
			array[start].add(e);
		}
		
	}

	
	public void addVertex() {
		//copy the old lists
		List<Edge>[]oldArr = array.clone();
		//make the array bigger
		array = new List[array.length+1];
		//copy all the lists back into the array
		for(int i=0;i<oldArr.length;i++){
			array[i] = oldArr[i];
		}
		//create one more list
		array[array.length-1] = new ArrayList<Edge>();
		
	}

	
	public void addVertices(int n) {
		//call the addVertex() method n-times
		for(int i=0;i<n;i++){
			addVertex();
		}
	}

	
	public List<Integer> getNeighbors(int v) {	
		//check if the vertex exists
		if(array.length < v+1) throw new IllegalArgumentException();
		
		//create list, which is returned in the end and store all predecessors in it
		List<Integer> ret = new ArrayList<Integer>();
		ret.addAll(getPredecessors(v));
		//create list and store all successors in it
		List<Integer> suc = new ArrayList<Integer>();
		suc = getSuccessors(v);
		
		//check which successors need to be added, to prevent duplicates 
		for(int i=0;i<suc.size();i++){
			if(!ret.contains(suc.get(i))){
				ret.add(suc.get(i));
			}
		}
			
		return ret;
	}

	
	public List<Integer> getPredecessors(int v) {
		//check if the vertex exists
		if(array.length < v+1) throw new IllegalArgumentException();
		//create list, which is returned in the end
		List<Integer> ret = new ArrayList<Integer>();
		//go throw all the lists except the one from vertex v and and search for v
		for(int i=0;i<getVertexCount();i++){
			if(i!=v){
				for(int j=0;j<array[i].size();j++){
					//add every vertex if the edge to v is found
					if(array[i].get(j).getNode() == v){
						ret.add(i);
					}				
				}
			}
		}	
		return ret;
	}

	
	public List<Integer> getSuccessors(int v) {
		//check if the vertex exists
		if(array.length < v+1) throw new IllegalArgumentException();
		List<Integer> ret= new ArrayList<Integer>();
		//go throw the list of v and add all the vertices
		for(int i=0; i<array[v].size();i++){
			ret.add(array[v].get(i).getNode());
		}
			
		return ret;
	}

	
	public int getVertexCount() {
		return array.length;
	}

	
	public double getEdgeWeight(int start, int end) {
		double ret = 0.0;
		//return infinity if no edge exists
		if(!hasEdge(start,end)) return Double.POSITIVE_INFINITY;
		//go throw the list of start vertex
		for(int i=0; i<array[start].size();i++){
			//search end and return the weight of the edge
			if(array[start].get(i).getNode() == end){
				ret = array[start].get(i).getWeight();
			}
		}
		return ret;			
	}

	
	public boolean hasEdge(int start, int end) {
		boolean flag = false;
		//if start or end vertex does not exist, return false
		if(start > getVertexCount()-1 || end > getVertexCount()-1) return false;
		//go throw the list of start vertex
		for(int i=0; i<array[start].size();i++){
			//search for end
			if(array[start].get(i).getNode() == end){
				flag = true;
			}
		}
		
		return flag;
	}

	
	public void removeEdge(int start, int end) {
		//look if the edge exists and can be removed
		if(hasEdge(start,end)){
			//go throw the list of start vertex
			for(int i=0;i<array[start].size();i++){
				//search for end and remove end from the list
				if(array[start].get(i).getNode() == end){
					array[start].remove(i);
				}
			}
		}
		
	}

	public void removeVertex() {
		//copy the old lists
		List<Edge>[]oldArr = array.clone();
		//decrease the array by one
		array = new List[array.length-1];
		//copy the lists back
		for(int i=0;i<oldArr.length-1;i++){
			array[i] = oldArr[i];
		}
		//remove the removed vertex from every list
		for(int j=0;j<array.length;j++){
			for(int k=0;k<array[j].size();k++){
				if(array[j].get(k).getNode() == array.length){
					array[j].remove(k);
				}
			}
		}			
	}

	
	public boolean isWeighted() {
		boolean flag = false;
		//go throw all lists and check if one value is not 1.0
		for(int i=0; i<array.length;i++){
			for(int j=0; j<array[i].size();j++){
				if(array[i].get(j).getWeight() != 1.0){
					flag = true;
				}
			}
		}
		return flag;
	}

	
	public boolean isDirected() {
		
		return true;
	}
}
