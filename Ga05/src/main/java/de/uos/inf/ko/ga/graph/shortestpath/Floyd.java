package de.uos.inf.ko.ga.graph.shortestpath;

import de.uos.inf.ko.ga.graph.Graph;

/**
 * Shortest-path computation with Floyd for determining the distances between
 * all pairs of vertices in a directed graph.
 *
 */
public class Floyd {

	/**
	 * Computes distances between all pairs of vertices.
	 * If a vertex is not reachable from another vertex, the corresponding entry
	 * of the distance matrix is Double.POSITIVE_INFINITY.
	 * @param graph Input graph
	 * @return Matrix of dimension n times n with entry d[i][j] being the distance from vertex i to vertex j
	 */
	public static double[][] shortestPaths(Graph graph) {	
		int n = graph.getVertexCount();
		//initialize distances arrays
		double[][]d = new double[n][n];
		
		for(int i=0; i<n;i++){
			for(int j=0;j<n;j++){
				//the diagonal is 0
				if(i == j) d[i][j] = 0;
				//for all the other initialize with edge weight
				else d[i][j] = graph.getEdgeWeight(i, j);
			}
		}
		//calculate shortest distances
		for(int k=0;k<n;k++){
			for(int i=0; i<n;i++){
				for(int j=0;j<n;j++){
					if(d[i][k] + d[k][j] < d[i][j]){
						d[i][j] = d[i][k] + d[k][j];
					}
				}
			}
		}
		return d;
	}
}
