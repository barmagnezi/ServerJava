package sView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observer;
import java.util.Queue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

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
public class serverWindow extends BasicWindow implements SView {
	HashMap<String, Command> commands;
	Queue<Command> commandsList;
	
	int width,height;
	Text TIP;
	Text TPort;
	
	public serverWindow(String title, int width, int height) {
		super(title, width, height);
		this.width=width;
		this.height=height;
		commandsList= new LinkedList<Command>();
	}
	String filepath;
	
	public void setCommands(HashMap<String, Command> commands) {
		//System.out.println(commands.size());
		this.commands=commands;
	}

	public Command getUserCommand() {
		return commandsList.poll();
	}
	
	@Override
	protected void initWidgets() {
		shell.setLayout(new GridLayout(3,false));
		System.out.println("VIEW INITWIDGETS");
		
		Image image = new Image(null,"resources/images/Servbackground.png");
		shell.setBackgroundImage(new Image(null, image.getImageData().scaledTo(this.width,this.height)));
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		
		//Text1 - Current IP
		this.TIP = new Text(shell, SWT.BORDER);
		this.TIP.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
		//TIP.setText(myServer.getPort());
		if (commands.get("getIP")==null)
			System.out.println("error");
		commandsList.add(commands.get("getIP"));
		this.setChanged();
		this.notifyObservers("nothing");
		
		//Text1 - Current IP
		this.TPort = new Text(shell, SWT.BORDER);
		this.TPort.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
		//TIP.setText(myServer.getPort());
		
		if (commands.get("getPort")==null)
			System.out.println("error");
		commandsList.add(commands.get("getPort"));
		this.setChanged();
		this.notifyObservers("nothing");
		
		TPort.setEnabled(false);
		TPort.addMouseListener(new MouseListener() {
				@Override
				public void mouseDown(MouseEvent e) {
					TPort.setEnabled(true);
				}

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					TPort.setEnabled(true);
				}

				@Override
				public void mouseUp(MouseEvent arg0) {
				}
		});
		
		//button1 - Save port
		Button BNewMaze=new Button(shell, SWT.PUSH);
		BNewMaze.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));		
		BNewMaze.setText("Save port");
		BNewMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						if(Integer.parseInt(TPort.getText())<1024){
							//Error
						}
						else{
							if (commands.get("setPort")==null)
								System.out.println("error");
							commandsList.add(commands.get("setPort"));
							setChanged();
							notifyObservers(TPort.getText());
							
							TPort.setEnabled(true);
						}
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		//List1 - Client IP
		List CIP = new List(shell, SWT.SINGLE | SWT.BORDER);
		List Cport = new List(shell, SWT.SINGLE | SWT.BORDER);
		List Ctime = new List(shell, SWT.SINGLE | SWT.BORDER);
		
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
		shell.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e){
                Rectangle rect = shell.getBounds();
                if(rect.height!=height || rect.width != width)
                	shell.setBounds(rect.x, rect.y, width, height);
			}
		});
		
	}	//initWidgets
	
	public void start(){
		this.setChanged();
		this.notifyObservers("start");
		run();
	}
	public void exit(){
		//gameView.exit();
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

	@Override
	public void setIP(String ip) {
		System.out.println("serverWindow-setip-ip: "+ip);
		TIP.setText(ip);
	}

	@Override
	public void setPort(int port) {
		System.out.println("serverWindow-setport-port: "+port);
		String temp=""+port;
		this.TPort.setText(temp);
		//this.TPort.setText("ASADSADS");
	}

	@Override
	public void update() {
		
	}
	
} //Class close
