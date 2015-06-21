package algorithms.search;


import java.util.ArrayList;
/**
* interface of problem that search able
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
*/
public interface Searchable {
   State getStartState();
   State getGoalState();
   ArrayList<State> getAllPossibleStates(State s);
   double GetNormalCost();
   double GetCostBetween(State s1,State s2);
}
