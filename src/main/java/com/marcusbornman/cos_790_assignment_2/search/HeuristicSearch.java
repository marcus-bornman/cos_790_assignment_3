package com.marcusbornman.cos_790_assignment_2.search;

import com.marcusbornman.cos_790_assignment_2.tools.HeuristicEngine;
import uk.ac.qub.cs.itc2007.ExamTimetablingProblem;
import uk.ac.qub.cs.itc2007.ExamTimetablingSolution;

public abstract class HeuristicSearch {
	protected final ExamTimetablingProblem problem;
	protected final ExamTimetablingSolution initialSolution;
	protected final long seed;
	protected final HeuristicEngine heuristicEngine;

	public HeuristicSearch(ExamTimetablingProblem problem, ExamTimetablingSolution initialSolution, long seed) {
		this.problem = problem;
		this.initialSolution = initialSolution;
		this.seed = seed;
		this.heuristicEngine = new HeuristicEngine(problem, seed);
	}

	public abstract ExamTimetablingSolution execute();
}
