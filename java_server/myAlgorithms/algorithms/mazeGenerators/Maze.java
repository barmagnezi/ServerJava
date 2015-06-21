package algorithms.mazeGenerators;

import java.util.ArrayList;
/**
* The Maze class represents us maze
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
* 
*/
public class Maze {
	
	private Cell[][] matrix;
	private int rows;
	private int cols;
	
	/**
	 * This constructor creates us maze with size by the parameters(all the walls exist)
	 * @param rows The number of the rows in the maze
	 * @param cols The number of the Column in the maze
	 * @return The maze
	*/
	public Maze(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		this.matrix = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = new Cell(i, j);				
			}
		}
	}
	/**
	 * This methods print the maze 
	 */
	public void print() 
	{
		for (int j = 0; j < cols; j++)
			System.out.print("__");
		System.out.println("_");
		
		for (int i = 0; i < rows; i++) {
			System.out.print("|");
			for (int j = 0; j < cols; j++) {				
				Cell cell = matrix[i][j];
				if (cell.getBottomWall().isExist())
					System.out.print("_");
				else
					System.out.print(" ");
				
				if (cell.getRightWall().isExist())
					System.out.print("|");
				else
					System.out.print(" ");	
								
			}
			System.out.println();
		}
	}
	public Cell getCell(int row,int col){
		return matrix[row][col];
	}
	
	public void SetWall(int row,int col,char wall,boolean x){
		if (wall!='b')
			matrix[row][col].setRightWall(x);
		else
			matrix[row][col].setBottomWall(x);
	}
	public ArrayList<Cell> GetAllNeighbors(Cell x){
		ArrayList<Cell> list=new ArrayList<Cell>();
		if(x.getCol()!=0)
			list.add(matrix[x.getRow()][x.getCol()-1]);
		if(x.getRow()!=0)
			list.add(matrix[x.getRow()-1][x.getCol()]);
		if(x.getRow()!=rows-1)
			list.add(matrix[x.getRow()+1][x.getCol()]);
		if(x.getCol()!=cols-1)
			list.add(matrix[x.getRow()][x.getCol()+1]);
		return list;
	}
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
	

}
