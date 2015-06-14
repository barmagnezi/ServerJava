package java_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import model.OffLineModel;
import model.StringMaze;
import model.StringSolution;

import org.hibernate.loader.custom.Return;

import presenter.Presenter;
import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import view.View;

public class MazeClientHandler extends Observable implements ClientHandler,View {
	PrintStream writer;
	BufferedReader reader;
	HashMap<String, Command> commands;
	Queue<Command> commandsList;
	

	public void HandleClient(InputStream input,OutputStream outputStream, OffLineModel m){
		writer = new PrintStream(outputStream);		
		reader=new BufferedReader(new InputStreamReader(input));
		commandsList= new LinkedList<Command>();
		Presenter p=new Presenter(this, m);
		this.addObserver(p);
		m.addObserver(p);
		start();
	}
	
	@Override
	public void HandleClient(InputStream input, OutputStream outputStream) {
	}

	@Override
	public void start() {
		this.setChanged();
		this.notifyObservers("start");
		while(true){
			try {
				//get client commands until get exit
				String line=reader.readLine();
				System.out.println("The client send:"+line+"\n");
				if(line.equals("exit")){
					System.out.println("The client end the session");
					commandsList.add(commands.get("exit"));
					this.setChanged();
					this.notifyObservers("");
					return;
				}
				String[] commandArg=line.split(" ", 2);
				commandsList.add(commands.get(commandArg[0]));
				this.setChanged();
				this.notifyObservers(commandArg[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands=commands;
	}

	public Command getUserCommand() {
		return commandsList.poll();
	}

	@Override
	public void displayMaze(Maze m, String name) {
			writer.println("sentMaze");
			writer.println(name);
			writer.println(StringMaze.MazeToString(m));
			writer.flush();
	}

	@Override
	public void displaySolution(Solution s) {
			writer.println("sentSolution");
			writer.println(StringSolution.SolutionToString(s));
			writer.flush();
	}

	@Override
	public void displayString(String msg) {
			writer.println("sentString");
			writer.println(msg);
			writer.flush();
	}

	@Override
	public void displayClue(String clue) {
			writer.println("sentClue");
			writer.println(clue);
			writer.flush();
	}

	@Override
	public void sendDiagsMode(boolean diag) {
		writer.println("sentDiagsMode");
		if(diag)
			writer.println("true");
		else
			writer.println("false");
		writer.flush();
	}
	
	@Override
	public ClientHandler CreateNewClientHandler() {
		return new MazeClientHandler();
	}
}