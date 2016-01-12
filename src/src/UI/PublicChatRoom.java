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
        
        //����������

        leftPanel.setLayout(leftGrid);
        //rightPanel.setLayout(rightGrid);

        //�󲿷���
        JPanel viewAreaPanel = new JPanel();
        JPanel functionPanel = new JPanel();
        JPanel inputFieldPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        
        //���
        //�ı���
        viewArea = new JTextArea(1,3);
        viewArea.setLineWrap(true);
        inputField = new JTextArea(1,3);
        inputField.setLineWrap(true);
        
        //���
        myPort = new JLabel();
        TMP = new JLabel();
        myIP = new JLabel();

        sendButton = new JButton("Send");
        
        sendFile = new JButton();
        sendPic = new JButton();
        
        

        //���������촰��
        JScrollPane sp1 = new JScrollPane(viewArea);
        sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JScrollPane sp2 = new JScrollPane(inputField);
        sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        //����

        frame.setSize(600,500);
        frame.setVisible(true);
        //frame.setResizable(false);
        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        
        //���������ֲ���
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
        
        
        //�󲿷ֲ���
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
        
        //viewAreaPanel ����
        viewAreaPanel.add(sp1);
        GridBagLayout viewAreaGrid = new GridBagLayout();
        viewAreaPanel.setLayout(viewAreaGrid);
        viewAreaGrid.setConstraints(sp1, s);
        
        //functionPanel ����
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
        
        
        //inputFieldPanel ����
        inputFieldPanel.add(sp2);
        GridBagLayout inputFieldGrid = new GridBagLayout();
        inputFieldPanel.setLayout(inputFieldGrid);
        inputFieldGrid.setConstraints(sp2, s);
        
        //buttonPanel ����
        BorderLayout buttonBorder = new BorderLayout();
        buttonPanel.setLayout(buttonBorder);
        JPanel tempPanel = new JPanel();
        tempPanel.add(sendButton);
        tempPanel.setSize(80,50);
        buttonPanel.add(tempPanel, BorderLayout.EAST);

        sendButton.addMouseListener((MouseListener) this);
        
        //�Ҳ��ֲ���
    	// �Ҳ��û��б�
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
    
	    public void mouseClicked(MouseEvent evt){
	        String messages = "";
	        Date date = new Date();
	        DateFormat df = DateFormat.getDateTimeInstance();
	        String formatDate = df.format(date);
	        messages="  "+myIP.getText()+"    "+formatDate+"\n  "+inputField.getText();
	        if (message == "  "+myIP.getText()+"    "+formatDate+"\n  ")
	        setMessage(messages);
	        if (messages == "") {
	    		
	    	} else {
	    		viewArea.setText(viewArea.getText()+messages+ "\n\n");
	    		inputField.setText("");
	    		setMessage("");
	    	}
	        
	    }
	     
	    public void setMessage(String msg) {
	    	message = msg;
	
	    }
	    public void appendChatWindowText(String msg) {
	    	if (msg == "") {
	    		
	    	} else {
	    		viewArea.setText(viewArea.getText()+msg+ "\n\n");
	    		inputField.setText("");
	    		setMessage("");
	    	}
	        
	    }
	    public String getMessage(){
	    	return message;
	    }
	
		public void appendPrivateChatWindowText(String msg) {
			// TODO Auto-generated method stub
			if (msg == "  ") {
			} else {
				viewArea.setText(viewArea.getText()+msg+ "\n\n");
				inputField.setText("");				
			}
		}
	
	
		public void refreshUserlist() {
			// TODO Auto-generated method stub
	        rightPanel.remove(scrollUserListWindow);
	        // refresh the new user list
	        userlistWin = new UserList(client, client.getUserlist());
	        remove(scrollUserListWindow);
	        scrollUserListWindow = new JScrollPane(userlistWin);
	        //addUserlistWindow();

	        
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
    }