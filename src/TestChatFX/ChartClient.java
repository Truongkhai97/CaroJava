package TestChatFX;


import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class ChartClient extends Application{

	private static InetAddress host;
	private static final int PORT = 9233;
	
	private static Socket link = null;                        
	private static Scanner input;
	private static PrintWriter output;
	
	private static JFrame frmChatServer;
	private static JTextArea textField;
	private static JTextField textField_1;
	private static JButton  btnNewButton;

	public static void main(String[] args) {
		
		launch(args);
	}

	public ChartClient() {
		initialize();
	}

	private void initialize() {
		frmChatServer = new JFrame();
		frmChatServer.setTitle("Chat Client");
		frmChatServer.setBounds(100, 100, 450, 300);
		frmChatServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatServer.getContentPane().setLayout(null);
		
		textField = new JTextArea();
		textField.setEditable(false);
		textField.setBounds(10, 10, 416, 168);
		frmChatServer.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 202, 319, 32);
		frmChatServer.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnNewButton==e.getSource()){
					output.println(textField_1.getText());//Step 4.
					StringBuffer t1 = new StringBuffer(textField.getText());
			         t1.append("\n");
			         t1.append("Client: "+textField_1.getText());
			         textField.setText(t1.toString());
				}
			}
		});
		btnNewButton.setBounds(341, 202, 85, 32);
		frmChatServer.getContentPane().add(btnNewButton);
		frmChatServer.show();
	}
	
	public static void initSocket()
	   {
		try
		{
			host = InetAddress.getByName("127.0.0.1");
		}
		catch(UnknownHostException uhEx)
		{
			System.out.println("Host ID not found!");
			System.exit(1);
		}
		accessServer();
	   }
	
	private static void accessServer()
	{
		try
		{
			link = new Socket(host,PORT);		//Step 1.

			input = new Scanner(link.getInputStream());//Step 2.

			output =new PrintWriter(link.getOutputStream(),true);//Step 2.
			
			String response;
			do
			{
				response = input.nextLine();
				StringBuffer t1 = new StringBuffer(textField.getText());
		         t1.append("\n");
		         t1.append("Server: "+response);
		         textField.setText(t1.toString());
		         
			}while (true);
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}

		finally
		{
			try
			{
				System.out.println(
							"\n* Closing connection... *");
				link.close();					//Step 4.
			}
			catch(IOException ioEx)
			{
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		initSocket();
	}
}
