package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.databean.LetterCommData;
import client.processor.UpdateProcessor;

import ui.MyButton;


import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageDetail {

	private JFrame frame;
	private TextArea content;
	private TextArea reply;
	private MyButton replybt;
	private UpdateProcessor updateProcessor;
	private int selectItemNum;
	private String userName;
	private String sendPerson;
	private String letterContent;
	private Date time;
	private ArrayList<LetterCommData> letterCommDataArrayList;
	/**
	 * Create the application.
	 */
	public MessageDetail(int i, UpdateProcessor updateProcessor, Message message, ArrayList<LetterCommData> letterCommDataaArrayList) {
		this.selectItemNum = i;
		this.updateProcessor = updateProcessor;
		this.letterCommDataArrayList = letterCommDataaArrayList;
		if (message.getBoxTypeText().equals("收件箱")) {
			this.userName = letterCommDataArrayList.get(i).getUsername();
			this.sendPerson = letterCommDataaArrayList.get(i).getLetterOut();
			this.letterContent = letterCommDataaArrayList.get(i).getLetterContent();
		} else {
			this.userName = letterCommDataaArrayList.get(i).getLetterOut();
			this.sendPerson = letterCommDataaArrayList.get(i).getUsername();
			this.letterContent = letterCommDataaArrayList.get(i).getLetterContent();
		}
		this.time = letterCommDataaArrayList.get(i).getTime();
		initialize();
		setListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("站内信 —— " + this.sendPerson);
		frame.setSize(new Dimension(360, 300));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-180,
						 (int)(screenSize.height)/2-225);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		content = new TextArea();
		content.setBounds(10, 10, 340, 187);
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		
		content.setText(this.sendPerson + ":\n发送时间：" + d.format(this.time) + "\n" + this.letterContent);
		content.setEditable(false);
		frame.getContentPane().add(content);
		
		reply = new TextArea();
		reply.setBounds(10, 203, 234, 65);
		frame.getContentPane().add(reply);
		
		replybt = new MyButton("回 复");
		replybt.setBounds(250, 221, 100, 28);
		frame.getContentPane().add(replybt);
	}
	
	public int getSelectItemNum() {
		return selectItemNum;
	}

	public void setSelectItemNum(int selectItemNum) {
		this.selectItemNum = selectItemNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setContentText(String s) {
		this.content.setText(s);
	}
	public String getReply() {
		return this.reply.getText();
	}
	
	public String getSendPerson() {
		return sendPerson;
	}

	public void setSendPerson(String sendPerson) {
		this.sendPerson = sendPerson;
	}

	public String getLetterContent() {
		return letterContent;
	}

	public void setLetterContent(String letterContent) {
		this.letterContent = letterContent;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	private void setListener(){
		replybt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if (MessageDetail.this.reply.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "回复内容不能为空！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					updateProcessor.replyLetter(MessageDetail.this);
					MessageDetail.this.frame.setVisible(false);
					/*
					LetterContent newLetter = new LetterContent(MessageDetail.this.sendPerson, MessageDetail.this.userName, 
							MessageDetail.this.reply.getText(), DateFormat.getDateInstance(), DataType.REPLY_LETTER);
					Respond respond = (Respond) msgClient.sendRequest(newLetter);
					if (respond.getResult() == true) {
						JOptionPane.showConfirmDialog(null, "回复成功");
					} else {
						JOptionPane.showConfirmDialog(null, respond.getErrorMessage());
					}
					*/
				}
			}
		});
	}
}
