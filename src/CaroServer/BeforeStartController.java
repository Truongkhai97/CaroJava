package CaroServer;

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

public class BeforeStartController implements Initializable {
	@FXML
	Button btnKetNoi;
	@FXML
	Button btnThoat;
	@FXML
	TextField tfIP;
	@FXML
	TextField tfName;

	public void btnKetNoiClicked() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StartGame.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Demo Game Caro-server");
		stage.setResizable(false);
		stage.show();
		Stage oldStage = (Stage) btnKetNoi.getScene().getWindow();
		oldStage.close();

	}

	public void btnThoatClicked() {
		System.exit(0);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			tfIP.setText(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Không tìm thấy IP hiện tại");
		}
	}
}
