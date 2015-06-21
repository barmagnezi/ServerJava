package algorithms.mazeGenerators;

import algorithms.demo.MazeSearchable;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.MazeManhhetenDistance;
/**
* The DFSMazeGenerator class create us random maze with the method generateMaze
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
* 
*/
public class RandomMazeGenerator extends CommonMazeGenerator {
	/**
	 * This method creates us maze with random maze
	 * @param rows The number of the rows in the maze
	 * @param cols The number of the Column in the maze
	 * @return The maze will create
	*/
	@Override
	public Maze generateMaze(int rows, int cols) {
		Maze m=new Maze(rows,cols);
		Searcher searcher=new AstarSearcher(new MazeManhhetenDistance());
		Searchable searchable=new MazeSearchable(m,m.getCell(0, 0),m.getCell(m.getRows()-1, m.getCols()-1),false,10,15);
			while (searcher.search(searchable).getSol()==null){
				int row = (int) (Math.random()*(rows)); 
				int col = (int) (Math.random()*(cols)); 
				int wall = (int) (Math.random()*2); 
				if (wall == 0 && row!=rows-1)
						m.getCell(row, col).setBottomWall(false);
				if (wall==1 && col!=cols-1)
						m.getCell(row, col).setRightWall(false);
			} 
				
		return m;
	}
	
}
