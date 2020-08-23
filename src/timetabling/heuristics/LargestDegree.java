package timetabling.heuristics;

import timetabling.Solution;

/**
 * The examination with the most clashes is scheduled first.
 */
public class LargestDegree implements LowLevelHeuristic {
	static final char CHAR_REPRESENTATION = 'l';

	@Override
	public Solution applyToSolution(Solution solution) {
		return null;
	}

	@Override
	public Character charRepresentation() {
		return CHAR_REPRESENTATION;
	}
}
