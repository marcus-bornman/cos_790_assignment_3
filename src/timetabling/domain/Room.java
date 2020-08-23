package timetabling.domain;

/**
 * Models a room in which an exam can be scheduled.
 */
public class Room {
	final int number;
	final int capacity;
	final int penalty;

	/**
	 * @param number - the identifier for the room.
	 * @param capacity - the number of students that a room can hold.
	 * @param penalty - the penalty associated with a room.
	 */
	public Room(int number, int capacity, int penalty) {
		this.number = number;
		this.capacity = capacity;
		this.penalty = penalty;
	}
}
