package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;
/**
 * Responsibilities: Calculates the distance to obstacle from any direction.
 * 
 * Collaborators: Controller, Maze, Direction
 * 
 * @author Spencer Bao
 *
 */
class ReliableSensor implements DistanceSensor{
	
	Controller controller = new Controller();
	Maze maze;
	Direction currentDirection;
	public static final int SENSE_ENERGY = 1;
	
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		if (powersupply[0] < SENSE_ENERGY) {
			throw new Exception("Not enough energy to use sensor.");
		}
		int[] position = currentPosition;
		CardinalDirection direction = currentDirection;
		int dx;
		int dy;
		int distance = 0;
		direction = currentDirection;
		
		dx = direction.getDirection()[0];
		dy = direction.getDirection()[1];
		while (maze.getFloorplan().
				hasNoWall(position[0], position[1], direction)){
			if (maze.getFloorplan().
					isExitPosition(position[0], position[1])) {
				return Integer.MAX_VALUE;
			}
			distance += 1;
			position[0] += dx;
			position[1] += dy;
			
		}
		return distance;	
	}

	@Override
	public void setMaze(Maze maze) {
		this.maze = maze;
		
	}

	@Override
	public void setSensorDirection(Direction mountedDirection) {
		currentDirection = mountedDirection;
	}

	@Override
	public float getEnergyConsumptionForSensing() {
		return SENSE_ENERGY;
	}

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Did not implement.");
		
	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Did not implement.");
		
	}

}
