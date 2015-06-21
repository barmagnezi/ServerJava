package TheBrain;

import java.util.concurrent.Callable;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MazeGenerator;

/**
* The MazeCallable class implements Callable for running in threads.
* Generates a maze with threads.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public class MazeCallable implements Callable<Maze>{
	MazeGenerator mg;
	int rows;
	int cols;
	Maze m;
	
	public MazeCallable(MazeGenerator mGenerator, int col, int row) {
		this.mg=mGenerator;
		this.cols=col;
		this.rows=row;
	}
	@Override
	public Maze call() throws Exception {
		m=mg.generateMaze(rows, cols);
		return this.m;
	}
}
