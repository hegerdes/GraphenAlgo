package de.uos.inf.ko.ga.graph;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.uos.inf.ko.ga.graph.converter.GraphConverter;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphList;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.reader.GraphReader;
import de.uos.inf.ko.ga.graph.render.RenderGraph;

public class OwnTest {

	public static void main(String[] args) {
		DirectedGraphList test = new DirectedGraphList();
		
		test.addVertices(5);
		
		test.addEdge(0, 3, 7.32);
		test.addEdge(0, 4);
		test.addEdge(2, 3);
		test.addEdge(0, 2);
		
		GraphConverter conv= new GraphConverter();
	
		DirectedGraphMatrix asList = (DirectedGraphMatrix) conv.toMatrix(test);
		
		List li = new ArrayList();
		li=test.getNeighbors(0);
		
		
		DirectedGraphMatrix graph = new DirectedGraphMatrix();
		
		File file = new File("src/test/resources/reader_test_directed.gra");
		if (!file.canRead() || !file.isFile()) System.out.println("Fehler");
		else{
			GraphReader read = new GraphReader();
			graph = (DirectedGraphMatrix) read.readDirectedGraph(file);
		}
		
		System.out.println();
	}

}
