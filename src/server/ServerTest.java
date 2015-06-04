package server;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import client.ClientRequester;



public class ServerTest {
	private Socket socket;
	private ClientRequester cr;
	private OutputStream os;
	private static final int MOVIE = 1;
	private static final int IDLE = 0;
	private static final int EXIT = 2;
	private static final byte[] CRLF = { 13, 10 };
	//.main(new String[] {"arg1", "arg2", "arg3"});

	@Test
	public void testServer() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 1234);
		os = socket.getOutputStream();
		
		os.write(MOVIE);
		os.write(CRLF);
		
		os.write(IDLE);
		os.write(CRLF);
		
		os.write(EXIT);
		os.write(CRLF);
		
		
		fail ("Not yet implemented");
	}

}
