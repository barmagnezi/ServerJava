package algorithms.search.aStar;

import algorithms.demo.MazeSearchable;
import algorithms.mazeGenerators.Cell;
import algorithms.search.Searchable;
import algorithms.search.State;
/**
* The MazeAirDistance class is heuristic of maze that not allowed to walk on diagonals
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
*/
public class MazeManhhetenDistance implements Heuristic {


	@Override
	public double CostToEnd(State Start,Searchable s) {
		Cell currect=((MazeSearchable)s).StateToCell(Start);
		Cell end= ((MazeSearchable)s).StateToCell(s.getGoalState());
		return ((end.getCol()-currect.getCol())+(end.getRow()-currect.getRow()))*s.GetNormalCost();
		//return (currect.getCol()-end.getCol())+(currect.getRow()-end.getRow())*s.GetNormalCost();
	}

}
