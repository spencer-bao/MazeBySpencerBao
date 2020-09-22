package generation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import generation.Order.Builder;

class MazeFactoryTest {

	
	
	/*Properties of a correct maze:
		A cell cannot have 4 walls.
		No fully enclosed spaces
		1 exit
		Each cell must be adjacent to one cell with 2 openings
		Exit can be reached from anywhere in the maze
	*/
	
	
	MazeFactory factory = new MazeFactory();
	StubOrder DFSorder = new StubOrder();
	
	@Before
	public void setUp() {
//		DFSorder = new StubOrder();
		DFSorder.builder = Builder.DFS;
		DFSorder.skillLevel = 2;
		DFSorder.isPerfect = true;
		factory.order(DFSorder);
		factory.waitTillDelivered();
	}
	
	
	/**
	 * Test case: check if all cells have 3 walls or less.
	 * <p>
	 * Method: hasWall(int x, int y, CardinalDirection dir)
	 * <p>
	 * Correct behavior: each cell can only have a wall in at most 3 directions.
	 */
	@Test
	void testForFourWalls() {
		Maze maze = DFSorder.getMaze();
		Floorplan floorplan = new Floorplan(maze.getWidth(), maze.getHeight());
		floorplan = maze.getFloorplan();
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++) {
				assertFalse(floorplan.hasWall(x, y, CardinalDirection.East) == true &&
							floorplan.hasWall(x, y, CardinalDirection.West) == true &&
							floorplan.hasWall(x, y, CardinalDirection.North) == true &&
							floorplan.hasWall(x, y, CardinalDirection.South) == true);
					
			}
		}
	}
		
		
	
	
	/**
	 * Test case: exit can be reached from anywhere in the maze.
	 * <p>
	 * Method: getDistanceValues(int x, int y)
	 * <p>
	 * Correct behavior: all cells return a number
	 */
	@Test
	void testCanReachExit() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test case: check if maze has any rooms or loops
	 * <p>
	 * Method: order.isPerfect()
	 * <p>
	 * Correct behavior: return true
	 */
	@Test
	void testNoRoomsOrLoops() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test case: check if maze only has one exit
	 * <p>
	 * Method: wallboard.getNeighborX() wallboard.getNeighborY()
	 * <p>
	 * Correct behavior: should return only one false when checking all wallboards.
	 */
	@Test
	void testOneExit() {
		fail("Not yet implemented");
	}
}
