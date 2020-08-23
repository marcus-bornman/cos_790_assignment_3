package timetabling.heuristics;

import timetabling.Solution;

/**
 * Largest enrollment (e) - The examination with the largest number of students is scheduled first.
 */
public class LargestEnrollment implements LowLevelHeuristic {
	static final char CHAR_REPRESENTATION = 'e';

	@Override
	public Solution applyToSolution(Solution solution) {
		return null;
	}

	@Override
	public Character charRepresentation() {
		return CHAR_REPRESENTATION;
	}
}
