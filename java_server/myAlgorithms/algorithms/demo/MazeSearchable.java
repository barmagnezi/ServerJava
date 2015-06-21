package algorithms.demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Searchable;
import algorithms.search.State;
/**
* The MazeSearchable class becomes maze to maze that we can run on it search algorithm
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
*/
public class MazeSearchable implements Searchable {

	private Maze maze;
	private Cell start;
	private Cell Goal;
	private boolean WithDiagonals;
	private double Normalcost;
	private double StepDiagonallyCost;
	
	/**
	 * This constructor creates us MazeSearchable
	 * @param maze The maze we want to make MazeSearchable
	 * @param withDiagonals True if allow to walk on diagonals
	 * @return The maze search able
	*/
	public MazeSearchable(Maze maze,boolean withDiagonals) {
		super();
		this.maze = maze;
		this.start = maze.getCell(0, 0);
		Goal = maze.getCell(maze.getRows()-1, maze.getCols()-1);
		WithDiagonals=withDiagonals;
		Normalcost=10;
		StepDiagonallyCost=15;
	}
	/**
	 * This constructor creates us MazeSearchable
	 * @param maze The maze we want to make MazeSearchable
	 * @param withDiagonals True if allow to walk on diagonals
	 * @param start The cell that the searcher start
	 * @param goal The cell that the searcher stop
	 * @param normalcost The value of normal step
	 * @param stepDiagonallyCost The value of diagonally step
	 * @return The maze search able
	*/
	public MazeSearchable(Maze maze, Cell start, Cell goal,
			boolean withDiagonals, double normalcost, double stepDiagonallyCost) {
		super();
		this.maze = maze;
		this.start = start;
		Goal = goal;
		WithDiagonals = withDiagonals;
		Normalcost = normalcost;
		StepDiagonallyCost = stepDiagonallyCost;
	}
	
	/**
	 * This method returns us The cell(on state type) that the searcher start
	 * @return The cell(on state type) that the searcher start
	 */
	@Override
	public State getStartState() {
		return CellToState(start);
	}
	/**
	 * This method returns The cell(on state type) that the searcher stop
	 * @return The cell(on state type) that the searcher stop
	 */
	@Override
	public State getGoalState() {
		return CellToState(Goal);
	}
	/**
	 * This method changes type from Cell to State
	 * @param c The cell we want to change to state
	 * @return The state 
	 */
	public State CellToState(Cell c){
		return new State(c.getRow()+"x"+c.getCol());
	}
	/**
	 * This method changes type from State to Cell
	 * @param s The state we want to change to cell
	 * @return The cell in the maze 
	 */
	public Cell StateToCell(State s){
		String[] RowCol=s.getState().split("x");
		return maze.getCell(Integer.parseInt(RowCol[0]),Integer.parseInt(RowCol[1]));
	}
	/**
	 * This method returns us all the neighbors of state
	 * @param s The state we want to see all its neighbors
	 * @return list of the neighbors
	 */
	@Override
	public ArrayList<State> getAllPossibleStates(State s) {
		Cell c=StateToCell(s);
		ArrayList<State> list=new ArrayList<State>();
		Cell x;
		
		if(c.getCol()!=0){
			x=maze.getCell(c.getRow(), c.getCol()-1);
			if (x.getRightWall().isExist()==false)
				list.add(CellToState(x));
			 }
		if(c.getCol()!=maze.getCols()-1){
			x=maze.getCell(c.getRow(), c.getCol()+1);
			if (c.getRightWall().isExist()==false)
					list.add(CellToState(x));
			 }
		if(c.getRow()!=0){
			x=maze.getCell(c.getRow()-1, c.getCol());
			if (x.getBottomWall().isExist()==false)
				list.add(CellToState(x));
			 }
		if(c.getRow()!=maze.getRows()-1){
			x=maze.getCell(c.getRow()+1, c.getCol());
			if (c.getBottomWall().isExist()==false)
				list.add(CellToState(x));
		
		}
		if (WithDiagonals==true){
			if(c.getRow()!=0 && c.getCol()!=0)
				if (maze.getCell(c.getRow(), c.getCol()-1).getRightWall().isExist()==false ||
						maze.getCell(c.getRow()-1, c.getCol()).getBottomWall().isExist()==false)
					list.add(CellToState(maze.getCell(c.getRow()-1, c.getCol()-1)));
			if(c.getRow()!=0 && c.getCol()!=maze.getCols()-1)
				if (c.getRightWall().isExist()==false ||
						maze.getCell(c.getRow()-1, c.getCol()).getBottomWall().isExist()==false)
							list.add(CellToState(maze.getCell(c.getRow()-1, c.getCol()+1)));
			if(c.getRow()!=maze.getRows()-1 && c.getCol()!=maze.getCols()-1)
				if (c.getRightWall().isExist()==false ||
						c.getBottomWall().isExist()==false)
					list.add(CellToState(maze.getCell(c.getRow()+1, c.getCol()+1)));
			if(c.getRow()!=maze.getRows()-1 && c.getCol()!=0)
				if (c.getBottomWall().isExist()==false ||
						maze.getCell(c.getRow(), c.getCol()-1).getRightWall().isExist()==false)
					list.add(CellToState(maze.getCell(c.getRow()+1, c.getCol()-1)));
		}
		 for(State UpdateCost:list)//***8
			 UpdateCost.setCost(this.GetCostBetween(s, UpdateCost)+s.getCost());//********
		 return list;
	}
	/**
	 * This method returns the value of normal step 
	 * @return The value of normal step 
	 */
	@Override
	public double GetNormalCost() {
		return Normalcost;
	}
	/**
	 * This method return the value of step from State to State only if they neighbors
	 * @return The value of the step  or -1 if they not neighbors
	 */
	@Override
	public double GetCostBetween(State s1, State s2) {
		Cell c1=StateToCell(s1);
		Cell c2=StateToCell(s2);
		if(Math.abs(c1.getRow()-c2.getRow())==1 && Math.abs(c1.getCol()-c2.getCol())==1)
			return StepDiagonallyCost;
		if(Math.abs(c1.getRow()-c2.getRow())==1 || Math.abs(c1.getCol()-c2.getCol())==1)
			return Normalcost;
		return -1;
	}

}
