package CaroClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BeforeStartController {
	@FXML
	Button btnSanSang;
	@FXML
	Button btnThoat;
	@FXML
	TextField tfIP;
	@FXML
	TextField tfName;
	private int port = 9231;
	Socket socket = null;

	public void btnSanSangClicked() throws IOException {
		System.out.println("Test event btnSS_Clicked"+tfIP.getText());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StartGame.fxml"));
		Parent root = loader.load();
		StartGameController controller=loader.getController();
		if(controller.connecting(tfIP.getText())) {
			controller.setIP(tfIP.getText());
			System.out.println("Do nothing");
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Demo Game Caro-client");
			stage.setResizable(false);
			stage.show();
			Stage oldStage = (Stage) btnSanSang.getScene().getWindow();
			oldStage.close();
		}
	}

	public void btnThoatClicked() {
		System.exit(0);
	}
}
