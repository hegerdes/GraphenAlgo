package de.uos.inf.ko.ga.graph.mst;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;
import de.uos.inf.ko.ga.graph.util.Node;
import de.uos.inf.ko.ga.graph.util.NodeHeap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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

        int start = findStart(graph);

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
                    mst.addEdge(current_node,next_node,weight);
                    added.add(next_node);
                    not_added.remove(next_node);

                }else{
                    //Found now new shortest edge. So ist must be isolated
                    System.out.print("FAIL: Es gibt Isolierte Knoten:");
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

		mst.addVertices(graph.getVertexCount());

		//INIT
        double weights[] = new double[graph.getVertexCount()];
        Arrays.fill(weights,Double.POSITIVE_INFINITY);

        //GenBinHeap<Node> heap = new GenBinHeap<>();
        NodeHeap heap = new NodeHeap();
        Set<Integer> added = new HashSet<>();
        List<Integer> neighbors;

        int start = findStart(graph);
        added.add(start);
		neighbors = graph.getNeighbors(start);

        for(Integer e: neighbors){
            heap.add(new Node(graph.getEdgeWeight(start,e),start,e));
            weights[e] = graph.getEdgeWeight(start,e);
        }

        while (!heap.isEmpty()){
            Node min = heap.remove();
            added.add(min.getEnd());
            mst.addEdge(min.getStart(),min.getEnd(),graph.getEdgeWeight(min.getStart(),min.getEnd()));

            neighbors = graph.getNeighbors(min.getEnd());
            for(Integer e: neighbors){
                if(!added.contains(e)){
                    if(graph.getEdgeWeight(min.getEnd(),e) < weights[e]){
                        weights[e] = graph.getEdgeWeight(min.getEnd(),e);
                        heap.add(new Node(graph.getEdgeWeight(min.getEnd(),e),min.getEnd(),e));
                    }
                }
            }


        }

		return mst;
	}

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
        }
        //Graph has no connected Edges
        return Integer.MAX_VALUE;

    }
}
