package server;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

//import client.Player;
//import core.BaseGamePanel;

public class Room implements AutoCloseable {
	private static SocketServer server;// used to refer to accessible server functions
	private String name;
	private final static Logger log = Logger.getLogger(Room.class.getName());

	// Commands
	private final static String COMMAND_TRIGGER = "/";
	private final static String CREATE_ROOM = "createroom";
	private final static String JOIN_ROOM = "joinroom";
	private List<ServerThread> clients = new ArrayList<ServerThread>();
	//private List<ClientPlayer> clients = new ArrayList<ClientPlayer>();
	//static Dimension gameAreaSize = new Dimension(400, 600);

	/*public Room(String name, boolean delayStart) {
    	super(delayStart);
    	this.name = name;
    	isServer = true;
    }*/

	public Room(String name) {
		this.name = name;
	}

	public Room(String prelobby, boolean b) {
		//super(b);
		this.name = prelobby;
		//isServer = true;
	}

	public static void setServer(SocketServer server) {
		Room.server = server;
	}

	public String getName() {
		return name;
	}



	protected synchronized void addClient(ServerThread client) {
		client.setCurrentRoom(this);
		boolean exists = false;
		//Iterator<>
		if (clients.indexOf(client) > -1) {
			log.log(Level.INFO, "Attempting to add a client that already exists");
		}
		else {
			clients.add(client);
			if (client.getClientName() != null) {
				client.sendClearList();
				sendConnectionStatus(client, true, "joined the room " + getName());
				updateClientList(client);
			}
		}
	}

	@SuppressWarnings("unused")
	private void updateClientList(ServerThread client) {
		Iterator<ServerThread> iter = clients.iterator();
		while (iter.hasNext()) {
			ServerThread c = iter.next();
			if (c != client) {
				boolean messageSent = client.sendConnectionStatus(c.getClientName(), true, null);
			}
		}
	}

	protected synchronized void removeClient(ServerThread client) {
		clients.remove(client);
		if (clients.size() > 0) {
			//sendMessage(client, "left the room");
			sendConnectionStatus(client, false, "left the room " + getName());
		}
		else {
			cleanupEmptyRoom();
		}
	}

