package sModel;

import presenter.PropertiesModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**
* Model interface.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public interface SModel {
	public String GetIP();
	public int GetPort();
	public void setPort(int port);
	public String getUsers();
	public void killServer();
	
}
