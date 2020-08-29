package DemoChatClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
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


public class ClientController implements Initializable{
	@FXML
	private TextArea taHienThi;
	@FXML
	private TextField tfNhapVao;
	@FXML
	private Button btnSend;
	
	Socket socket=null;
	int port=9233;
	InetAddress host;
	Scanner in;
	PrintWriter out;
	String message;
	
	public void btnSend_Clicked() {
		taHienThi.appendText("\nClient>"+tfNhapVao.getText());
		System.out.println(tfNhapVao.getText());
		out.println(tfNhapVao.getText());
		tfNhapVao.clear();
		GetMessage();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			host=InetAddress.getLocalHost();
			socket=new Socket(host,port);
			in=new Scanner(socket.getInputStream());
			out=new PrintWriter(socket.getOutputStream(),true);
//			System.out.println(out);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("UnknowHost !");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Khong the ket noi !");
			System.exit(1);
		}
//		GetMessage();
	}
	
	public void GetMessage() {
//		do {
			message=in.nextLine();
			taHienThi.appendText("Server>"+message);
//		}
//		while(!message.equals("***CLOSE***"));
	}
}
