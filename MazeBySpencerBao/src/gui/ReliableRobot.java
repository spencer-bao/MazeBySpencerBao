package gui;

import generation.CardinalDirection;
import gui.Constants.UserInput;
/**
 * Responsibilities: imitate a human player. Can turn and move. Obtain information
 * about the absence or presence of walls in any given direction. 4 sensors to 
 * measure how far it is from a wall in that direction.
 * 
 * Collaborators: Robot, DistanceSensor
 * 
 * @author Spencer Bao
 * 
 */
public class ReliableRobot implements Robot{

	Controller controller;
	float batteryLevel = 3500;
	int odometer = 0;
	
	@Override
	public void setController(Controller controller){
		IllegalArgumentException e = new IllegalArgumentException();
		
		if (controller == null) {
			
			System.out.println("ReliableRobot's controller is null.");
			throw e;
			
		} else if (controller.currentState != controller.states[2]){
			System.out.println("ReliableRobot's controller is not in the playing state.");
			throw e;
		} else {
			this.controller = controller;
		}
		
	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		Exception e = new Exception();
		int[] currentPosition = controller.getCurrentPosition();
		if (currentPosition[0] >= controller.panel.getWidth()) {
			throw e;
		} else if (currentPosition[1] >= controller.panel.getHeight()) {
			throw e;
		} else {
			return currentPosition;
		}
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		return controller.getCurrentDirection();
	}

	@Override
	public float getBatteryLevel() {
		return batteryLevel;
	}

	@Override
	public void setBatteryLevel(float level) {
		batteryLevel = level;
	}

	@Override
	public float getEnergyForFullRotation() {
		return 12;
	}

	@Override
	public float getEnergyForStepForward() {
		return 6;
	}

	@Override
	public int getOdometerReading() {
		return odometer;
	}

	@Override
	public void resetOdometer() {
		odometer = 0;
	}

	@Override
	public void rotate(Turn turn) {
		float turnEnergy = getEnergyForFullRotation()/4;
		if (turnEnergy <= batteryLevel) {
			switch (turn) {
				case LEFT:
					controller.keyDown(UserInput.Left, 1);
				case RIGHT:
					controller.keyDown(UserInput.Right, 1);
				case AROUND:
					controller.keyDown(UserInput.Right, 1);
					batteryLevel -= turnEnergy;
					if (turnEnergy <= batteryLevel) {
						controller.keyDown(UserInput.Right, 1);
					}
			}
			batteryLevel -= turnEnergy;
		}
	}

	@Override
	public void move(int distance) {
		for (int i = 0; i < distance; i++) {
			if (getEnergyForStepForward() <= batteryLevel) {
				controller.keyDown(UserInput.Up, 1);
				batteryLevel -= getEnergyForStepForward();
			}			
		}
	}

	@Override
	public void jump() {
		if (getEnergyForStepForward() <= batteryLevel) {
			controller.keyDown(UserInput.Jump, 1);
			batteryLevel -= getEnergyForStepForward();
		}
	}

	@Override
	public boolean isAtExit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInsideRoom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasStopped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}

}
