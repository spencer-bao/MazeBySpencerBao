package generation;

import java.util.Random;

public class MazeBuilderEller extends MazeBuilder implements Runnable{

	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	
	@Override
	protected void generatePathways() {
		// assign each cell in a row to a number
		// make a 2d array corresponding to each cell
		// have a set counter
		int[][] set = new int[height][width]; // [rows, col]
		int set_counter = 1;
		for (int i = 0; i < width; i++) {
			set[0][i] = set_counter;
			set_counter++;
		}
		
		// join adjacent cells, they merge into the same set
		Random random = new Random();
		int x = 0;
		int y = 0;
		
		CardinalDirection cd = CardinalDirection.East;
		floorplan.setCellAsVisited(x, y); 
		Wallboard wallboard = new Wallboard(x, y, cd);
		
		for (int i = 0; i < height - 1; i++) {
			cd = CardinalDirection.East;
			wallboard.setLocationDirection(x, y, cd);
			for (int j = 0; j < width- 1; j++) { // if not a load-bearing wall
				if (random.nextInt(2) == 0) { // randomly delete wallboards
					floorplan.deleteWallboard(wallboard);
					set[y][x+1] = set[y][x]; // merged cells belong to same set
				}
				floorplan.setCellAsVisited(x, y); 
				x++;
				}
			y++; // to next row
			x = 0;
		// random vertical connections, at least 1 per set, assign them to the set they connect to
		// flesh out next row, assigning remaining cells to its own set
			cd = CardinalDirection.North;
			wallboard.setLocationDirection(x, y, cd);
			for (int j = 0; j < width - 1; j++) {
				if (random.nextInt(2) == 0) {
					floorplan.deleteWallboard(wallboard);
					set[y][x] = set[y-1][x];
				} else {
					set[y][x] = set_counter;
					set_counter++;
				}
				floorplan.setCellAsVisited(x, y);
				x++;
			}
			x = 0;
		
		}
		
		for (int j = 0; j < width - 1; j++) {
			floorplan.deleteWallboard(wallboard);
			floorplan.setCellAsVisited(x, y);
			x++;
		}
		
		
	}
}
