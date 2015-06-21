package algorithms.search.aStar;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

import algorithms.search.CommonSearcher;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import algorithms.search.State;
/**
* The AstarSearcher class create us searcher that using the A* algorithm
* A* algorithm run with specific heuristic for specific problem
* The heuristic need to accepted in the constructor
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
* 
*/
public class AstarSearcher extends CommonSearcher {

	Heuristic h;
	
	/**
	 * This constructor create AstarSearcher
	 *  A* algorithm run with specific heuristic for specific problem
	 * The heuristic need to accepted in the constructor
	 * @param h The heuristic
	*/
	public AstarSearcher(Heuristic h) {
		super();
		this.h = h;
	}
	/**
	 * This method return solution of some search problems(need to be Searchable)
	 * @param s The search problem
	 * @return The Solution
	*/
	@Override
	public Solution search(Searchable s) {
		System.out.println("Dsvdsvvd");
		ArrayList<State> closedset=new ArrayList<State>();
		State current=null;
		openList.add(s.getStartState());
		s.getStartState().setCost(h.CostToEnd(s.getStartState(), s));
		while (openList.isEmpty()==false){
			current=this.popOpenList();
			if (current.equals(s.getGoalState())){
				return  backTrace(current, s.getStartState(),s);
			}
			closedset.add(current);
			for(State neighbor:s.getAllPossibleStates(current)){
				if (closedset.contains(neighbor)==false){
					double tentative_g_score=current.getCost()+s.GetCostBetween(current, neighbor);
					if(openList.contains(neighbor)==false || tentative_g_score<neighbor.getCost()||neighbor.getCost()==-1){
						neighbor.setCameFrom(current);
						neighbor.setCost(tentative_g_score+h.CostToEnd(neighbor, s)-h.CostToEnd(current, s));
						if (openList.contains(neighbor)==false)
							openList.add(neighbor);
					}
				}
			}
		}
		
		Solution sol=new Solution();
		return sol;
	}
	private Solution backTrace(State start,State end,Searchable searchable){
		Solution s=new Solution();
	    Stack<State> total_path=new Stack<State>();
		total_path.push(start);
		while (start.getCameFrom()!=null){
			start=start.getCameFrom();
			total_path.push(start);
		}
		total_path=costupdate(total_path,searchable);
		s.setSol(total_path);
		return s;
	}
	private Stack<State> costupdate(Stack<State> stack,Searchable searchable){
		Stack<State> h=new Stack<State>();
		State x=stack.pop();
		x.setCost(0);
		h.push(x);
		while(stack.isEmpty()==false){
			x=stack.pop();
			x.setCost(x.getCameFrom().getCost()+searchable.GetCostBetween(x, x.getCameFrom()));
			h.push(x);
		}
		
		while(h.isEmpty()==false){
			stack.push(h.pop());
		}
		return stack;
	}
	
}


/*
Pseudocode
	עם הערות שלי	

function A*(start,goal)
closedset := the empty set    // The set of nodes already evaluated.
openset := {start}    // The set of tentative nodes to be evaluated, initially containing the start node 
//תור עדיפויות כאשר ככל שפונקציית המרחק של האיבר קטנה יותר הוא יצא ראשון
came_from := the empty map    // The map of navigated nodes.

g_score[start] := 0    // Cost from start along best known path.
//Estimated total cost from start to goal through y.
f_score[start] := g_score[start] + heuristic_cost_estimate(start, goal)

while openset is not empty
  current := the node in openset having the lowest f_score[] value
  if current = goal
      return reconstruct_path(came_from, goal)

  remove current from openset
  add current to closedset
  for each neighbor in neighbor_nodes(current)
      if neighbor in closedset
          continue
      tentative_g_score := g_score[current] + dist_between(current,neighbor) //העלות להגיע לשכן
		אם הוא לא בצמתים בתור עדיפויות או שמצאנו דרך להגיע אליו שהיא יותר זולה
      if neighbor not in openset or tentative_g_score < g_score[neighbor] 
          came_from[neighbor] := current
          g_score[neighbor] := tentative_g_score
          f_score[neighbor] := g_score[neighbor] + heuristic_cost_estimate(neighbor, goal)
          if neighbor not in openset
              add neighbor to openset

return failure
*/
