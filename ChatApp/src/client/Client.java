package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	Socket socket;

	BufferedReader br;
	PrintWriter out;

	// constructor
	public Client() {
		try {
			System.out.println("Sending request to server...");
			socket = new Socket("192.168.10.55", 7777);
			System.out.println("Connection established");

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
			while (true) {

				try {
					String msg = br.readLine();
					if (msg.equals("exit") || msg.equals("stop") || msg.equals("end")) {
						System.out.println("Server terminated the chat");
						socket.close();
						break;
					}
					System.out.println("Server : " + msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		System.out.println("This is client..");
		new Client();
	}
}
