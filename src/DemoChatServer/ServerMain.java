package DemoChatServer;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ServerMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("Server.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
//			primaryStage.setResizable(false);
			primaryStage.setTitle("SERVER");
			primaryStage.show();
//			System.out.println("Da in ra");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

