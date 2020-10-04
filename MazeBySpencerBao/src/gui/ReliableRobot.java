package gui;

import generation.CardinalDirection;
import gui.Constants.UserInput;
/**
 * Responsibilities: imitate a human player. Can turn and move. Obtain information
 * about the absence or presence of walls in any given direction. 4 sensors to 
 * measure how far it is from a wall in that direction.
 * 
 * Collaborators: Robot, DistanceSensor, Controller, Maze, Floorplan
 * 
 * @author Spencer Bao
 * 
 */
public class ReliableRobot implements Robot{
	
	public static final int BATTERY_LEVEL = 3500;
	public static final int STEP_ENERGY = 6;
	public static final int ROTATE_ENERGY = 3;
	public static final int JUMP_ENERGY = 6;
	
	Controller controller;
	float[] batteryLevel = {BATTERY_LEVEL};
	int odometer = 0;
	ReliableSensor sensor;
	
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
		int[] currentPosition = controller.getCurrentPosition();
		if (currentPosition[0] >= controller.panel.getWidth()) {
			throw new Exception("Current x position is outside maze.");
		} else if (currentPosition[1] >= controller.panel.getHeight()) {
			throw new Exception("Current y position is outside maze.");
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
		return batteryLevel[0];
	}

	@Override
	public void setBatteryLevel(float level) {
		batteryLevel[0] = level;
	}

	@Override
	public float getEnergyForFullRotation() {
		return ROTATE_ENERGY * 4;
	}

	@Override
	public float getEnergyForStepForward() {
		return STEP_ENERGY;
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

		if (ROTATE_ENERGY <= batteryLevel[0] && hasStopped() == false) {
			switch (turn) {
				case LEFT:
					controller.keyDown(UserInput.Left, 1);
				case RIGHT:
					controller.keyDown(UserInput.Right, 1);
				case AROUND:
					controller.keyDown(UserInput.Right, 1);
					batteryLevel[0] -= ROTATE_ENERGY;
					if (ROTATE_ENERGY <= batteryLevel[0]) {
						controller.keyDown(UserInput.Right, 1);
					} else {
						batteryLevel[0] = 0;
					}
			}
			batteryLevel[0] -= ROTATE_ENERGY;
		} else {
			batteryLevel[0] = 0;
		}
	}

	@Override
	public void move(int distance) {
		for (int i = 0; i < distance; i++) {
			if (getEnergyForStepForward() <= batteryLevel[0] && hasStopped() == false) {
				controller.keyDown(UserInput.Up, 1);
				batteryLevel[0] -= STEP_ENERGY;
			} else {
				batteryLevel[0] = 0;
				break;
			}
		}
	}

	@Override
	public void jump() {
		if (40 <= batteryLevel[0] && hasStopped() == false) {
			controller.keyDown(UserInput.Jump, 1);
			batteryLevel[0] -= JUMP_ENERGY;
		} else {
			batteryLevel[0] = 0;
		}
	}

	@Override
	public boolean isAtExit() {
		int[] current_position;
		try {
			current_position = getCurrentPosition();
			if (controller.getMazeConfiguration().getDistanceToExit(current_position[0], current_position[1]) == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("isAtExit(): error getting current position.");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isInsideRoom() {		
		int[] current_position;
		try {
			current_position = getCurrentPosition();
			return controller.getMazeConfiguration().getFloorplan().isInRoom(current_position[0], current_position[1]);
		} catch (Exception e) {
			System.out.println("isInsideRoom(): error getting current position.");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean hasStopped() {
		int[] current_position;
		
		try {
			current_position = getCurrentPosition();
			if (getOdometerReading() <= 0) {
				return true;
			} else if (controller.getMazeConfiguration().getFloorplan().
					hasWall(current_position[0], current_position[1], getCurrentDirection())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("hasStopped(): error getting current position.");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		
		CardinalDirection currentDirection = getCurrentDirection();
		switch (direction){

			case BACKWARD:
				currentDirection = currentDirection.oppositeDirection();
			case LEFT:
				currentDirection = currentDirection.oppositeDirection();
				currentDirection = currentDirection.rotateClockwise();
			case RIGHT:
				currentDirection = currentDirection.rotateClockwise();
			case FORWARD:
			default:
			break;
		}
		
		try {
			return sensor.distanceToObstacle(getCurrentPosition(), currentDirection, batteryLevel);
		} catch (Exception e) {
			System.out.println("distanceToObstacle(): error getting current position.");
			e.printStackTrace();
		} 
		return 0;
	}

	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		
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
