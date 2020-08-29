package KongCodeServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ServerController implements Initializable {
	@FXML
	private TextField input;
	@FXML
	private VBox view;
	@FXML
	private ScrollPane scroll;

	// socket
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private Scanner reader;
	private PrintWriter writer;

	public void send() {
		if (input.getText().trim().isEmpty())
			return;

		Label label = new Label(input.getText());
		label.setFont(new Font("Arial", 18));
		label.setTextFill(Color.WHITE);
		label.setPadding(new Insets(5, 14, 5, 14));
		label.setStyle("-fx-background-color:green; -fx-background-radius:15");
		HBox message = new HBox(label);
		message.setAlignment(Pos.BASELINE_RIGHT);
		view.getChildren().add(message);
		// gui tin di
		if (clientSocket.isConnected())
			writer.println(input.getText());
		input.setText("");

	}

	public void receive(String messageStr) {
		Label label = new Label(messageStr);
		label.setFont(new Font("Arial", 18));
		label.setTextFill(Color.WHITE);
		label.setPadding(new Insets(5, 14, 5, 14));
		label.setStyle("-fx-background-color:blue; -fx-background-radius:15");
		HBox message = new HBox(label);
		message.setAlignment(Pos.BASELINE_LEFT);
		view.getChildren().add(message);

	}

	public void initSocket(int port) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("waiting for cờ lai...");
				try {
					serverSocket = new ServerSocket(port);
					clientSocket = serverSocket.accept();
					System.out.println("connected...");
					reader = new Scanner(clientSocket.getInputStream());
					writer = new PrintWriter(clientSocket.getOutputStream(), true);
					System.out.println("bbb");
					while (clientSocket.isConnected()) {
						if (reader.hasNextLine()) {
							javafx.application.Platform.runLater(() -> receive(reader.nextLine()));
						}
						Thread.sleep(1000);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						clientSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		scroll.vvalueProperty().bind(view.heightProperty());
	}
}
