package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;
import gui.Robot.Turn;
/**
 * Responsibilities: Uses knowledge of cell distances from exit to choose the next best position to move.
 * 
 * Collaborators: ReliableRobot, Maze, CardinalDirection
 * 
 * @author Spencer Bao
 *
 */
public class Wizard implements RobotDriver{

	ReliableRobot rob;
	Maze maze;
	
	@Override
	public void setRobot(Robot r) {
		rob = (ReliableRobot) r;
	}

	@Override
	public void setMaze(Maze maze) {
		this.maze = maze;	
	}

	@Override
	public boolean drive2Exit() throws Exception {
		int nextX;
		int nextY;
		CardinalDirection nextDirection;
		boolean finished = false;
		
		while (!finished) {
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
						break;
					}
				}
				return true;
			}
			
			nextX = maze.getNeighborCloserToExit(rob.getCurrentPosition()[0], rob.getCurrentPosition()[1])[0];
			nextY = maze.getNeighborCloserToExit(rob.getCurrentPosition()[0], rob.getCurrentPosition()[1])[1];
			
			nextDirection = CardinalDirection.getDirection(nextX - rob.getCurrentPosition()[0], 
															nextY - rob.getCurrentPosition()[1]);
			
//			System.out.println("currDirection: " + rob.getCurrentDirection().name());
//			System.out.println("nextDirection: " + nextDirection.name());
			switch (rob.getCurrentDirection()) {
			case North:
				switch (nextDirection) {
				case West:
					rob.rotate(Turn.RIGHT);
					break;
				case East:
					rob.rotate(Turn.LEFT);
					break;
				case South:
					rob.rotate(Turn.AROUND);
					break;
				case North:
					break;
				}
				break;
//				if (nextDirection.equals(CardinalDirection.West)){
//					rob.rotate(Turn.RIGHT);
//				} else if (nextDirection.equals(CardinalDirection.East)){
//					rob.rotate(Turn.LEFT);
//				} else if (nextDirection.equals(CardinalDirection.South)){
//					rob.rotate(Turn.AROUND);
//				}
//				break;
			case South:
				switch (nextDirection) {
				case West:
					rob.rotate(Turn.LEFT);
					break;
				case East:
					rob.rotate(Turn.RIGHT);
					break;
				case North:
					rob.rotate(Turn.AROUND);
					break;
				case South:
					break;
				}
				break;
				
//				if (nextDirection.equals(CardinalDirection.West)){
//					rob.rotate(Turn.LEFT);
//				} else if (nextDirection.equals(CardinalDirection.East)){
//					rob.rotate(Turn.RIGHT);
//				} else if (nextDirection.equals(CardinalDirection.North)){
//					rob.rotate(Turn.AROUND);
//				}
//				break;
			case East:
				switch (nextDirection) {
				case North:
					rob.rotate(Turn.RIGHT);
					break;
				case South:
					rob.rotate(Turn.LEFT);
					break;
				case West:
					rob.rotate(Turn.AROUND);
					break;
				case East:
					break;
				}
				break;
				
//				if (nextDirection.equals(CardinalDirection.North)){
//					rob.rotate(Turn.RIGHT);
//				} else if (nextDirection.equals(CardinalDirection.South)){
//					rob.rotate(Turn.LEFT);
//				} else if (nextDirection.equals(CardinalDirection.West)){
//					rob.rotate(Turn.AROUND);
//				}
//				break;
			case West:
				switch (nextDirection) {
				case South:
					rob.rotate(Turn.RIGHT);
					break;
				case North:
					rob.rotate(Turn.LEFT);
					break;
				case East:
					rob.rotate(Turn.AROUND);
					break;
				case West:
					break;
				}
				break;
				
//				if (nextDirection.equals(CardinalDirection.South)){
//					rob.rotate(Turn.RIGHT);
//					
//				} else if (nextDirection.equals(CardinalDirection.North)){
//					rob.rotate(Turn.LEFT);
//				} else if (nextDirection.equals(CardinalDirection.East)){
//					rob.rotate(Turn.AROUND);
//				}
//				break;
			default:
				break;
			}
//			System.out.println("Moving.");
			rob.move(1);

		}
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		int nextX;
		int nextY;
		CardinalDirection nextDirection;
		
		if (rob.hasStopped()) {
			throw new Exception("Not enough energy to drive to exit.");
		}
		
		
		nextX = maze.getNeighborCloserToExit(rob.getCurrentPosition()[0], rob.getCurrentPosition()[1])[0];
		nextY = maze.getNeighborCloserToExit(rob.getCurrentPosition()[0], rob.getCurrentPosition()[1])[1];
		
		nextDirection = CardinalDirection.getDirection(nextX - rob.getCurrentPosition()[0], 
														nextY - rob.getCurrentPosition()[1]);
		
		switch (rob.getCurrentDirection()) {
		case North:
			switch (nextDirection) {
			case West:
				rob.rotate(Turn.RIGHT);
			case East:
				rob.rotate(Turn.LEFT);
			case South:
				rob.rotate(Turn.AROUND);
			case North:
			}
		case South:
			switch (nextDirection) {
			case West:
				rob.rotate(Turn.LEFT);
			case East:
				rob.rotate(Turn.RIGHT);
			case North:
				rob.rotate(Turn.AROUND);
			case South:
			}
		case East:
			switch (nextDirection) {
			case North:
				rob.rotate(Turn.RIGHT);
			case South:
				rob.rotate(Turn.LEFT);
			case West:
				rob.rotate(Turn.AROUND);
			case East:
			}
		case West:
			switch (nextDirection) {
			case South:
				rob.rotate(Turn.RIGHT);
			case North:
				rob.rotate(Turn.LEFT);
			case East:
				rob.rotate(Turn.AROUND);
			case West:
			}
		}
		rob.move(1);
		if (rob.isAtExit()) {
			return true;
		} else{
			return false;
		}
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
