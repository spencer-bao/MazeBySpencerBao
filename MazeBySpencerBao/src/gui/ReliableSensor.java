package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;

class ReliableSensor implements DistanceSensor{
	
	Controller controller = new Controller();
	Maze maze = controller.getMazeConfiguration();

	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		
		int[] position = currentPosition;
		CardinalDirection direction = currentDirection;
		int dx;
		int dy;
		int distance = 0;
		direction = currentDirection;
		
		dx = direction.getDirection()[0];
		dy = direction.getDirection()[1];
		while (controller.getMazeConfiguration().getFloorplan().
				hasNoWall(position[0], position[1], direction)){
			if (controller.getMazeConfiguration().getFloorplan().
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getEnergyConsumptionForSensing() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}

}
