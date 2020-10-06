package com.marcusbornman.cos_790_assignment_2.tools;

import uk.ac.qub.cs.itc2007.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class provides the means to build an initial solution the examination timetabling problem whilst
 * making an effort to avoid as many hard constraints as possible.
 */
public class InitialSolutionBuilder {
	private final Random randomGenerator;

	private final ExamTimetablingProblem problem;

	public InitialSolutionBuilder(ExamTimetablingProblem problem, long seed) {
		this.randomGenerator = new Random(seed);
		this.problem = problem;
	}


	/**
	 * @return a solution to the given problem. the solution schedules each exam in the first viable period and room
	 * which does not violate any hard constraints. If no such combination exists then the exam is scheduled in a random
	 * period and room.
	 */
	public ExamTimetablingSolution generateInitialSolution() {
		ExamTimetablingSolution currSolution = new ExamTimetablingSolution(problem, new ArrayList<>());
		for (Exam exam : problem.exams) {
			boolean scheduled = false;
			Room room = problem.rooms.get(randomGenerator.nextInt(problem.rooms.size()));

			for (Period period : problem.periods) {
				ExamTimetablingSolution newSolution = schedule(currSolution, exam, period, room);

				if (newSolution.distanceToFeasibility() <= currSolution.distanceToFeasibility()) {
					currSolution = newSolution;
					scheduled = true;
					break;
				}
			}
			if (!scheduled) currSolution = scheduleRandomly(currSolution, exam);
		}

		return currSolution;
	}

	private ExamTimetablingSolution scheduleRandomly(ExamTimetablingSolution currSolution, Exam exam) {
		Period period = problem.periods.get(randomGenerator.nextInt(problem.periods.size()));
		Room room = problem.rooms.get(randomGenerator.nextInt(problem.rooms.size()));
		currSolution = schedule(currSolution, exam, period, room);
		return currSolution;
	}

	private ExamTimetablingSolution schedule(ExamTimetablingSolution currSolution, Exam exam, Period period, Room room) {
		Booking newBooking = new Booking(exam, period, room);
		List<Booking> newBookings = new ArrayList<>(currSolution.bookings);
		newBookings.add(newBooking);
		return new ExamTimetablingSolution(problem, newBookings);
	}
}
