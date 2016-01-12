package src.UI;

import javax.swing.*;

import src.Client;

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


    public P2PChatRoom(Client client, String ip,HashMap<String, P2PChatRoom> map){
    	
    	
        frame = new JFrame("P2P Chat Room");
        GridBagLayout leftGrid = new GridBagLayout();
        GridBagLayout mainGrid = new GridBagLayout();
        frame.setLayout(mainGrid);
        
        //左部分
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(leftGrid);
        //rightPanel.setLayout(rightGrid);

        //左部分区
        JPanel viewAreaPanel = new JPanel();
        JPanel functionPanel = new JPanel();
        JPanel inputFieldPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        
        //组件
        //文本域
        viewArea = new JTextArea(1,1);
        viewArea.setLineWrap(true);
        inputField = new JTextArea(1,1);
        inputField.setLineWrap(true);
        
        //组件
        friendPort = new JLabel();
        friendPort.setText(String.valueOf(client.getPort()));	//拿用户状态填充
        friendIP = new JLabel();
        friendIP.setText(client.getIP());	//拿用户名填充
        TMP = new JLabel();
        TMP.setText(":");
        frame.setTitle(friendIP.getText()+TMP.getText()+friendPort.getText());
        
        sendButton = new JButton("Send");
        sendFile = new JButton();
        sendPic = new JButton();

        //滚动条聊天窗口
        JScrollPane sp1 = new JScrollPane(viewArea);
        sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane sp2 = new JScrollPane(inputField);
        sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        //布局

        frame.setSize(600,500);
        frame.setVisible(true);
        //frame.setResizable(false);
        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        
        //左右两部分布局
        frame.add(leftPanel);
        s.gridy = 0;
        s.gridx = 0;
        s.weightx = 25;
        s.weighty = 1;
        mainGrid.setConstraints(leftPanel, s);
        
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

        sendButton.addMouseListener((MouseListener) this);
        }

    
 
    public void mouseClicked(MouseEvent evt){
        String messages = "";
        Date date = new Date();
        DateFormat df = DateFormat.getDateTimeInstance();
        String formatDate = df.format(date);
        messages="  "+friendIP.getText()+"    "+formatDate+"\n  "+inputField.getText();
        setMessage(messages);
    }
     
    public void setMessage(String msg) {
    	message = msg;

    }
    public void appendPrivateChatWindowText(String msg) {
    	if (msg == "  ") {
    	} else {
	        viewArea.setText(viewArea.getText()+msg+ "\n\n");
	        inputField.setText("");
	        setMessage("");
    	}
    }
    public String getMessage(){
    	return message;
    }

    public void mousePressed(MouseEvent evt){ }
    public void mouseReleased(MouseEvent evt){ }
    public void mouseEntered(MouseEvent e){ }
    public void mouseExited(MouseEvent e){ }
     
    }