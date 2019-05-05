package de.uos.inf.ko.ga.graph;

import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.mst.Prim;
import de.uos.inf.ko.ga.graph.render.RenderGraph;
import de.uos.inf.ko.ga.graph.util.GraphGenerator;

import java.io.IOException;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws IOException {

        final Random random = new Random(); // set seed to obtain deterministic results
        /* generate a random graph */
        final Graph graph = new UndirectedGraphMatrix();
        GraphGenerator.generateRandomGraph(graph, 10, random, 0.25);

        RenderGraph.renderGraph(graph, "out/random.png");
        /* construct minimum spanning trees with both List and Heap implementations */
        final Graph mstList = Prim.minimumSpanningTreeList(graph);
        final Graph mstHeap = Prim.minimumSpanningTreeHeap(graph);

        RenderGraph.renderGraph(mstList, "out/list.png");

    }
}
