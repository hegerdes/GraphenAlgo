package de.uos.inf.ko.ga.shortestpath;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.reader.GraphReader;
import de.uos.inf.ko.ga.graph.shortestpath.BellmanFord;
import de.uos.inf.ko.ga.graph.shortestpath.Floyd;
import de.uos.inf.ko.ga.graph.util.GraphGenerator;

public class BellmanFordFloydTest {

	private static final List<String> GRAPHS = Arrays.asList(
			"floyd_01.gra",
			"floyd_02.gra",
			"floyd_03.gra"
	);

	/**
	 * Determine distances between all pairs of vertices with Bellman-Ford and Floyd
	 * and output the distances between all pairs.
	 */
	@Test
	public void testCompareBellmanFordAndFloyd() {
		for (final String filename : GRAPHS) {
			final File fileGraph = new File("src/test/resources/" + filename);
			
			try {
				final Graph graph = GraphReader.readDirectedGraph(fileGraph);
				assertNotNull(graph);
		
				BellmanFord b = new BellmanFord();
				Floyd f = new Floyd();
				//get all shortest paths with Floyd
				double[][] d_floyd = f.shortestPaths(graph);
				
				//print matrix
				for(int i=0;i<d_floyd.length;i++){
					for(int j=0;j<d_floyd[i].length;j++){
						System.out.print(d_floyd[i][j] + " ");
					}
					System.out.println();
				}
				System.out.println();
				
				//get all shortest paths with Bellman
				for(int i=0;i<graph.getVertexCount();i++){
					double[] d_bellmann = b.shortestPaths(graph,i);					
					Assert.assertArrayEquals(d_bellmann, d_floyd[i], 0.01);	
					//print matrix
					for(int j=0;j<d_bellmann.length;j++){
						System.out.print(d_bellmann[j] + " ");
					}
					System.out.println();
				}
				System.out.println("-------------------------------------");
			} catch (Exception ex) {
				fail("caught an exception while computing shortest paths: " + ex);
			}
		}
	}
	
	/**
	 * Generate random graphs and report the running times of Bellman-Ford and Floyd.
	 */
	@Test
	public void testCompareRunningTimes() {
		final Random random = new Random();
		final int n = 100;

		long totalTimeBellmanFord = 0; 
		long totalTimeFloyd = 0;
		long [] timesBellman = new long [10];
		long [] timesFloyd = new long [10];
		
		
		GraphGenerator graphgen = new GraphGenerator();
		BellmanFord b = new BellmanFord();
		Floyd f = new Floyd();
		
		//get shortest path with floyd and bellmann for 10 instances
		for (int inst = 0; inst < 10; ++inst) {
			Graph graph = new DirectedGraphMatrix();
			//generate random graphs with n vertices
			graphgen.generateRandomGraph(graph, n, random, 0.3);
			
			//get times
			long timeStart = System.currentTimeMillis();
			bellmanFordAllPairs(graph);		
			long timeEnd = System.currentTimeMillis();
			timesBellman[inst] = timeEnd - timeStart;
			
			timeStart = System.currentTimeMillis();
			f.shortestPaths(graph);
			timeEnd = System.currentTimeMillis();
			timesFloyd[inst] = timeEnd - timeStart;
			
		
		}
		
		//output calculated times
		System.out.println("------Compare running times---------");
		System.out.println("Bellmann Times:");
		for(int i=0;i<timesBellman.length;i++){
			System.out.print(timesBellman[i]+ " ");
			totalTimeBellmanFord = totalTimeBellmanFord + timesBellman[i];
		}
		System.out.println();
		System.out.println("Bellmann total time: " + totalTimeBellmanFord);
		System.out.println();
		System.out.println();
		
		System.out.println("Floyd Times:");
		for(int i=0;i<timesBellman.length;i++){
			System.out.print(timesFloyd[i]+ " ");
			totalTimeFloyd = totalTimeFloyd + timesFloyd[i];
		}
		System.out.println();
		System.out.println("Floyd total time: " + totalTimeFloyd);
		
		
	}

	/**
	 * Executes Bellman-Ford with each vertex as the starting vertex.
	 * @param graph Input graph
	 * @return distance matrix with distances between all pairs of vertices
	 */
	private double[][] bellmanFordAllPairs(Graph graph) {
		final int n = graph.getVertexCount();
		
		double d[][] = new double[n][n];
		
		for (int u = 0; u < n; ++u) {
			d[u] = BellmanFord.shortestPaths(graph, u);
			assertNotNull(d[u]);
		}
		
		return d;
	}
}
