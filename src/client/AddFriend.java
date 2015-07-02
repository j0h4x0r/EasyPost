package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.processor.UpdateProcessor;

import ui.MyButton;
import ui.MyTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;

public class AddFriend {

	private JFrame frame;
	private MyTextField userName;			//要添加的好友姓名输入框
	private MyButton add;		
	private UpdateProcessor updateProcessor;
	/**
	 * Create the application.
	 */
	public AddFriend(UpdateProcessor updateProcessor) {
		this.updateProcessor = updateProcessor;
		initialize();
		setListener();	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("添加好友");
		frame.setSize(new Dimension(300, 150));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-150,
						 (int)(screenSize.height)/2-200);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		userName = new MyTextField(5);
		userName.setBounds(40, 23, 220, 35);
		userName.setText("请填写好友名称...");
		frame.getContentPane().add(userName);
		
		add = new MyButton("添加");
		add.setBounds(100, 75, 100, 28);
		frame.getContentPane().add(add);
		
		frame.getContentPane().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
			
				
			}
		});
		userName.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(userName.getText().replace(" ","").equals("")){
					userName.setText("请填写好友名称...");
				}else if (userName.getText().equals("请填写好友名称...")) {
					userName.setText("");
				}
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (userName.getText().equals("请填写好友名称...")) {
					userName.setText("");
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void closeWindow() {
		this.frame.setVisible(false);
	}
	
	public void setListener(){
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (userName.getText().equals("") || userName.getText().equals("请填写好友名称...")) {
					JOptionPane.showMessageDialog(null, "未填写添加好友姓名，请填写！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {					
					if (updateProcessor.addFriend(userName.getText(), AddFriend.this)) {
						FriendList.friendList.addFriend();
					}
					
				}				
			}
		});
	}

}
