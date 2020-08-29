package TestChat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {
	
	private static DatagramSocket socket;
	private static boolean running;
	private static ArrayList<UserInfo> clients = new ArrayList<UserInfo>();
	private static int ClientID;
	
	public static void KhoiDong(int port) {
    try {
    	
    	socket = new DatagramSocket(port);
    	running = true;
    	Nghe();
    	System.out.println("Đã khởi động máy chủ qua port: "+port);
    	
    } catch (Exception e) {
    	e.printStackTrace();
    }
	}
	
    private static void TruyenGui(String message) {
    	for (UserInfo info : clients) {
    		Gui(message, info.getAddress(), info.getPort());
    	}

}

    private static void Gui(String message, InetAddress address, int port) {
    	try {
    		message += "\\e";
    		byte[] data = message.getBytes();
    		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
    		socket.send(packet);
    		System.out.println("Gui tin nhan den "+address.getHostAddress()+":"+port);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}

}

    private static void Nghe() {
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
                   TruyenGui(message);
                   }
				
				}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}; threadNghe.start();
}
    /*
     * LỆNH SERVER
     * \con:[name] -> kết nối client đến máy chủ
     * \dis:[id] -> ngắt kết nối của client khỏi máy chủ
     */
private static boolean Lenh(String message, DatagramPacket packet) {
	if(message.startsWith("\\con:")) {
		// Chạy mã kết nối
		String name = message.substring(message.indexOf(":")+1);
		clients.add(new UserInfo(name, ClientID++, packet.getAddress(), packet.getPort()));
		TruyenGui("Người dùng: "+name+" đã kết nối!");
		return true;
	}
	
	return false;
}
    
    public static void Ngung() {
    	running = false;

}
}
