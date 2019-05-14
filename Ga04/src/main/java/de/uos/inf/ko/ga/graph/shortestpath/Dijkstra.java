package de.uos.inf.ko.ga.graph.shortestpath;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;

public class Dijkstra {

	private static final double EPS = 0.0001;

	/**
	 * Computes distances of all vertices in a graph starting at a certain vertex.
	 * The distances returned is Double.POSITIVE_INFINITY if a vertex is not
	 * reachable from the start vertex.
	 * @param graph Input graph
	 * @param start Start vertex for the computation of the distances
	 * @return Array containing the distance d[v] from the start node for each vertex v
	 */
	public static double[] shortestPaths(Graph graph, int start) {
		final int n = graph.getVertexCount();
		final double[] dist = new double[n];

		//INIT
		//Set Dist to infinity and get all node numbers
		Arrays.fill(dist,Double.POSITIVE_INFINITY);
		Set<Integer> not_added = IntStream.rangeClosed(0, graph.getVertexCount()-1).boxed().collect(Collectors.toSet());
		Set<Integer> added = new HashSet<>();

		//Start pos
		dist[start] = 0;
		added.add(start);
		not_added.remove(start);

		//Init the weight for the first Node and its neighbors
		List<Integer> neighbors = graph.getNeighbors(start);
		for (int i : neighbors) {
			dist[i] = graph.getEdgeWeight(start, i);
		}

		while (added.size() < n) {
			int min = not_added.iterator().next();
			for (int i : not_added) {
				if (dist[i] < dist[min]) {min = i;}
			}

			//Found the min. Add ot added an remove form not_added
			added.add(min);
			not_added.remove(min);

			//Get neighbors form the current min and go on
			neighbors = graph.getNeighbors(min);
			neighbors.removeAll(added);

			//Update weights for all neighbors of next node
			for (int i : neighbors) {
				dist[i] = Math.min(dist[i], dist[min] + graph.getEdgeWeight(min, i));
			}
		}
		return dist;
	}
	
	/**
	 * Constructs a directed shortest-path tree from the distances of a shortest-path computation.
	 * This method determines the edges used by shortest paths between the start vertex and
	 * the remaining vertices. At first, it adds all edges outgoing from the start vertex, and
	 * then does this for the newly added vertices, and so on.
	 * @param graph Input graph
	 * @param start Start vertex
	 * @param dist distances of the vertices from the start vertex
	 * @return Graph containing edges of the shortest paths starting at the start vertex
	 */
	public static Graph shortestPathGraphFromDistances(Graph graph, int start, double[] dist) {
		final int n = graph.getVertexCount();

		/* construct new graph with the same set of vertices */
		final Graph shortestPathTree = new DirectedGraphList();
		shortestPathTree.addVertices(n);

		/* queue containing the vertices that have been seen yet */
		final boolean[] seen = new boolean[n];
		final Queue<Integer> queue = new LinkedList<>();

		/* add the start vertex to the queue */
		seen[start] = true;
		queue.add(start);

		while (!queue.isEmpty()) {
			final int u = queue.poll();

			/* determine the neighbors of 'u' that are reachable from 'start' via 'u' in a shortest path */
			for (final int v : graph.getSuccessors(u)) {
				if (!seen[v]) {
					/* test whether the value dist[v] obtained its value from the value dist[u] */
					if (Math.abs(dist[u] + graph.getEdgeWeight(u, v) - dist[v]) <= EPS) {
						seen[v] = true;
						shortestPathTree.addEdge(u, v, graph.getEdgeWeight(u, v));
						queue.add(v);
					}
				}
			}
		}

		return shortestPathTree;
	}

	/**
	 * Find a start Node that is not isolated
	 * @param graph The graph to look in
	 * @return Vertex-ID od the first node with at least one edge
	 */
	private static int findStart(Graph graph){
		//It could happen that the start node is isolated. So find a start node that has at least one edge
		int start = 0;
		boolean found = false;
		while (!found){
			for(int i=0;i < graph.getVertexCount()-1; i++){
				if(graph.hasEdge(start,i)){
					//Found a start with at least one edge
					return start;
				}
			}
			start++;
			if(start > graph.getVertexCount()) found = true;
		}
		//Graph has no connected Edges
		return Integer.MAX_VALUE;
	}
}
