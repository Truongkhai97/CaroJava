package DemoChatServer1;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Server1 extends Application {

	private static Socket socket = null;
	private static final int port = 9233;
	private static ServerSocket serverSocket;
//	String message;
	private static Scanner in;
	private static PrintWriter out;
	private static Button btnConnect;
	private static TextField tfMessage;
	private static TextArea taDisplayMessage;
	private static Button btnSend;
	private static GridPane root;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		launch(args);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Stage primaryStage) {
		root = new GridPane();
		root.setPadding(new Insets(20));
		root.setHgap(15);
		root.setVgap(15);

		taDisplayMessage = new TextArea();
		taDisplayMessage.setEditable(false);
		root.add(taDisplayMessage, 0, 0, 3, 2);

		tfMessage = new TextField();
		root.add(tfMessage, 2, 3);

//		btnConnect = new Button("Open connection");
//		btnConnect.setAlignment(Pos.CENTER_RIGHT);
//		btnConnect.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				getMessage();
//			}
//		});
//		root.add(btnConnect, 2, 2);

		btnSend = new Button("Send");
		btnSend.setAlignment(Pos.CENTER_RIGHT);
		btnSend.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				out.println(tfMessage.getText());
				StringBuffer t1 = new StringBuffer(tfMessage.getText());
				t1.append("\n");
				t1.append("Client: " + tfMessage);
				taDisplayMessage.setText(t1.toString());
			}
		});
		root.add(btnSend, 3, 3);

		Scene scene = new Scene(root, 500, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("SERVER");
		primaryStage.show();
	}

	public static void initSocket() {
		System.out.println("Opening port...\n");
		try {
			serverSocket = new ServerSocket(port); // Step 1.
		} catch (IOException ioEx) {
			System.out.println("Unable to attach to port!");
			System.exit(1);
		}
		do {
			handleClient();
		} while (true);
	}

	private static void handleClient() {

		try {
			socket = serverSocket.accept(); // Step 2.

			in = new Scanner(socket.getInputStream()); // Step 3.
			out = new PrintWriter(socket.getOutputStream(), true); // Step 3.

			String message = in.nextLine(); // Step 4.

			while (true) {
				StringBuffer t1 = new StringBuffer(taDisplayMessage.getText());
				t1.append("\n");
				t1.append("Client: " + message);
				taDisplayMessage.setText(t1.toString());
				message = in.nextLine();

			}

		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}

		finally {
			try {
				System.out.println("\n* Closing connection... *");
				socket.close(); // Step 5.
			} catch (IOException ioEx) {
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		initialize(primaryStage);
		initSocket();
	}
}
