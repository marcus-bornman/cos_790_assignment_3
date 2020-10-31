package com.marcusbornman.cos_790_assignment_3.evohyp;

import org.moeaframework.problem.tsplib.TSPInstance;
import org.moeaframework.problem.tsplib.Tour;
import solution.Solution;

public class EvohypSolution extends Solution {
	private Object heuristic;

	private final Double fitness;

	public final Tour tour;

	/**
	 * @param tspInstance - the TSP for which this is a solution.
	 * @param tour        - a tour that represents a solution to tspInstance.
	 */
	public EvohypSolution(TSPInstance tspInstance, Tour tour) {
		this.tour = tour;
		this.fitness = tour.distance(tspInstance);
	}

	@Override
	public int fitter(Solution other) {
		return Double.compare(other.getFitness(), getFitness());
	}

	@Override
	public double getFitness() {
		return this.fitness;
	}

	@Override
	public Object getSoln() {
		return tour;
	}

	@Override
	public void setHeuristic(Object heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	public Object getHeuristic() {
		return this.heuristic;
	}
}
