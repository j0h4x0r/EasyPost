package client;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import client.processor.UpdateProcessor;

import ui.MyButton;
import ui.MyPasswordField;
import ui.MyTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Reg {

	private JFrame frame;
	private MyTextField userName;
	private JPasswordField password;
	private JPasswordField confirm;
	private MyButton reg;
	private MyButton cancel;
	private UpdateProcessor updateProcessor;
	

	/**
	 * Create the application.
	 */
	public Reg(UpdateProcessor updateProcessor) {
		this.updateProcessor = updateProcessor;
		initialize();
		setListener();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("注册");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setSize(new Dimension(200,240));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-100,
						 (int)(screenSize.height)/2-250);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		userName = new MyTextField(5);
		userName.setBounds(25, 25, 150, 30);
		userName.setText("请输入用户名...");
		userName.setFont(new Font("",0,12));
		frame.getContentPane().add(userName);
		
		password = new MyPasswordField(5);
		password.setBounds(25, 65, 150, 30);
		password.setText("请输入密码...");
		password.setEchoChar('\0');
		password.setFont(new Font("",0,12));
		frame.getContentPane().add(password);
		
		confirm = new MyPasswordField(5);
		confirm.setBounds(25, 105, 150, 30);
		confirm.setText("再次输入密码...");
		confirm.setEchoChar('\0');
		confirm.setFont(new Font("",0,12));
		frame.getContentPane().add(confirm);
		
		userName.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(userName.getText().equals("请输入用户名...")){
					userName.setText("");
				}
				if(String.valueOf(password.getPassword()).replace(" ","").equals("")){
					password.setText("请输入密码...");
				    password.setEchoChar('\0');				    
				}
				if(String.valueOf(confirm.getPassword()).replace(" ","").equals("")){
					confirm.setText("再次输入密码...");
					confirm.setEchoChar('\0');				    
				}
			}
		});
		
		password.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(String.valueOf(password.getPassword()).equals("请输入密码...")){
					password.setText("");
				    password.setEchoChar('*');
				}
				if(userName.getText().replace(" ","").equals("")){
					userName.setText("请输入用户名...");
				}
				if(String.valueOf(confirm.getPassword()).replace(" ","").equals("")){
					confirm.setText("再次输入密码...");
					confirm.setEchoChar('\0');				    
				}
			}
		});
		
		confirm.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(String.valueOf(confirm.getPassword()).equals("再次输入密码...")){
					confirm.setText("");
				    confirm.setEchoChar('*');
				}
				if(userName.getText().replace(" ","").equals("")){
					userName.setText("请输入用户名...");
				}
				if(String.valueOf(password.getPassword()).replace(" ","").equals("")){
					password.setText("请输入密码...");
					password.setEchoChar('\0');				    
				}
			}
		});
		
		frame.getContentPane().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(userName.getText().replace(" ","").equals("")){
					userName.setText("请输入用户名...");
				}	
				if(String.valueOf(password.getPassword()).replace(" ","").equals("")){
					password.setText("请输入密码...");
				    password.setEchoChar('\0');				    
				}
				if(String.valueOf(confirm.getPassword()).replace(" ","").equals("")){
					confirm.setText("再次输入密码...");
					confirm.setEchoChar('\0');				    
				}
			}
		});
		
		reg = new MyButton("注册");
		reg.setBounds(65, 150, 70, 20);
		reg.setFont(new Font("",0,12));
		frame.getContentPane().add(reg);
		
		cancel = new MyButton("取消");
		cancel.setBounds(65, 180, 70, 20);
		cancel.setFont(new Font("",0,12));
		frame.getContentPane().add(cancel);
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

	private void setListener(){
		reg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				register();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showDialog("退出","确认要取消注册吗?");
			}
		});
	}
	
	private void register(){
		if (this.userName.getText().equals("请输入用户名...")|| this.userName.getText().equals("")) {
			showDialog("错误", "未输入用户名，请填写用户名");
			this.frame.setVisible(true);
		} else if (String.valueOf(this.password.getPassword()).equals("请输入密码...") || String.valueOf(this.password.getPassword()).equals("")) {
			showDialog("错误", "未输入密码，请填写密码");			 
			this.frame.setVisible(true);
		} else if (String.valueOf(this.confirm.getPassword()).equals("再次输入密码...") || String.valueOf(this.confirm.getPassword()).equals("")) {
			showDialog("错误", "未确认密码，请输入确认密码");			
			this.frame.setVisible(true);
		} else if (!String.valueOf(this.password.getPassword()).equals(String.valueOf(this.confirm.getPassword()))) {
			showDialog("错误", "密码和确认密码不一致，请重新输入");						
			this.frame.setVisible(true);
		} else {
			if (updateProcessor.regist(Reg.this)) {
				this.frame.setVisible(false);
			};
					//数据库写入，并且显示新用户界面

		}
		
	}

	public MyTextField getUserName() {
		return userName;
	}

	public JPasswordField getPassword() {
		return password;
	}
	
	
	
}






