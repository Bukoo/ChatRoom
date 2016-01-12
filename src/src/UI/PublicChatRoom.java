package src.UI;

import javax.swing.*;

import src.Client;

import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.util.Date;
 
public class PublicChatRoom extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;

	private static Client client;

    private JFrame frame;
    private JTextArea viewArea;
    private JTextArea inputField;
    private JButton sendButton;
    //private JButton cancelButton;
    private JLabel myPort;
    private JLabel myIP;
    private JLabel TMP;
    private JButton sendFile;
    private JButton sendPic;
    private String message;
    private JScrollPane scrollUserListWindow;
    private UserList userlistWin;
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    

    public PublicChatRoom(Client _client){
    	client = _client;
    	
    	requestName();
        requestList();

        frame = new JFrame("Default Chat Room");
        GridBagLayout leftGrid = new GridBagLayout();
        GridBagLayout mainGrid = new GridBagLayout();
        frame.setLayout(mainGrid);
        
        //左右两部分

        leftPanel.setLayout(leftGrid);
        //rightPanel.setLayout(rightGrid);

        //左部分区
        JPanel viewAreaPanel = new JPanel();
        JPanel functionPanel = new JPanel();
        JPanel inputFieldPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        
        //组件
        //文本域
        viewArea = new JTextArea(1,3);
        viewArea.setLineWrap(true);
        inputField = new JTextArea(1,3);
        inputField.setLineWrap(true);
        
        //组件
        myPort = new JLabel();
        TMP = new JLabel();
        myIP = new JLabel();

        sendButton = new JButton("Send");
        
        sendFile = new JButton();
        sendPic = new JButton();
        
        

        //滚动条聊天窗口
        JScrollPane sp1 = new JScrollPane(viewArea);
        sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JScrollPane sp2 = new JScrollPane(inputField);
        sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        //布局

        frame.setSize(600,500);
        frame.setVisible(true);
        //frame.setResizable(false);
        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        
        //左右两部分布局
        frame.add(leftPanel);
        frame.add(rightPanel);
        s.gridy = 0;
        s.gridx = 0;
        s.weightx = 40;
        s.weighty = 1;
        mainGrid.setConstraints(leftPanel, s);
    	s.gridy = 0;
        s.gridx = 40;
        s.weightx = 1;
        s.weighty = 1;
        mainGrid.setConstraints(rightPanel, s);
        
        
        //左部分布局
        leftPanel.add(viewAreaPanel);
        leftPanel.add(functionPanel);
        leftPanel.add(inputFieldPanel);
        leftPanel.add(buttonPanel);
        s.gridy = 0;
        s.gridheight = 10;
        s.weighty = 10;
        s.weightx = 1;
        //viewAreaPanel.setBackground(Color.blue);
        leftGrid.setConstraints(viewAreaPanel, s);
        s.gridy = 10;
        s.gridheight = 1;
        s.weighty = 1;
        s.weightx = 1;
        //functionPanel.setBackground(Color.green);
        leftGrid.setConstraints(functionPanel, s);
        s.gridy = 11;
        s.gridheight = 4;
        s.weighty = 4;
        s.weightx = 1;
        //inputFieldPanel.setBackground(Color.red);
        leftGrid.setConstraints(inputFieldPanel, s);
        s.gridy = 15;
        s.gridheight = 1;
        s.weighty = 0.02;
        s.weightx = 1;
        //buttonPanel.setBackground(Color.cyan);
        leftGrid.setConstraints(buttonPanel, s);
        
        //viewAreaPanel 布局
        viewAreaPanel.add(sp1);
        GridBagLayout viewAreaGrid = new GridBagLayout();
        viewAreaPanel.setLayout(viewAreaGrid);
        viewAreaGrid.setConstraints(sp1, s);
        
        //functionPanel 布局
        functionPanel.add(sendFile);
        functionPanel.add(sendPic);
        functionPanel.setLayout(null);
        sendFile.setBounds(10,3,20,20);
        sendFile.setBorderPainted(false);
        sendPic.setBounds(50,3,20,20);
        sendPic.setBorderPainted(false);
        
        ImageIcon filepic = new ImageIcon("images/file.jpg");
        int width = (int)sendFile.getWidth();
        int height = (int)sendFile.getHeight();
        filepic.setImage(filepic.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        sendFile.setIcon(filepic);
        
        ImageIcon picpic = new ImageIcon("images/pic.png");
        int width1 = (int)sendPic.getWidth();
        int height1 = (int)sendPic.getHeight();
        picpic.setImage(picpic.getImage().getScaledInstance(width1, height1, Image.SCALE_DEFAULT));
        sendPic.setIcon(picpic);
        
        
        //inputFieldPanel 布局
        inputFieldPanel.add(sp2);
        GridBagLayout inputFieldGrid = new GridBagLayout();
        inputFieldPanel.setLayout(inputFieldGrid);
        inputFieldGrid.setConstraints(sp2, s);
        
        //buttonPanel 布局
        BorderLayout buttonBorder = new BorderLayout();
        buttonPanel.setLayout(buttonBorder);
        JPanel tempPanel = new JPanel();
        tempPanel.add(sendButton);
        tempPanel.setSize(80,50);
        buttonPanel.add(tempPanel, BorderLayout.EAST);

        sendButton.addActionListener(new sendButtonListener());
        
        //右部分布局
    	// 右侧用户列表
    	userlistWin = new UserList(client, client.getUserlist());
    	scrollUserListWindow = new JScrollPane(userlistWin);

        userlistWin.setOpaque(true);
        scrollUserListWindow.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollUserListWindow.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollUserListWindow.setPreferredSize(new Dimension(180, 150));
        rightPanel.add(scrollUserListWindow);
    	
    }
    private void requestName() {
        client.requestClientname();
        client.input();
        System.setIn(new ByteArrayInputStream("".getBytes()));
    }

    private void requestList() {
        client.requestUserlist();
        client.input();
        //System.out.println(client.getUserlist()+"aaaaaaaaaaaaaa");
        System.setIn(new ByteArrayInputStream("".getBytes()));
    }


		private class sendButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (inputField.getText() == "") {
			        setMessage("");
			    } else {
			        String messages = "";
				    Date date = new Date();
				    DateFormat df = DateFormat.getDateTimeInstance();
				    String formatDate = df.format(date);
				    messages="  "+formatDate+"\n  "+inputField.getText() + "\n";
			        setMessage(messages);
			    	client.message_to_all(getMessage());
			    	inputField.setText("");
				}
			}
	    }
	    public void setMessage(String msg) {
	    	message = msg;
	    }
	    public String getMessage(){
	    	return message;
	    }
	    
	    public void appendChatWindowText() {
	    	viewArea.setText(viewArea.getText()+client.getMessage());
	    }

		public void appendPrivateChatWindowText(String sender) {
			// TODO Auto-generated method stub
			viewArea.setText(viewArea.getText()+ "\n\n");
		}
	
	
		public void refreshUserlist() {
			// TODO Auto-generated method stub
	        rightPanel.remove(scrollUserListWindow);
	        // refresh the new user list
	        userlistWin = new UserList(client, client.getUserlist());
	        scrollUserListWindow = new JScrollPane(userlistWin);
	        
	        scrollUserListWindow.setHorizontalScrollBarPolicy(
	            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        scrollUserListWindow.setVerticalScrollBarPolicy(
	            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	        scrollUserListWindow.setPreferredSize(new Dimension(180, 150));
	        revalidate();
	        repaint();
	        rightPanel.add(scrollUserListWindow);
			
		}


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
    }