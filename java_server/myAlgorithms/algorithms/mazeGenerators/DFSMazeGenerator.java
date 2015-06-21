package algorithms.mazeGenerators;

import java.util.List;

/**
* The DFSMazeGenerator class create us maze with DFS algorithm with the method generateMaze
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
* 
*/
public class DFSMazeGenerator implements MazeGenerator {
	private boolean[][] visits;
	private Maze maze;
	int pr=1;
	int rec=0;
	/**
	 * This method creates us maze with DFS algorithm
	 * @param rows The number of the rows in the maze
	 * @param cols The number of the Column in the maze
	 * @return The maze will create
	*/
	@Override
	public Maze generateMaze(int rows, int cols) {
		visits=new boolean[rows][cols];
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				visits[i][j]=false;
		maze = new Maze(rows, cols);
		visits[0][0]=true;
		generateMazeRec(maze.getCell(0, 0));
		return maze;
	}
	
	private void generateMazeRec(Cell c){
		List<Cell> list=maze.GetAllNeighbors(c);
		for(int i=0;i<list.size()+2;i++){
			int random_index = (int) (Math.random()*(list.size())); 
			Cell random_cell=list.get(random_index);
			if (visits[random_cell.getRow()][random_cell.getCol()]==true){

			}
			else{
				visits[random_cell.getRow()][random_cell.getCol()]=true;
				if(c.getCol()>random_cell.getCol())
					{				
					random_cell.setRightWall(false);
					}
				if(c.getCol()<random_cell.getCol())
				{
					c.setRightWall(false);
				}
				if(c.getRow()>random_cell.getRow())
				{
					random_cell.setBottomWall(false);
				}
				if(c.getRow()<random_cell.getRow())
				{
					c.setBottomWall(false);
				}
				
				generateMazeRec(random_cell);
			}
			list.remove(random_index);
		}
	}

}


