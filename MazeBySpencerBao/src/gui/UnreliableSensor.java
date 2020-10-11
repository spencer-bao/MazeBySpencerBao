package gui;

public class UnreliableSensor extends ReliableSensor implements Runnable{
	
	Thread thread = new Thread(this);
	long betweenFailureTime;
	long repairTime;
	boolean operational = true;
	
	public void start(int meanTimeBetweenFailures, int meanTimeToRepair) {
		betweenFailureTime = meanTimeBetweenFailures;
		repairTime  = meanTimeToRepair;
		thread.start();
	}
	
	@Override
	public void run() {
		while (thread != null) {
			try {
				operational = true;
				Thread.sleep(betweenFailureTime);
				operational = false;
				Thread.sleep(repairTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}

	public void stop() {
		thread = null;
	}
	
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		start(meanTimeBetweenFailures, meanTimeToRepair);
		
	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		stop();
		
	}

}

