package generation;

public class StubOrder implements Order{
	
	Builder builder;
	int skillLevel;
	boolean isPerfect;
	int seed;
	Maze maze;
	
	@Override
	public int getSkillLevel() {
		return skillLevel;
	}

	@Override
	public Builder getBuilder() {
		return builder;
	}

	@Override
	public boolean isPerfect() {
		return isPerfect;
	}

	@Override
	public int getSeed() {
		return seed;
	}

	public Maze getMaze(Maze mazeConfig) {
		return mazeConfig;
	}
	
	@Override
	public void deliver(Maze mazeConfig) {
		getMaze(mazeConfig);
	}

	@Override
	public void updateProgress(int percentage) {

	}

}
