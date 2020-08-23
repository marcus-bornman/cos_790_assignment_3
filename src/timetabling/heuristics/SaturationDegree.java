package timetabling.heuristics;

import timetabling.Solution;

/**
 * The examination with the least number of feasible periods on the timetable is scheduled first.
 */
public class SaturationDegree implements LowLevelHeuristic {
	static final char CHAR_REPRESENTATION = 's';

	@Override
	public Solution applyToSolution(Solution solution) {
		return null;
	}

	@Override
	public Character charRepresentation() {
		return CHAR_REPRESENTATION;
	}
}
