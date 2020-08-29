package ThamKhao.MyChat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class ChartServer {

	private static ServerSocket servSock;
	private static final int PORT = 9233;
	
	private static Socket link = null;                        
	private static Scanner input;
	private static PrintWriter output;
	
	private static JFrame frmChatServer;
	private static JTextArea textField;
	private static JTextField textField_1;
	private static JButton  btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
					ChartServer window = new ChartServer();
					window.frmChatServer.setVisible(true);
//				
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//			}
//		});
		
		initSocket();
	}

	/**
	 * Create the application.
	 */
	public ChartServer() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatServer = new JFrame();
		frmChatServer.setTitle("Chat Server");
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
			         t1.append("Server: "+textField_1.getText());
			         textField.setText(t1.toString());
				}
			}
		});
		btnNewButton.setBounds(341, 202, 85, 32);
		frmChatServer.getContentPane().add(btnNewButton);
		
		
	}
	
	public static void initSocket()
	   {
	      System.out.println("Opening port...\n");
	      try
	      {
	         servSock = new ServerSocket(PORT);      //Step 1.
	      }
	      catch(IOException ioEx)
	      {
	         System.out.println("Unable to attach to port!");
	         System.exit(1);
	      }
	      do
	      {
	         handleClient();
	      }while (true);
	   }
	
	private static void handleClient()
	   {
	            
	      try
	      {
	         link = servSock.accept();               //Step 2.

	         input = new Scanner(link.getInputStream()); //Step 3.
	         output = new PrintWriter(link.getOutputStream(),true); //Step 3.
	         
	         String message = input.nextLine();    //Step 4.
	         
	         while (true)
	         {
		         StringBuffer t1 = new StringBuffer(textField.getText());
		         t1.append("\n");
		         t1.append("Client: "+message);
		         textField.setText(t1.toString());
	             message = input.nextLine();
	             
	         }
	         
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
					link.close();				    //Step 5.
				}
				catch(IOException ioEx)
				{
					System.out.println("Unable to disconnect!");
					System.exit(1);
				}
			}
		}
}
