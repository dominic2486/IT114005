import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import utils.Debug;
//https://github.com/MattToegel/IT114/blob/SocketSample_C2S2MC/SocketsSample_C2S2MC/src/SocketServer.java

public class SocketServer3 {
	int port = 3002;
	public static boolean isRunning = true;
	private List<ServerThread> clients = new ArrayList<ServerThread>();
	
	private void start(int port) {
		this.port = port;
		Debug.log("Waiting for client");
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			while(SocketServer3.isRunning) {
				try {
					Socket client = serverSocket.accept();
					System.out.println("Client connecting...");
					//Server thread is the server's representation of the client
					ServerThread thread = new ServerThread(client, this);
					thread.start();
					//add client thread to list of clients
					clients.add(thread);
					System.out.println("Client added to clients pool");
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
				//Thread.sleep(50);
				Debug.log("closing server socket");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	int getClientIndexByThreadId(long id) {
		for(int i = 0, l = clients.size(); i < l;i++) {
			if(clients.get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}
	
	//Broadcast given message to everyone connected
	/*public synchronized void broadcast(String message, long id) {
		//let's temporarily use the index as the client identifier to
		//show in all client's chat. You'll see why this is a bad idea
		//when clients disconnect/reconnect.
		int from = getClientIndexByThreadId(id);
		message = (from>-1?"Client[" + from+"]":"unknown") + ": " + message;
		//end temp identifier
		System.out.println("Sending message to " + clients.size() + " clients");
		Iterator<ServerThread> iter = clients.iterator();
		while(iter.hasNext()) {
			ServerThread client = iter.next();
			boolean messageSent = client.send(message);
			if(!messageSent) {
				//if we got false, due to update of send()
				//we can assume the client lost connection
				//so let's clean it up
				iter.remove();
				System.out.println("Removed client " + client.getId());
			}
		}
	}*/
	public synchronized void broadcast(String message, long id) {
		// let's temporarily use the thread id as the client identifier to
		// show in all client's chat. This isn't good practice since it's subject to
		// change as clients connect/disconnect
		message = String.format("User[%d]: %s", id, message);
		// end temp identifier

		// loop over clients and send out the message
		Iterator<ServerThread> it = clients.iterator();
		while (it.hasNext()) {
			ServerThread client = it.next();
			boolean wasSuccessful = client.send(message);
			if (!wasSuccessful) {
				Debug.log("Removing disconnected client from list");
				it.remove();
				broadcast("Disconnected", id);
			}
		}
	}
	

	public static void main(String[] args) {
		// let's allow port to be passed as a command line arg
		// in eclipse you can set this via "Run Configurations"
		// -> "Arguments" -> type the port in the text box -> Apply
		int port = -1;// make some default
		if (args.length >= 1) {
			String arg = args[0];
			try {

				port = Integer.parseInt(arg);
			} catch (Exception e) {
				// ignore this, we know it was a parsing issue
			}
		}
		if (port > -1) {
			Debug.log("Starting Server");
			SocketServer3 server = new SocketServer3();
			Debug.log("Listening on port " + port);
			server.start(port);
			Debug.log("Server Stopped");
		}
	}
}