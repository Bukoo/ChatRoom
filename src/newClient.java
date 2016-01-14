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
        
        PublicChatRoom ui = new PublicChatRoom(client);

        while(true){
            int flag = client.input();
            if (flag == 1) {
                ui.appendChatWindowText();
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
