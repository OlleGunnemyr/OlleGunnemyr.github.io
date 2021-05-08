import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import tcpclient.TCPClient;

public class HTTPAsk {
	static int BUFFERSIZE = 1024;
    public static void main( String[] args) throws IOException{
        // Your code here
		int serverPort = Integer.parseInt(args[0]);
	    ServerSocket welcomeSocket = new ServerSocket(serverPort);
	    String ok = "HTTP/1.1 200 OK\r\n\r\n";
	    String notFound = "HTTP/1.1 404 Not Found\r\n\r\n";
	    String badRequest = "HTTP/1.1 400 Bad Request\r\n\r\n";

	    String output = null;

	    while(true){
			Socket connectionSocket = welcomeSocket.accept();
			connectionSocket.setSoTimeout(3000);
			byte[] fromClientBuffer = new byte[BUFFERSIZE];
			StringBuilder str = new StringBuilder();

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

			String[] splitResponse = str.toString().split("[ =&?/]");


			String hostname = null;
			int port = 0;
			String string = null;
			boolean check1, check2, check3, check4;
			check1 = check2 = check3 = check4 = false;

			if ((splitResponse.length > 1) && splitResponse[2].equals("ask")){
				for(int i = 0; i < splitResponse.length; i++){
					switch(splitResponse[i]){

						case "hostname":
						hostname = splitResponse[i+1];
						check1 = true;
						break;

						case "port":
						port = Integer.parseInt(splitResponse[i+1]);
						check2 = true;
						break;

						case "string":
						string = splitResponse[i+1];
						break;

						case "HTTP":
						check3 = true;
						break;

						case "GET":
						check4 = true;
						break;
					}
				}

				try{
					output = (string == null)? ok + TCPClient.askServer(hostname, port) : ok + TCPClient.askServer(hostname, port, string);
				}

				catch(IOException e){
					output = notFound;
				}

				if (!check1 || !check2 || !check3 || !check4){
					output = badRequest;
				}
			}

			else{
				output = badRequest;
			}

			connectionSocket.getOutputStream().write(output.getBytes(StandardCharsets.UTF_8));
			connectionSocket.close();
		}
    }
}

