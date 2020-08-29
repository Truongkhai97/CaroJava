package TestChat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JTextField;

public class ClientWindow {

	private JFrame frmIChat;
	private JTextField messageField;
	private static JTextArea textArea = new JTextArea();
	private Client client;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					ClientWindow window = new ClientWindow();
					window.frmIChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientWindow() {
		initialize();
		
		String name = JOptionPane.showInputDialog("Nhập tên của bạn");
		client = new Client(name, "10.10.33", 117);
	}

	private void initialize() {
		frmIChat = new JFrame();
		frmIChat.setTitle("Nhóm Chat");
		frmIChat.setBounds(100, 100, 597, 469);
		frmIChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIChat.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		frmIChat.getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		frmIChat.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		messageField = new JTextField();
		messageField.setColumns(60);
		panel.add(messageField);
		
		JButton btnGửi = new JButton("Gửi");
		btnGửi.addActionListener(e -> {
			if(!messageField.getText().equals("")) {
			client.Gui(messageField.getText());
			messageField.setText("");
			}
		});
		panel.add(btnGửi);
		frmIChat.setLocationRelativeTo(null);
		
	}
	
	public static void printToConsole(String message) {
		textArea.setText(textArea.getText()+message+"\n");
	}
}
