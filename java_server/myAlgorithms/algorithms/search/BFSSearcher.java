package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
/**
* The BFSSearcher class create us searcher that using the BFS algorithm
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
* 
*/
public class BFSSearcher extends CommonSearcher {

	/**
	 * This method return solution of some search problems(need to be Searchable)
	 * @param s The search problem
	 * @return The Solution
	*/
	@Override
	public Solution search(Searchable s) {
		  openList.add(s.getStartState());
		  HashSet<State> closedSet=new HashSet<State>();

		  while(openList.size()>0){
			  
			    State n=popOpenList();// dequeue
			    closedSet.add(n);
			    if(n.equals(s.getGoalState())){
			    	 return backTrace(n, s.getStartState()); 
				      // private method, back traces through the parents
			    }
				     
	
			    ArrayList<State> successors=s.getAllPossibleStates(n); //however it is implemented
			    for(State state : successors){
				      if(!closedSet.contains(state) && !openList.contains(state)){
					        state.setCameFrom(n);
					        openList.add(state);
				      } 
				      else{
				    	state.setCameFrom(n);
				        if (closedSet.contains(state)==true){
				        	Iterator<State> i=closedSet.iterator();
				        	while(i.hasNext()){
				        		State x=i.next();
				        		if(x.equals(state)&&state.getCost()<x.getCost()){
				        			closedSet.remove(x);
				        			closedSet.add(state);
				        			break;
				        		}
				        	}
				        }
				        else{
				        	Iterator<State> i=openList.iterator();
				        	while(i.hasNext()){
				        		State x=i.next();
				        		if(x.equals(state)&&state.getCost()<x.getCost()){
				        			openList.remove(x);
				        			openList.add(state);
				        			break;
				        		}
				        	}
				        } 	
				      }
			     }
		  }
		  Solution sol=new Solution();
		  return sol;
	}

	
	

}
