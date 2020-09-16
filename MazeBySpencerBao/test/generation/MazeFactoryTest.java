package generation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MazeFactoryTest {

	
	
	/*Properties of a correct maze:
		A cell cannot have 4 walls.
		No fully enclosed spaces
		1 exit
		Each cell must be adjacent to one cell with 2 openings
		Exit can be reached from anywhere in the maze
	*/
	
	/**
	 * Test case: check if all cells have 3 walls or less.
	 * <p>
	 * Method: hasWall(int x, int y, CardinalDirection dir)
	 * <p>
	 * Correct behavior: each cell can only have a wall in at most 3 directions.
	 */
	@Test
	void testForFourWalls() {
		fail("Not yet implemented");
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
	
}
