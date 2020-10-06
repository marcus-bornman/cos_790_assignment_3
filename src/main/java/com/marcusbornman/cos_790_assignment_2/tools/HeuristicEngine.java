package com.marcusbornman.cos_790_assignment_2.tools;

import uk.ac.qub.cs.itc2007.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A class for applying constructive Low-level heuristics to an examination timetabling problem to produce a solution
 * to the problem.
 */
public class HeuristicEngine {
	public static final String SUPPORTED_HEURISTICS = "ABCDE";

	private final ExamTimetablingProblem problem;

	private final Random randomGenerator;

	/**
	 * @param problem - the problem for which this engine is going to produce solutions.
	 */
	public HeuristicEngine(ExamTimetablingProblem problem, long seed) {
		this.problem = problem;
		this.randomGenerator = new Random(seed);
	}

	/**
	 * @param heuristicChar - the character representative of the heuristic to apply.
	 * @param solution      - the solution to which to apply the heuristic.
	 * @return a new solution with the given heuristic applied.
	 */
	public ExamTimetablingSolution applyToSolution(Character heuristicChar, ExamTimetablingSolution solution) {
		switch (heuristicChar) {
			case 'A':
				return rescheduleConflictingExam(solution);
			case 'B':
				return rescheduleOverbookedPeriod(solution);
			case 'C':
				return rescheduleTooShortPeriod(solution);
			case 'D':
				return reschedulePeriodConstraint(solution);
			case 'E':
				return rescheduleRoomConstraint(solution);
			default:
				throw new IllegalArgumentException(heuristicChar + " is not a supported heuristic character.");
		}
	}

	private ExamTimetablingSolution rescheduleConflictingExam(ExamTimetablingSolution solution) {
		for (Booking bookingA : solution.bookings) {
			for (Booking bookingB : solution.bookings) {
				if (bookingA.equals(bookingB)) continue;
				boolean doClash = bookingA.period.number == bookingB.period.number;
				boolean doShareStudents = problem.clashMatrix[bookingA.exam.number][bookingB.exam.number] > 0;
				if (doClash && doShareStudents) {
					solution = reschedule(bookingA, solution);
					solution = reschedule(bookingB, solution);
					return solution;
				}
			}
		}

		return solution;
	}

	private ExamTimetablingSolution rescheduleOverbookedPeriod(ExamTimetablingSolution solution) {
		for (Period period : problem.periods) {
			for (Room room : problem.rooms) {
				List<Booking> periodRoomBookings = solution.bookings.stream().filter(b -> b.period.number == period.number && b.room.number == room.number).collect(Collectors.toList());
				int numSeatsNeeded = periodRoomBookings.stream().mapToInt(b -> b.exam.students.size()).sum();
				if (numSeatsNeeded > room.capacity) {
					for (Booking booking : periodRoomBookings) {
						solution = reschedule(booking, solution);
					}
					return solution;
				}
			}
		}

		return solution;
	}

	private ExamTimetablingSolution rescheduleTooShortPeriod(ExamTimetablingSolution solution) {
		for (Booking booking : solution.bookings) {
			if (booking.exam.duration > booking.period.duration) {
				solution = reschedule(booking, solution);
				return solution;
			}
		}

		return solution;
	}

	private ExamTimetablingSolution reschedulePeriodConstraint(ExamTimetablingSolution solution) {
		for (PeriodHardConstraint constraint : problem.periodHardConstraints) {
			Booking bookingOne = solution.bookings.stream().filter(b -> b.exam.number == constraint.examOneNum).findFirst().orElse(null);
			Booking bookingTwo = solution.bookings.stream().filter(b -> b.exam.number == constraint.examTwoNum).findFirst().orElse(null);
			if (bookingOne == null || bookingTwo == null) continue;

			switch (constraint.constraintType) {
				case "EXAM_COINCIDENCE":
					if (problem.clashMatrix[constraint.examOneNum][constraint.examTwoNum] > 0) continue;
					if (bookingOne.period.number != bookingTwo.period.number) {
						solution = reschedule(bookingOne, solution);
						solution = reschedule(bookingTwo, solution);
						return solution;
					}
					break;
				case "EXCLUSION":
					if (bookingOne.period.number == bookingTwo.period.number) {
						solution = reschedule(bookingOne, solution);
						solution = reschedule(bookingTwo, solution);
						return solution;
					}
					break;
				case "AFTER":
					if (bookingOne.period.getDateTime().isAfter(bookingTwo.period.getDateTime())) {
						solution = reschedule(bookingOne, solution);
						solution = reschedule(bookingTwo, solution);
						return solution;
					}
					break;
			}
		}

		return solution;
	}

	private ExamTimetablingSolution rescheduleRoomConstraint(ExamTimetablingSolution solution) {
		for (RoomHardConstraint constraint : problem.roomHardConstraints) {
			if (constraint.constraintType.equals("ROOM_EXCLUSIVE")) {
				Booking booking = solution.bookings.stream().filter(b -> b.exam.number == constraint.examNum).findFirst().orElse(null);
				if (booking == null) continue;
				boolean isNotBookedAlone = solution.bookings.stream().anyMatch(b -> b.room.number == booking.room.number && b.period.number == booking.period.number);
				if (isNotBookedAlone) {
					solution = reschedule(booking, solution);
					return solution;
				}
			}
		}

		return solution;
	}

	private ExamTimetablingSolution reschedule(Booking oldBooking, ExamTimetablingSolution oldSolution) {
		// Determine random room from the rooms that have enough capacity
		List<Room> rooms = new ArrayList<>(problem.rooms);
		rooms.removeIf(r -> r.capacity < oldBooking.exam.students.size());
		Room room = rooms.get(randomGenerator.nextInt(rooms.size()));

		// Determine random period from the periods that are long enough and will not lead to any clashes; or, just any random period if no clash-free period could be found
		List<Period> periods = new ArrayList<>(problem.periods);
		periods.removeIf(p -> p.duration < oldBooking.exam.duration);
		periods.removeIf(p -> oldSolution.bookings.stream().filter(b -> b.period.equals(p) && b.room.equals(room)).anyMatch(b -> problem.clashMatrix[oldBooking.exam.number][b.exam.number] > 0));
		Period period = periods.isEmpty() ? problem.periods.get(randomGenerator.nextInt(problem.periods.size())) : periods.get(randomGenerator.nextInt(periods.size()));

		// Reschedule the exam to the room and period that have been determined
		Booking newBooking = new Booking(oldBooking.exam, period, room);
		List<Booking> newBookings = new ArrayList<>(oldSolution.bookings);
		newBookings.remove(oldBooking);
		newBookings.add(newBooking);

		return new ExamTimetablingSolution(problem, newBookings);
	}
}
