package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.processor.UpdateProcessor;

import ui.MyButton;
import ui.MyTextField;


import java.awt.TextArea;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WriteMessage {

	private JFrame frame;
	private TextArea content;
	private MyButton send;
	private String userName;
	private MyTextField sendToUserName;
	private Label label;
	
	private String letterIn;
	
	private  UpdateProcessor updateProcessor;
	
	/**
	 * Create the application.
	 */
	public WriteMessage(UpdateProcessor updateProcessor) {
		this.updateProcessor = updateProcessor;
		this.userName= updateProcessor.getUserName();
		initialize();
		setListener();
	}
	
	public WriteMessage(String userName, String letterIn, UpdateProcessor updateProcessor) {
		this.userName = userName;
		this.letterIn = letterIn;
		this.updateProcessor = updateProcessor;
		initialize();
		setListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("站内信 ——  写信");
		frame.setSize(new Dimension(360, 300));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-180,
						 (int)(screenSize.height)/2-225);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		content = new TextArea();
		content.setBounds(10, 44, 340, 177);
		frame.getContentPane().add(content);
		
		send = new MyButton("发 送");
		send.setBounds(130, 230, 100, 28);
		frame.getContentPane().add(send);
		
		label = new Label("收件人");
		label.setBounds(10, 14, 43, 17);
		label.setBackground(Color.white);
		frame.getContentPane().add(label);
		
		sendToUserName = new MyTextField(5);
		sendToUserName.setBounds(59, 10, 291, 28);
		sendToUserName.setText(this.letterIn);
		frame.getContentPane().add(sendToUserName);
		
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLetterIn() {
		return this.sendToUserName.getText();
	}

	public void setLetterIn(String letterIn) {
		this.letterIn = letterIn;
	}

	private void setListener() {
		this.send.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (sendToUserName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "发件人不得为空！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (updateProcessor.sendLetter(WriteMessage.this)) {
						WriteMessage.this.frame.setVisible(false);
					}
				}
			}
		});
	}
	
	public String getLetterContent() {
		return this.content.getText();
	}

}
