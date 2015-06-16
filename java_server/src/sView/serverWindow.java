package sView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import View.Command;
import sModel.MyClient;

/**
* Server Main window, setting the default buttons and the main gameView widget.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 11.5.2015
*/
public class serverWindow extends BasicWindow implements SView {
	HashMap<String, Command> commands;
	Queue<Command> commandsList;
	
	int width,height;
	Text TIP;
	Text TPort;
	List CList;
	
	boolean isKilled;
	
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
		
		Image image = new Image(null,"resources/images/Servbackground.png");
		shell.setBackgroundImage(new Image(null, image.getImageData().scaledTo(this.width,this.height)));
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		//Label1 - IP
		Label SIP = new Label(shell, SWT.NONE);
		SIP.setText("Current Server IP: ");
		SIP.setBackground(new Color(null, new RGB(255, 255, 255)));
		
		//Text1 - Current IP
		TIP = new Text(shell, SWT.BORDER);
		TIP.setBackground(new Color(null, new RGB(255, 255, 255)));
		TIP.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 2, 1));
		TIP.setEnabled(false);

		//Label2 - Port
		Label Sport = new Label(shell, SWT.NONE);
		Sport.setText("Current Server Port: ");
		Sport.setBackground(new Color(null, new RGB(255, 255, 255)));
		
		//Text2 - Current Port
		TPort = new Text(shell, SWT.BORDER);
		TPort.setBackground(new Color(null, new RGB(255, 255, 255)));
		TPort.setEnabled(false);
		//TIP.setText(myServer.getPort());
		
		//Updating IP and Port by the data in the model
		commandsList.add(commands.get("getIP"));
		this.setChanged();
		this.notifyObservers("nothing");
		commandsList.add(commands.get("getPort"));
		this.setChanged();
		this.notifyObservers("nothing");
		
		/*TPort.setEditable(false);
		TPort.addMouseListener(new MouseListener() {
				@Override
				public void mouseDown(MouseEvent e) {
					TPort.setEnabled(true);
					System.out.println("1");
				}

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					TPort.setEnabled(true);
					System.out.println("2");
				}

				@Override
				public void mouseUp(MouseEvent arg0) {
					System.out.println("3");
				}
		});*/
		
		//button1 - Save port
		/*Button BNewMaze=new Button(shell, SWT.PUSH);
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
		});*/
		Label History = new Label(shell, SWT.NONE);
		History.setText("History of request:");
		History.setBackground(new Color(null, new RGB(255, 255, 255)));
		History.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 2, 1));
		
		//List for Clients -	<IP>   <PORT>   <TIME>
		CList = new List(shell, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.DOUBLE_BUFFERED);
		CList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3));
		CList.setBackground(new Color(null, new RGB(255, 255, 255)));
		CList.getVerticalBar().setVisible(true);
		commandsList.add(commands.get("reqUp"));
		setChanged();
		notifyObservers("nothing");
		shell.addHelpListener(new HelpListener() {
		
			@Override
			public void helpRequested(HelpEvent arg0) {
				MessageBox messageBox = new MessageBox(shell,  SWT.OK);
				messageBox.setMessage("Senia Kalma - 321969941\nBar Magnezi - 209043827 \n\t\t\t\tEnjoy.");
				messageBox.setText("Help");
				messageBox.open();
			}
		});
		
		//button2 -	Load Server properties - (Sprop)
		Button Sprop=new Button(shell, SWT.PUSH);
		Sprop.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 2));
		Sprop.setText("Load server setting");
		Sprop.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
  						FileDialog fd=new FileDialog(shell,SWT.OPEN);
  						fd.setText("open");
  						fd.setFilterPath("");
  					  String[] names = {
  					      "Extensible Markup Language (*.xml)"};
  						String[] filterExt = { "*.xml"};
  						fd.setFilterNames(names);
  						fd.setFilterExtensions(filterExt);
  						fd.open();
  						if(fd.getFileName()=="")
  							return;
  						commandsList.add(commands.get("setNewProperties"));
  						System.out.println(fd.getFilterPath()+"/"+fd.getFileName());
  						setChanged();
  						notifyObservers(fd.getFilterPath()+"/"+fd.getFileName());		//Need to complete
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		//button3 - Load Game properties - (Gprop)
		Button Gprop=new Button(shell, SWT.PUSH);
		Gprop.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 2));
		Gprop.setText("Load game setting");
		Gprop.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
  						FileDialog fd=new FileDialog(shell,SWT.OPEN);
  						fd.setText("open");
  						fd.setFilterPath("");
  					  String[] names = {
  					      "Extensible Markup Language (*.xml)"};
  						String[] filterExt = { "*.xml"};
  						fd.setFilterNames(names);
  						fd.setFilterExtensions(filterExt);
  						fd.open();
  						if(fd.getFileName()=="")
  							return;
  						setChanged();
  						commandsList.add(commands.get("newModelprop"));
						setChanged();
  						notifyObservers(fd.getFilterPath()+"/"+fd.getFileName());
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		//button4 - Stop Server
		Button Stop=new Button(shell, SWT.PUSH);
		Stop.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, false, 1, 2));
		Stop.setText("Stop the server");
		Stop.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						commandsList.add(commands.get("kill"));
						setChanged();
						notifyObservers("nothing");
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		//http://stackoverflow.com/questions/16899807/swt-modifying-window-close-button-red-x
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg) {
	            int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
	            MessageBox messageBox = new MessageBox(shell, style);
	            messageBox.setText("Exit");
	            commandsList.add(commands.get("isKilled"));
				setChanged();
				notifyObservers("");
				if(isKilled==false)
					messageBox.setMessage("Close the ServerGUI?(Server will still run)");
				else
					messageBox.setMessage("Close the GUI?");
	            if(messageBox.open() == SWT.YES){
	            	arg.doit = true;
					/*commandsList.add(commands.get("killServer"));
					setChanged();
					notifyObservers("nothing");*/
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
		while(true){
			commandsList.add(commands.get("isKilled"));
			this.setChanged();
			this.notifyObservers("");
			if(isKilled==false){
				System.out.println("The server is still running...");
				System.out.print("Do you want to shutdown the server?(y/n)\n");
				BufferedReader r=new BufferedReader(new InputStreamReader(System.in));
				try {
					if(r.readLine().equals("y")){
						commandsList.add(commands.get("kill"));
						commandsList.add(commands.get("exit"));
						this.setChanged();
						this.notifyObservers("");
						r.close();
						return;
					}else{
						System.out.print("if you want open the GUI\n Type GUI and press enter\n");
						if(r.readLine().equalsIgnoreCase("GUI")){
							this.display=new Display();
							this.shell=new Shell(this.display);
							run();
						}	
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void exit(){
		if(!shell.isDisposed())
			shell.dispose();
		commandsList.add(commands.get("exit"));
		this.setChanged();
		this.notifyObservers("");
	}

	@Override
	public void displayString(String msg) {
		getDisplay();
		Display.getDefault().asyncExec(new Runnable() {
		//getDisplay().getDefault().asyncExec(new Runnable() {		//Was just this line(without 2 above)
		    public void run() {
				MessageBox messageBox = new MessageBox(shell,  SWT.OK);
				messageBox.setMessage(msg);
				messageBox.setText("Message");
				messageBox.open();
			    }
			});
	}


	@Override
	public void setIP(String ip) {
		TIP.setText(ip);
	}

	@Override
	public void setPort(int port) {
		String temp=""+port;
		this.TPort.setText(temp);
	}

	@Override
	public void update(ArrayList<MyClient> arrayList) {
		getDisplay();
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
		    	if(CList!=null){
			    	CList.removeAll();
			    	//System.out.println(arrayList.get(0).getClient().getPort());					//	8327 - Technical port
			    	//System.out.println(arrayList.get(0).getClient().getLocalPort());				//	5401 - Port(for connection)
			    	//System.out.println(arrayList.get(0).getClient().getRemoteSocketAddress());	//	/127.0.0.1:8327
			    	//System.out.println(arrayList.get(0).getClient().getLocalAddress());			//	/127.0.0.1
			    	//System.out.println(arrayList.get(0).getClient().getLocalSocketAddress());		//	/127.0.0.1:5401
			    	FontData[] fD = TIP.getFont().getFontData();
			    	fD[0].setHeight(10);
			    	fD[0].setStyle(SWT.BOLD);
			    	CList.setFont(new Font(display,fD[0]));
			    	boolean flag;
			    	for(int i=0;i<arrayList.size();i++){
			    		flag=true;
			    		/*for(int j=0;j<arrayList.size();j++){
			    			if(arrayList.get(i).getClientNum()==arrayList.get(j).getClientNum())
			    				flag=false;
			    		}*/
			    		if(flag==true)
			    			if(arrayList.get(i).getClientNum()!=-1)
			    				CList.add("IP: "+arrayList.get(i).getIP()+"   "+"Port: "+arrayList.get(i).getPort()+"   Local Port: "+ arrayList.get(i).getLocalPort() +"       Client request: " + arrayList.get(i).getClientNum() + "       " + arrayList.get(i).getTimeConnected());
			    			else
			    				CList.add("                                                         SERVER IS DOWN");
			    	}
		    	}
		    }
		});
	}

	@Override
	public void setKilled(boolean k) {
		isKilled=k;
	}

	@Override
	public void showPort(int port) {
		if(TPort!=null)
			TPort.setText(""+port);
	}

	/*@Override		//OLD, BEFORE ARRAYLIST<MYCLIENTS>
	public void update(String array) {	//Array is 10.5.5.5 5400 12/06/15-19:01&10.6.6.6 5401 12/06/15-19:05&10.0...
												//	STRING	INT		STRING	   	STRING 	INT		STRING
		CList.removeAll();
		boolean flag=false;
		int i=0;
		String line[];
		String data[];
		if(array!=null)
			flag=true;
		while(flag==true){
			line=array.split("&");		//line[0] = IP PORT TIME, line[1] = IP PORT TIME
			data=line[i].split(" ");	//Data[0] = IP, Data[1]=PORT, Data[2]=TIME
			CList.add(data[0]+"\t"+data[1]+"\t"+data[2]);	//<IP>-tab-<port>-tab-<time>
			i++;
			if(i==line.length)
				flag=false;
		}
	}*/
	
} //Class close
