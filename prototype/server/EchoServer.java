// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

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
  //Class variables *************************************************
	enum permissionLevel {
		USER,
		BOSS,
		ADMIN
	}
	
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT =5555;
  
  /**
   * The interface type variable. It allows the implementation of 
   * the display method in the client.
   */
  ChatIF serverUI;

  
  //Constructors ****************************************************
  
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

  
  //Instance methods ************************************************
  
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
    		  	Command reply;
    		  	System.out.println(args[0]+" command");
    		  	switch (args[0]) {
	  	  			case ("!list"):
	  	  				//ctlg=(catalog) jdbc.listCatalog();
	  	  				//ctlg.printCatalog();
	  	  				client.sendToClient(jdbc.listCatalog());
				  	break;
		
		  	  		case ("!updatePrice"):
		  	  			client.sendToClient(jdbc.updatePrice(args));
				  	break;
		
		  	  		case ("!login"):
		  	  			System.out.println("TODO! !login");
		  	  			((User)cmd.obj).printUser();
		  	  			reply=new Command("TODO! !login ");
		  	  			reply.obj=cmd.obj;
		  	  			client.sendToClient(reply);
				  	break;
		
		  	  		case ("!signUp"):
		  	  			System.out.println("TODO! !signUp");
		  	  			((User)cmd.obj).printUser();
		  	  			reply=new Command("TODO! !signUp ");
		  	  			reply.obj=cmd.obj;
		  	  			client.sendToClient(reply);
				  	break;
		
		  	  		case ("!editItem"):
		  	  			System.out.println("TODO! !editItem");
		  	  			((Item)cmd.obj).printItem();
		  	  			reply=new Command("TODO! !editItem ");
		  	  			reply.obj=cmd.obj;
		  	  			client.sendToClient(reply);
				  	break;
		
		  	  		case ("!addItem"):
		  	  			System.out.println("TODO! !addItem");
		  	  			((Item)cmd.obj).printItem();
		  	  			reply=new Command("TODO! !addItem ");
		  	  			reply.obj=cmd.obj;
		  	  			client.sendToClient(reply);
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
    if (message.charAt(0) == '#')
    {
      runCommand(message);
    }
    else
    {
      // send message to clients
      serverUI.display(message);
      this.sendToAllClients("SERVER MSG> " + message);
    }
  }

  /**
   * This method executes server commands.
   *
   * @param message String from the server console.
 * @throws SSLException 
   */
  private void runCommand(String message) 
  {
    // run commands
    // a series of if statements
	  
	  if(message.equals("#additem")) {
		  Item newItem = new Item();
		  newItem.setId(7);
		  newItem.setName("flower11");
		  newItem.setPrice(22.32);
		  newItem.setAmount(47);
		  newItem.setShop("KukurikuShop");
		  
		  try {
			jdbc.addNewObjectToDataBase(newItem);
		} catch (SSLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  if(message.equals("#adduser")) {
		  signedUser sUser = new signedUser();
		  sUser.setId(326869716);
		  sUser.setPassword("123456");
//		  sUser.setPermissionLevel(permissionLevel.ADMIN);
		  sUser.setUserName("Feodor");
		  sUser.setPhone("0548835483");
		  
		  try {
			jdbc.addNewObjectToDataBase(sUser);
		} catch (SSLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		  
	  }
	  

    if (message.equalsIgnoreCase("#quit"))
    {
      quit();
    }
    else if (message.equalsIgnoreCase("#stop"))
    {
      stopListening();
    }
    else if (message.equalsIgnoreCase("#close"))
    {
      try
      {
        close();
      }
      catch(IOException e) {}
    }
    else if (message.toLowerCase().startsWith("#setport"))
    {
      if (getNumberOfClients() == 0 && !isListening())
      {
        // If there are no connected clients and we are not 
        // listening for new ones, assume server closed.
        // A more exact way to determine this was not obvious and
        // time was limited.
        int newPort = Integer.parseInt(message.substring(9));
        setPort(newPort);
        //error checking should be added
        serverUI.display
          ("Server port changed to " + getPort());
      }
      else
      {
        serverUI.display
          ("The server is not closed. Port cannot be changed.");
      }
    }
    else if (message.equalsIgnoreCase("#start"))
    {
      if (!isListening())
      {
        try
        {
          listen();
        }
        catch(Exception ex)
        {
          serverUI.display("Error - Could not listen for clients!");
        }
      }
      else
      {
        serverUI.display
          ("The server is already listening for clients.");
      }
    }
    else if (message.equalsIgnoreCase("#getport"))
    {
      serverUI.display("Currently port: " + Integer.toString(getPort()));
    }
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
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
