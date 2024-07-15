package serverGUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import clientGUI.ClientGUI;

@SuppressWarnings("serial")
public class ServerGUI extends JFrame {

	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter out;

	// component

	private JLabel heading = new JLabel("Server Area");
	private JTextArea messageArea = new JTextArea();
	private JTextField messageInput = new JTextField();

	private Font font = new Font("Roboto", Font.BOLD, 20);

	// constructor
	public ServerGUI() {
		try {
			server = new ServerSocket(7777);
			System.out.println("Server is ready to accept connection");
			System.out.println("Waiting...");
			socket = server.accept();

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());

			createGUI();
			handleEvent();
			startReading();
		} catch (Exception e) {
			System.out.println("Someting went wrong...");
		}
	}

	private void handleEvent() {
		messageInput.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					String contentSend = messageInput.getText();
					messageArea.append("Me : " + contentSend + "\n");
					out.println(contentSend);
					out.flush();
					messageInput.setText("");
					messageInput.requestFocus();
				}
			}

		});
	}

	private void createGUI() {
		this.setTitle("Server Side");
		this.setSize(600, 500);
		this.setLocationRelativeTo(null); // align to center the window

		// component coding

		heading.setFont(new Font("Roboto", Font.BOLD, 45));
		messageArea.setFont(font);
		messageInput.setFont(font);

		URL imageUrl = ClientGUI.class.getResource("logo3.png");
		int customWidth = 100;
		int customHeight = 50;

		ImageIcon icon = new ImageIcon(imageUrl, "Logo");
		icon.setImage(icon.getImage().getScaledInstance(customWidth, customHeight, java.awt.Image.SCALE_SMOOTH));

		heading.setIcon(icon);

		heading.setHorizontalTextPosition(SwingConstants.LEFT);
		heading.setVerticalTextPosition(SwingConstants.BOTTOM);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

		messageArea.setEditable(false);

		// layout frame

		this.setLayout(new BorderLayout());

		// adding component into frame
		this.add(heading, BorderLayout.NORTH);
		JScrollPane jScrollPane = new JScrollPane(messageArea);

		this.add(jScrollPane, BorderLayout.CENTER);
		this.add(messageInput, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	// start reading
	public void startReading() {

		// catch the data from client using thread

		System.out.println("Start reading..");

		Runnable r1 = () -> {
			while (!server.isClosed()) {

				try {
					String msg = br.readLine();
					if (msg.equals("exit") || msg.equals("stop") || msg.equals("end")) {
						System.out.println("Client terminated the chat");
						JOptionPane.showMessageDialog(this, "Client Terminated the chat");
						messageInput.setEnabled(false);
						server.close();
						break;
					}
//					System.out.println("Server : " + msg);
					messageArea.append("Client : " + msg + "\n");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, "Server is closed..");
					System.exit(0);
//					e.printStackTrace();
				}
			}
		};
		new Thread(r1).start();
	}

	// start writing
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
				JOptionPane.showMessageDialog(this, "Someting went wrong..");
				System.exit(0);
//				e.printStackTrace();
			}
		};
		new Thread(r2).start();
	}

	public static void main(String[] args) {
		System.out.println("This is Server... ");
		new ServerGUI();
	}

}
