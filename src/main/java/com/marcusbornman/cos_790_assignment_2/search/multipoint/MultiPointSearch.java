package com.marcusbornman.cos_790_assignment_2.search.multipoint;

import com.marcusbornman.cos_790_assignment_2.search.HeuristicSearch;
import com.marcusbornman.cos_790_assignment_2.tools.HeuristicEngine;
import distrgenalg.DistrGenAlg;
import uk.ac.qub.cs.itc2007.ExamTimetablingProblem;
import uk.ac.qub.cs.itc2007.ExamTimetablingSolution;

public class MultiPointSearch extends HeuristicSearch {
	private final String parameterFilePath;

	public MultiPointSearch(ExamTimetablingProblem problem, ExamTimetablingSolution solution, long seed, String parameterFilePath) {
		super(problem, solution, seed);
		this.parameterFilePath = parameterFilePath;
	}

	@Override
	public ExamTimetablingSolution execute() {
		EvohypProblem evohypProblem = new EvohypProblem(problem, initialSolution, heuristicEngine);
		DistrGenAlg genAlg = new DistrGenAlg(seed, HeuristicEngine.SUPPORTED_HEURISTICS, 4);
		genAlg.setParameters(parameterFilePath);
		genAlg.setProblem(evohypProblem);
		EvohypSolution solution = (EvohypSolution) genAlg.evolve();
		return solution.solution;
	}

	@Override
	public String toString() {
		return "singlepoint (seed=" + seed + ")";
	}
}
