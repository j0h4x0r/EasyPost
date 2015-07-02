package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import client.processor.UpdateProcessor;

import ui.MyButton;
import ui.MyTextField;


import java.awt.TextArea;

public class ApplyGroup {

	private String groupLead;
	private JFrame frame;
	private JLabel userName;
	private MyTextField groupName;
	private TextArea reason;
	private MyButton apply;
	private Main main;
	private UpdateProcessor updateProcessor;		//数据操作
	/**
	 * Create the application.
	 */
	public ApplyGroup(String name, Main main, UpdateProcessor updateProcessor) {
		this.groupLead = name;
		this.main = main;					//父界面
		this.updateProcessor = updateProcessor;
		initialize();
		setListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				showDialog("退出","确认要退出编辑吗?");
			}
		});
		frame.setTitle("申请分组");
		frame.setSize(new Dimension(300,360));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-150,
						 (int)(screenSize.height)/2-275);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		userName = new JLabel("申请人: " + this.groupLead);
		userName.setBounds(20, 10, 260, 24);
		frame.getContentPane().add(userName);
		
		groupName = new MyTextField(5);
		groupName.setBounds(20, 40, 260, 24);
		groupName.setText("请填写小组名称...");
		frame.getContentPane().add(groupName);

		reason = new TextArea();
		reason.setBounds(20, 80, 260, 200);
		reason.setText("请填写申请理由...");
		frame.getContentPane().add(reason);
		
		groupName.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(groupName.getText().equals("请填写小组名称...")){
					groupName.setText("");
				}
				if(reason.getText().replace(" ","").equals("")){
					reason.setText("请填写申请理由...");			    
				}	
			}
		});
		
		reason.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(reason.getText().equals("请填写申请理由...")){
					reason.setText("");
				}
				if(groupName.getText().replace(" ","").equals("")){
							groupName.setText("请填写小组名称...");
				}	
			}
		});
		
		frame.getContentPane().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(groupName.getText().replace(" ","").equals("")){
					groupName.setText("请填写小组名称...");
				}
				if(reason.getText().replace(" ","").equals("")){
					reason.setText("请填写申请理由...");			    
				}
			}
		});
		
		apply = new MyButton("提交申请");
		apply.setBounds(98, 295, 100, 28);
		frame.getContentPane().add(apply);
	}
	
	private void setListener(){
		apply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			Boolean b = updateProcessor.applyGroup(ApplyGroup.this.groupName.getText(), 
						ApplyGroup.this.reason.getText(), ApplyGroup.this.groupLead, ApplyGroup.this.main);

			if (b) {
				ApplyGroup.this.frame.setVisible(false);
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
		
		yes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				d.dispose();
			}
		});
		
		no.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
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
}
