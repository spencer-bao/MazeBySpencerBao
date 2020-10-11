package gui;
/**
 * Responsibilities: Instantiates the sensors and tells them when to work or fail.
 * <p>
 * Colaborators: UnreliableSensor, Wallfollower
 * @author spencer
 *
 */

public class UnreliableRobot extends ReliableRobot{
	public static final int MEAN_TIME_TO_REPAIR = 2;
	public static final int MEAN_TIME_BTWN_FAILURES = 4;
	
	UnreliableSensor fowardSensor 	= new UnreliableSensor();
	UnreliableSensor backwardSensor = new UnreliableSensor();
	UnreliableSensor leftSensor 	= new UnreliableSensor();
	UnreliableSensor rightSensor 	= new UnreliableSensor();
	
	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not implemented.");
		
	}

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not implemented.");
		
	}
}
