package DemoChatServer1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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

public class Server extends Application {
	private Socket socket = null;
	private final int port = 9233;
	private ServerSocket serverSocket;
//	String message;
	private Scanner in;
	private PrintWriter out;
	private Button btnConnect;
	private TextField tfMessage;
	private TextArea taDisplayMessage;
	private Button btnSend;
	private GridPane root;

	public static void main(String[] args) {
		launch(args);
	}
	
	public void DrawWindow(Stage primaryStage) {
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
	
	public void initSocket() {
		System.out.println("Opening port....\n");
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to attach to port");
			System.exit(1);
		}
		do {
			getMessage();
		} while (true);
	}
	
	public void getMessage() {
		// TODO Auto-generated method stub
		try {
			socket = serverSocket.accept();
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
			
			String message = in.nextLine();
			while (true) {
//				tfMessage.appendText("\nServer>" + message);
				StringBuffer t1 = new StringBuffer(tfMessage.getText());
				t1.append("\n");
				t1.append("Client: " + message);
				taDisplayMessage.setText(t1.toString());
				message = in.nextLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Khong the mo ket noi");
		} finally {
			try {
				System.out.println("\n* Closing connection... *");
				socket.close();
			} catch (IOException ioEx) {
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}

	@Override
	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
//		System.out.println("1");
		DrawWindow(primaryStage);
//		System.out.println("2");
		initSocket();
//		System.out.println("3");
	}
}
