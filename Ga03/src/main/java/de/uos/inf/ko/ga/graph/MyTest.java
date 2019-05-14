package de.uos.inf.ko.ga.graph;

import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.mst.Prim;
import de.uos.inf.ko.ga.graph.render.RenderGraph;
import de.uos.inf.ko.ga.graph.util.GraphGenerator;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MyTest {

    public static void main(String[] args) throws IOException {

        final Random random = new Random();
        /* generate a random graph */
        final Graph graph = new UndirectedGraphMatrix();
        GraphGenerator.generateRandomGraph(graph, 10, random, 0.25);

        RenderGraph.renderGraph(graph, "out/random.png");
        /* construct minimum spanning trees with both List and Heap implementations */
        final Graph mstList = Prim.minimumSpanningTreeList(graph);
        final Graph mstHeap = Prim.minimumSpanningTreeHeap(graph);

        RenderGraph.renderGraph(mstList, "out/list.png");
        RenderGraph.renderGraph(mstHeap, "out/heap.png");
        assertEquals(getGraphWeight(mstList), getGraphWeight(mstHeap), 0.001);


//Test HEAP

//        List<Double> test_list = new ArrayList<>();
//        for(int i = 50; i> 0; i--){
//            test_list.add((double)i);
//        }
//        GenBinHeap<Node> bh = new GenBinHeap<>();
//
//        for(Double e: test_list){
//            bh.add(new Node(e,1));
//        }
//        bh.print();
//        bh.remove();
//        System.out.println();
//        bh.print();

    }

    private static double getGraphWeight(Graph graph) {
        double totalWeight = 0.0;

        for (int u = 0; u < graph.getVertexCount(); ++u) {
            for (int v : graph.getSuccessors(u)) {
                totalWeight += graph.getEdgeWeight(u, v);
            }
        }

        /* if the graph is undirected, then we counted all edge weights twice */
        return graph.isDirected() ? totalWeight : (0.5 * totalWeight);
    }
}
