// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.text.ParseException;
import java.util.LinkedList;

import javax.net.ssl.SSLException;

import classes.*;
import ocsf.server.*;
import common.*;
import db.jdbc;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT =5555;
  
  private LinkedList<String> userList;
  
  /**
   * The interface type variable. It allows the implementation of 
   * the display method in the client.
   */
  ChatIF serverUI;
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

   /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   * @param serverUI The interface type variable.
   */
  public EchoServer(int port, ChatIF serverUI) throws IOException
  {
    super(port);
    this.serverUI = serverUI;
  }
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		Command cmd;
		if(msg instanceof Command) {
    	  cmd=(Command)msg;
    	  System.out.println("Message received: " + cmd.msg + " from \"" + 
      				client.getInfo("loginID") + "\" " + client);
		}
		else {
    	  System.out.println("no Command recieved");
    	  return;
		}
		if(cmd.msg.startsWith("!")) {
			try {
    		  	String args[] = cmd.msg.trim().split("\\s+");
    		  	System.out.println(args[0]+" command");
    		  	switch (args[0]) {
	  	  			case ("!list"):
	  	  				client.sendToClient(jdbc.listCatalog());
				  	break;
		
		  	  		case ("!login"):
		  	  			if(userList.contains(((User) cmd.obj).getUserName())) {
		  	  				cmd.msg="login - user alredy loged in";
		  	  			}
		  	  			else {
		  	  				Command res = new Command("");
		  	  				cmd=jdbc.logIn(cmd);
		  	  				if(cmd.msg.equals("login Success"))
		  	  					userList.add(((User) cmd.obj).getUserName());
		  	  			}
		  	  			client.sendToClient(cmd);
				  	break;
		
		  	  		case ("!signUp"):
		  	  			client.sendToClient(jdbc.signUp(cmd));
				  	break;
		
		  	  		case ("!editItem"):
		  	  			client.sendToClient(jdbc.editItem(cmd));
				  	break;
		
		  	  		case ("!addItem"):
		  	  			client.sendToClient(jdbc.addItem(cmd));
				  	break;
				  	
		  	  		case("!order"):
		  	  			client.sendToClient(jdbc.order(cmd));
		  	  		break;
		  	  		
		  	  		case("!validate"):
		  	  			client.sendToClient(jdbc.validate(cmd));
		  	  		break;
				  	
		  	  		case("!cancel"):
					try {
						client.sendToClient(jdbc.cancelOrder(cmd));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		  	  		break;
		  	  		
		  	  		case("!logout"):
		  	  			userList.remove(((User) cmd.obj).getUserName());
		  	  			cmd.msg="Loged Out";
		  	  			client.sendToClient(cmd);
		  	  		break;
		  	  		
		  	  		case("!getOrders"):
		  	  			client.sendToClient(jdbc.listUserOrders(cmd));
		  	  		break;
			  	
	  	  		
	  			} // switch
    	  } // try
    	  catch (SSLException e) {
				e.printStackTrace();
    	  } // catch
    	  catch (IOException e) {
				e.printStackTrace();
    	  } // catch
      } // if
}

  /**
   * This method handles all data coming from the UI
   *
   * @param message The message from the UI
   */
  public void handleMessageFromServerUI(String message)
  {
	  System.out.println("chat disabled.");
  }
 
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
	  userList = new LinkedList<String>();
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }

  /**
   * Run when new clients are connected. Implemented by Benjamin Bergman,
   * Oct 22, 2009.
   *
   * @param client the connection connected to the client
   */
  protected void clientConnected(ConnectionToClient client) 
  {
    // display on server and clients that the client has connected.
    String msg = "A Client has connected";
    System.out.println(msg);
    try {
		client.sendToClient(new Command("successfuly connected to server "));
	} catch (IOException e) {
		e.printStackTrace();
	}
  }

  /**
   * Run when clients disconnect. Implemented by Benjamin Bergman,
   * Oct 22, 2009
   *
   * @param client the connection with the client
   */
  synchronized protected void clientDisconnected(
    ConnectionToClient client)
  {
    // display on server and clients when a user disconnects
    String msg = client.getInfo("loginID").toString() + " has disconnected";
    
//	CHECK THIS SHIT *****************************
    
    System.out.println(msg);
    this.sendToAllClients(msg);
  }

  /**
   * Run when a client suddenly disconnects. Implemented by Benjamin
   * Bergman, Oct 22, 2009
   *
   * @param client the client that raised the exception
   * @param Throwable the exception thrown
   */
  synchronized protected void clientException(
    ConnectionToClient client, Throwable exception) 
  {
    String msg = client.getInfo("loginID").toString() + " has disconnected";

    System.out.println(msg);
    this.sendToAllClients(msg);
  }

  /**
   * This method terminates the server.
   */
  public void quit()
  {
    try
    {
      close();
    }
    catch(IOException e)
    {
    }
    System.exit(0);
  }


  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
