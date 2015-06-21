package alg;

import java.util.Iterator;
import java.util.Stack;
/**
* The Solution class describe us Solution(use Stack)
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
* 
*/
public class Solution {
	private Stack<State> sol;
	@Override
	public String toString() {
		String s="";
		if(sol!=null){
			Stack<State> new_sol=new Stack<State>();
			State x;
			while(sol.size()!=1){
				x=sol.pop();
				s=s+x.getState()+"->";
				new_sol.add(x);
			}
			x=sol.pop();
			s=s+x.getState();
			new_sol.add(x);
			while(new_sol.isEmpty()==false){
				sol.add(new_sol.pop());
			}
			return s;
		}
		else{
			return "No Solution";
		}
			
	}
	

	public Stack<State> getSol() {
		return sol;
	}

	public void setSol(Stack<State> sol) {
		this.sol = sol;
	}
	/**
	 * This methods print the solution 
	 */
	public void print(){
		if(sol!=null){
			Stack<State> new_sol=new Stack<State>();
			State x;
			while(sol.size()!=1){
				x=sol.pop();
				System.out.print(x.getState()+"->");
				new_sol.add(x);
			}
			x=sol.pop();
			System.out.println(x.getState());
			new_sol.add(x);
			while(new_sol.isEmpty()==false){
				sol.add(new_sol.pop());
			}
		}
		else{
			System.out.println("No Solution");
		}
	}
	/**
	 * This methods return the solution price
	 */
	public double GetPrice(){
		Iterator<State> x=sol.iterator();
		return x.next().getCost();
	}
}
