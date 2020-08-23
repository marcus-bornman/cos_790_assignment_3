package timetabling.heuristics;

import timetabling.Solution;

/**
 * An interface for Low-level heuristics of the Timetabling Problem.
 */
public interface LowLevelHeuristic {
	/**
	 * Retrieve a low-level heuristic from the character representation of the heuristic.
	 *
	 * @param heuristicChar - the character representation of the heuristic.
	 * @return an instance of the applicable heuristic.
	 */
	static LowLevelHeuristic fromChar(Character heuristicChar) {
		switch (heuristicChar) {
			case LargestDegree.CHAR_REPRESENTATION:
				return new LargestDegree();
			case LargestEnrollment.CHAR_REPRESENTATION:
				return new LargestEnrollment();
			case LargestWeightedDegree.CHAR_REPRESENTATION:
				return new LargestWeightedDegree();
			case SaturationDegree.CHAR_REPRESENTATION:
				return new SaturationDegree();
			default:
				throw new IllegalArgumentException("No low-level heuristic has been implemented with the string representation of `" + heuristicChar + "`.");
		}
	}

	/**
	 * Apply the heuristic to the given solution before returning it.
	 *
	 * @param solution - the solution to which to apply the heuristic.
	 * @return the updated instance of the solution.
	 */
	Solution applyToSolution(Solution solution);

	/**
	 * @return the character representation of the heuristic.
	 */
	Character charRepresentation();
}
