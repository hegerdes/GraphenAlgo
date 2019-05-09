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

        //Add all nodes to minimal exit graph
        mst.addVertices(graph.getVertexCount());

        //Add the start node to added and remove it form not_added
        int start = findStart(graph);
        added.add(start);
        not_added.remove(start);

        //Default for global compare
        double weight = Double.POSITIVE_INFINITY;
        int next_node = Integer.MAX_VALUE;
        int current_node = Integer.MAX_VALUE;
        double glob_weight = 0;

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
                if(weight < Double.POSITIVE_INFINITY){
                    //Found ne new shortest edge and add it to minimal graph
                    mst.addEdge(current_node,next_node,weight);
                    added.add(next_node);
                    not_added.remove(next_node);
                    //For order in prim_01.gra, prim_02.gra, prim_03.gra
                    //glob_weight +=weight;
                    //System.out.println("Wähle Kante: e(" + current_node + "," + next_node + ")");
                }else{
                    //Found no new shortest edge. So ist must be isolated
                    System.out.println("FAIL: Es gibt Isolierte Knoten: " + not_added.toString());
                    return  new UndirectedGraphList();
                }
            //Reset global compare states
            weight = Double.POSITIVE_INFINITY;
            next_node = Integer.MAX_VALUE;
            current_node = Integer.MAX_VALUE;
        }
        //For weight in prim_01.gra, prim_02.gra, prim_03.gra
        //System.out.println("Gesamtes Wewicht: " + glob_weight);
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

		//INIT
        double[] weights = new double[graph.getVertexCount()];
        Arrays.fill(weights,Double.POSITIVE_INFINITY);
        mst.addVertices(graph.getVertexCount());
        NodeHeap heap = new NodeHeap();
        Set<Integer> added = new HashSet<>();
        List<Integer> neighbors;

        //Find a not isolated Node for start
        int start = findStart(graph);
        double glob_weight = 0;
        added.add(start);
		neighbors = graph.getNeighbors(start);

		//Put first neighbors in heap and remember weights
        for(Integer e: neighbors){
            heap.add(new Node(graph.getEdgeWeight(start,e),start,e));
            weights[e] = graph.getEdgeWeight(start,e);
        }

        //Get all Nodes through neighbors and add the one with min weight to mst
        while (added.size() <= graph.getVertexCount()- 1 && !heap.isEmpty()){
            //remove the cheapest Node and add to mst
            Node min = heap.remove();
            //Only add node if not added already
            if(!added.contains(min.getEnd())) {
                mst.addEdge(min.getStart(), min.getEnd(), graph.getEdgeWeight(min.getStart(), min.getEnd()));
                //For order in prim_01.gra, prim_02.gra, prim_03.gra
//                System.out.println("Wähle Kante: e(" + min.getStart() + "," + min.getEnd() + ")");
//                glob_weight += min.getWeight();
            }
            added.add(min.getEnd());
            added.add(min.getStart());

            //get new neighbors
            neighbors = graph.getNeighbors(min.getEnd());
            for(Integer e: neighbors){
                if(!added.contains(e)){
                    if(graph.getEdgeWeight(min.getEnd(),e) < weights[e]){
                        weights[e] = graph.getEdgeWeight(min.getEnd(),e);
                        heap.add(new Node(graph.getEdgeWeight(min.getEnd(),e),min.getEnd(),e));
                    }
                }
            }
            //If Heap is already empty than there are isolated Nodes. A empty graph will be returned
            //Can also leave them out but than there is the danger of a two unconnected graphs
            if(heap.isEmpty() && added.size() < graph.getVertexCount()) return  new UndirectedGraphList();
        }
        //For weight in prim_01.gra, prim_02.gra, prim_03.gra
        //System.out.println("Gesamtes Gewicht: " + glob_weight);
		return mst;
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

    /**
     * NOTE IS NOTE USED ANYMORE
     * If graph has isloated return true
     * @param g Graph to look in
     * @return true if isolated nodes else false
     */
    private static boolean hasIsolated(Graph g){
	    boolean isolated;
	    for (int i = 0; i < g.getVertexCount() - 1; i++){
            isolated = true;
	        for(int j = 0; j < g.getVertexCount() -1; j++){
                if(g.hasEdge(i,j)) isolated = false;
            }
	        if(isolated) return true;
        }
	    return false;
    }
}
