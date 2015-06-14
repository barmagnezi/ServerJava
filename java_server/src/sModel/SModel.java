package sModel;

import java.util.ArrayList;
import java.util.HashMap;

import presenter.PropertiesModel;
import sPresenter.SPropertiesModel;
import View.Command;
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
	public ArrayList<MyClient> getUsers();
	public void killServer();
	
	/**
	 * Sets the server.
	 */
	public void start();
	/**
	 * Stops the work and saves the data.
	 */
	public void stop();
	public void setProperties(SPropertiesModel mproperties);
}
