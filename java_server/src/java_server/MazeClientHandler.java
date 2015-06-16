package java_server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import model.Model;
import model.OffLineModel;
import model.StringMaze;
import model.StringSolution;
import presenter.PropertiesModel;

public class MazeClientHandler implements ClientHandler{
	


	Model model;
	PropertiesModel prop=null;
	
	public MazeClientHandler() {
		//System.out.println("setting client asnasinaesinaes");
		model=new OffLineModel();
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
		prop=Mproperties;
		model.start();
	}
	
	public void close(){
		System.out.println("offlineModel closed");
		model.stop();
	}

	@Override
	public void HandleClient(InputStream input, OutputStream outputStream) {
		PrintStream writer=new PrintStream(outputStream);
		BufferedReader reader=new BufferedReader(new InputStreamReader(input));
		try {
			String line=reader.readLine();
			//System.out.println(line);
			String[] commandArg=line.split(" ", 2);
			
			//check the command
			
			if(commandArg[0].equals("generateMaze")){
				String[] nameIndex= commandArg[1].split(" ");
				String[] index=nameIndex[1].split(",");
				model.generateMaze(nameIndex[0], Integer.parseInt(index[0]), Integer.parseInt(index[1]) );
				writer.println("The maze "+nameIndex[0]+" is ready");
				writer.flush();
			}
			
			if(commandArg[0].equals("getMaze")){
				//System.out.println("send maze:"+commandArg[1]);
				Maze m=model.getMaze(commandArg[1]);
				//writer.println("sentMaze");
				if(m==null)
					writer.println("sentMaze!"+"mazeNotFound");
				else
					writer.println("sentMaze!"+StringMaze.MazeToString(m));
				writer.flush();
			}
			
			if(commandArg[0].equals("solveMaze")){
				model.solveMaze(commandArg[1]);
				writer.println("The solution for "+commandArg[1]+" is ready");
				writer.flush();
			}
			
			if(commandArg[0].equals("getSolution")){
				Solution s=model.getSolution(commandArg[1]);
				//writer.println("sentSolution");
				if(s==null)
					writer.println("sentSolution!"+"solutionNotFound");
				else{
					String sol=StringSolution.SolutionToString(s);
					//System.out.println("The sol that sent is:"+sol);
					writer.println("sentSolution!"+sol);
					}
				writer.flush();
			}
			if(commandArg[0].equals("GetClue")){
				//System.out.println("Get Clue in maze client handler");
				String c=model.getClue(commandArg[1]);
				//System.out.println("MazeClientHandler,GetClue,result is:"+c);
				writer.println("sentClue!"+c);
				//writer.println(c);
				writer.flush();
			}
			
		} catch (IOException e) {
			System.out.println("the client disconnected(cant read from it)");
		}
	}
}
