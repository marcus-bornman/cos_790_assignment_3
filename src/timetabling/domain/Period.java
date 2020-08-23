package timetabling.domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Models a period in which an exam can be scheduled.
 */
public class Period {
	final int number;
	final LocalDate date;
	final LocalTime time;
	final int duration;
	final int penalty;

	/**
	 * @param number - the identifier for the period.
	 * @param date - the date for the period.
	 * @param time - the time for the period.
	 * @param duration - the duration of the period.
	 * @param penalty - the penalty associated with the period.
	 */
	public Period(int number, LocalDate date, LocalTime time, int duration, int penalty) {
		this.number = number;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.penalty = penalty;
	}
}
