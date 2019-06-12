package de.uos.inf.ko.ga.tsp;

import de.uos.inf.ko.ga.graph.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class representing a tour in a graph.
 * @author Tobias Oelschlägel
 *
 */
public class Tour {
	public final static int TOURNUMBER = 100;

	private final Graph g;
	private final int[] vertices;
	private int size;

	/**
	 * Initializes an empty tour
	 * @param g Graph
	 */
	public Tour(Graph g) {
		this(g, null);
	}

	/**
	 * Creates a copy of a tour.
	 * @param tour Tour to be copied
	 */
	public Tour(Tour tour) {
		this(tour.getGraph(), tour.getVertices());
	}

	public int getSize() {
		return size;
	}

	/**
	 * Initializes a tour with a given order of the vertices.
	 * @param g Graph
	 * @param vertices Order of the vertices
	 */
	public Tour(Graph g, int[] vertices) {
		this.g = g;
		this.size = g.getVertexCount();

		if (vertices != null) {
			this.vertices = new int[vertices.length];
			System.arraycopy(vertices, 0, this.vertices, 0, vertices.length);
		} else {
			this.vertices = new int[0];
		}
	}

	public static Tour[] randomTours(Graph g)
	{
		Tour[] randomTours = new Tour[TOURNUMBER];

		for (int i = 0; i < TOURNUMBER; ++i)
		{
			// Fill a list with 0 to number of vertices numbers and shuffle it
			List<Integer> randTourList = IntStream.range(0,g.getVertexCount()).boxed().collect(Collectors.toList());
			Collections.shuffle(randTourList);

			// Through javas great features convert INT[] to int[] - GREAT
			Integer[] tmp = randTourList.toArray(new Integer[g.getVertexCount()-1]);
			int[] randTourArray = new int[g.getVertexCount()-1];
			for(int j = 0; j < g.getVertexCount()-1; ++j){
				randTourArray[j] =  tmp[j];
			}
			Tour t = new Tour(g,randTourArray);
			System.out.println(t + " " + i);
			randomTours[i] = t;

		}
		return randomTours;
	}

	/**
	 * Computes the total costs of the tour.
	 * @return total sum of costs of edges contained in the tour
	 */
	public double getCosts() {
		double costs = 0.0;
		
		final int n = this.vertices.length;

		for (int i = 0; i < n; ++i) {
			costs += this.g.getEdgeWeight(this.vertices[i], this.vertices[(i + 1) % n]);
		}
		
		return costs;
	}

	/**
	 * Gets the order of the vertices of the tour.
	 * @return order of vertices
	 */
	public int[] getVertices() {
		return this.vertices;
	}

	/**
	 * Gets the graph.
	 * @return graph
	 */
	public Graph getGraph() {
		return this.g;
	}
	
	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.vertices.length; i++){
			s.append(this.vertices[i]);
			s.append(" -> ");
		}
		
		s.append(this.vertices[0]);
		s.append(", Kosten = ");
		s.append(this.getCosts());

		return s.toString();
	}
}
 