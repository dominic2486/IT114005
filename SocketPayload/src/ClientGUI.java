import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class ClientGUI extends JPanel implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	JFrame mainFrame = new JFrame("Socket Client GUI");
	JPanel topPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JTextField inputField = new JTextField(15);
    JButton sendBut = new JButton();
    JTextArea textArea = new JTextArea(5, 20);
	JScrollPane scrollPane = new JScrollPane(); 
	//JComboBox lobbyListBox = new JComboBox()
    ClientGUI here = this;
    Socket server;
    ObjectOutputStream out;
    ObjectInputStream in;
    static String name="GUI";
    
    
    public ClientGUI()
    {	
    	//Configure JPanels
    	topPanel.setBackground(new Color(0, 122, 0));
    	topPanel.setLayout(new FlowLayout());
    	centerPanel.setLayout(new FlowLayout());
    	inputField.addKeyListener(this);
    	
    	//Configure for textArea/Scroll pane
    	textArea.setLineWrap(true);
    	textArea.setWrapStyleWord(true);
    	textArea.setCaretPosition(textArea.getDocument().getLength());
    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	scrollPane.setPreferredSize(new Dimension(450, 350));
    	scrollPane.setViewportView(textArea);
    	textArea.setEditable(false);
    	
    	//Configure Buttons
    	sendBut.setText("Send");
    	sendBut.addActionListener(new SendInput());
    	sendBut.setEnabled(true);
    	
    	//Adding Components to panels
    	topPanel.add(inputField);
    	topPanel.add(sendBut);
    	centerPanel.add(scrollPane, BorderLayout.CENTER);
    	
    	//Adding panels to Frame
    	this.setLayout(new BorderLayout());
        this.add(topPanel, "North");
        this.add(bottomPanel, "South");
        //this.add(rightPanel, "East");
        //this.add(leftPanel, "West");
        this.add(centerPanel, "Center");
    }
	
	public void addMessage(String msg)
	{
		if(msg!=null) {
			textArea.setText(textArea.getText() + "\n" + msg );
		    textArea.setCaretPosition(textArea.getDocument().getLength());
		}
			//textArea.insert(msg,0);
			
		//textArea.
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == 10) 
        {
            sendMessage();
        }
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
	}
	
	public void sendMessage() {
		String line=inputField.getText();
		inputField.setText("");
		Payload p = new Payload();
		p.setPayloadType(PayloadType.MESSAGE);
		p.setMessage(line);
		try {
			out.writeObject(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Socket connect(String address, int port) {
		try {
			server = new Socket(address, port);
			addMessage("Client connected");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return server;
	}
	
	private void close() {
		if(server != null) {
			try {
				server.close();
				addMessage("Closed socket");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class SendInput implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			sendMessage();
			/*String line=inputField.getText();
			System.out.println("pushed");
			Payload p = new Payload();
			p.setPayloadType(PayloadType.MESSAGE);
			p.setMessage(line);
			try {
				ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
				out.writeObject(p);
			} catch (IOException e1) {
				e1.printStackTrace();
			}*/

//			/*try {
//	    		ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
//				ObjectInputStream in = new ObjectInputStream(server.getInputStream());
//	    	
//				//do {
//				if(!signedIn)
//				{
//					addMessage("Please enter a username to continue");
//					return;
//				}
//				else if(name=="") {
//					Payload p = new Payload();
//					name = inputField.getText();
//					p.setPayloadType(PayloadType.CONNECT);
//					p.setMessage(name);
//					out.writeObject(p);
//					signedIn=true;
//				}
//				else {
//					String line=inputField.getText();
//					System.out.println(line);
//					if(!"quit".equalsIgnoreCase(line) && line != null) {
//						//grab line and throw it into a payload object
//						Payload p = new Payload();
//						//we can also default payloadtype in payload
//						//to a desired value, though it's good to be clear
//						//what we're sending
//						p.setPayloadType(PayloadType.MESSAGE);
//						p.setMessage(line);
//						out.writeObject(p);
//					}
//					else {
//						System.out.println("Stopping input thread");
//						//we're quitting so tell server we disconnected so it can broadcast
//						Payload p = new Payload();
//						p.setPayloadType(PayloadType.DISCONNECT);
//						p.setMessage("bye");
//						out.writeObject(p);
//					}
//				}
//					
//		//}while(!server.isClosed() && name!=null && name.length() == 0);
//				
//				
//			}catch(Exception z) {
//				z.printStackTrace();
//			}
//			addMessage("ended");
		}
		
	}
	
	private void processPayload(Payload payload) {
		//addMessage(payload.toString());
		switch(payload.getPayloadType()) {
		case CONNECT:
			addMessage(
					String.format("Client \"%s\" connected", payload.getMessage())
			);
			break;
		case DISCONNECT:
			addMessage(
					String.format("Client \"%s\" disconnected", payload.getMessage())
			);
			break;
		case MESSAGE:
			addMessage(
					String.format("%s", payload.getMessage())
			);
			break;
		default:
			addMessage("Unhandled payload type: " + payload.getPayloadType().toString());
			break;
		}
	}
	
	
	public static void main(String[]args) throws IOException 
	{		
		int port = 3002;
		try{
			//not safe but try-catch will get it
			port = Integer.parseInt(args[0]);
		}
		catch(Exception e){
			System.out.println("Invalid port, using default 3002");
		}
		ClientGUI gui = new ClientGUI();
		name=(String)JOptionPane.showInputDialog("Enter your username: ");
		gui.connect("127.0.0.1", port);
		gui.display();
	}

	private void display() {
		mainFrame.setDefaultCloseOperation(3);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setContentPane(this);
        mainFrame.setPreferredSize(new Dimension(500, 450));
        mainFrame.pack();
        mainFrame.setVisible(true);
        try {
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			Payload p = new Payload();
			//name = inputField.getText();
			p.setPayloadType(PayloadType.CONNECT);
			p.setMessage(name);
			out.writeObject(p);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
        Thread fromServerThread = new Thread() {
			@Override
			public void run() {
				try {
					//ObjectInputStream in = new ObjectInputStream(server.getInputStream());
					Payload fromServer;
					//while we're connected, listen for payloads from server
					while(!server.isClosed() && (fromServer = (Payload)in.readObject()) != null) {
						//System.out.println(fromServer);
						processPayload(fromServer);
					}
					System.out.println("Stopping server listen thread");
				}
				catch (Exception e) {
					if(!server.isClosed()) {
						e.printStackTrace();
						System.out.println("Server closed connection");
					}
					else {
						System.out.println("Connection closed");
					}
				}
				finally {
					close();
				}
			}
		};
		fromServerThread.start();//start the thread
	}

}
