package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import client.databean.LetterCommData;
import client.processor.GetProcessor;
import client.processor.UpdateProcessor;

import ui.MyButton;


import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Message {

	private JFrame frame;
	private List list;
	private MyButton showFriendList;
	private MyButton write;
	private MyButton boxType;
	
	private ArrayList<LetterCommData> letterCommDataArrayList;
	private GetProcessor getProcessor;
	private UpdateProcessor updateProcessor;
	
	/**
	 * Create the application.
	 */
	public Message(GetProcessor getProcessor, UpdateProcessor updateProcessor) {
		this.getProcessor = getProcessor;
		this.updateProcessor = updateProcessor;
		initialize();
		setListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("收件箱");
		frame.setSize(new Dimension(345, 400));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-150,
						 (int)(screenSize.height)/2-275);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		list = new List();
		list.setBounds(10, 10, 324, 303);
		frame.getContentPane().add(list);
		
/*
		for(int i=0;i<40;i++)
			list.add("收件箱"+i);
		*/
		showFriendList = new MyButton("好友列表");
		showFriendList.setBounds(10, 329, 100, 28);
		frame.getContentPane().add(showFriendList);
		
		boxType = new MyButton("收件箱");
		boxType.setBounds(122, 329, 100, 28);
		frame.getContentPane().add(boxType);
		
		write = new MyButton("写信");
		write.setBounds(234, 329, 100, 28);
		frame.getContentPane().add(write);
		letterCommDataArrayList = getProcessor.getLetterList(this);		//初始化信箱列表
		
		
	}
	
	
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public MyButton getBoxType() {
		return boxType;
	}

	public void setBoxType(MyButton boxType) {
		this.boxType = boxType;
	}

	/*
	 * 获得boxType的内容
	 */
	public String getBoxTypeText() {
		return this.frame.getTitle();
	}
	
	/*
	 * 设置boxType的内容
	 */
	public Boolean setBoxTypeText(String text) {
		this.boxType.setText(text);
		return true;
	}
	
	/*
	 * 获取对方姓名
	 */
	public String getLetterOut() {
		return this.list.getSelectedItem();
	}
	
	public void setListener(){
		showFriendList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FriendList.friendList.setVisible(true);
			}
		});
		
		write.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex()>-1)
					new WriteMessage(Message.this.updateProcessor.getUserName(), list.getSelectedItem(), updateProcessor);
				else
					new WriteMessage(updateProcessor);
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2&&e.getButton()==1)
					new MessageDetail(list.getSelectedIndex(), updateProcessor, Message.this, letterCommDataArrayList);
			}
		});
		
		boxType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Message.this.frame.getTitle().equals("收件箱"))
					Message.this.frame.setTitle("发件箱");
				else
					Message.this.frame.setTitle("收件箱");
				letterCommDataArrayList = getProcessor.getLetterList(Message.this);
				//Message.this.setLetterList(letterCommDataArrayList);
			}
		});
	}
	
	/*
	 * 对收件箱或者发件箱列表进行初始化
	 */
	public Boolean setLetterList(ArrayList<LetterCommData> letterCommData) {
		this.list.removeAll();
		if (letterCommData != null) {
			for (LetterCommData l : letterCommData) {
				this.list.add(l.getUsername());			
			}
		}
		if ( this.boxType.getText().equals("收件箱")) {
			this.boxType.setText("发件箱");
		} else {
			this.boxType.setText("收件箱");
		}
		return true;
	}
}
