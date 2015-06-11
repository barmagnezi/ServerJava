package view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import view.View;

public class ViewGUI extends Observable implements View{

	HashMap<String, Command> commands;
	Queue<Command> commandsList;
	MazeViewWidget Widget;
	//only for ViewGUI work!!!!!
	public ViewGUI(MazeViewWidget widget) {
		super();
		Widget = widget;
		commandsList= new LinkedList<Command>();
	}
	
	public void setCommands(HashMap<String, Command> commands) {
		//System.out.println(commands.size());
		this.commands=commands;
	}

	public Command getUserCommand() {
		return commandsList.poll();
	}
	///////////////////////////////////////////////////
	
	public void generateMaze(String name,int rows,int cols){
		if (commands.get("generateMaze")==null)
			System.out.println("error");
		commandsList.add(commands.get("generateMaze"));
		this.setChanged();
		this.notifyObservers(name+" "+rows+","+cols);
	}

	public void solveMaze(String name)
	{
		commandsList.add(commands.get("solveMaze"));
		this.setChanged();
		this.notifyObservers(name);
	}
	public void displaymaze(String name) {
		commandsList.add(commands.get("displayMaze"));
		this.setChanged();
		this.notifyObservers(name);
	}
	public void displaySolution(String name){
		commandsList.add(commands.get("displaySolution"));
		this.setChanged();
		this.notifyObservers(name);
	}

	public void getclue(int row,int col){
		commandsList.add(commands.get("GetClue"));
		this.setChanged();
		this.notifyObservers(Widget.mazeName+" "+row+","+col);
		
	}
	public void setproperties(String path) {
		commandsList.add(commands.get("setNewProperties"));
		this.setChanged();
		this.notifyObservers(path);
	}
	public void exit(){
		commandsList.add(commands.get("exit"));
		this.setChanged();
		this.notifyObservers("");
	}
	public void checkMotion(String Mazename,int CurrentRow,int CurrentCol,int nextRow,int nextCol){
		commandsList.add(commands.get("checkMotion"));
		this.setChanged();
		this.notifyObservers(Mazename+" "+CurrentRow+" "+CurrentCol+" "+nextRow+" "+nextCol);
	}
	//view @Override
	@Override
	public void start() {
		this.setChanged();
		this.notifyObservers("start");
	}

	@Override
	public void displayMaze(Maze m,String name) {
		Widget.setMazeName(name);
		Widget.displayMaze(m);
	}

	@Override
	public void displaySolution(Solution s) {
		Widget.displaySolution(s);
		
	}

	@Override
	public void displayString(String msg) {
		Widget.displayString(msg);
	}

	@Override
	public void displayClue(String clue) {
		Widget.displayClue(clue);
	}

	@Override
	public void getDiagsMode(boolean diag) {
		Widget.Diagonals=diag;
		
	}

	/*not work
	@Override
	public void getData(Object Data, String details) {
		if(details.equals("checkMotion"))
			Widget.checkMotionFlag=(Boolean) Data;
		Widget.checkMotionFlag.notify();
	}*/


	

}
