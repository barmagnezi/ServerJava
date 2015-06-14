package view;

import java.util.HashMap;

import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**
* View interface.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public interface View {
	/**
	 * Starts the view with the myCLI.
	 */
	void start(); 
	/**
	 * Sets out hashmap commands with the received hashmap.
	 * @param commands hashmap of string<->command containing out available commands.
	 */
	void setCommands(HashMap<String, Command> commands); 
	/**
	 * Takes out a user command from our user command queue.
	 */
	Command getUserCommand(); 
	/**
	 * Displays the received maze using MyMazeDispleyer.
	 * @param m Maze.
	 */
	void displayMaze(Maze m,String name); 
	/**
	 * Displays the received solution using MySolutionDispleyer.
	 * @param s Solution.
	 */
	void displaySolution(Solution s);
	/**
	 * Displays a string to our outputStream.
	 */
	void displayString(String msg);
	/**
	 * Displays the clue
	 */
	void displayClue(String clue);
	/**
	 * set in the view the diags Mode
	 * @param diag
	 */
	void sendDiagsMode(boolean diag);
}
