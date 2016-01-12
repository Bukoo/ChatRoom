package src;

import src.UI.*;

/*this is main function*/
public class newClient {

    private Client client;

    public newClient() {
        UserInfo userInfo = new UserInfo();
        new LoginUI(userInfo);
        while (userInfo.getFlag()) {
            System.out.print("");
        }
        client = new Client(userInfo.getIp(),userInfo.getPortNum());
        /*if (client == null) {
        	System.out.println("the client is null");
        } else {
        	System.out.println("the client is real");
        }
        System.out.println(userInfo.getIp());
        System.out.println(userInfo.getPortNum());
        
        client.requestUserlist();
        System.out.println(client.getUserlist()+"bbbbbbbbbbbb");
        //System.out.println(client.input());
        System.setIn(new ByteArrayInputStream("".getBytes()));
        
        //System.out.println("Œ¥‘À––public ±flag£∫"+client.input());*/
        
        PublicChatRoom ui = new PublicChatRoom(client);

        System.out.println("enter input");
        while(true){
            int flag = client.input();
            if (flag == 1) {
                ui.appendChatWindowText(ui.getMessage());
            } else if (flag == 2) {
                ui.appendPrivateChatWindowText(ui.getMessage());
            } else if (flag == 3) {
                ui.refreshUserlist();
            }
        }
    }

    public static void main(String[] args) {
        new newClient();
    }
}
