package algorithms.search.aStar;


import algorithms.search.Searchable;
import algorithms.search.State;
/**
* interface of Heuristic
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
*/
public interface Heuristic {
	public double CostToEnd(State Start,Searchable s);
}
