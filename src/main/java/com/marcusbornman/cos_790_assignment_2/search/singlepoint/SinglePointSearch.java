package com.marcusbornman.cos_790_assignment_2.search.singlepoint;

import com.marcusbornman.cos_790_assignment_2.tools.HeuristicEngine;
import com.marcusbornman.cos_790_assignment_2.search.HeuristicSearch;
import uk.ac.qub.cs.itc2007.ExamTimetablingProblem;
import uk.ac.qub.cs.itc2007.ExamTimetablingSolution;

import java.util.ArrayList;

public class SinglePointSearch extends HeuristicSearch {
	public SinglePointSearch(ExamTimetablingProblem problem, ExamTimetablingSolution solution, long seed) {
		super(problem, solution, seed);
	}

	@Override
	public ExamTimetablingSolution execute() {
		char[] heuristics = HeuristicEngine.SUPPORTED_HEURISTICS.toCharArray();
		ExamTimetablingSolution currSolution = new ExamTimetablingSolution(problem, new ArrayList<>(initialSolution.bookings));

		for (int i = 0; i < problem.exams.size(); i++) {
			ExamTimetablingSolution bestSolution = null;
			for (char heuristic : heuristics) {
				ExamTimetablingSolution newSolution = heuristicEngine.applyToSolution(heuristic, currSolution);
				if (bestSolution == null || newSolution.distanceToFeasibility() < bestSolution.distanceToFeasibility()) {
					bestSolution = newSolution;
				}
			}

			currSolution = bestSolution;
		}

		return currSolution;
	}

	@Override
	public String toString() {
		return "singlepoint (seed=" + seed + ")";
	}
}
