package de.uos.inf.ko.ga.tsp;

import de.uos.inf.ko.ga.graph.Graph;

import java.util.*;
import java.math.*;

public class TwoOpt {

	/**
	 * Perform a two-opt exchange step of the edges (v[pos1], v[pos1 + 1]) and (v[pos2], v[pos2 + 1]).
	 * The resulting tour visits
	 *   v[0], ..., v[pos1], v[pos2], v[pos2 - 1], ..., v[pos1 + 1], v[pos2 + 1], v[pos2 + 2], ..., v[n - 1].
	 * Special case: if the last edge (v[n - 1], v[0]) is used, then the following tour is created:
	 *   v[0], v[pos1 + 1], v[pos1 + 2], ..., v[n - 1], v[pos1], v[pos1 - 1], ..., v[0]
	 * @param tour Tour
	 * @param pos1 Index of the starting vertex of the first edge
	 * @param pos2 Index of the starting vertex of the second edge
	 * @return tour obtained by performing the edge exchange
	 */
	public static Tour twoOptExchange(Tour tour, int pos1, int pos2) {
		assert(tour != null);
		assert(pos1 >= 0);
		assert(pos2 > pos1 + 1);
		assert(pos2 < tour.getVertices().length);
		assert(pos1 != (pos2 + 1) % tour.getVertices().length);


		boolean pos2_last = (pos2 == tour.getSize() - 1);
		int pos2suc = pos1;

		//Keep old values to compare
		int[] newTour = tour.getVertices().clone();
		final int[] oldTour = tour.getVertices().clone();

		//Shift edges
		if (pos2_last) {
			int i = 1;
			while (pos1+1 < newTour.length - 1) {
				newTour[i] = oldTour[pos1+1];
				pos1++;
				i++;
			}
			while (pos2suc >= 0){
				newTour[i] = oldTour[pos2suc];
				pos2suc--;
				i++;
			}
		}else {
			System.arraycopy(oldTour, 0, newTour, 0, pos1);

			int offset = 0;
			for (int c = pos1; c <= pos2; ++c) {
				newTour[c] = oldTour[pos2 - offset];
				offset++;
			}
			pos2++;
			while(pos2 < oldTour.length){
				newTour[pos2] = oldTour[pos2];
				pos2++;
			}
		}
		return new Tour(tour.getGraph(),newTour);
	}

	/**
	 * Single step of the Two-Opt neighborhood for the TSP with either
	 * first-fit or best-fit selection of the neighbor.
	 * - First-fit returns the first neighbor that is found that has a
	 *   better objective value than the original tour.
	 * - Best-fit always searches through the whole neighborhood and
	 *   returns one of the tours with the best objective values.
	 * @param tour
	 * @param firstFit whether to use first-fit or best-fit for neighbor selection
	 * @return tour obtained by performing the first or the best improvement
	 */
	public static Tour twoOptNeighborhood(Tour tour, boolean firstFit) {

		Tour best_tour = new Tour(tour);
		Tour new_tour = new Tour(tour);


		//First-fit
		if(firstFit) {
			for (int i = 0; i < tour.getSize(); i++) {
				if (i >= 2) {
					for (int x = 0; x < i - 2; x++) {
						if (x != (i + 1) % tour.getVertices().length) {
							new_tour = twoOptExchange(tour, x, i);
							if (new_tour.getCosts() < best_tour.getCosts()) {
								return new_tour;
							}
						}
					}
				}
				for (int x = i + 2; x < tour.getSize(); x++) {
					if (i != (x + 1) % tour.getVertices().length) {
						new_tour = twoOptExchange(tour, i, x);
						if (new_tour.getCosts() < best_tour.getCosts()) {
							return new_tour;
						}
					}
				}
			}
			return new_tour;
		}else {
			//Best-fit
			for (int i = 0; i < tour.getSize(); i++) {
				if (i >= 2) {
					for (int x = 0; x < i - 2; x++) {
						if (x != (i + 1) % tour.getVertices().length) {
							new_tour = twoOptExchange(tour, x, i);
							if (new_tour.getCosts() < best_tour.getCosts()) {
								best_tour = new_tour;
							}
						}
					}
				}
				for (int x = i + 2; x < tour.getSize(); x++) {
					if (i != (x + 1) % tour.getVertices().length) {
						new_tour = twoOptExchange(tour, i, x);
						if (new_tour.getCosts() < best_tour.getCosts()) {
							best_tour = new_tour;
						}
					}
				}
			}
			return best_tour;
		}
	}

	/**
	 * Iterative Two-Opt neighborhood for the TSP.
	 * This method calls twoOptNeighborhood iteratively as long as the
	 * tour can be improved.
	 * @param tour Tour to be improved
	 * @param firstFit whether to use first-fit or best-fit for neighbor selection
	 * @return best tour obtained by iteratively applying the two-opt neighborhood
	 */
	public static Tour iterativeTwoOpt(Tour tour, boolean firstFit) {

		if (!firstFit ){tour = twoOptNeighborhood(tour,false);}
		else {
			while (twoOptNeighborhood(tour, true).getCosts() - tour.getCosts() != 0) {
				if(twoOptNeighborhood(tour,true).getCosts() - tour.getCosts() > 0) {
					tour = twoOptNeighborhood(tour,true);
				}
			}
		}
		return tour;
	}



}
