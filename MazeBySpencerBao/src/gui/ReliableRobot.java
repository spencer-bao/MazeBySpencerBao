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
		if (turnEnergy <= batteryLevel && hasStopped() == false) {
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
					} else {
						batteryLevel = 0;
					}
			}
			batteryLevel -= turnEnergy;
		} else {
			batteryLevel = 0;
		}
	}

	@Override
	public void move(int distance) {
		for (int i = 0; i < distance; i++) {
			if (getEnergyForStepForward() <= batteryLevel && hasStopped() == false) {
				controller.keyDown(UserInput.Up, 1);
				batteryLevel -= getEnergyForStepForward();
			} else {
				batteryLevel = 0;
				break;
			}
		}
	}

	@Override
	public void jump() {
		if (40 <= batteryLevel && hasStopped() == false) {
			controller.keyDown(UserInput.Jump, 1);
			batteryLevel -= 40;
		} else {
			batteryLevel = 0;
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
		
		try {
			int[] currentPosition = getCurrentPosition();
			CardinalDirection currentDirection = getCurrentDirection();
			int dx;
			int dy;
			int distance = 0;
			
			switch(direction){		
				case BACKWARD:
					currentDirection.oppositeDirection();	
				case RIGHT:
					currentDirection.rotateClockwise();
				case LEFT:
					currentDirection.oppositeDirection();
					currentDirection.rotateClockwise();
				case FORWARD:
			}
			dx = currentDirection.getDirection()[0];
			dy = currentDirection.getDirection()[1];
			while (controller.getMazeConfiguration().getFloorplan().
					hasNoWall(currentPosition[0], currentPosition[1], currentDirection)){
				distance += 1;
				currentPosition[0] += dx;
				currentPosition[1] += dy;
			}
			return distance;	
		} catch (Exception e) {
			System.out.println("distanceToObstacle(): error getting current position.");
			e.printStackTrace();
		}
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
