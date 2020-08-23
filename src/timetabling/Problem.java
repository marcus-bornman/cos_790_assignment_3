package timetabling;

import problemdomain.ProblemDomain;
import timetabling.domain.*;
import timetabling.heuristics.LowLevelHeuristic;

import java.util.List;

public class Problem extends ProblemDomain {
	final List<Exam> exams;

	final List<Period> periods;

	final List<Room> rooms;

	final List<PeriodHardConstraint> periodHardConstraints;

	final List<RoomHardConstraint> roomHardConstraints;

	final List<InstitutionalWeighting> institutionalWeightings;

	/**
	 * The constructor for the Problem class.
	 *
	 * @param exams                   - The exams that need to be scheduled.
	 * @param periods                 - The periods in which exams can be scheduled.
	 * @param rooms                   - The rooms in which exams can be scheduled.
	 * @param periodHardConstraints   - The set of hard constraints for periods.
	 * @param roomHardConstraints     - The set of hard constraints for rooms.
	 * @param institutionalWeightings - The set of institutional weightings which serve as soft constraints.
	 */
	public Problem(List<Exam> exams, List<Period> periods, List<Room> rooms, List<PeriodHardConstraint> periodHardConstraints, List<RoomHardConstraint> roomHardConstraints, List<InstitutionalWeighting> institutionalWeightings) {
		this.exams = exams;
		this.periods = periods;
		this.rooms = rooms;
		this.periodHardConstraints = periodHardConstraints;
		this.roomHardConstraints = roomHardConstraints;
		this.institutionalWeightings = institutionalWeightings;
	}

	/**
	 * This method takes the heuristic combination heuristicComb evolved by
	 * the genetic algorithm in the package GenAlg or DistrGenAlg as input. The
	 * string specified in heuristicComb is used to construct a solution to the
	 * problem and returns an object of type InitialSoln. An instance of the type
	 * InitialSoln combines the objective value or function of the objective value
	 * that will be used as a fitness value for each chromosome by the genetic
	 * algorithm and the solution created using the evolved string of heuristics.
	 *
	 * @param heuristicComb - the heuristic combination evolved by the genetic
	 *                      algorithm in the package GenAlg or DistrGenAlg.
	 * @return an object of type InitialSoln.
	 */
	@Override
	public Solution evaluate(String heuristicComb) {
		Solution solution = new Solution();

		for (char character : heuristicComb.toCharArray()) {
			LowLevelHeuristic lowLevelHeuristic = LowLevelHeuristic.fromChar(character);
			solution = lowLevelHeuristic.applyToSolution(solution);
		}

		return solution;
	}
}
