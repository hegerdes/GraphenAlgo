package de.uos.inf.ko.ga.tsp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.reader.GraphReader;

public class TwoOptTest {

	private static final List<String> GRAPHS = Arrays.asList(
			"tsp_01.gra",
			"tsp_02.gra",
			"tsp_03.gra"
	);

	@Test
	public void testRunTwoOptOnTestGraphs() throws IOException {
		for (final String filename : GRAPHS) {
			final File fileGraph = new File("src/test/resources/" + filename);

			//try {
				final Graph graph = GraphReader.readUndirectedGraph(fileGraph);
				assertNotNull(graph);

				// TODO: generate 100 random TSP tours and call TwoOpt.iterativeTwoOpt() on them
				Tour[] randomTSPs = Tour.randomTours(graph);

				for(int i = 0; i < randomTSPs.length; ++i){
					randomTSPs[i] = TwoOpt.iterativeTwoOpt(randomTSPs[i],false);
				}

				Tour best = randomTSPs[0];
				Tour worst = randomTSPs[0];
				double cost_all = 0;

				for(Tour t: randomTSPs){
				    if(t.getCosts() > worst.getCosts()) worst = t;
                    if(t.getCosts() < best.getCosts()) best = t;
                    cost_all += t.getCosts();
                }

				// TODO: output minimum, maximum, and average tour length
				System.out.println("TSP Results for file: " + filename);
				System.out.println("Best: " + best.toString() + " Cost: " + best.getCosts());
				System.out.println("Worst: " + worst.toString() + " Cost: " + worst.getCosts());
				System.out.println("Average: " + cost_all/Tour.TOURNUMBER + "\n");



//			} catch (Exception ex) {
//				fail("caught an exception while computing TSP tours: " + ex);
//			}
		}
	}
}
