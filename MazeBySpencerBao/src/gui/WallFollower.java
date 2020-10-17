package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;
import gui.Robot.Turn;

/**
 * Responsibilities: controls the robot through the maze using the left hand rule algorithm.
 * 
 * Collaborators: ReliableRobot, Controller, RobotDriver
 * 
 * @author Spencer Bao
 */
public class WallFollower implements RobotDriver{

	UnreliableRobot rob;
	Maze maze;
	
	
	@Override
	public void setRobot(Robot r) {
		rob = (UnreliableRobot) r;
	}

	@Override
	public void setMaze(Maze maze) {
		this.maze = maze;
	}
	
	// if reached end: end of program
	// else if there is no left wall: rotate counter clockwise 90 degrees and step forward
	// else if there is no front wall: step forward
	// else rotate clockwise 90 degrees
	@Override
	public boolean drive2Exit() throws Exception {
		boolean finished = false;
		int[] currentPosition;
		CardinalDirection currentDirection;
		
//		rob.startFailureAndRepairProcess(Direction.LEFT, UnreliableRobot.MEAN_TIME_BTWN_FAILURES, UnreliableRobot.MEAN_TIME_TO_REPAIR);
		while(!finished) {
//			System.out.println("operational: " + rob.leftSensor.operational);
			while (rob.leftSensor.operational == true) {
				currentPosition = rob.getCurrentPosition();
				currentDirection = rob.getCurrentDirection();
				if (rob.hasStopped()) {
					throw new Exception("Not enough energy to drive to exit.");
				}
				if (rob.isAtExit()) {
					for (Direction d : Direction.values()) {
						if (rob.distanceToObstacle(d) == Integer.MAX_VALUE) {
							switch (d) {
							case BACKWARD:
								rob.rotate(Turn.AROUND);
								break;
							case FORWARD:
								break;
							case LEFT:
								rob.rotate(Turn.LEFT);
								break;
							case RIGHT:
								rob.rotate(Turn.RIGHT);
								break;
							default:
								break;
							}
							rob.move(1);
							rob.stopFailureAndRepairProcess(Direction.LEFT);
							rob.stopFailureAndRepairProcess(Direction.FORWARD);
							rob.stopFailureAndRepairProcess(Direction.RIGHT);
							rob.stopFailureAndRepairProcess(Direction.BACKWARD);
							break;
						}
					}
					return true;
				} else if (!maze.hasWall(currentPosition[0], currentPosition[1], 
						currentDirection.rotateClockwise())) {					
					rob.rotate(Turn.LEFT);
					rob.move(1);
				} else if (!maze.hasWall(currentPosition[0], currentPosition[1], currentDirection)) {
					rob.move(1);
				} else {
					rob.rotate(Turn.RIGHT);
				}
			}
		}
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		int[] currentPosition;
		CardinalDirection currentDirection;
		
		currentPosition = rob.getCurrentPosition();
		currentDirection = rob.getCurrentDirection();
		if (rob.hasStopped()) {
			throw new Exception("Not enough energy to drive to exit.");
		}
		if (rob.isAtExit()) {
			for (Direction d : Direction.values()) {
				if (rob.distanceToObstacle(d) == Integer.MAX_VALUE) {
					switch (d) {
					case BACKWARD:
						rob.rotate(Turn.AROUND);
						break;
					case FORWARD:
						break;
					case LEFT:
						rob.rotate(Turn.LEFT);
						break;
					case RIGHT:
						rob.rotate(Turn.RIGHT);
						break;
					default:
						break;
					}
					rob.move(1);
					rob.stopFailureAndRepairProcess(Direction.LEFT);
					rob.stopFailureAndRepairProcess(Direction.FORWARD);
					rob.stopFailureAndRepairProcess(Direction.RIGHT);
					rob.stopFailureAndRepairProcess(Direction.BACKWARD);
					break;
				}
			}
			return true;
		} else if (!maze.hasWall(currentPosition[0], currentPosition[1], 
				currentDirection.rotateClockwise())) {					
			rob.rotate(Turn.LEFT);
			rob.move(1);
		} else if (!maze.hasWall(currentPosition[0], currentPosition[1], currentDirection)) {
			rob.move(1);
		} else {
			rob.rotate(Turn.RIGHT);
		}
		
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		return ReliableRobot.BATTERY_LEVEL - rob.getBatteryLevel();
	}

	@Override
	public int getPathLength() {
		return rob.getOdometerReading();
	
	}
}
