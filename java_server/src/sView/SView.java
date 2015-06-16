package sView;

import java.util.ArrayList;
import java.util.HashMap;
import sModel.MyClient;
import View.Command;

/**
 * View interface.
 * 
 * @author Bar Magnezi and Senia Kalma
 * @version 1.0
 * @since 17.5.2015
 */
public interface SView {
	void setIP(String ip);

	void setPort(int port);

	void setKilled(boolean k);
	void update(ArrayList<MyClient> arrayList);

	/**
	 * Starts the view with the myCLI.
	 */
	void start();

	/**
	 * Sets out hashmap commands with the received hashmap.
	 * 
	 * @param commands
	 *            hashmap of string<->command containing out available commands.
	 */
	void setCommands(HashMap<String, Command> commands);

	/**
	 * Takes out a user command from our user command queue.
	 */
	Command getUserCommand();

	/**
	 * Displays a string to our outputStream.
	 */
	void displayString(String msg);

	void showPort(int port);

	
}
