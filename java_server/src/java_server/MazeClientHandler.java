package java_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import org.hibernate.loader.custom.Return;

import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import view.View;

public class MazeClientHandler extends Observable implements ClientHandler,View {
	ObjectOutputStream writer;
	BufferedReader reader;
	HashMap<String, Command> commands;
	Queue<Command> commandsList;
	@Override
	public void HandleClient(InputStream input, OutputStream outputStream) {
		try {
			writer=new ObjectOutputStream(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		reader=new BufferedReader(new InputStreamReader(input));
		commandsList= new LinkedList<Command>();
	}

	@Override
	public void start() {
		while(true){
			try {
				//get client commands until get exit
				String line=reader.readLine();
				System.out.println("The client send:"+line+"\n");
				if(line.equals("exit")){
					System.out.println("The client end the session");
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
		try {
			writer.writeObject("sentMaze");
			writer.writeObject(name);
			writer.writeObject(m);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void displaySolution(Solution s) {
		try {
			writer.writeObject("sentSolution");
			writer.writeObject(s);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void displayString(String msg) {
		try {
			writer.writeObject("sentString");
			writer.writeObject(msg);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void displayClue(String clue) {
		try {
			writer.writeObject("sentString");
			writer.writeObject(clue);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getDiagsMode(boolean diag) {
		Boolean diagObject=diag;
		try {
			writer.writeObject("sentDiagsMode");
			writer.writeObject(diagObject);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
