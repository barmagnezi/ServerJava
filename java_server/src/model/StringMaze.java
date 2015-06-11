package model;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

/**
* Saves and represents the maze as a string.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public class StringMaze {
	
	public static String MazeToString(Maze maze){
		String strMaze=maze.getRows()+","+maze.getCols()+"/";
		for(int i=0;i<maze.getRows();i++)
			for(int j=0;j<maze.getCols();j++){
				Cell c=maze.getCell(i, j);
				strMaze+=i+","+j+"-";
				if(c.getBottomWall().isExist())
					strMaze+="b";
				if(c.getRightWall().isExist())
					strMaze+="r";
				strMaze+="&";
			}
		return strMaze;
	}
	public static Maze StringToMaze(String maze){
		String[] rowcolAndCells=maze.split("/");
		String[] rowAndcol=rowcolAndCells[0].split(",");
		int rows=Integer.parseInt(rowAndcol[0]);
		int cols=Integer.parseInt(rowAndcol[1]);
		Maze realMaze=new Maze(rows, cols);
		//remove all the walls from the maze 
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++){
				realMaze.SetWall(i, j, 'b', false);
				realMaze.SetWall(i, j, 'r', false);
			}
		String[] cells=rowcolAndCells[1].split("&");
		for(String cell:cells){
			String[] indexAndWalls=cell.split("-");
			if(indexAndWalls.length==2){
				String[] index=indexAndWalls[0].split(",");
				char[] walls=indexAndWalls[1].toCharArray();
				for(char wall:walls)
					realMaze.SetWall(Integer.parseInt(index[0]), Integer.parseInt(index[1]), wall, true);
			}
		}
		return realMaze;
	}
}
