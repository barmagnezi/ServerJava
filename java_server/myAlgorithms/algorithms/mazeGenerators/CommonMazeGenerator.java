package algorithms.mazeGenerators;
/**
* The CommonMazeGenerator class
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
*/
public abstract class CommonMazeGenerator implements MazeGenerator  {
	public abstract Maze generateMaze(int rows, int cols);
}
