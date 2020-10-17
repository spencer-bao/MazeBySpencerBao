package gui;

/**
 * Responsibilities: Starts the thread if the controller is in the playing state.
 * Unreliable sensors only work if operational == true. The thread switches operational to true and false.
 * 
 * Collaborators: ReliableSensor, Runnable, UnreliableRobot, WallFollower
 * 
 * @author Spencer Bao
 * 
 */
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
		while (thread != null && controller.currentState == controller.states[2]) {
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

