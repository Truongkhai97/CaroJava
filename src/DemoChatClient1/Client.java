package DemoChatClient1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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

public class Client extends Application {
	private  InetAddress host;
	private  Socket socket = null;
	private  final int port = 9233;
	String message;
	Scanner in;
	PrintWriter out;
	Button btnConnect;
	TextField tfMessage;
	TextArea taDisplayMessage;
	Button btnSend;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		GridPane root = new GridPane();
		root.setPadding(new Insets(20));
		root.setHgap(15);
		root.setVgap(15);

		taDisplayMessage = new TextArea();
		root.add(taDisplayMessage, 0, 0, 3, 2);

		tfMessage = new TextField();
		root.add(tfMessage, 2, 3);

		btnConnect = new Button("Connect to server");
		btnConnect.setAlignment(Pos.CENTER_RIGHT);
		root.add(btnConnect, 2, 2);
		btnConnect.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				getMessage();
			}

		});

		btnSend = new Button("Send");
		btnSend.setAlignment(Pos.CENTER_RIGHT);
		root.add(btnSend, 3, 3);
		btnSend.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				out.println(tfMessage.getText());
			}
		});

		Scene scene = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("CLIENT");
		primaryStage.show();

	}

	private void getMessage() {
		// TODO Auto-generated method stub
		try {
			host = InetAddress.getLocalHost();
			socket = new Socket(host, port);
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Khong the tao host");
			
//			do {
				message = in.nextLine();
				tfMessage.appendText("\nServer>" + message);
//			} while (!message.equals("CLOSE"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Khong the tao socket");
		}

		finally
		{
			try
			{
				System.out.println(
							"\n* Closing connection... *");
				socket.close();					
			}
			catch(IOException ioEx)
			{
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}

}
