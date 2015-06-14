package java_server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import presenter.PropertiesModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import model.Model;
import model.OffLineModel;
import model.StringMaze;
import model.StringSolution;

public class MazeClientHandlerwithoutMVP implements ClientHandler{

	public void close(){
		model.stop();
	}
	public void newProp(String path){
		InputStream from = null;
		PropertiesModel Mproperties;
		try {
			from = new FileInputStream(path);
			Mproperties = new PropertiesModel(from);
		} catch (Exception e) {
			File theDir = new File("resources");
			theDir.mkdirs();
			Mproperties = new PropertiesModel(null);}		
		model.setProperties(Mproperties);
	}
	Model model=new OffLineModel();
	PropertiesModel prop=null;
	@Override
	public void HandleClient(InputStream input, OutputStream outputStream) {
		if(prop==null)
			newProp("//"); //run default prop
		PrintStream writer=new PrintStream(outputStream);
		BufferedReader reader=new BufferedReader(new InputStreamReader(input));
		//////// send to client if he can go in diags
		writer.println("sentDiagsMode");
		if(prop.diag)
			writer.println("true");
		else
			writer.println("false");
		writer.flush();
		//////////////////
		while(true){
			try {
				String line=reader.readLine();
				System.out.println(line);
				String[] commandArg=line.split(" ", 2);
				if(commandArg[0].equals("generateMaze")){
					String[] nameIndex= commandArg[1].split(" ");
					String[] index=nameIndex[1].split(",");
					model.generateMaze(nameIndex[1], Integer.getInteger(index[0]), Integer.getInteger(index[1]));
				}
				if(commandArg[0].equals("getMaze")){
					Maze m=model.getMaze(commandArg[1]);
					writer.println("sentMaze");
					writer.println(StringMaze.MazeToString(m));
					writer.flush();
				}
				if(commandArg[0].equals("solveMaze")){
					model.solveMaze(commandArg[1]);
				}
				if(commandArg[0].equals("getSolution")){
					Solution s=model.getSolution(commandArg[1]);
					writer.println("sentSolution");
					writer.println(StringSolution.SolutionToString(s));
					writer.flush();
				}if(commandArg[0].equals("GetClue")){
					String c=model.getClue(commandArg[1]);
					writer.println("sentClue");
					writer.println(c);
					writer.flush();
				}
			} catch (IOException e) {
				System.out.println("cant read from client");
			}
		}
	}
}