	private void cleanupEmptyRoom() {
		// If name is null it's already been closed. And don't close the Lobby
		if (name == null || name.equalsIgnoreCase(SocketServer.LOBBY)) {
			return;
		}
		try {
			log.log(Level.INFO, "Closing empty room: " + name);
			close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void joinRoom(String room, ServerThread client) {
		server.joinRoom(room, client);
	}

	protected void joinLobby(ServerThread client) {
		server.joinLobby(client);
	}

	protected void createRoom(String room, ServerThread client) {
		if (server.createNewRoom(room)) {
			sendMessage(client, "Created a new room");
			joinRoom(room, client);
		}
	}

	/***
	 * Helper function to process messages to trigger different functionality.
	 * 
	 * @param message The original message being sent
	 * @param client  The sender of the message (since they'll be the ones
	 *                triggering the actions)
	 */
	private String processCommands(String message, ServerThread client) {
		//boolean wasCommand = false;
		String response = null;
		try {
			if (message.indexOf(COMMAND_TRIGGER) > -1) {
				String[] comm = message.split(COMMAND_TRIGGER);
				log.log(Level.INFO, message);
				String part1 = comm[1];
				String[] comm2 = part1.split(" ");
				String command = comm2[0];
				String clientName=comm2[1];
				if (command != null) {
					command = command.toLowerCase();
				}
				String roomName;
				switch (command) {
				case CREATE_ROOM:
					roomName = comm2[1];
					/*if (server.createNewRoom(roomName)) {
				    	joinRoom(roomName, client);
				    }*/
					createRoom(roomName, client);
					response = "Created room "+ roomName;
					break;
				case JOIN_ROOM:
					roomName = comm2[1];
					joinRoom(roomName, client);
					response = "Joined room "+ roomName;
					break;
				case "roll":
					Integer sidesOfDie=6;
					String num = Integer.toString((int)((Math.random() * sidesOfDie)+1));
					sendMessage(client, "<b>D: "+num+"</b>");
					break;

				case "flip":
					int randFlip = (int) Math.random() *2;
					if(randFlip==0) {

						response="<b><i>C: Heads</b></i>";
						//sendMessage(client, "C: Heads");
					}else {
						response="<b><i>C: Tails</b></i>";

						//sendMessage(client, "C: Tails");
					}
					break;
				case "mute":
					clientName = comm2[1];
					if(!client.isMuted(clientName))
					{
						client.mutesClients.add(clientName);
						response = "muted "+clientName;
					}
					break;
					//mute part1(username) from clients messages
					//-search for clients with username

					/*Iterator<ServerThread> iter = clients.iterator();
					while (iter.hasNext()) {
						ServerThread c = iter.next();
						//boolean messageSent = c.sendConnectionStatus(client.getClientName(), isConnect, message);
						String username=c.getClientName();
						if (username.toLowerCase().equals(comm2[1].toLowerCase()))
						{
							client.muteClientFromThis(comm2[1]);
						}


					}
					response = "muted"+comm2[1];*/
					//client.muteClientFromThis(part1);
				case "unmute":
					clientName = comm2[1];
					if(client.isMuted(clientName))
					{
						client.mutesClients.remove(clientName);
						response = "unmuted "+clientName;
					}
					break;

				}
			}else {
				response=message;
				if(response.indexOf("@") > -1) {
					String temp=message;
					List<String> users= new ArrayList<String>();
					while(response.indexOf("@")>-1) {
						String user = StringUtils.substringBetween(response, "@", " ");
						users.add(user);
						temp=temp.replace("@"+user, "");
						
						
					}
					//String mess = s1[s1.length-1];
					//System.out.println(mess);
					//mess += s1[0];
					/*for (int i = 1; i < StringUtils.countMatches(response, "@"); i++) {
						if (i % 2 == 0) {
							users.add(s1[i]);
						}
						
					}*/
					response = temp;
					sendPrivateMessage(client, response, users);
					//response = mess;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if(response.indexOf("@@") > -1) {
			String[] s1 = response.split("@@");
			String mess = "";
			mess += s1[0];
			for (int i = 1; i < s1.length; i++) {
				if (i % 2 == 0) {
					mess += s1[i];
				}
				else {
					mess += "<b>" + s1[i] + "</b>";
				}
			}
			response = mess;
			//message=response;
			//sendMessage(client, response);

		}
		if(response.indexOf("##") > -1) {
			String[] s1 = response.split("##");
			String mess = "";
			mess += s1[0];
			for (int i = 1; i < s1.length; i++) {
				if (i % 2 == 0) {
					mess += s1[i];
				}
				else {
					mess += "<i>" + s1[i] + "</i>";
				}
			}
			response = mess;	        
		}
		if(response.indexOf("!!") > -1) {
			String[] s1 = response.split("!!");
			String mess = "";
			mess += s1[0];
			for (int i = 1; i < s1.length; i++) {
				if (i % 2 == 0) {
					mess += s1[i];
				}
				else {
					mess += "<font color='red'>" + s1[i] + "</font>";
				}
			}
			response = mess;	        
		}
		if (response.indexOf("__") > -1) {
			String[] s1 = response.split("__");
			String mess = "";
			mess += s1[0];
			for (int i = 1; i < s1.length; i++) {
				if (i % 2 == 0) {
					mess += s1[i];
				}
				else {
					mess += "<u>" + s1[i] + "</u>";
				}
			}
			response = mess;
		}

		return response;
	}

	protected void sendConnectionStatus(ServerThread client, boolean isConnect, String message) {
		Iterator<ServerThread> iter = clients.iterator();
		while (iter.hasNext()) {
			ServerThread c = iter.next();
			boolean messageSent = c.sendConnectionStatus(client.getClientName(), isConnect, message);
			if (!messageSent) {
				iter.remove();
				log.log(Level.INFO, "Removed client " + c.getId());
			}
		}
	}

	/***
	 * Takes a sender and a message and broadcasts the message to all clients in
	 * this room. Client is mostly passed for command purposes but we can also use
	 * it to extract other client info.
	 * 
	 * @param sender  The client sending the message
	 * @param message The message to broadcast inside the room
	 */
	protected void sendMessage(ServerThread sender, String message) {
		log.log(Level.INFO, getName() + ": Sending message to " + clients.size() + " clients");
		//List<String> noMessageList = sender.getMutedUsers();
		String resp = processCommands(message,sender);
		if (resp==null) {
			// it was a command, don't broadcast
			return;
		}
		message = resp;
		Iterator<ServerThread> iter = clients.iterator();
		while (iter.hasNext()) {
			ServerThread client = iter.next();
			if(!client.isMuted(sender.getClientName())) {
				boolean messageSent = client.send(sender.getClientName(), message);
				if (!messageSent) {
					iter.remove();
					log.log(Level.INFO, "Removed client " + client.getId());
				}
			}
		}

	}
	
	
	protected void sendPrivateMessage(ServerThread sender, String message, List<String> users) {
		log.log(Level.INFO, getName() + ": Sending message to " + clients.size() + " clients");
		//List<String> noMessageList = sender.getMutedUsers();
		String resp = processCommands(message,sender);
		if (resp==null) {
			// it was a command, don't broadcast
			return;
		}
		message = resp;
		Iterator<ServerThread> iter = clients.iterator();
		while (iter.hasNext()) {
			ServerThread client = iter.next();
			if(users.contains(client.getName()))
				if(!client.isMuted(sender.getClientName())) {
					boolean messageSent = client.send(sender.getClientName(), message);
					if (!messageSent) {
						iter.remove();
						log.log(Level.INFO, "Removed client " + client.getId());
					}
				}
		}

	}

	/***
	 * Will attempt to migrate any remaining clients to the Lobby room. Will then
	 * set references to null and should be eligible for garbage collection
	 */
	@Override
	public void close() throws Exception {
		int clientCount = clients.size();
		if (clientCount > 0) {
			log.log(Level.INFO, "Migrating " + clients.size() + " to Lobby");
			Iterator<ServerThread> iter = clients.iterator();
			Room lobby = server.getLobby();
			while (iter.hasNext()) {
				ServerThread client = iter.next();
				lobby.addClient(client);
				iter.remove();
			}
			log.log(Level.INFO, "Done Migrating " + clients.size() + " to Lobby");
		}
		server.cleanupRoom(this);
		name = null;
		// should be eligible for garbage collection now
	}

	public List<String> getRooms(String room) {
		return server.getRooms(room);
	}

}