package gui;

import generation.Maze;
import gui.Robot.Direction;

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
		boolean finished = false;
		
		while (!finished) {
			if (rob.hasStopped()) {
				throw new Exception("Not enough energy to drive to exit.");
			}
			if (rob.isAtExit()) {
				return true;
			}
			
			nextX = maze.getNeighborCloserToExit(rob.getCurrentPosition()[0], rob.getCurrentPosition()[1])[0];
			nextY = maze.getNeighborCloserToExit(rob.getCurrentPosition()[0], rob.getCurrentPosition()[1])[1];
			
			for (Direction d : Direction.values()) {
				
			}
			
		}
		
		
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return 0;
	}

}
