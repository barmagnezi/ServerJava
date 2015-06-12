package sModel;

import presenter.PropertiesModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**
* Model interface.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public interface SModel {
	public String GetIP();
	public int GetPort();
	public void setPort(String arg);
	
	/**
	 * This method generates a maze using threads with an inputed data.
	 * After it finishes it sent a notification with as "maze "+name+" is ready.".
	 * @param name The name we want to give the maze.
	 * @param col The number of columns our maze will have (>2)
	 * @param row The number of rows our maze will have (>2)
	 */
	public void generateMaze(String name,int rows, int cols);
	/**
	 * Returns a maze by an inputed name(String).
	 */
	public Maze getMaze(String name); 
	/**
	 * This method solves a maze using threads.
	 * After it finishes it sent a notification with as "solution "+name+" is ready.".
	 * @param name The maze we want to solve.
	 */
	public void solveMaze(String name);
	/**
	 * gets the name of an inputed maze name.
	 * @param name	the name of the solution we want to find it's name.
	 * @return the name of the solution(string).
	 */
	public Solution getSolution(String name);
	/**
	 * Reads from the data file and sets the hashmaps by the data read.
	 */
	public void start();
	/**
	 * Stops the work and saves the data.
	 */
	public void stop();
	/**
	 * Setting the current properties with an inputed PropertiesModel Object.
	 */
	public void setProperties(PropertiesModel mproperties);
	/**
	 * this method get cell and return the next step from the cell
	 * @param arg The x,y of the cell in this syntax: nameMaze x,y
	 * @return The next cell in this syntax: x,y
	 */
	public String getClue(String arg);
	//public boolean checkMotion(String Mazename,int CurrentRow,int CurrentCol,int nextRow,int nextCol); not work
}
