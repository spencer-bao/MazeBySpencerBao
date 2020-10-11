package gui;

public class UnreliableSensor extends ReliableSensor implements Runnable{
	
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Did not implement.");
		
	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Did not implement.");
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
