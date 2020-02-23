package Transfer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import Transfer.UI.Launcher;

public class Bootstrap {

	public static Delay delay = new Delay(120); // FPS
	public static int password = 1000 + new Random().nextInt(9000);
	public static List<Socket> connecters = new ArrayList<>();
	public static int BLOCK_SIZE = 8192; // Byte 단위 
	public static Launcher frame;
	public static void main(String[] args) {

		delay.setSyncDelay(true);
		
		Thread server = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					ServerSocket socket = new ServerSocket(7532);
					while(true) {
						Socket client = socket.accept();
						System.out.println("확인");
						ClientHandle handler = new ClientHandle(client);
						handler.start();
					}
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		server.start();		
		frame = new Launcher();
	}

}
