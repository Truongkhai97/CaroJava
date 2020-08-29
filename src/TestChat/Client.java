package TestChat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	
	private DatagramSocket socket;
	private InetAddress address;
	private int port;
	private boolean running;
	private String name;

	public Client(String name, String address, int port) {
		try {
			this.name = name;
			this.address = InetAddress.getByName(address);
			this.port = port;
			socket = new DatagramSocket();
			running = true;
			Nghe();
			Gui("\\con:"+name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void Gui(String message) {
		try {
			if(!message.startsWith("\\")) {
				message = name+": "+message;
			}
    		message += "\\e";
    		byte[] data = message.getBytes();
    		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
    		socket.send(packet);
    		System.out.println("Gui tin nhan den "+address.getHostAddress()+":"+port);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
    private void Nghe() {
	Thread threadNghe = new Thread("Chat Listener") {
		public void run() {
			try {
				while(running) {
				//Biến đổi tin nhắn thành packet để tiện theo dõi
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
                    socket.receive(packet);
                    
                   String message = new String(data);
                   message = message.substring(0, message.indexOf("\\e"));
				//QUẢN LÝ message
                   if(!Lenh(message, packet)) {
                   ClientWindow.printToConsole(message);
                   }
				
				}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}; threadNghe.start();
}
    
    private static boolean Lenh(String message, DatagramPacket packet) {
    	if(message.startsWith("\\con:")) {
    		// Chạy mã kết nối
    		
    		return true;
    	}
    	
    	return false;
    }
}
