package sView;

import java.util.HashMap;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import view.View;

/**
* Main window interface, setting the default buttons and the main gameView widget.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 31.5.2015
*/
public class serverWindow extends BasicWindow implements View {

	public serverWindow(String title, int width, int height) {
		super(title, width, height);
	}
	String filepath;
	
	@Override
	protected void initWidgets() {
		shell.setLayout(new GridLayout(3,false));
		shell.setBackgroundImage(new Image(null, "resources/images/Servbackground.png"));
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		//button1 - Create New Maze
		Button BNewMaze=new Button(shell, SWT.PUSH);
		BNewMaze.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));		
		BNewMaze.setText("Create new maze");
		BNewMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						//new CreateNMaze("New Maze", 300, 150, display, gameView).run();
						//gameView.setFocus();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		
		shell.addHelpListener(new HelpListener() {
			
			@Override
			public void helpRequested(HelpEvent arg0) {
				MessageBox messageBox = new MessageBox(shell,  SWT.OK);
				messageBox.setMessage("Senia Kalma - 321969941\nBar Magnezi - 209043827 \n\t\t\t\tEnjoy.");
				messageBox.setText("Help");
				messageBox.open();
			}
		});
		
		//http://stackoverflow.com/questions/16899807/swt-modifying-window-close-button-red-x
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg) {
	            int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
	            MessageBox messageBox = new MessageBox(shell, style);
	            messageBox.setText("Exit");
	            messageBox.setMessage("Close the ServerGUI?(Server will still run)");
	            if(messageBox.open() == SWT.YES){
	            	arg.doit = true;
	            }else{
	            	arg.doit = false;
	            }
			}
		});
	}	//initWidgets
	
	public void start(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//gameView.start();
			}
		}).start();
		run();
		
	}
	public void exit(){
		//gameView.exit();
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Command getUserCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayMaze(Maze m, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(Solution s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayString(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayClue(String clue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getDiagsMode(boolean diag) {
		// TODO Auto-generated method stub
		
	}
	
} //Class close
