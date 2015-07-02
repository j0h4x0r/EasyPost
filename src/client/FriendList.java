package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;

import javax.swing.JFrame;

import client.processor.GetProcessor;
import client.processor.UpdateProcessor;

import comm.MsgClient;

import ui.MyButton;


import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FriendList {
	
	public static FriendList friendList = new FriendList();
	
	private String userName;			//用户姓名
	private JFrame frame;
	private MyButton addFriend;
	private MyButton delFriend;
	private List list;

	private PopupMenu menu;

	private MyButton sendMessage;
	
	private MsgClient msgClient;
	private UpdateProcessor updateProcessor;
	private GetProcessor getProcessor;
	
	private Main main;
	
	/**
	 * Create the application.
	 */
	
	private FriendList() {
		initialize();
		setListener();
		frame.setVisible(true);
	}

	public String getUserName() {
		return userName;
	}

	public UpdateProcessor getUpdateProcessor() {
		return updateProcessor;
	}

	public void setUpdateProcessor(UpdateProcessor updateProcessor) {
		FriendList.friendList.updateProcessor = updateProcessor;
	}

	public GetProcessor getGetProcessor() {
		return getProcessor;
	}

	public void setGetProcessor(GetProcessor getProcessor) {
		FriendList.friendList.getProcessor = getProcessor;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public MsgClient getMsgClient() {
		return msgClient;
	}

	public Boolean setUserName(String userName) {
		FriendList.friendList.userName = userName;
		return true;
	}
	
	public Boolean setMsgClient(MsgClient msgClient) {
		this.msgClient = msgClient;
		return true;
	}
	
	public void addFriend(){
		list.removeAll();
		for (String string : FriendList.friendList.getProcessor.getFrientArrayList()) {
			list.add(string);
		}
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("好友列表");
		frame.setSize(new Dimension(270, 450));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-400,
						 (int)(screenSize.height)/2-275);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		list = new List();
		list.setBounds(10, 10, 249, 356);
		frame.getContentPane().add(list);
		
		/*
		list.add("好友1");
		list.add("好友2");
		*/
		menu = new PopupMenu();
		MenuItem mi = new MenuItem("删除好友");
		mi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {		
				list.remove(list.getSelectedIndex());
			}		
		});
		menu.add(mi);
		mi = new MenuItem("发送站内信");
		mi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {		
				new WriteMessage(updateProcessor);
			}		
		});
		
		addFriend = new MyButton("添加");
		addFriend.setBounds(10, 378, 75, 28);
		frame.getContentPane().add(addFriend);
		
		delFriend = new MyButton("删除");
		delFriend.setBounds(97, 378, 75, 28);
		frame.getContentPane().add(delFriend);
		
		sendMessage = new MyButton("写信");
		sendMessage.setBounds(184, 378, 75, 28);
		frame.getContentPane().add(sendMessage);
		
	}
	
	/*
	 * 增加好友
	 */
	public Boolean addFriendList(ArrayList<String> friendList) {
		for (String s : friendList) {
			this.list.add(s);
		}
		return true;
	}
	
	private void setListener(){
		/*
		 * 增加好友
		 */
		addFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddFriend(FriendList.this.updateProcessor);
			}
		});
		
		/*
		 * 删除好友
		 */
		delFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (friendList.updateProcessor.delFriend(friendList)) {
					list.remove(list.getSelectedIndex());
				}
				
			}
		});
		
		sendMessage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new WriteMessage(FriendList.friendList.userName, list.getSelectedItem(), updateProcessor);
			}			
		});
		
		list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if(e.getButton()==1&&e.getClickCount()==2&&list.getSelectedIndex()>-1){
            		new WriteMessage(FriendList.friendList.userName, FriendList.friendList.list.getSelectedItem(), updateProcessor);
				}
            }
        });
	}
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void setVisible(boolean b){
		FriendList.friendList.frame.setVisible(b);
		FriendList.friendList.frame.validate();
	}
	
	public void add(String userName){
		list.add(userName);
		list.validate();
	}
	
}
