package com.marcusbornman.cos_790_assignment_2;

import distrgenalg.DistrGenAlg;
import uk.ac.qub.cs.itc2007.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	public static void main(String[] args) throws IOException {
		// Program arguments
		String parameterFilePath = args[0];
		String problemFilePath = args[1];
		long seed = Long.parseLong(args[2]);

		// Set up the problem instance
		Globals.randomGenerator = new Random(seed);
		ExamTimetablingProblem examTimetablingProblem = ExamTimetablingProblem.fromFile(problemFilePath);
		ExamTimetablingSolution initialSolution = generateInitialSolution(examTimetablingProblem);
		EvohypProblem evohypProblem = new EvohypProblem(examTimetablingProblem, initialSolution);

		// Set up the genetic algorithm
		DistrGenAlg genAlg = new DistrGenAlg(seed, PerturbativeHeuristicEngine.SUPPORTED_HEURISTICS, 4);
		genAlg.setParameters(parameterFilePath);
		genAlg.setProblem(evohypProblem);

		// Evolve the genetic algorithm
		genAlg.evolve();
	}

	private static ExamTimetablingSolution generateInitialSolution(ExamTimetablingProblem problem) {
		ExamTimetablingSolution currSolution = new ExamTimetablingSolution(problem, new ArrayList<>());
		for (Exam exam : problem.exams) {
			boolean scheduled = false;
			for (Period period : problem.periods) {
				for (Room room : problem.rooms) {
					Booking newBooking = new Booking(exam, period, room);
					List<Booking> newBookings = new ArrayList<>(currSolution.bookings);
					newBookings.add(newBooking);
					ExamTimetablingSolution newSolution = new ExamTimetablingSolution(problem, newBookings);

					if (newSolution.distanceToFeasibility() <= currSolution.distanceToFeasibility()) {
						currSolution = newSolution;
						scheduled = true;
						break;
					}
				}
				if (scheduled) break;
			}
			if (!scheduled) currSolution = scheduleRandomly(problem, currSolution, exam);
		}

		return currSolution;
	}

	private static ExamTimetablingSolution scheduleRandomly(ExamTimetablingProblem problem, ExamTimetablingSolution currSolution, Exam exam) {
		Period period = problem.periods.get(Globals.randomGenerator.nextInt(problem.periods.size()));
		Room room = problem.rooms.get(Globals.randomGenerator.nextInt(problem.rooms.size()));
		Booking booking = new Booking(exam, period, room);
		List<Booking> newBookings = new ArrayList<>(currSolution.bookings);
		newBookings.add(booking);
		currSolution = new ExamTimetablingSolution(problem, newBookings);
		return currSolution;
	}
}
