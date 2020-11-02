import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.Debug;

public class SocketServer {
	int port = 3002;
	public static boolean isRunning = true;
	private List<Room> rooms = new ArrayList<Room>();
	private Room lobby;// here for convenience
	
	private void start(int port) {
		this.port = port;
		Debug.log("Waiting for client");
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			lobby = new Room("Lobby", this);
			rooms.add(lobby);
			while(SocketServer.isRunning) {
				try {
					Socket client = serverSocket.accept();
					Debug.log("Client connecting...");
					//Server thread is the server's representation of the client
					ServerThread thread = new ServerThread(client, lobby);
					thread.start();
					//add client thread to list of clients
					lobby.addClient(thread);
					Debug.log("Client added to clients pool");
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				isRunning = false;
				Thread.sleep(50);
				Debug.log("closing server socket");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Deprecated
	/*int getClientIndexByThreadId(long id) {
		for(int i = 0, l = rooms.size(); i < l;i++) {
			if()) rooms.get(i)).getId() == id) {
				return i;
			}
		}
		return -1;
	}*/
	/*public synchronized void broadcast(Payload payload, String name) {
		String msg = payload.getMessage();
		payload.setMessage(
				//prepending client name to front of message
				(name!=null?name:"[Name Error]") 
				//including original message if not null (with a prepended colon)
				+ (msg != null?": "+ msg:"")
		);
		broadcast(payload);
	}*/
	
	public synchronized void broadcast(Payload payload) {
		System.out.println("Sending message to " + rooms.size() + " clients");
		Iterator<Room> iter = rooms.iterator();
		while(iter.hasNext()) {
			Room client = iter.next();
			boolean messageSent = client.send(payload);
			if(!messageSent) {
				//if we got false, due to update of send()
				//we can assume the client lost connection
				//so let's clean it up
				iter.remove();
				System.out.println("Removed client " + client.getId());
			}
		}
	}
	//Broadcast given payload to everyone connected
	public synchronized void broadcast(Payload payload, long id) {
		//let's temporarily use the index as the client identifier to
		//show in all client's chat. You'll see why this is a bad idea
		//when clients disconnect/reconnect.
		int from = getClientIndexByThreadId(id);
		String msg = payload.getMessage();
		payload.setMessage(
				//prepending client name to front of message
				(from>-1?"Client[" + from+"]":"unknown") 
				//including original message if not null (with a prepended colon)
				+ (msg != null?": "+ msg:"")
		);
		//end temp identifier (maybe this won't be too temporary as I've reused
		//it in a few samples now)
		broadcast(payload);
		
	}
	//Broadcast given message to everyone connected
	public synchronized void broadcast(String message, long id) {
		Payload payload = new Payload();
		payload.setPayloadType(PayloadType.MESSAGE);
		payload.setMessage(message);
		broadcast(payload, id);
	}
	
	protected Room getLobby() {
		return lobby;
	}

	/***
	 * Helper function to check if room exists by case insensitive name
	 * 
	 * @param roomName The name of the room to look for
	 * @return matched Room or null if not found
	 */
	private Room getRoom(String roomName) {
		for (int i = 0, l = rooms.size(); i < l; i++) {
			if (rooms.get(i).getName().equalsIgnoreCase(roomName)) {
				return rooms.get(i);
			}
		}
		return null;
	}
	 

	protected synchronized boolean joinRoom(String roomName, ServerThread client) {
		Room newRoom = getRoom(roomName);
		Room oldRoom = client.getCurrentRoom();
		if (newRoom != null) {
			if (oldRoom != null) {
				Debug.log(client.getName() + " leaving room " + oldRoom.getName());
				oldRoom.removeClient(client);
			}
			Debug.log(client.getName() + " joining room " + newRoom.getName());
			newRoom.addClient(client);
			return true;
		}
		return false;
	}
	
	/*
	 * Attempts to create a room with given name if it doesn't exist already.
	 * 
	 * @param roomName The desired room to create
	 * @return true if it was created and false if it exists
	 */
	protected synchronized boolean createNewRoom(String roomName) {
		if (getRoom(roomName) != null) {
			// TODO can't create room
			Debug.log("Room already exists");
			return false;
		} else {
			Room room = new Room(roomName, this);
			rooms.add(room);
			Debug.log("Created new room: " + roomName);
			return true;
		}
	}
	
	public static void main(String[] args) {
		//let's allow port to be passed as a command line arg
		//in eclipse you can set this via "Run Configurations" 
		//	-> "Arguments" -> type the port in the text box -> Apply
		int port = 3002;//make some default
		if(args.length >= 1) {
			String arg = args[0];
			try {
				port = Integer.parseInt(arg);
			}
			catch(Exception e) {
				//ignore this, we know it was a parsing issue
			}
		}
		System.out.println("Starting Server");
		SocketServer server = new SocketServer();
		System.out.println("Listening on port " + port);
		server.start(port);
		System.out.println("Server Stopped");
	}
}