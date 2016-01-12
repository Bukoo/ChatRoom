package src;
import java.awt.*;
// bin
import javax.swing.*;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.*;

public class Server extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private ServerSocket serverSocket;
	// 鏈嶅姟鍣ㄧ鍙�
	private static final int SERVERPORT = 54321;
	// 鍒涘缓绾跨▼姹�
	private ExecutorService executorService;
    // 鍌ㄥ瓨瀹㈡埛绔繛鎺�
	private static ArrayList<Socket> myClientList = new ArrayList<Socket>();
	private static JTextArea jTextArea = new JTextArea();
	// JFrame frame = new JFrame();
	public Server() {
		JFrame frame = new JFrame();
		//JPanel panel = new JPanel();
		// 褰搄TextArea閲屽唴瀹硅繃闀挎椂鐢熸垚婊氬姩鏉�
		frame.add(new JScrollPane(jTextArea));
		frame.setTitle("MINET鏈嶅姟鍣�");
		// 璁剧疆绐楀彛涓哄彲瑙�
		frame.setVisible(true);
		frame.setSize(300, 500);
		frame.setLocation(200,100);
		// 瀹氫箟绐楀彛鐨勫浘鏍囦负image.png
		frame.setIconImage(Toolkit.getDefaultToolkit().createImage
			(Server.class.getResource("image.png")));
		// 鐢ㄦ埛鍗曞嚮绐楀彛鐨勫叧闂寜閽椂绋嬪簭鎵ц鐨勬搷浣�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			// 璁剧疆鏈嶅姟鍣ㄧ鍙�
			serverSocket = new ServerSocket(SERVERPORT);
			// 鍒涘缓涓�涓嚎绋嬫睜
			executorService = Executors.newCachedThreadPool();
			jTextArea.append("鏈嶅姟鍣ㄥ凡缁忓惎鍔╘n");
			// 鐢ㄦ潵涓存椂淇濆瓨瀹㈡埛绔繛鎺ョ殑socket瀵硅薄
			Socket client = null;
			while (true) {
				// 鎺ユ敹瀹㈡埛杩炴帴骞舵坊鍔犲埌List涓�
				client = serverSocket.accept();
				myClientList.add(client);
				// 褰撴湁鏂扮敤鎴蜂笂绾挎椂锛屽悜姣忎釜鍦ㄧ嚎鐢ㄦ埛鍙戦�佹柊鐨勭敤鎴峰垪琛�
				sendNewClientList();
				// 寮�鍚竴涓鎴风绾跨▼
				executorService.execute(new ThreadServer(client));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// 鍚戞瘡涓湪绾跨敤鎴疯繑鍥炲湪绾跨敤鎴峰垪琛�
		static public void sendNewClientList() throws IOException {
			String list ="request_list";
			// encoding = "UTF-8";
			//int n = myClientList.size();
			//list += n + "\n";
			for (Socket client:myClientList) {
				list += "["+client.getInetAddress()+"]"+"["+client.getPort()+"]"+"\n";
			}
			for (Socket client:myClientList) {
				DataOutputStream toclient;
			    toclient = new DataOutputStream(client.getOutputStream());
			    toclient.writeUTF(list);
			
				//PrintWriter myPrintWriter;
				//myPrintWriter = new PrintWriter(client.getOutputStream(), true);
				// 浠tf-8鐨勭紪鐮佹牸寮忓皢淇℃伅鎵撳嵃鍑烘潵
				//myPrintWriter.format(encoding,null);
				//myPrintWriter.write(list);
			}
		}


	static class ThreadServer implements Runnable {  // 鍒涘缓瀹炵幇Runnable鎺ュ彛鐨勭被
		private Socket client;
		//private BufferedReader dis;
		private DataInputStream dis;
		private DataOutputStream toclient;
		//private PrintWriter myPrintWriter;
		private String myStrMSG;
		String ipAddress;  // 璁板綍瀹㈡埛绔殑ip
		int port;  // 璁板綍瀹㈡埛绔殑绔彛

		public ThreadServer(Socket socket) throws IOException {
			this.client = socket;
			ipAddress = socket.getInetAddress().toString();  // 璁板綍瀹㈡埛绔殑ip
			port = socket.getPort();  // 璁板綍瀹㈡埛绔殑绔彛
			dis = new DataInputStream(client.getInputStream());
			myStrMSG = "鐢ㄦ埛锛�" + this.client.getInetAddress() + "涓婄嚎浜嗭紒 鐢ㄦ埛鎬绘暟锛�" + myClientList.size();
			sendMessageToAllClient();
		}
		@SuppressWarnings("deprecation")
		public void run() {
			try {
				while((myStrMSG = dis.readLine()) != null) {
					jTextArea.append(myStrMSG+ "\n");
					messageRecog(myStrMSG); // 璇嗗埆瀹㈡埛绔彂閫佺殑娑堟伅绫诲瀷
				}
			} catch (Exception e) {
				
			}
		}

        // 鍙戦�佹秷鎭粰鎵�鏈夊鎴风
		public void sendMessageToAllClient() throws IOException {
			// jTextArea.append(myStrMSG+"\n");
			System.out.println(myStrMSG);  // 鎺у埗鍙拌緭鍑�
			for (Socket client:myClientList) {
				DataOutputStream toclient;
				toclient = new DataOutputStream(client.getOutputStream());
				String toclientMessage = myStrMSG;
				toclientMessage = "鏉ヨ嚜" + "["+ ipAddress + "]" + "["+ port + "]鐨勬秷鎭�" + ":" + myStrMSG;
				toclient.writeUTF(toclientMessage);
			}
		}

		public void sendMessageToOneClient() throws IOException {
			String toclientMessage = myStrMSG;
			for (Socket client:myClientList) {  // 浠庡彂缁欐湇鍔″櫒鐨勬秷鎭腑绛涢�夊嚭闇�瑕乸2p鍙戦�佺殑娑堟伅
				if (toclientMessage.startsWith("[" + client.getInetAddress() + "]" + "[" + client.getPort() + "]")) {

					toclientMessage = "鏉ヨ嚜" + "["+client.getInetAddress()+"]" + "["+client.getPort()+"]" + "鐨勬秷鎭�" + toclientMessage;
					//DataOutputStream toclient;
					toclient = new DataOutputStream(client.getOutputStream());
					toclient.writeUTF(toclientMessage);
					jTextArea.append("p2p message:" + toclientMessage + "\n");
			    }
		    }
	    }

	    public void sendClientList() throws IOException {
			String sendList = "request_list";
			// int n = myClientList.size();
			DataOutputStream toclient;
			toclient = new DataOutputStream(client.getOutputStream());
			for(Socket client:myClientList){
				sendList += "["+client.getInetAddress()+"]" + "["+client.getPort()+"]" + "\n";				
			}
			toclient.writeUTF(sendList);	
		}

		public void sendClientName() throws IOException {
			String msg = "request_clientname" + "["+ipAddress+"]" + "["+port+"]";
			toclient= new DataOutputStream(client.getOutputStream());
			toclient.writeUTF(msg);
		}

	    public void messageRecog(String message) throws IOException {
	    	if (message.startsWith("request_list")) {
				sendClientList();
			} else if (message.startsWith("message_all")) {
				sendMessageToAllClient();
			} else if (message.startsWith("message_one")) {
				sendMessageToOneClient();
			} else if (message.startsWith("close")) {
				removeConnection();
			} else if (message.startsWith("request_clientname")) {
				sendClientName();
			} else if (message.startsWith("file_all")) {
					sendFileToAllClient(message);			
			} else if (message.startsWith("file_one")) {
					sendFileToOneClient(message);
			}
	    }
  		
		
		public void sendFileToOneClient(String message) throws IOException {
			myStrMSG = myStrMSG.replace("file_one", "");
			int index = myStrMSG.indexOf("\n");

			String len = myStrMSG.substring(index + 1, myStrMSG.length());
			long file_length = Long.parseLong(len);
			double file_loop = file_length/1024.0;
			file_loop = Math.ceil(file_loop);  // 鍚戜笂鍙栨暣
			

			byte[] inputByte = new byte[1024]; 
		
			for (Socket client:myClientList) {
				if (myStrMSG.startsWith("["+client.getInetAddress()+"]" + "["+client.getPort()+"]")) {
					myStrMSG = myStrMSG.replace("["+client.getInetAddress()+"]" + "["+client.getPort()+"]", "");
					toclient= new DataOutputStream(client.getOutputStream());
					myStrMSG = "file_one" + "["+ipAddress+"]" + "["+port+"]" + myStrMSG;
					toclient.writeUTF(myStrMSG);
					for (long i = 0; i < file_loop; i++) {
						int length = dis.read(inputByte, 0, inputByte.length);
						toclient.write(inputByte, 0, length);   
					}
					jTextArea.append("p2p message" + myStrMSG+"\n");
				}
			}
		}

		public void sendFileToAllClient(String message) throws IOException {
			myStrMSG = myStrMSG.replace("file_all","");
			int index = myStrMSG.indexOf("\n");

			String len = myStrMSG.substring(index+1, myStrMSG.length());
			long file_length = Long.parseLong(len);
			double file_loop = file_length/1024.0;
			file_loop = Math.ceil(file_loop);
			byte[] inputByte = new byte[1024];
			//  寰幆鍙戦�佺涓�鏉￠�氱煡娑堟伅
			for (Socket client:myClientList) {
				toclient= new DataOutputStream(client.getOutputStream());
				myStrMSG = "file_all" + "["+ipAddress+"]" + "["+port+"]" + myStrMSG;
				toclient.writeUTF(myStrMSG);
				jTextArea.append(myStrMSG + "\n");
			}
			
			for (long i = 0; i < file_loop; i++) {
				int length = dis.read(inputByte, 0, inputByte.length);
				for(Socket client:myClientList) {
					toclient= new DataOutputStream(client.getOutputStream());
					toclient.write(inputByte, 0, length);
				}
			}
		}

	public void removeConnection() {
		jTextArea.append("remove client" + "\n");
		for (int i = 0, len = myClientList.size(); i < len; i++) {
			if (myClientList.get(i) == client) {
				myClientList.remove(i);
				--len;
				--i;
			}
		}
		
		try {
			sendNewClientList(); // 鍚戝叾浠栧湪绾跨敤鎴峰彂閫佹柊鐨勭敤鎴峰垪琛�
		} catch (IOException e) {
			System.out.println("error occurs!");
		}

		try {
			client.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}


}


	public static void main(String[] args) {
		new Server();
	}

}
