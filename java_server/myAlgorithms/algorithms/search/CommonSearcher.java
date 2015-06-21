package algorithms.search;

import java.util.PriorityQueue;
import java.util.Stack;
/**
* The CommonSearcher class is abstract searcher that use priority queue
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
* 
*/
public abstract class CommonSearcher implements Searcher {

	 protected PriorityQueue<State> openList;
	 private int evaluatedNodes;
	 /**
		 * This constructor create us priority queue(openList)
		*/
	 public CommonSearcher() {
	  openList=new PriorityQueue<State>();
	  evaluatedNodes=0;
	 }

	 protected State popOpenList() {
	  evaluatedNodes++;
	  return openList.poll();
	 }
	 @Override
	 public abstract Solution search(Searchable s);

	 @Override
	 public int getNumberOfNodesEvaluated() {
	  return evaluatedNodes;
	 }
	 /**
		 * This method create us path solution that will be states from start to end
		 * @param start The state we want to do back trace from it
		 * @param end The state we want to stop the back trace
	*/
	 
	 protected Solution backTrace(State start,State end){
			Solution s=new Solution();
		    Stack<State> total_path=new Stack<State>();
			total_path.push(start);
			while (start.getCameFrom()!=null){
				start=start.getCameFrom();
				total_path.push(start);
			}
			s.setSol(total_path);
			return s;
	 }
	}
