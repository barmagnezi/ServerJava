package algorithms.search.aStar;

import algorithms.demo.MazeSearchable;
import algorithms.mazeGenerators.Cell;
import algorithms.search.Searchable;
import algorithms.search.State;

/**
* The MazeAirDistance class is heuristic of maze that allow to walk on diagonals
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
*/
public class MazeAirDistance implements Heuristic {

	@Override
	public double CostToEnd(State Start,Searchable s) {
		Cell currect=((MazeSearchable)s).StateToCell(Start);
		Cell end= ((MazeSearchable)s).StateToCell(s.getGoalState());
		double x;
		x=Math.pow(currect.getCol()-end.getCol(),2)+Math.pow((currect.getRow()-end.getRow()),2);
		x=Math.pow(x, 0.5);
		return x*s.GetNormalCost();
	}


}
