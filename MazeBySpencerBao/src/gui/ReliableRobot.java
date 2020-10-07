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
	
	ReliableSensor sensor = new ReliableSensor();
	
	private boolean hasStopped = false;
	
//	public int[] currentPosition;

	@Override
	public void setController(Controller controller){
//		IllegalArgumentException e = new IllegalArgumentException();
//		
//		if (controller == null) {
//			
//			System.out.println("ReliableRobot's controller is null.");
//			throw e;
//			
//		} else if (controller.currentState != controller.states[2]){
//			System.out.println("ReliableRobot's controller is not in the playing state.");
//			throw e;
//		} else {
			this.controller = controller;
//		}
		
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
//			System.out.println("Before rotate: " + getCurrentDirection().name());
			switch (turn) {
				case LEFT:
					controller.keyDown(UserInput.Left, 1);
					break;
				case RIGHT:
					controller.keyDown(UserInput.Right, 1);
					break;
				case AROUND:
					controller.keyDown(UserInput.Right, 1);
					batteryLevel[0] -= ROTATE_ENERGY;
					if (ROTATE_ENERGY <= batteryLevel[0]) {
						controller.keyDown(UserInput.Right, 1);
					} else {
						hasStopped = true;
						batteryLevel[0] = 0;
					}
					break;
			}
//			System.out.println("After rotate: " + getCurrentDirection().name());
			batteryLevel[0] -= ROTATE_ENERGY;
		} else {
			hasStopped = true;
			batteryLevel[0] = 0;
		}
	}

	@Override
	public void move(int distance) {
		try {
			for (int i = 0; i < distance; i++) {
				if (getEnergyForStepForward() <= batteryLevel[0] && hasStopped() == false) {
					if (controller.getMazeConfiguration().getFloorplan().hasWall(getCurrentPosition()[0], 
							getCurrentPosition()[1], getCurrentDirection())) { // if there is a wall, stop
						hasStopped = true;
						break;
					}
					controller.keyDown(UserInput.Up, 1);
//					currentPosition = controller.getCurrentPosition();
					batteryLevel[0] -= STEP_ENERGY;
					odometer += 1;
				} else {
					hasStopped = true;
					batteryLevel[0] = 0;
					break;
				}
			} 
		}catch (Exception e) {
				System.out.println("move(): position out of maze.");
		}
	}

	@Override
	public void jump() {
		if (40 <= batteryLevel[0] && hasStopped() == false) {
			controller.keyDown(UserInput.Jump, 1);
			batteryLevel[0] -= JUMP_ENERGY;
			odometer += 1;
		} else {
			hasStopped = true;
			batteryLevel[0] = 0;
		}
	}

	@Override
	public boolean isAtExit() {
		int[] current_position;
		try {
			current_position = getCurrentPosition();
			if (controller.getMazeConfiguration().getDistanceToExit(current_position[0], 
					current_position[1]) == 1) {
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
		if (getBatteryLevel() <= 0) {
			hasStopped = true;
			return true;
		} 
		else if (hasStopped == true) {
			return true;
		} 
		else {
			return false;
		}

	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		
		CardinalDirection senseDirection = getCurrentDirection();
		System.out.println("senseDirection: " + senseDirection.name());
		switch (direction){
			case BACKWARD:
				senseDirection = senseDirection.oppositeDirection();
				break;
			case LEFT:
//				senseDirection = senseDirection.oppositeDirection();
				senseDirection = senseDirection.rotateClockwise();
//				System.out.println("senseDirection after: " + senseDirection.name());
				break;
			case RIGHT:
				senseDirection = senseDirection.oppositeDirection();
				senseDirection = senseDirection.rotateClockwise();
//				System.out.println("senseDirection after: " + senseDirection.name());
				break;
			case FORWARD:
				break;
		}	
		
		try {
			sensor.setMaze(controller.getMazeConfiguration());
			return sensor.distanceToObstacle(getCurrentPosition(), senseDirection, batteryLevel);
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
		throw new UnsupportedOperationException("Not implemented.");
		
	}

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Not implemented.");
		
	}

}
