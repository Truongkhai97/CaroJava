package DemoChatServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ServerController implements Initializable {
	@FXML
	private TextArea taHienThi;
	@FXML
	private TextField tfNhapVao;
	@FXML
	private Button btnSend;

	Socket socket = null;
	ServerSocket serverSocket = null;
	int port = 9233;
	InetAddress host;
	Scanner in = null;
	PrintWriter out = null;
	String message;

	public void btnSend_Clicked() {
		taHienThi.appendText("\nServer>" + tfNhapVao.getText());
		out.println(tfNhapVao.getText());
		tfNhapVao.clear();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("Opening port...\n");
		try {
			serverSocket = new ServerSocket(port); // Step 1.
		} catch (IOException ioEx) {
			System.out.println("Unable to attach to port!");
			System.exit(1);
		}
		try {
			GetMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Cant call method getMessage");
		}
//		do {
//			handleClient();
//		} while (true);
	}

	public void btnMoKetNoi_Clicked() {
//		GetMessage();
//		do {
//		if(message.equals("***CLOSE***"))
//			System.exit(1);
		message = in.nextLine();
		System.out.println(message);
		taHienThi.appendText("\nClient>" + message);
//		} while (!message.equals("***CLOSE***"));
	}

	public void GetMessage() throws IOException {
//		serverSocket = new ServerSocket(port);
		socket = serverSocket.accept();
		System.out.println("Connected");
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream(), true);
//		do {
//			message = in.nextLine();
//			taHienThi.appendText("Client>" + message);
//		} while (!message.equals("***CLOSE***"));
	}

//	public void handleClient() {
////	      Socket link = null;                        //Step 2.
//
//		try {
//			socket = serverSocket.accept(); // Step 2.
//
//			Scanner input = new Scanner(socket.getInputStream()); // Step 3.
//			PrintWriter output = new PrintWriter(socket.getOutputStream(), true); // Step 3.
//
//			int numMessages = 0;
//			String message = input.nextLine(); // Step 4.
//
//			String servMess = "";
////			Scanner userEntry = new Scanner(System.in);
//
//			while (!message.equals("***CLOSE***")) {
//				System.out.println("Client: " + message);
//
//				System.out.print("Enter message: ");
////				servMess = userEntry.nextLine();
//				output.println(servMess); // Step 4.
//
//				message = input.nextLine();
//			}
//			output.println(numMessages + " messages received.");// Step 4.
//		} catch (IOException ioEx) {
//			ioEx.printStackTrace();
//		}
//
//		finally {
//			try {
//				System.out.println("\n* Closing connection... *");
//				socket.close(); // Step 5.
//			} catch (IOException ioEx) {
//				System.out.println("Unable to disconnect!");
//				System.exit(1);
//			}
//		}
//	}
}
