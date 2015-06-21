
package algorithms.demo;

import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.search.BFSSearcher;
import algorithms.search.CommonSearcher;
import algorithms.search.Solution;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.MazeAirDistance;
import algorithms.search.aStar.MazeManhhetenDistance;
/**
* The Demo class only show us some examples of searches and create a maze with  DFS algorithm 
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
*/
public class Demo {
	/**
	 * This method uses all we need to do in part 1 in the project
	 * first this method prints random maze that create with DFS algorithm
	 * second it will  print 4 Searches 2 for every algorithm(A Star and BFS)
	 * for each one it will print solution without diagonals and with diagonals
	*/
	public void run(){
		System.out.println("1)Create DFS maze(20,20)");
		Maze m=(new DFSMazeGenerator()).generateMaze(20, 20);
		m.print();
		
		System.out.println("2)Searcher without diagonals");
		MazeSearchable MS=new MazeSearchable(m, false);
		CommonSearcher se=new BFSSearcher();
		System.out.print("BFS searcher solution:");
		Solution sol=se.search(MS);
		sol.print();
		System.out.println("solution Length:"+sol.getSol().size());
		System.out.println("solution price:"+sol.GetPrice());
		System.out.println("Number of nodes evaluated:"+se.getNumberOfNodesEvaluated());
		se=new AstarSearcher(new MazeManhhetenDistance());
		System.out.print("AStar searcher solution:");
		sol=se.search(MS);
		sol.print();
		System.out.println("solution Length:"+sol.getSol().size());
		System.out.println("solution price:"+sol.GetPrice());
		System.out.println("Number of nodes evaluated:"+se.getNumberOfNodesEvaluated());
		
		System.out.println("3)Searcher with diagonals");
		MS=new MazeSearchable(m, true);
		se=new BFSSearcher();
		System.out.print("BFS searcher solution:");
		sol=se.search(MS);
		sol.print();
		System.out.println("solution Length:"+sol.getSol().size());
		System.out.println("solution price:"+sol.GetPrice());
		System.out.println("Number of nodes evaluated:"+se.getNumberOfNodesEvaluated());
		se=new AstarSearcher(new MazeAirDistance());
		System.out.print("AStar searcher solution:");
		sol=se.search(MS);
		sol.print();
		System.out.println("solution Length:"+sol.getSol().size());
		System.out.println("solution price:"+sol.GetPrice());
		System.out.println("Number of nodes evaluated:"+se.getNumberOfNodesEvaluated());
		
		
	}
}
