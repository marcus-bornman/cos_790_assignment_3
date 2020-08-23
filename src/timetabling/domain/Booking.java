package timetabling.domain;

/**
 * Models a specific booking for an exam.
 */
public class Booking {
	final int examNum;
	final int timeSlotNum;
	final int roomNum;

	/**
	 * @param examNum - the number of the booked exam.
	 * @param timeSlotNum - the time slot number of the booked exam.
	 * @param roomNum - the room number of the booked exam.
	 */
	public Booking(int examNum, int timeSlotNum, int roomNum) {
		this.examNum = examNum;
		this.timeSlotNum = timeSlotNum;
		this.roomNum = roomNum;
	}
}
