package client;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import client.processor.UpdateProcessor;

import comm.DataType;

import ui.MyButton;
import ui.MyTextField;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostPost {

	private JFrame frame;
	private MyTextField postTitle;
	private TextArea postContent;
	private MyButton post;
	private Main main;
	private String userName;
	UpdateProcessor updateProcessor;

	/**
	 * Create the application.
	 */
	public PostPost(String title, Main main, UpdateProcessor updateProcessor) {
		this.main = main;
		this.userName = main.getUserName();
		initialize(title);
		this.updateProcessor = updateProcessor;
		setListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String title) {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				showDialog("退出","确认要退出编辑吗?");
			}
		});
		frame.setTitle("发表新帖 —— " + title);
		frame.setSize(new Dimension(600, 520));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-300,
						 (int)(screenSize.height)/2-275);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		postTitle = new MyTextField(5);
		postTitle.setBounds(10, 10, 584, 30);
		postTitle.setText("请输入标题...");
		frame.getContentPane().add(postTitle);
		
		postContent = new TextArea();
		postContent.setBounds(10, 46, 580, 377);
		frame.getContentPane().add(postContent);
		
		postTitle.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(postTitle.getText().equals("请输入标题...")){
					postTitle.setText("");
				}
			}
		});
		
		postContent.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(postTitle.getText().replace(" ","").equals("")){
					postTitle.setText("请输入标题...");			    
				}	
			}
		});
		
		frame.getContentPane().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(postTitle.getText().replace(" ","").equals("")){
					postTitle.setText("请输入标题...");			    
				}	
			}
		});
		
		
		post = new MyButton("发表");
		post.setBounds(250, 445, 100, 28);
		frame.getContentPane().add(post);
	}
	
	private void setListener(){
		
		/*
		 * 发布帖子按钮监听
		 */
		post.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date dateFormat = new Date();
				String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateFormat);
				if (updateProcessor.postPost(main, PostPost.this, dateFormat, DataType.POST_POST)) {
					main.addPost(PostPost.this.postTitle.getText(), 
							PostPost.this.userName, dateStr, dateStr, "0");								//若发布成功，在用户界面中添加帖子
					PostPost.this.frame.setVisible(false);
				}
			}
		});
	}
	
	private void showDialog(String title, String content){
		final JDialog d = new JDialog(frame,title);
		d.setBackground(Color.WHITE);
		d.setModal(true);
		d.setSize(new Dimension(300,150));
		d.setResizable(false);
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		d.setLocation((int)(screenSize.width)/2-150,
						 (int)(screenSize.height)/2-150);
		SpringLayout d_layout = new SpringLayout();
		d.getContentPane().setLayout(d_layout);
		
		JLabel l = new JLabel("",SwingConstants.CENTER);
	//	l.setIcon();
		l.setText(content);
		l.setFont(new Font("",0,18));
		d.getContentPane().add(l);
		
		MyButton yes = new MyButton("确认");
		Font font=new Font("",0,14);
		yes.setFont(font);
		d.getContentPane().add(yes);
		
		MyButton no = new MyButton("取消");
		no.setFont(font);
		d.getContentPane().add(no);
		
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				d.dispose();
			}
		});
		
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				d.dispose();
			}
		});
		
		d_layout.putConstraint(SpringLayout.NORTH, l, 20, SpringLayout.NORTH, d.getContentPane());
		d_layout.putConstraint(SpringLayout.SOUTH, l, 70, SpringLayout.NORTH, d.getContentPane());
		d_layout.putConstraint(SpringLayout.WEST, l, 0, SpringLayout.WEST, d.getContentPane());
		d_layout.putConstraint(SpringLayout.EAST, l, 0, SpringLayout.EAST, d.getContentPane());
		
		d_layout.putConstraint(SpringLayout.SOUTH, yes, -20, SpringLayout.SOUTH, d.getContentPane());
		d_layout.putConstraint(SpringLayout.NORTH, yes, -50, SpringLayout.SOUTH, d.getContentPane());
		d_layout.putConstraint(SpringLayout.EAST, yes, -105, SpringLayout.EAST, d.getContentPane());
		d_layout.putConstraint(SpringLayout.WEST, yes, -180, SpringLayout.EAST, d.getContentPane());
	
		d_layout.putConstraint(SpringLayout.SOUTH, no, -20, SpringLayout.SOUTH, d.getContentPane());
		d_layout.putConstraint(SpringLayout.NORTH, no, -50, SpringLayout.SOUTH, d.getContentPane());
		d_layout.putConstraint(SpringLayout.EAST, no, -15, SpringLayout.EAST, d.getContentPane());
		d_layout.putConstraint(SpringLayout.WEST, no, -90, SpringLayout.EAST, d.getContentPane());
		
		d.setVisible(true);
	}

	public MyTextField getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(MyTextField postTitle) {
		this.postTitle = postTitle;
	}

	public TextArea getPostContent() {
		return postContent;
	}

	public void setPostContent(TextArea postContent) {
		this.postContent = postContent;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
