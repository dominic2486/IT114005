package client;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.commons.lang3.StringUtils;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

public class ClientUI extends JFrame implements Event {

	private static final long serialVersionUID = 1L;
	boolean saveOnCloseTester=false;
	CardLayout card;
	ClientUI self;
	JPanel textArea;
	JPanel userPanel;
	RoomsPanel roomsPanel;
	List<User> users = new ArrayList<User>();
	List<String> mutedUsers = new ArrayList<String>();
	JMenuBar menu;
	JTextField username;

	Dimension windowSize = new Dimension(400, 400);
	private final static Logger log = Logger.getLogger(ClientUI.class.getName());

	public ClientUI(String title) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu = new JMenuBar();
		JMenu roomsMenu = new JMenu("Actions");
		JMenuItem roomsSearch = new JMenuItem("Rooms");
		roomsSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("clicked Rooms");
				goToPanel("rooms");
			}

		});
		roomsMenu.add(roomsSearch);
		menu.add(roomsMenu);
		windowSize.width *= .8;
		windowSize.height *= .8;
		setPreferredSize(windowSize);
		setSize(windowSize);// This is needed for setLocationRelativeTo()
		setLocationRelativeTo(null);
		self = this;
		setTitle(title);
		card = new CardLayout();
		setLayout(card);
		createConnectionScreen();
		createUserInputScreen();

		createPanelRoom();
		createPanelUserList();
		this.setJMenuBar(menu);
		createRoomsPanel();
		addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent evt) {
				ClientUI.this.exitForm(evt);
			}
		});
		showUI();
	}


	/*
	 * Method is called when the jframe is being closed
	 */
	protected void exitForm(WindowEvent evt) {
		SocketClient.INSTANCE.sendMessage("/savemuted");
		if(saveOnCloseTester) {
			int choice = JOptionPane.showConfirmDialog(null, "Do you want to save chat before leaving?", "Save Chat History?", JOptionPane.YES_NO_OPTION);
			if(choice==JOptionPane.YES_OPTION) {
				saveMessages();
			}				
		}
		return;
	}

	void createConnectionScreen() {
		//System.out.printf
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel hostLabel = new JLabel("Host:");
		JTextField host = new JTextField("127.0.0.1");
		panel.add(hostLabel);
		panel.add(host);
		JLabel portLabel = new JLabel("Port:");
		JTextField port = new JTextField("3001");
		panel.add(portLabel);
		panel.add(port);
		JButton button = new JButton("Next");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String _host = host.getText();
				String _port = port.getText();
				if (_host.length() > 0 && _port.length() > 0) {
					try {
						connect(_host, _port);
						//self.next();
						goToPanel("userInput");
					}
					catch (IOException e1) {
						e1.printStackTrace();
						log.log(Level.SEVERE, "Error connecting");
					}
				}
			}
		});
		panel.add(button);
		this.add(panel);
	}

	void createUserInputScreen() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel userLabel = new JLabel("Username:");
		username = new JTextField();
		username.setFocusable(true);

		panel.add(userLabel);
		panel.add(username);
		JButton button = new JButton("Join");
		username.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "sendAction");
		username.getActionMap().put("sendAction", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent actionEvent) {
				button.doClick();
			}
		});
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = username.getText();
				if (name != null && name.length() > 0) {
					SocketClient.INSTANCE.setUsername(name);
					self.next();
				}
			}

		});

		panel.add(button);

		this.add(panel, "userInput");
		//username.requestFocusInWindow();
	}

	void createPanelRoom() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		textArea = new JPanel();
		textArea.setLayout(new BoxLayout(textArea, BoxLayout.Y_AXIS));
		textArea.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.add(scroll, BorderLayout.CENTER);

		JPanel input = new JPanel();
		input.setLayout(new BoxLayout(input, BoxLayout.X_AXIS));
		JTextField text = new JTextField();
		input.add(text);
		JButton button = new JButton("Send");
		text.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "sendAction");
		text.getActionMap().put("sendAction", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent actionEvent) {
				button.doClick();
			}
		});

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (text.getText().length() > 0) {
					SocketClient.INSTANCE.sendMessage(text.getText());
					text.setText("");
				}
			}

		});
		JButton saveButton = new JButton("Save Chat");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveMessages();
			}

		});
		input.add(button);
		input.add(saveButton);
		panel.add(input, BorderLayout.SOUTH);
		this.add(panel, "lobby");
	}

	void createPanelUserList() {
		userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
		userPanel.setAlignmentY(Component.TOP_ALIGNMENT);

		JScrollPane scroll = new JScrollPane(userPanel);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		Dimension d = new Dimension(100, windowSize.height);
		scroll.setPreferredSize(d);

		textArea.getParent().getParent().getParent().add(scroll, BorderLayout.EAST);

	}

	void saveMessages()
	{
		try {
			String filename = JOptionPane.showInputDialog(new JFrame(), "Enter the file name that you want");
			if(filename==null)
				filename = "output.txt";
			else if(!filename.contains(".txt"))
				filename+=".txt";
			BufferedWriter output = new BufferedWriter(new FileWriter(filename));
			//Object[] options = {"plaintext","html/text"};
			int choice = JOptionPane.showConfirmDialog(null, "Do you want the chat saved as plaintex(otherwise it saves the html/text)", "Save File", JOptionPane.YES_NO_OPTION);
			for(Component i:textArea.getComponents()) {
				if(choice==JOptionPane.YES_OPTION)
					output.write(((JEditorPane) i).getText().replaceAll("\\<.*?\\>", "").trim()+'\n');
				else
					output.write(((JEditorPane) i).getText());
			}
			output.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	void createRoomsPanel() {
		roomsPanel = new RoomsPanel(this);
		this.add(roomsPanel, "rooms");
	}

	void addClient(String name) {
		User u = new User(name);
		Dimension p = new Dimension(userPanel.getSize().width, 30);
		u.setPreferredSize(p);
		u.setMinimumSize(p);
		u.setMaximumSize(p);
		userPanel.add(u);
		users.add(u);
		pack();
	}

	void removeClient(User client) {
		userPanel.remove(client);

		client.removeAll();
		userPanel.revalidate();
		userPanel.repaint();
	}

	/***
	 * Attempts to calculate the necessary dimensions for a potentially wrapped
	 * string of text. This isn't perfect and some extra whitespace above or below
	 * the text may occur
	 * 
	 * @param str
	 * @return
	 */
	int calcHeightForText(String str) {
		FontMetrics metrics = self.getGraphics().getFontMetrics(self.getFont());
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(str);
		final int PIXEL_PADDING = 6;
		Dimension size = new Dimension(adv, hgt + PIXEL_PADDING);
		final float PADDING_PERCENT = 1.1f;
		// calculate modifier to line wrapping so we can display the wrapped message
		int mult = (int) Math.floor(size.width / (textArea.getSize().width * PADDING_PERCENT));
		//System.out.println(mult);
		mult++;
		return size.height * mult;
	}

	void addMessage(String str) {
		JEditorPane entry = new JEditorPane();
		entry.setContentType("text/html");
		entry.setEditable(false);
		//entry.setLayout(null);
		entry.setText(str);
		Dimension d = new Dimension(textArea.getSize().width, calcHeightForText(str));
		// attempt to lock all dimensions
		entry.setMinimumSize(d);
		entry.setPreferredSize(d);
		entry.setMaximumSize(d);
		textArea.add(entry);

		pack();
		//System.out.println(entry.getSize());
		JScrollBar sb = ((JScrollPane) textArea.getParent().getParent()).getVerticalScrollBar();
		sb.setValue(sb.getMaximum());
	}

	void next() {
		card.next(this.getContentPane());
	}

	void previous() {
		card.previous(this.getContentPane());
	}

	void goToPanel(String panel) {
		switch (panel) {
		case "rooms":
			// get rooms
			roomsPanel.removeAllRooms();
			SocketClient.INSTANCE.sendGetRooms(null);
			break;
		default:
			// no need to react
			break;
		}
		card.show(this.getContentPane(), panel);
		switch (panel) {
		case "userInput":
			username.grabFocus();
			break;
		default:
			// no need to react
			break;
		}
	}

	void connect(String host, String port) throws IOException {
		SocketClient.INSTANCE.registerCallbackListener(this);
		SocketClient.INSTANCE.connectAndStart(host, port);
	}

	void showUI() {
		pack();
		Dimension lock = textArea.getSize();
		textArea.setMaximumSize(lock);
		lock = userPanel.getSize();
		userPanel.setMaximumSize(lock);
		setVisible(true);
	}

	@Override
	public void onClientConnect(String clientName, String message) {
		log.log(Level.INFO, String.format("occ%s: %s", clientName, message));
		addClient(clientName);
		if (message != null && !message.trim().isEmpty()) {
			self.addMessage(String.format("%s: %s", clientName, message));
		}
	}

	@Override
	public void onClientDisconnect(String clientName, String message) {
		log.log(Level.INFO, String.format("ocd%s: %s", clientName, message));
		//self.addMessage(String.format("%s: %s", clientName, message));
		Iterator<User> iter = users.iterator();
		while (iter.hasNext()) {
			User u = iter.next();
			if (u.getName() == clientName) {
				removeClient(u);
				iter.remove();
				self.addMessage(String.format("%s: %s", clientName, message));
				break;
			}

		}
	}

	@Override
	public void onMessageReceive(String clientName, String message) {
		log.log(Level.INFO, String.format("%s: %s", clientName, message));
		self.addMessage(String.format("%s: %s", clientName, message));
	}

	public static void main(String[] args) {
		ClientUI ui = new ClientUI("My UI");
		if (ui != null) {
			log.log(Level.FINE, "Started");
		}
	}

	@Override
	public void onChangeRoom() {
		Iterator<User> iter = users.iterator();
		while (iter.hasNext()) {
			User u = iter.next();
			removeClient(u);
			iter.remove();
		}

	}

	@Override
	public void onGetRoom(String roomName) {
		if(roomsPanel != null)
		{
			roomsPanel.addRoom(roomName);
			pack();
		}

	}
}