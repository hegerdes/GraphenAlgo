package de.uos.inf.ko.ga.graph.shortestpath;

import java.util.List;
import de.uos.inf.ko.ga.graph.Graph;

/**
 * Shortest-path computation with Bellman-Ford for determining the distances
 * from a vertex to all other vertices in a directed graph.
 *
 */
public class BellmanFord {

	/**
	 * Computes distances to all vertices in a graph starting at a certain vertex.
	 * The distances returned is Double.POSITIVE_INFINITY if a vertex is not
	 * reachable from the start vertex.
	 * @param graph Input graph
	 * @param start Start vertex for the computation of the distances
	 * @return Array containing the distance d[v] from the start node for each vertex v
	 */
	public static double[] shortestPaths(Graph graph, int start) {
		int n = graph.getVertexCount();
		//initialize distances array
		double[] d = new double[n];

		//set to edge weight (infinity) if no direct connection to start
		for(int i=0;i<d.length;i++){
			d[i] = graph.getEdgeWeight(start, i);
		}
		//from start to start: 0
		d[start] = 0;

		//recursion
		for(int k=1;k<n;k++){
			for(int j=0;j<n;j++){
				//set d[j] to new value if the path from s to j is shorter
				//if you choose a path with i
				if(findMin(d,graph,j) < d[j]){
					d[j] = findMin(d,graph,j);
				}
			}
		}

		return d;

	}

	/**
	 * Method to get the distance from the start vertex to j with using a path with i
	 *
	 * @param d
	 * @param graph
	 * @param j
	 * @return minimum as double
	 */
	private static double findMin(double[]d, Graph graph, int j){
		int n = graph.getVertexCount();
		double min = Double.POSITIVE_INFINITY;
		//for all edges
		for(int i=0;i<n;i++){
			if(graph.hasEdge(i, j)){
				//get minimum path from s to i and then to j
				if(d[i] + graph.getEdgeWeight(i, j) < min){
					min = d[i] + graph.getEdgeWeight(i, j);
				}
			}

		}
		return min;

	}

}
