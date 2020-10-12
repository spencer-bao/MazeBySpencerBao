package gui;
/**
 * Responsibilities: Instantiates the sensors and tells them when to work or fail.
 * <p>
 * Collaborators: UnreliableSensor, WallFollower
 * @author Spencer Bao
 *
 */

public class UnreliableRobot extends ReliableRobot{
	public static final int MEAN_TIME_TO_REPAIR = 2000; // time in millisec
	public static final int MEAN_TIME_BTWN_FAILURES = 4000; // time in millisec
	
	UnreliableSensor forwardSensor 	= new UnreliableSensor();
	UnreliableSensor backwardSensor = new UnreliableSensor();
	UnreliableSensor leftSensor 	= new UnreliableSensor();
	UnreliableSensor rightSensor 	= new UnreliableSensor();
	
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
