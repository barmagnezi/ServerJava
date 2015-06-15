package model;

import java.util.Stack;

import algorithms.search.Solution;
import algorithms.search.State;

/**
* Saves and represents the solution as a string.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public class StringSolution {
	public static String SolutionToString(Solution solution){
		Solution n=new Solution();
		n.setSol(solution.getSol());
		String strsol="";
		Stack<State> sol=n.getSol();
		strsol+=sol.size()+"/";
		while(!sol.isEmpty()){
			strsol+=sol.pop().getState()+"->";
		}
		return strsol;
	}
	public static Solution StringToSolution(String solution){
		Stack<State> sol=new Stack<State>();
		String[] sizeSol=solution.split("/");
		int size=Integer.parseInt(sizeSol[0]);
		String[] states=sizeSol[1].split("->");
		for(int i=size-1;i!=-1;i--)
			sol.push(new State(states[i]));
		Solution realsol=new Solution();
		realsol.setSol(sol);
		return realsol;
	}
}
