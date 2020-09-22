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
	
	public void setBuilder(Builder builder) {
		this.builder = builder;
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

	public Maze getMaze() {
		return maze;
	}
	
	@Override
	public void deliver(Maze mazeConfig) {
		this.maze = mazeConfig;
	}

	@Override
	public void updateProgress(int percentage) {

	}

}
