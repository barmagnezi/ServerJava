package notUsed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import sModel.ClientHandler;
import TheBrain.Model;
import TheBrain.OffLineModel;
import TheBrain.PropertiesModel;
import TheBrain.StringMaze;
import TheBrain.StringSolution;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class MazeClientHandlerwithoutMVP implements ClientHandler{

	Model model=new OffLineModel();
	PropertiesModel prop=null;
	
	
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
		prop=Mproperties;
	}
	
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
				System.out.println("bar");
				String[] commandArg=line.split(" ", 2);
				if(commandArg[0].equals("generateMaze")){
					String[] nameIndex= commandArg[1].split(" ");
					String[] index=nameIndex[1].split(",");
					System.out.println(nameIndex[0]);
					System.out.println(index[0]);
					System.out.println(index[1]);
					model.generateMaze(nameIndex[0], Integer.parseInt(index[0]), Integer.parseInt(index[1]) );
				}
				if(commandArg[0].equals("getMaze")){
					System.out.println(commandArg[1]);
					Maze m=model.getMaze(commandArg[1]);
					writer.println("sentMaze");
					if(m==null)
						writer.println("mazeNotFound");
					else
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
				break;
			}
		}
	}
	public void close(){
		System.out.println("offlineModel close");
		model.stop();
	}
}