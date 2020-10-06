package com.marcusbornman.cos_790_assignment_2;

import problemdomain.ProblemDomain;
import uk.ac.qub.cs.itc2007.ExamTimetablingProblem;
import uk.ac.qub.cs.itc2007.ExamTimetablingSolution;

import java.util.ArrayList;

public class EvohypProblem extends ProblemDomain {
	public final ExamTimetablingProblem problem;

	private final ExamTimetablingSolution initialSolution;

	/**
	 * The constructor for the ExamTimetablingProblem class.
	 *
	 * @param problem         - the examination timetabling problem instance that needs to be solved.
	 * @param initialSolution - the initial solution which need to be improved.
	 */
	public EvohypProblem(ExamTimetablingProblem problem, ExamTimetablingSolution initialSolution) {
		this.problem = problem;
		this.initialSolution = initialSolution;
	}

	/**
	 * This method takes the heuristic combination heuristicComb evolved by
	 * the genetic algorithm in the package GenAlg or DistrGenAlg as input. The
	 * string specified in heuristicComb is used to construct a solution to the
	 * problem and returns an object of type ExamTimetablingSolution. An instance of the type
	 * ExamTimetablingSolution combines the objective value or function of the objective value
	 * that will be used as a fitness value for each chromosome by the genetic
	 * algorithm and the solution created using the evolved string of heuristics.
	 *
	 * @param heuristicComb - the heuristic combination evolved by the genetic
	 *                      algorithm in the package GenAlg or DistrGenAlg.
	 * @return an object of type ExamTimetablingSolution.
	 */
	@Override
	public EvohypSolution evaluate(String heuristicComb) {
		char[] heuristics = heuristicComb.toCharArray();
		ExamTimetablingSolution currSolution = new ExamTimetablingSolution(problem, new ArrayList<>(initialSolution.bookings));
		PerturbativeHeuristicEngine perturbativeHeuristicEngine = new PerturbativeHeuristicEngine(problem);

		for (int i = 0; i < problem.exams.size(); i++) {
			char heuristic = heuristics[(i % heuristics.length)];
			currSolution = perturbativeHeuristicEngine.applyToSolution(heuristic, currSolution);
		}

		return new EvohypSolution(currSolution);
	}
}
