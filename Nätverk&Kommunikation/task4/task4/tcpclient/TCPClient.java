package tcpclient;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TCPClient
{
	private static int BUFFERSIZE = 1024;

	 public static String askServer(String hostname, int port, String ToServer) throws  IOException {

		 Socket clientSocket = new Socket(hostname, port);

	    	clientSocket.setSoTimeout(3000);

	    	byte[] fromUserBuffer = ToServer.getBytes(StandardCharsets.UTF_8);
	    	byte[] fromServerBuffer = new byte[BUFFERSIZE];

	    	clientSocket.getOutputStream().write(fromUserBuffer);
	    	clientSocket.getOutputStream().write('\n');

	    	int fromServerLength = 0;
	    	StringBuilder str = new StringBuilder();

	    	while(fromServerLength != -1) {

	    		try {
	    	fromServerLength = clientSocket.getInputStream().read(fromServerBuffer);

	    	if(fromServerLength != -1) {
	    	String serverResponse = new String(fromServerBuffer, 0, fromServerLength, StandardCharsets.UTF_8);
	    	str.append(serverResponse);
	    	}
	    		}

	    		catch(IOException exception) {
	    			fromServerLength = -1;
	    		}
	    	}

	    	clientSocket.close();
	    	return str.toString();
	    }

	    public static String askServer(String hostname, int port) throws  IOException {
	    	Socket clientSocket = new Socket(hostname, port);

	    	clientSocket.setSoTimeout(3000);

			byte[] fromServerBuffer = new byte[BUFFERSIZE];

	    	int fromServerLength = 0;
	    	StringBuilder str = new StringBuilder();

	    	while(fromServerLength != -1) {

	    		try {
	    	fromServerLength = clientSocket.getInputStream().read(fromServerBuffer);

	    	if(fromServerLength != -1) {
	    	String serverResponse = new String(fromServerBuffer, 0, fromServerLength, StandardCharsets.UTF_8);
	    	str.append(serverResponse);
	    	}
	    		}

	    		catch(IOException exception) {
	    			fromServerLength = -1;
	    		}
	    	}

	    	clientSocket.close();
	    	return str.toString();

	    }
}


