package CaroServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.xml.ws.handler.MessageContext;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Light.Point;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class StartGameController implements Initializable {
	@FXML
	private GridPane grpBanCo;
	@FXML
	private Button btnSend;
	@FXML
	private TextArea taChat;
	@FXML
	private Label lblTurn;
	@FXML
	private ProgressBar pbCountTime;

	Boolean flag = true;

	private int port = 9231;
	private InetAddress host;
	Socket socket = null;
	ServerSocket serverSocket;
	Scanner in;
	PrintWriter out;
	String message;

	private OCo matrix[][] = new OCo[20][20];

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		displayChessBoard();
		connecting();
		OpponientTurn();
	}

	// Hàm connecting
	public void connecting() {
		// TODO Auto-generated method stub
		try {
			serverSocket = new ServerSocket(port);

			socket = serverSocket.accept();
			System.out.println("Connected !");
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Khong the tao server socket!");
			System.exit(1);
		}
	}

	// hiển thị bàn cờ-vẽ bàn cờ
	private void displayChessBoard() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				OCo oco = new OCo();
				oco.setText("");
				oco.setX(i);
				oco.setY(j);
				oco.setMinWidth(30);
				oco.setMinHeight(30);
				matrix[i][j] = oco;
				grpBanCo.add(oco, j, i);

				oco.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						btn_Clicked(oco);
					}
				});
			}
		}
	}

	// xử lý khi một ô cờ được click
	public void btn_Clicked(OCo oco) {
		/*
		 * Ý tưởng: Sau khi click sẽ gửi đi tọa độ x,y sang máy đối thủ, máy đối thủ sẽ
		 * dùng tọa độ đó để hiển thị lên bàn cờ, tương tự sau khi đối thủ click cũng sẽ
		 * gửi tọa độ về cho mình để hiển thị
		 */
		Mark(oco);
		out.println(oco.x + "-" + oco.y);
		OpponientTurn();
		OpponientTurn();
	}

	// căn cứ theo turn để đánh dấu X-O, kiểm tra game kết thúc chưa
	public void Mark(OCo oco) {
		if (oco.getText().toString() != "")
			return;
		if (flag) {
			oco.setText("O");
			flag = false;
			lblTurn.setText("X");
			lblTurn.setTextFill(Color.RED);

		} else {
			oco.setText("X");
			oco.setTextFill(Color.RED);
			flag = !flag;
			lblTurn.setText("O");
			lblTurn.setTextFill(Color.BLACK);
		}
		if (isEndGame(oco)) {
			EndGame();
		}
	}

	// duyệt các đường chéo, dọc, ngang để kiểm tra xem đã kết thúc game chưa
	private boolean isEndGame(OCo oco) {
		// TODO Auto-generated method stub
		return isEndGameHorizontal(oco) || isEndGameVertical(oco) || isEndGameMainSubDiagonal(oco)
				|| isEndGameSubDiagonal(oco);
	}

	// kiểm tra thắng thua theo đường chéo phụ
	private boolean isEndGameSubDiagonal(OCo oco) {
		// TODO Auto-generated method stub
		int countLeft = 0;
		int countRight = 0;

		for (int j = 0; j <= 19 - (oco.x < oco.y ? oco.x : oco.y); j++) {// vd:oco(9,5) hàng x=9 cột y=5,
																			// 9-5,10-4,11-3,12-2,13-1,14-0 ->hàng tăng,
																			// cột
																			// giảm
			if ((oco.x + j > 19) || (oco.y - j < 0))// oco(0,19), 1-18,2-17,3-16,...19-0
				break;
			if (matrix[oco.x + j][oco.y - j].getText().equals(oco.getText())) {// oco(19,19)
				countLeft++;
			} else
				break;
		}
		for (int i = 1; i <= 19 - (oco.x > oco.y ? oco.x : oco.y); i++) {// 8-6,7-7,...0-14 ->hàng giảm, cột tăng
			if ((oco.x - i) < 0 || (oco.y + i > 19))
				break;
			if (matrix[oco.x - i][oco.y + i].getText().equals(oco.getText())) {
				countRight++;
			} else
				break;
		}
//		int tong = countLeft + countRight;
//		System.out.println(", chéo phụ:" + tong + ", Oco(" + oco.x + "," + oco.y + "), text: " + oco.getText());
		return countLeft + countRight >= 5;
	}

	// kiểm tra thắng thua theo đương chéo chính
	private boolean isEndGameMainSubDiagonal(OCo oco) {
		// TODO Auto-generated method stub
		int countLeft = 0;
		int countRight = 0;

		for (int i = 0; i <= (oco.x < oco.y ? oco.x : oco.y); i++) {// vd:oco(6,9) hàng 6 cột 9, ->hàng giảm cột giảm
																	// 6-9,5-8,4-7...(-3)-0
			if ((oco.x - i) < 0 || (oco.y - i) < 0)
				break;
			if (matrix[oco.x - i][oco.y - i].getText().equals(oco.getText())) {
				countLeft++;
			} else
				break;
		}

		for (int j = 1; j <= (19 - (oco.x > oco.y ? oco.x : oco.y)); j++) {// duyet 7-10,8-11,9-12,10-13,11-14,12-15,
																			// ...-16-19 ->hàng tăng cột tăng
			if (matrix[oco.x + j][oco.y + j].getText().equals(oco.getText())) {
				countRight++;
			} else
				break;
		}
//		int tong = countLeft + countRight;
//		System.out.print(", chéo chính:" + tong);
		return countLeft + countRight >= 5;

	}

	// kiểm tra thắng thua theo hàng dọc
	private boolean isEndGameVertical(OCo oco) {
		// TODO Auto-generated method stub
		int countTop = 0;
		int countBottom = 0;

		for (int i = oco.x; i >= 0; i--) {// vd:oco(6,9) hàng 6 cột 9
			if (matrix[i][oco.y].getText().equals(oco.getText())) {
				countTop++;
			} else
				break;
		}

		for (int j = oco.x + 1; j < 20; j++) {
			if (matrix[j][oco.y].getText().equals(oco.getText())) {
				countBottom++;
			} else
				break;
		}
//		int tong = countTop + countBottom;
//		System.out.print(", dọc:" + tong);
		return countTop + countBottom >= 5;

	}

	// kiểm tra thắng thua theo hang ngang
	private boolean isEndGameHorizontal(OCo oco) {
		// TODO Auto-generated method stub
		int countLeft = 0;
		int countRight = 0;

		for (int i = oco.y; i >= 0; i--) {// vd:oco(6,9) hàng 6 cột 9
			if (matrix[oco.x][i].getText().equals(oco.getText())) {
				countLeft++;
			} else
				break;
		}

		for (int j = oco.y + 1; j < 20; j++) {
			if (matrix[oco.x][j].getText().equals(oco.getText())) {
				countRight++;
			} else
				break;
		}
//		int tong = countLeft + countRight;
//		System.out.print("Ngang:" + tong);
		return countLeft + countRight >= 5;

	}

	// xử lý khi kết thúc game
	private void EndGame() {
		// TODO Auto-generated method stub
		grpBanCo.setDisable(true);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Alert");
		alert.setHeaderText("Kết quả:");
		alert.setContentText("Bên " + (lblTurn.getText() == "X" ? "O" : "X") + " thắng!");
		alert.showAndWait();
	}

	// new game-vẽ lại bàn cờ mới
	public void newGame() {
		out.println("***NEWGAME***");
		grpBanCo.setDisable(false);
		grpBanCo.getChildren().clear();
		displayChessBoard();
	}

	// sự kiện cho tùy chọn quit
	public void quitGame() {
		out.println("***QUITGAME***");
		System.exit(0);
	}

	// Viet ham de xu ly vi tri nhan duoc
	public ToaDo XuLyViTri(String viTri) {
		int viTriDauPhanCach = viTri.indexOf("-");
		int x = Integer.parseInt(viTri.substring(0, viTriDauPhanCach));
		int y = Integer.parseInt(viTri.substring(viTriDauPhanCach + 1, viTri.length()));
		ToaDo toaDo = new ToaDo(x, y);
		return toaDo;
	}

	public void OpponientTurn() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("OpnTurn duoc goi");
				String message = in.nextLine();
//				System.out.println("Client message: "+message);
				if (message.equals("***NEWGAME***")) {
					grpBanCo.setDisable(false);
					grpBanCo.getChildren().clear();
					flag = true;
					displayChessBoard();
				} else if (message.equals("***QUITGAME***")) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Alert");
					alert.setHeaderText("Thông báo:");
					alert.setContentText("Đối thủ đã thoát!");
					alert.showAndWait();
					grpBanCo.setDisable(true);
				} else {
					ToaDo toaDo = XuLyViTri(message);
					Mark(matrix[toaDo.hang][toaDo.cot]);
				}
			}
		});
	}
}
