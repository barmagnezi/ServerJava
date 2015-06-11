package model;



import java.util.ArrayList;

import algorithms.demo.MazeSearchable;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;
import algorithms.search.State;

public class MazeSearchableFixed extends MazeSearchable {

	Maze maze;
	boolean withDiagonals;
	public MazeSearchableFixed(Maze maze, boolean withDiagonals) {
		super(maze, withDiagonals);
		this.maze=maze;
		this.withDiagonals=withDiagonals;
	}
	public MazeSearchableFixed(Maze maze, Cell start, Cell goal,
			boolean withDiagonals, double normalcost, double stepDiagonallyCost) {
		super(maze, start, goal, withDiagonals, normalcost, stepDiagonallyCost);
		this.maze=maze;
		this.withDiagonals=withDiagonals;
	}
	
	public boolean checkMotion(int CurrentRow,int CurrentCol,int nextRow,int nextCol){
		State Current=CellToState(maze.getCell(CurrentRow, CurrentCol));
		ArrayList<State> states = getAllPossibleStates(Current);
		for(State i:states)
			if(i.getState().equals(Current))
				return true;
		return false;
	}
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
		if (withDiagonals==true){
			int col=c.getCol();
			int row=c.getRow();
			//right-up
			if(!(col+1==maze.getCols() || row-1==-1)){ //Test surfing
				boolean flag=true;										  	
				if(maze.getCell(row-1, col).getRightWall().isExist() &&      // |_	
						maze.getCell(row-1, col+1).getBottomWall().isExist())//.
					flag=false;
				if(maze.getCell(row, col).getRightWall().isExist()&&			//  |
						maze.getCell(row-1, col).getRightWall().isExist())		// .|
					flag=false;
				if(maze.getCell(row-1, col).getBottomWall().isExist()&&			//_ _
						maze.getCell(row-1, col+1).getBottomWall().isExist())   //.
					flag=false;
				if(maze.getCell(row-1, col).getBottomWall().isExist()&&   ///corner like ._|
						maze.getCell(row, col).getRightWall().isExist())
					flag=false;
				if(flag)
					list.add(CellToState(maze.getCell(row-1, col+1)));
			}
			//right-down
			if(!(col+1==maze.getCols() || row+1==maze.getRows())){ //Test surfing
				boolean flag=true;										  	
				if(maze.getCell(row+1, col).getRightWall().isExist() &&    //.
						maze.getCell(row, col+1).getBottomWall().isExist())// |-
					flag=false;
				if(maze.getCell(row, col).getRightWall().isExist() &&			// . |
						maze.getCell(row+1, col).getRightWall().isExist())		//   |
					flag=false;
				if(maze.getCell(row, col).getBottomWall().isExist()&&			//.
						maze.getCell(row, col+1).getBottomWall().isExist())   //--
					flag=false;
				if(maze.getCell(row, col).getBottomWall().isExist()&&   ///corner like ._|
						maze.getCell(row, col).getRightWall().isExist())
					flag=false;
				if(flag)
					list.add(CellToState(maze.getCell(row+1, col+1)));
			}
			//left-down
			if(!(col-1==-1 || row+1==maze.getRows())){ //Test surfing
				boolean flag=true;										  	
				if(maze.getCell(row+1, col-1).getRightWall().isExist() &&    //   .
						maze.getCell(row, col-1).getBottomWall().isExist())  // -|
					flag=false;
				if(maze.getCell(row, col-1).getRightWall().isExist()&&			//   |.
						maze.getCell(row+1, col-1).getRightWall().isExist())	//   |
					flag=false;
				if(maze.getCell(row, col).getBottomWall().isExist()&&			// .
						maze.getCell(row, col-1).getBottomWall().isExist())     //--
					flag=false;
				if(maze.getCell(row, col).getBottomWall().isExist()&&   ///corner like ._|
						maze.getCell(row, col-1).getRightWall().isExist())
					flag=false;
				if(flag)
					list.add(CellToState(maze.getCell(row+1, col-1)));
			}
			//left-up
			if(!(col-1==-1 || row-1==-1)){ //Test surfing
				boolean flag=true;										  	
				if(maze.getCell(row-1, col-1).getRightWall().isExist() &&      // _|	
						maze.getCell(row-1, col-1).getBottomWall().isExist())  //   .
					flag=false;
				if(maze.getCell(row, col-1).getRightWall().isExist()&&				//  |
						maze.getCell(row-1, col-1).getRightWall().isExist())		//  |.
					flag=false;
				if(maze.getCell(row-1, col).getBottomWall().isExist()&&			//_ _
						maze.getCell(row-1, col-1).getBottomWall().isExist())   //  .
					flag=false;
				if(maze.getCell(row-1, col).getBottomWall().isExist()&&   ///corner like ._|
						maze.getCell(row, col-1).getRightWall().isExist())
					flag=false;
				if(flag)
					list.add(CellToState(maze.getCell(row-1, col-1)));
			}
			
		}	
		 for(State UpdateCost:list)//***8
			 UpdateCost.setCost(this.GetCostBetween(s, UpdateCost)+s.getCost());//********
		 return list;
	}

}
