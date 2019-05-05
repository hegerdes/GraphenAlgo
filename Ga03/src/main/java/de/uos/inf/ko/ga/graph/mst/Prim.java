package de.uos.inf.ko.ga.graph.mst;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Prim {

	/**
	 * Computes a minimum spanning tree of an undirected graph using Prim's algorithm.
	 * @param graph Input graph
	 * @return Minimum spanning tree of the input graph
	 */
	public static Graph minimumSpanningTreeList(Graph graph) {
		assert(graph != null);
		assert(!graph.isDirected());
		final Graph mst = new UndirectedGraphList();

		//Use Set instead of List because of Java ist too stupid to compare the int and Integer with autoboxing
        //Java does not compare correctly with autoboxing. Function is still the same though.
		//List of all visited nodes. Empty at start.
        Set<Integer> added = new HashSet<>();
        //List of all nodes in the graph.
        Set<Integer> not_added = IntStream.rangeClosed(0, graph.getVertexCount()-1).boxed().collect(Collectors.toSet());

        //It could happen that the start node is isolated. So find a start node that has at least one edge
        int start = 0;
        boolean found = false;
        while (!found){
            for(Integer end: not_added){
                if(graph.hasEdge(start,end)){
                    //Found a start with at least one edge
                    found = true;
                    break;
                }
            }
            start++;
            if(start >= graph.getVertexCount()-1){
                //Graph has no connected Edges
                return mst;
            }
        }
        //Add all nodes to minimal exit graph
        mst.addVertices(graph.getVertexCount());

        //Add the start node to added and remove it form not_added
        added.add(start);
        not_added.remove(start);

        //Default for global compare
        double weight = Double.POSITIVE_INFINITY;
        int next_node = Integer.MAX_VALUE;
        int current_node = Integer.MAX_VALUE;

        //Loop till all Nodes visited
        while (not_added.size() != 0){
                for(Integer i: added){
                    for(Integer j: not_added){
                        if(graph.hasEdge(i,j)){
                            if(graph.getEdgeWeight(i,j) < weight){
                                //Remember edge(i,j) and weight because till now its the best edge
                                weight = graph.getEdgeWeight(i,j);
                                current_node = i;
                                next_node = j;
                            }
                        }
                    }
                }
                if(weight < Double.POSITIVE_INFINITY && current_node < Integer.MAX_VALUE &&
                        next_node < Integer.MAX_VALUE){
                    //Found ne new shortest edge and add it to minimal graph
                    System.out.println("Add: " + current_node + " " + next_node + " GW: " + weight);
                    mst.addEdge(current_node,next_node,graph.getEdgeWeight(current_node,next_node));
                    added.add(next_node);
                    not_added.remove(next_node);

                }else{
                    //Found now new shortest edge. So ist must be isolated
                    System.out.println("Es gibt Isolierte Knoten:");
                    for(Integer e: not_added) System.out.print(e + " ");
                    System.out.println();
                    break;
                }
            //Reset global compare states
            weight = Double.POSITIVE_INFINITY;
            next_node = Integer.MAX_VALUE;
            current_node = Integer.MAX_VALUE;
        }
		return mst;
	}

	/**
	 * Computes a minimum spanning tree of an undirected graph using Prim's algorithm.
	 * @param graph Input graph
	 * @return Minimum spanning tree of the input graph
	 */
	public static Graph minimumSpanningTreeHeap(Graph graph) {
		assert(graph != null);
		assert(!graph.isDirected());

		final Graph mst = new UndirectedGraphList();

		/* TODO: implement Prim's algorithm */

		return mst;
	}
}
