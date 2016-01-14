/*
 * @(#)UserListUI.java    1.0 2015/12/08
 */

package src.UI;

import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import src.*;

/**
 * provide the GUI of resist window
 *
 * @version 1.0 08 Dec 2015
 */
public class UserList extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<JLabel> list;
    private HashMap<String, P2PChatRoom> privateChatList;
    // if there is a private-chat-window for a user
    private Box box;
    private Client client;

    public UserList(Client client_, ArrayList<String> userList) {
        client = client_;

        list = new ArrayList<JLabel>();
        privateChatList = new HashMap<String, P2PChatRoom>();

        box = Box.createVerticalBox();
        for (int i = 0; i < userList.size(); i++) {
            JLabel user = new JLabel(userList.get(i));
            list.add(user);
            box.add(user);
        }
        initUI();
    }

    private void initUI() {
        add(box);

        // add listener to each user label
        for (final JLabel user : list) {
            user.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Boolean flag = false;
                    //click on client's ip
                    for (String user_ : client.getUserlist()) {
                        if (user_.equals(user.getText())) {
                            System.out.println("You may have P2P chat");
                            flag = true;
                            break;
                        }
                    }
                    // have clicked
                    if (!user.getText().equals(client.getClientname()) && flag
                        && (privateChatList.get(user.getText()) == null)) {
                            new P2PChatRoom(client, user.getText(),privateChatList);
                    } else {
                        System.out.println(
                            "You have created a P2P window");
                    }
                }
            });
        }
    }

    public void closePrivateChatWindows() {
        for (P2PChatRoom ui : privateChatList.values()) {
            ui.dispose();
        }
    }

    public void removeFromList(String friend) {
        privateChatList.put(friend, null);
    }

    // return null if there is no private-chat-window with this user
    public P2PChatRoom getP2PChatRoom(String sender) {
        if (privateChatList.get(sender) == null) {
        	P2PChatRoom pcUI = new P2PChatRoom(client,sender, privateChatList);
            return pcUI;
        } else {
            return privateChatList.get(sender);
        }
    }
}
