package src.UI;
import src.Client;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
 
public class P2PChatRoom extends JFrame implements MouseListener {

    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private JTextArea viewArea;
    private JTextArea inputField;
    private JButton sendButton;
    //private JButton cancelButton;
    private JLabel friendPort;
    private JLabel friendIP;
    private JLabel TMP;
    private JButton sendFile;
    private JButton sendPic;
    private String message;
    private String friend;
    private Client client;
	private HashMap<String, P2PChatRoom> privateChatList;

    public P2PChatRoom(Client _client, String ip,HashMap<String, P2PChatRoom> map){
    	super(ip);
    	client = _client;
    	friend = ip;
    	privateChatList = map;
        privateChatList.put(friend, this);
        //addComponentListener((ComponentListener) this);
        //addWindowListener((WindowListener) this);
        
    	
        frame = new JFrame("P2P Chat Room");
        GridBagLayout leftGrid = new GridBagLayout();
        GridBagLayout mainGrid = new GridBagLayout();
        frame.setLayout(mainGrid);
        
        // leftPanel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(leftGrid);

        // leftPanel components
        JPanel viewAreaPanel = new JPanel();
        JPanel functionPanel = new JPanel();
        JPanel inputFieldPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        
        // set components' attribute
        viewArea = new JTextArea(1,1);
        viewArea.setLineWrap(true);
        inputField = new JTextArea(1,1);
        inputField.setLineWrap(true);
        
        friendPort = new JLabel();
        friendPort.setText(String.valueOf(client.getPort()));
        friendIP = new JLabel();
        friendIP.setText(client.getIP());
        TMP = new JLabel();
        TMP.setText(":");
        frame.setTitle(friendIP.getText()+TMP.getText()+friendPort.getText());
        
        sendButton = new JButton("Send");
        sendFile = new JButton();
        sendPic = new JButton();

        // scrollPanel
        JScrollPane sp1 = new JScrollPane(viewArea);
        sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane sp2 = new JScrollPane(inputField);
        sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        frame.setSize(600,500);
        frame.setVisible(true);
        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        
        // leftPanel layout
        frame.add(leftPanel);
        s.gridy = 0;
        s.gridx = 0;
        s.weightx = 25;
        s.weighty = 1;
        mainGrid.setConstraints(leftPanel, s);
        
        leftPanel.add(viewAreaPanel);
        leftPanel.add(functionPanel);
        leftPanel.add(inputFieldPanel);
        leftPanel.add(buttonPanel);
        s.gridy = 0;
        s.gridheight = 10;
        s.weighty = 10;
        s.weightx = 1;
        leftGrid.setConstraints(viewAreaPanel, s);

        s.gridy = 10;
        s.gridheight = 1;
        s.weighty = 1;
        s.weightx = 1;
        leftGrid.setConstraints(functionPanel, s);

        s.gridy = 11;
        s.gridheight = 4;
        s.weighty = 4;
        s.weightx = 1;
        leftGrid.setConstraints(inputFieldPanel, s);

        s.gridy = 15;
        s.gridheight = 1;
        s.weighty = 0.02;
        s.weightx = 1;
        leftGrid.setConstraints(buttonPanel, s);
        
        
        //viewAreaPanel
        viewAreaPanel.add(sp1);
        GridBagLayout viewAreaGrid = new GridBagLayout();
        viewAreaPanel.setLayout(viewAreaGrid);
        viewAreaGrid.setConstraints(sp1, s);
        
        //functionPanel
        functionPanel.add(sendFile);
        functionPanel.add(sendPic);
        functionPanel.setLayout(null);
        sendFile.setBounds(10,3,20,20);
        sendFile.setBorderPainted(false);
        sendPic.setBounds(50,3,20,20);
        sendPic.setBorderPainted(false);
        
        ImageIcon filepic = new ImageIcon("src/images/file.jpg");
        int width = (int)sendFile.getWidth();
        int height = (int)sendFile.getHeight();
        filepic.setImage(filepic.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        sendFile.setIcon(filepic);
        
        ImageIcon picpic = new ImageIcon("src/images/pic.png");
        int width1 = (int)sendPic.getWidth();
        int height1 = (int)sendPic.getHeight();
        picpic.setImage(picpic.getImage().getScaledInstance(width1, height1, Image.SCALE_DEFAULT));
        sendPic.setIcon(picpic);
        
        
        //inputFieldPanel
        inputFieldPanel.add(sp2);
        GridBagLayout inputFieldGrid = new GridBagLayout();
        inputFieldPanel.setLayout(inputFieldGrid);
        inputFieldGrid.setConstraints(sp2, s);
        
        //buttonPanel
        BorderLayout buttonBorder = new BorderLayout();
        buttonPanel.setLayout(buttonBorder);
        JPanel tempPanel = new JPanel();
        tempPanel.add(sendButton);
        tempPanel.setSize(80,50);
        buttonPanel.add(tempPanel, BorderLayout.EAST);

        sendButton.addActionListener(new sendButtonListener());
        }

    
 
    private class sendButtonListener implements ActionListener{
        
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String messages = "";
	        Date date = new Date();
	        DateFormat df = DateFormat.getDateTimeInstance();
	        String formatDate = df.format(date);
	        messages="  "+formatDate+"\n  "+inputField.getText();
	        String tmpstr = messages;
	        String tmpusr = friend;
	        
	        client.message_to_one(tmpusr,tmpstr);
	        inputField.setText("");
	        viewArea.setText(viewArea.getText() + "\n" + messages+"\n");
		}
    }
     
    public void setMessage(String msg) {
    	message = msg;

    }
    public void appendChatWindowText() {
        viewArea.append(friend + client.getMessage() + "\n");
    }
    public String getMessage(){
    	return message;
    }

    public void windowClosed(WindowEvent arg0) {
	    // TODO Auto-generated method stub
	    System.out.println("Windows Closed!");
	}
    
    public void windowClosing(WindowEvent arg0) {
        // TODO Auto-generated method stub
        // remove this private chat window from list
        privateChatList.put(friend, null);
        dispose();
    }
    
    public void mousePressed(MouseEvent evt){ }
    public void mouseReleased(MouseEvent evt){ }
    public void mouseEntered(MouseEvent e){ }
    public void mouseExited(MouseEvent e){ }
	@Override
	public void mouseClicked(MouseEvent arg0) {	}
     
    }
