package timetabling.domain;

import java.util.List;

/**
 * Models a single exam that needs to be booked.
 */
public class Exam {
	final int number;
	final int durationInMinutes;
	final List<String> students;

	/**
	 * @param number - the identifier for the exam.
	 * @param durationInMinutes - the duration that the exam will last.
	 * @param students - the list of students partaking in the exam.
	 */
	public Exam(int number, int durationInMinutes, List<String> students) {
		this.number = number;
		this.durationInMinutes = durationInMinutes;
		this.students = students;
	}
}
