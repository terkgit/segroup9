package client;

import java.io.*;

import GUI.GUIController;
import client.*;
import common.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  static ChatClient client;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   * @param loginID The user's ID.
   */
  public ClientConsole(final String loginID, final String host, final int port) 
  {
    try 
    {
      client= new ChatClient(loginID, host, port, this);
    } 
    catch(final IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
      final BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true) 
      {
        message = fromConsole.readLine();
        client.handleMessageFromClientUI(message);
      }
    } 
    catch (final Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(final String message) 
  {
    System.out.println(message);
    GUIController.display(message);
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The user ID.
   * @param args[1] The host to connect to.
   * @param args[2] The port to connect to.
   */
  public static void main(final String[] args) 
  {
    String host = "";
    int port = 0;  //The port number
    String loginID = "";
    try
    {
      loginID = args[0];
    }
    catch(final ArrayIndexOutOfBoundsException e)
    {
      System.out.println("usage: java ClientConsole loginID [host [port]]");
      System.exit(1);
    }
    try
    {
      host = args[1];
    }
    catch(final ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    try {
      port = Integer.parseInt(args[2]);
    } catch (final ArrayIndexOutOfBoundsException e){
      port = DEFAULT_PORT;
    }
    final ClientConsole chat= new ClientConsole(loginID, host, port);
    chat.accept();  //Wait for console data
  }


	public static void start(String[] args) {
		// TODO Auto-generated method stub
	    String host = "";
	    int port = 0;  //The port number
	    String loginID = "";
	    try
	    {
	      loginID = args[0];
	    }
	    catch(final ArrayIndexOutOfBoundsException e)
	    {
	      System.out.println("usage: java ClientConsole loginID [host [port]]");
	      System.exit(1);
	    }
	    try
	    {
	      host = args[1];
	    }
	    catch(final ArrayIndexOutOfBoundsException e)
	    {
	      host = "localhost";
	    }
	    try {
	      port = Integer.parseInt(args[2]);
	    } catch (final ArrayIndexOutOfBoundsException e){
	      port = DEFAULT_PORT;
	    }
	    final ClientConsole chat= new ClientConsole(loginID, host, port);
//	    chat.accept();  //Wait for console data
	}


	public static void send(Object obj) {
		String[] parse=(String[])obj;
		String message=parse[0];
		client.handleMessageFromClientUI(message);
	}
}
//End of ConsoleChat class
