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
		frame.setTitle("�ռ���");
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
			list.add("�ռ���"+i);
		*/
		showFriendList = new MyButton("�����б�");
		showFriendList.setBounds(10, 329, 100, 28);
		frame.getContentPane().add(showFriendList);
		
		boxType = new MyButton("�ռ���");
		boxType.setBounds(122, 329, 100, 28);
		frame.getContentPane().add(boxType);
		
		write = new MyButton("д��");
		write.setBounds(234, 329, 100, 28);
		frame.getContentPane().add(write);
		letterCommDataArrayList = getProcessor.getLetterList(this);		//��ʼ�������б�
		
		
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
	 * ���boxType������
	 */
	public String getBoxTypeText() {
		return this.frame.getTitle();
	}
	
	/*
	 * ����boxType������
	 */
	public Boolean setBoxTypeText(String text) {
		this.boxType.setText(text);
		return true;
	}
	
	/*
	 * ��ȡ�Է�����
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
				if(Message.this.frame.getTitle().equals("�ռ���"))
					Message.this.frame.setTitle("������");
				else
					Message.this.frame.setTitle("�ռ���");
				letterCommDataArrayList = getProcessor.getLetterList(Message.this);
				//Message.this.setLetterList(letterCommDataArrayList);
			}
		});
	}
	
	/*
	 * ���ռ�����߷������б���г�ʼ��
	 */
	public Boolean setLetterList(ArrayList<LetterCommData> letterCommData) {
		this.list.removeAll();
		if (letterCommData != null) {
			for (LetterCommData l : letterCommData) {
				this.list.add(l.getUsername());			
			}
		}
		if ( this.boxType.getText().equals("�ռ���")) {
			this.boxType.setText("������");
		} else {
			this.boxType.setText("�ռ���");
		}
		return true;
	}
}
