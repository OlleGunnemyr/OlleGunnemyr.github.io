import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class HTTPEcho {
	static int BUFFERSIZE = 1024;
    public static void main( String[] args) throws  IOException  {
        // Your code here
        int port = Integer.parseInt(args[0]);
        ServerSocket welcomeSocket = new ServerSocket(port);
        String header = "HTTP/1.1 200 OK\r\n\r\n";

        while(true){
			Socket connectionSocket = welcomeSocket.accept();
			connectionSocket.setSoTimeout(3000);
			byte[] fromClientBuffer = new byte[BUFFERSIZE];
			StringBuilder str = new StringBuilder();
			str.append(header);

			int fromClientLength = 0;

			while(fromClientLength != -1){

				try{
					fromClientLength = connectionSocket.getInputStream().read(fromClientBuffer);

					if (fromClientLength != -1){
						String serverResponse = new String(fromClientBuffer, 0, fromClientLength, StandardCharsets.UTF_8);
						str.append(serverResponse);
					}
				}

				catch(IOException exception){
					fromClientLength = -1;
				}
			}
			String response = str.toString();
			byte[] toClientBuffer = response.getBytes(StandardCharsets.UTF_8);

			connectionSocket.getOutputStream().write(toClientBuffer);
			connectionSocket.close();
		}

    }
}

