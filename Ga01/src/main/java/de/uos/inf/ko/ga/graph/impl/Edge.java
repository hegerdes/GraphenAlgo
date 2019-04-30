package de.uos.inf.ko.ga.graph.impl;

/**
 * This class represents an edge for a list implementation of a graph
 * 
 * @author thart
 *
 */
public class Edge{
	// Because every vertex has its own list only the connected vertex needs to be stored
	private int endknoten;
	private double weight;
	
	/**
	 * Constructor which is called with an integer for the vertex and 
	 * one double for the weight of the edge
	 * 
	 * @param n
	 * @param w
	 */
	public Edge(int n, double w){
		endknoten = n;
		weight = w;
	}
	
	/**
	 * Method returns the connected vertex of an edge
	 * 
	 * @return vertex as int
	 */
	public int getNode(){
		return endknoten;
	}
	
	/**
	 * Method returns the weight of an edge
	 * 
	 * @return weight as double
	 */
	public double getWeight(){
		return weight;
	}
	
}
