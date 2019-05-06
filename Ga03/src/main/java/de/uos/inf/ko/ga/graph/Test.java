package de.uos.inf.ko.ga.graph;

import de.uos.inf.ko.ga.graph.util.BinaryHeap;
import de.uos.inf.ko.ga.graph.util.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {

//        final Random random = new Random(); // set seed to obtain deterministic results
//        /* generate a random graph */
//        final Graph graph = new UndirectedGraphMatrix();
//        GraphGenerator.generateRandomGraph(graph, 10, random, 0.25);
//
//        RenderGraph.renderGraph(graph, "out/random.png");
//        /* construct minimum spanning trees with both List and Heap implementations */
//        final Graph mstList = Prim.minimumSpanningTreeList(graph);
//        final Graph mstHeap = Prim.minimumSpanningTreeHeap(graph);
//
//        RenderGraph.renderGraph(mstList, "out/list.png");


        List<Double> test_list = new ArrayList<>();

        for(int i = 50; i> 0; i--){
            test_list.add((double)i);
        }


        BinaryHeap<Node> bh = new BinaryHeap<>();

        for(Double e: test_list){
            bh.add(new Node(e,1));
        }
        bh.print();
        bh.remove();
        System.out.println();
        bh.print();

    }
}
