package model;

import java.util.Observable;

import presenter.PropertiesModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**
* MyModel with stubs, only for first cheacking.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public class MyModelStub extends Observable implements Model {

	@Override
	public void generateMaze(String name, int rows,int cols) {
		System.out.println("generateMaze");
		this.setChanged();
		this.notifyObservers("generateMazeCompleted "+name);
	}

	@Override
	public Maze getMaze(String name) {
		System.out.println("getMaze");
		return null;
	}

	@Override
	public void solveMaze(Maze m) {
		System.out.println("solveMaze");
		this.setChanged();
		this.notifyObservers("solveMazeCompleted "+"mazeName"); //only for example
		//this.notifyObservers("solveMazeCompleted "+name);
	}

	@Override
	public Solution getSolution(String name) {
		System.out.println("getSolution");
		return null;
	}

	@Override
	public void stop() {
		System.out.println("Stop");

	}

	@Override
	public void setProperties(PropertiesModel mproperties) {
		System.out.println("setProperties");
		
	}

	@Override
	public void start() {
		System.out.println("start");
		
	}
	@Override
	public String getClue(String arg) {
		System.out.println("getclue:"+arg);
		return arg;
	}
	/*dont work
	@Override
	public boolean checkMotion(String Mazename, int CurrentRow, int CurrentCol,
			int nextRow, int nextCol) {
		System.out.println("checkMotion");
		return false;
	}*/

}
