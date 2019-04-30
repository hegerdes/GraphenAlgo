package de.uos.inf.ko.ga.graph.converter;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

import java.util.List;

/**
 * Methods for converting between list and matrix representations of graphs.
 */
public class GraphConverter {

	/**
	 * Constructs a graph represented by adjacency lists.
	 * Outputs an instance of DirectedGraphList if the input graph is directed, and outputs an instance of UndirectedGraphList otherwise.
	 * The weights of the edges of the new graph are the same as the ones of the input graph.
	 * @param graph Graph to be converted
	 * @return graph Graph represented by adjacency list
	 */
	public static Graph toList(Graph graph) {
		if (graph.isDirected()) {
			Graph g = new DirectedGraphList();
			return buildGraph(graph,g);
		} else {
			Graph g = new UndirectedGraphList();
			return buildGraph(graph,g);
		}
	}

	/**
	 * Constructs a graph represented by an adjacency matrix.
	 * Outputs an instance of DirectedGraphMatrix if the input graph is directed, and outputs an instance of UndirectedGraphMatrix otherwise.
	 * The weights of the edges of the new graph are the same as the ones of the input graph.
	 * @param graph Graph to be converted
	 * @return graph Graph represented by an adjacency matrix
	 */
	public static Graph toMatrix(Graph graph) {
		if (graph.isDirected()) {
			Graph g = new DirectedGraphMatrix();
			return buildGraph(graph,g);
		} else {
			Graph g = new UndirectedGraphMatrix();
			return buildGraph(graph,g);
		}
	}

	/**
	 * Rebuilds the Graph with the provided functions of <coder>Graph</coder>
	 * @param ori The original Graph
	 * @param dest The rebuild Graph with the same infos as ori
	 * @return The dest graph
	 */
	private static Graph buildGraph(Graph ori, Graph dest){
		dest.addVertices(ori.getVertexCount());
		List<Integer> l;
		for(int i = 0; i< ori.getVertexCount(); i++){
			l = ori.getSuccessors(i);
			for(Integer e: l){
				if(ori.hasEdge(i, e)){
					dest.addEdge(i, e, ori.getEdgeWeight(i, e));
				}
			}
			l.clear();
		}
		return dest;



	}

}
