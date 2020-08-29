package KongCodeClient;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ClientMain extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		ClientController controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientScene.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			controller = loader.getController();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Chat demo -client");
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					// TODO Auto-generated method stub
					Platform.exit();
					System.exit(0);

				}
			});
			System.out.println("aaa");

		} catch (Exception e) {
			e.printStackTrace();
		}
		controller.initSocket(1234);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
