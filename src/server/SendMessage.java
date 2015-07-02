package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.MyButton;
import ui.MyTextField;

import java.awt.TextArea;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendMessage {

	private JFrame frame;
	private TextArea content;
	private MyButton send;
	private String userName;
	private MyTextField sendToUserName;
	private Label label;

	/**
	 * Create the application.
	 */
	public SendMessage() {
		this.userName= "";
		initialize();
	}
	
	public SendMessage(String userName) {
		this.userName = userName;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("站内信");
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
		
		send = new MyButton("发送");
		send.setBounds(130, 230, 100, 28);
		frame.getContentPane().add(send);
		
		label = new Label("收件人");
		label.setBounds(10, 14, 43, 17);
		label.setBackground(Color.white);
		frame.getContentPane().add(label);
		
		sendToUserName = new MyTextField(5);
		sendToUserName.setBounds(59, 10, 291, 28);
		sendToUserName.setText(this.userName);
		sendToUserName.setEditable(false);
		frame.getContentPane().add(sendToUserName);	
		
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Service mService = new Service();
				if(content.getText().toString().equals("")){
					JOptionPane.showMessageDialog(frame, "信息不能为空~");
					return;
				}
				if(mService.sendMessage(userName, content.getText().toString())){
					JOptionPane.showMessageDialog(frame, "^_^发送成功！");
					frame.dispose();
				}
				else
					JOptionPane.showMessageDialog(frame, "*_*发送失败。。。");
				
			}
		});
	}
}
