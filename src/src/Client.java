package src;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream; 
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private DataOutputStream output_;
    private DataInputStream input_;
    private FileInputStream input_file;
    private FileOutputStream output_file;
    private byte[] sendBytes;
    private byte[] inputBytes;
    private String message;
    private String sender;
    private String filename;
    private String clientname;
    private ArrayList<String> userlist;
    private String ip;
    private int port;

    //constructor
    public Client(String hostip, int port) {
        try {
        	this.ip = hostip;
        	this.port = port;
            socket=new Socket(hostip, port);
            output_ = new DataOutputStream(socket.getOutputStream());
            input_ = new DataInputStream(socket.getInputStream());
            input_file = null;
            output_file = null;
            sendBytes = null;
            inputBytes = null;
            message = "";
            sender = "";
            clientname = "";
            filename = "";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getIP() {
    	return this.ip;
    }
    public int getPort() {
    	return this.port;
    }
    //close the client
    public void close() {
        try {
            output_.writeUTF("close");
            output_.flush();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }	
    }

    //send a userlist request to server
    public void requestUserlist() {
        try {
            output_.writeUTF("request_list");
            output_.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //send a clientname request to server
    public void requestClientname() {
        try {
            output_.writeUTF("request_clientname");
            output_.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //send message in chatroom
    public void message_to_all(String str) {
        try {
            output_.writeUTF("message_all"+str);
            output_.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //send message to single
  public void message_to_one(String user, String str) {
      try {
          output_.writeUTF("message_one"+user+str);
          output_.flush();
      } catch (UnknownHostException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

    //send file in chatroom
    public void file_to_all(File f, String fname) {
        try {
            long len = f.length();
            output_.writeUTF("file_all"+fname+'\n'+String.valueOf(len));
            output_.flush();

            input_file = new FileInputStream(f);
            sendBytes = new byte[1024];
            int length = 0;
            double sumL = 0;
            while ((length = input_file.read(sendBytes, 0, sendBytes.length)) > 0) {  
                sumL += length;
                System.out.println("Transmit"+((sumL/len)*100)+"%");  
                output_.write(sendBytes, 0, length);
                output_.flush();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //send file to single
    public void file_to_one(String user, File f, String fname) {
        try {
            long len = f.length();
            output_.writeUTF("file_one"+user+fname+"\n"+String.valueOf(len));
            output_.flush();

            int tmp = 0;
            double cur = 0;
            input_file = new FileInputStream(f);
            sendBytes = new byte[1024];
            while ((tmp = input_file.read(sendBytes, 0, sendBytes.length)) > 0) {  
                cur += tmp;    
                System.out.println("Transmit"+((cur/len)*100)+"%");  
                output_.write(sendBytes, 0, tmp);  
                output_.flush();  
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //return different value to tell UI the different type of input
    public int input() {
        String msg = "";
        try {
            msg = input_.readUTF();
            if (msg.startsWith("message_all")) {  //the Server send a chatroom message
                message = msg.replace("message_all","");
                return 1;
            } else if (msg.startsWith("message_one")) {  //the Server send a private message
                msg = msg.replace("message_one","");
                sender = "";
                char[] msg1 = msg.toCharArray();
                //[ip][port]
                for(int i = 0, count = 0; count != 2; i++) {
                    if(msg1[i] == ']') {
                        sender = sender+msg1[i];
                        count++;
                    } else {
                        sender = sender+msg1[i];
                    }
                }
                message = msg.replace(sender,"");
                return 2;
            }  if (msg.startsWith("request_list")) { //the Server send a online user list
            	userlist = new ArrayList<String>();
            	msg = msg.replace("request_list","");
                String user[] = msg.split("\n");
                //int len = Integer.parseInt(user[0]);
                //the first string is user number

                for (int i = 0; i < user.length; i++) {
                	userlist.add(user[i]);
                }
                System.out.println("tian jia userlist success");
                return 3;
            } else if (msg.startsWith("request_clientname")) {  //the Server send a clientname of this client
                clientname = msg.replace("request_clientname","");
                return 4;
            }  else if (msg.startsWith("file_all")) {  //the Server send a file
                msg = msg.replace("file_all","");
                sender = "";
                char[] msg1 = msg.toCharArray();
                //[ip][port]
                for(int i = 0, count = 0; count != 2; i++) {
                    if(msg1[i] == ']') {
                        sender = sender+msg1[i];
                        count++;
                    } else {
                        sender = sender+msg1[i];
                    }
                }
                //get filename
                filename = "";
                msg = msg.replace(sender,"");
                String tmp[] = msg.split("\n");
                filename = tmp[0];
                long l = Long.parseLong(tmp[1]); 
                File f = new File("C:/MINET"); 
                if(!f.exists()){
                    f.mkdir();
                }

                long cur = 0;
                int t = 0;
                output_file = new FileOutputStream(new File("C:/MINET/"+filename));
                inputBytes = new byte[1024];
                while (true) {
                    t = input_.read(inputBytes, 0, inputBytes.length);
                    cur += t;
                    output_file.write(inputBytes, 0, t);  
                    output_file.flush(); 
                    if (cur >= l) {
                        output_file.close();
                        break;
                    }   
                }
                return 5;
            } else if (msg.startsWith("file_one")) {  //the Server send a file
                msg = msg.replace("file_one","");
                sender = "";
                //[ip][port]
                char[] msg1 = msg.toCharArray();
                for(int i = 0, count = 0; count != 2; i++) {
                    if(msg1[i] == ']') {
                        sender = sender+msg1[i];
                        count++;
                    } else {
                        sender = sender+msg1[i];
                    }
                }
                filename = "";
                msg = msg.replace(sender,"");
                String tmp[] = msg.split("\n");
                filename = tmp[0];
                long l = Long.parseLong(tmp[1]); 
                File f = new File("C:/MINET"); 
                if(!f.exists()){  
                    f.mkdir();    
                }

                long cur = 0;
                int t = 0;
                output_file = new FileOutputStream(new File("C:/MINET/"+filename));
                inputBytes = new byte[1024];
                while (true) {
                    t = input_.read(inputBytes, 0, inputBytes.length);
                    cur += t;
                    output_file.write(inputBytes, 0, t);  
                    output_file.flush(); 
                    if (cur >= l) {
                        output_file.close();
                        break;
                    }   
                }
                return 5;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //return the massage that be sent to Client
    public String getMessage() {
        return message;
    }

    //return the sender who send massage to Client
    public String getSender() {
        return sender;
    }

    //return the list of online user
    public ArrayList<String> getUserlist() {
        return userlist;
    }

    //return the clientname of this Client
    public String getClientname() {
        return clientname;
    }

}
