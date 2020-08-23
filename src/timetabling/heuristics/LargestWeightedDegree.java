package timetabling.heuristics;

import timetabling.Solution;

/**
 * The examination with the largest number of students involved in clashes is scheduled first.
 */
public class LargestWeightedDegree implements LowLevelHeuristic {
	static final char CHAR_REPRESENTATION = 'w';

	@Override
	public Solution applyToSolution(Solution solution) {
		return null;
	}

	@Override
	public Character charRepresentation() {
		return CHAR_REPRESENTATION;
	}
}
