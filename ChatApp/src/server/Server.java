package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter out;

	// constructor
	public Server() {
		try {
			server = new ServerSocket(7777);
			System.out.println("Server is ready to accept connection");
			System.out.println("Waiting...");
			socket = server.accept();

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());

			startReading();
			startWriting();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startReading() {

		// catch the data from client using thread

		System.out.println("Start reading..");

		Runnable r1 = () -> {

			try {
				while (true) {
					String msg = br.readLine();
					if (msg.equals("exit") || msg.equals("stop") || msg.equals("end")) {
						System.out.println("Client terminated the chat");
						server.close();
						break;
					}
					System.out.println("Client : " + msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		new Thread(r1).start();
	}

	public void startWriting() {

		// it takes the data from the user and send response to it

		System.out.println("Writing started...");

		Runnable r2 = () -> {
			try {

				while (!socket.isClosed()) {
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

					String content = br1.readLine();

					out.println(content);
					out.flush();
					if (content.equals("exit") || content.equals("stop") || content.equals("end")) {
						socket.close();
						break;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		};
		new Thread(r2).start();
	}

	public static void main(String[] args) {
		System.out.println("This is server.. going to start server");

		new Server();
	}
}
