package algorithms.mazeGenerators;

/**
* The Cell class describe us cell on maze
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
* 
*/
public class Cell implements Comparable<Cell>{


	
	private Wall RightWall = new Wall();
	private Wall BottomWall = new Wall();
	

	private int row;
	private int col;
	
	
	
	public Cell(int row, int col)
	{
		setRow(row);
		setCol(col);
	}

	@Override
	public boolean equals(Object obj) {
		if(row==((Cell)obj).getRow() && col==((Cell)obj).getCol() && RightWall==((Cell)obj).getRightWall() && BottomWall==((Cell)obj).getBottomWall() )
			return true;
		else
			return false;
	}
	public void printRowCol(){
		System.out.println("Cell:row:"+row+"col"+col);
	}
	public int getRow() {
		return row;
	}



	public void setRow(int row) {
		this.row = row;
	}



	public int getCol() {
		return col;
	}



	public void setCol(int col) {
		this.col = col;
	}


	public Wall getRightWall() {
		return RightWall;
	}


	public void setRightWall(boolean x) {
		RightWall.setExist(x);;
	}


	public Wall getBottomWall() {
		return BottomWall;
	}


	public void setBottomWall(boolean x) {
		BottomWall.setExist(x);
	}

	/**
	 * this method compares two cells
	 * @param o The cell we want to compare
	 * @return 0 if they equal else 1
	 */
	@Override
	public int compareTo(Cell o) {
		if (row==o.getRow() && col==o.getCol() && RightWall==o.getRightWall() && BottomWall==o.getBottomWall() )
			return 0;
		return 1;
	}
	

}
