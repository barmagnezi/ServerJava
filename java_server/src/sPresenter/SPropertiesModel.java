package sPresenter;

import java.beans.XMLDecoder;
import java.io.InputStream;
import java.io.Serializable;

/**
* The PropertiesModel is a class holding the variables needed for our model.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public class SPropertiesModel implements Serializable {
	public int AllowedClients;
	public int port;
	public int dely;
	
	
	public SPropertiesModel() {
	}
	
	/**
	 * Default copy constructor.
	 */
	private void copyConstructor(SPropertiesModel prop) {
		this.AllowedClients=prop.getAllowedClients();
		this.port=prop.getPort();
		this.dely=prop.getDely();
	}
	/**
	 * Sets the properties model from an inputStream.
	 * @param from inputStream containing the XML file.
	 */
	public SPropertiesModel(InputStream from) {
		System.out.println("FROM IS "+from);
		try{
			XMLDecoder XML = null;	
			XML = new XMLDecoder(from);	
			this.copyConstructor((SPropertiesModel) XML.readObject());
			XML.close();
		}catch(Exception e ){
			System.out.println("\n***	Not found server properties file - runing on default values		***\n"
					+ "after exit command the properties will save in resources/serverProperties.xml");
			this.setPort(5401);
			this.setAllowedClients(5);
			this.setDely(5000000);
		}
	}

	public int getAllowedClients() {
		return AllowedClients;
	}

	public void setAllowedClients(int allowedClients) {
		AllowedClients = allowedClients;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getDely() {
		return dely;
	}

	public void setDely(int dely) {
		this.dely = dely;
	}
	

	/*public String toString(){
		String str="";
		str+=this.AllowedThreads+" "+this.MGenerator.toString()+" "+this.nameSolver;
		if(nameSolver.equalsIgnoreCase("BFS"))
			str+=" PlaceholderForNoHue";
		else
			str+=" "+this.Hue.toString();
		if(this.diag==true)
			str+=" 1";
		else
			str+=" 0";
		return str;
	}*/

	/*public void setPropeties(String path) {
		XMLDecoder XML = null;
		try {
			XML = new XMLDecoder(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties properties=(Properties) XML.readObject();
		if(XML==null || properties==null){ // ||m.setProperties(properties.toString())!=1){
			properties.setAllowedThreads(3);				//Setting default values for not found or bad XML file.
			properties.setDiag(true);
			properties.setMGenerator(new DFSMazeGenerator());
			Heuristic Hur = new MazeAirDistance();
			properties.setMSolver(new AstarSearcher(Hur));
			HashMap<String, Maze> nameMaze = null;
			properties.setNameMaze(nameMaze);
			HashMap<Maze, Solution> mazeSol = null;
			properties.setMazeSol(mazeSol);
			//m.setProperties(prop.toString());
			
		}
		XML.close();
	}*/
}
