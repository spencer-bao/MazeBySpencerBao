package gui;
/**
 * Responsibilities: Instantiates the sensors and tells them when to work or fail.
 * <p>
 * Collaborators: UnreliableSensor, WallFollower
 * 
 * @author Spencer Bao
 *
 */

public class UnreliableRobot extends ReliableRobot{
	public static final int MEAN_TIME_TO_REPAIR = 2000; // time in millisec
	public static final int MEAN_TIME_BTWN_FAILURES = 4000; // time in millisec
	
	DistanceSensor forwardSensor;
	DistanceSensor leftSensor;
	DistanceSensor rightSensor;
	DistanceSensor backwardSensor;
	
	/**
	 * Constructs the UnreliableRobot with 4 distance sensors that is by default reliable but can be changed by the user.
	 * 
	 * @param flrb follows the -r tag and tells whether the forward, left, right, and backward sensors should
	 * contain reliable or unreliable sensors
	 */
	public UnreliableRobot(String flrb) {
		String[] flrbSplit = new String[4];
		if (flrb == null) {
			for (int i = 0; i < flrbSplit.length; i++) {
				flrbSplit[i] = "1";
			}
		} else {
			flrbSplit = flrb.split("");
		}
		
		if (flrbSplit[0].equals("1")) {
			forwardSensor 	= new ReliableSensor();
		} else if (flrbSplit[0].equals("0")) {
			forwardSensor 	= new UnreliableSensor();
		} else {
			throw new UnsupportedOperationException();
		}
		
		if (flrbSplit[1].equals("1")) {
			leftSensor 	= new ReliableSensor();
		} else if (flrbSplit[1].equals("0")) {
			leftSensor 	= new UnreliableSensor();
		} else {
			throw new UnsupportedOperationException();
		}
		
		if (flrbSplit[2].equals("1")) {
			rightSensor 	= new ReliableSensor();
		} else if (flrbSplit[2].equals("0")) {
			rightSensor 	= new UnreliableSensor();
		} else {
			throw new UnsupportedOperationException();
		}
		
		if (flrbSplit[3].equals("1")) {
			backwardSensor 	= new ReliableSensor();
		} else if (flrbSplit[3].equals("0")) {
			backwardSensor 	= new UnreliableSensor();
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		switch (direction) {
		case BACKWARD:
			backwardSensor.startFailureAndRepairProcess(MEAN_TIME_BTWN_FAILURES, MEAN_TIME_TO_REPAIR);
			break;
		case FORWARD:
			forwardSensor.startFailureAndRepairProcess(MEAN_TIME_BTWN_FAILURES, MEAN_TIME_TO_REPAIR);
			break;
		case LEFT:
			leftSensor.startFailureAndRepairProcess(MEAN_TIME_BTWN_FAILURES, MEAN_TIME_TO_REPAIR);
			break;
		case RIGHT:
			rightSensor.startFailureAndRepairProcess(MEAN_TIME_BTWN_FAILURES, MEAN_TIME_TO_REPAIR);
			break;
		}
		
	}

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		switch (direction) {
		case BACKWARD:
			backwardSensor.stopFailureAndRepairProcess();
			break;
		case FORWARD:
			forwardSensor.stopFailureAndRepairProcess();
			break;
		case LEFT:
			leftSensor.stopFailureAndRepairProcess();
			break;
		case RIGHT:
			rightSensor.stopFailureAndRepairProcess();
			break;
		}
		
	}
}
