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
		frame.setTitle("ע��");
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
		userName.setText("�������û���...");
		userName.setFont(new Font("",0,12));
		frame.getContentPane().add(userName);
		
		password = new MyPasswordField(5);
		password.setBounds(25, 65, 150, 30);
		password.setText("����������...");
		password.setEchoChar('\0');
		password.setFont(new Font("",0,12));
		frame.getContentPane().add(password);
		
		confirm = new MyPasswordField(5);
		confirm.setBounds(25, 105, 150, 30);
		confirm.setText("�ٴ���������...");
		confirm.setEchoChar('\0');
		confirm.setFont(new Font("",0,12));
		frame.getContentPane().add(confirm);
		
		userName.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(userName.getText().equals("�������û���...")){
					userName.setText("");
				}
				if(String.valueOf(password.getPassword()).replace(" ","").equals("")){
					password.setText("����������...");
				    password.setEchoChar('\0');				    
				}
				if(String.valueOf(confirm.getPassword()).replace(" ","").equals("")){
					confirm.setText("�ٴ���������...");
					confirm.setEchoChar('\0');				    
				}
			}
		});
		
		password.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(String.valueOf(password.getPassword()).equals("����������...")){
					password.setText("");
				    password.setEchoChar('*');
				}
				if(userName.getText().replace(" ","").equals("")){
					userName.setText("�������û���...");
				}
				if(String.valueOf(confirm.getPassword()).replace(" ","").equals("")){
					confirm.setText("�ٴ���������...");
					confirm.setEchoChar('\0');				    
				}
			}
		});
		
		confirm.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(String.valueOf(confirm.getPassword()).equals("�ٴ���������...")){
					confirm.setText("");
				    confirm.setEchoChar('*');
				}
				if(userName.getText().replace(" ","").equals("")){
					userName.setText("�������û���...");
				}
				if(String.valueOf(password.getPassword()).replace(" ","").equals("")){
					password.setText("����������...");
					password.setEchoChar('\0');				    
				}
			}
		});
		
		frame.getContentPane().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(userName.getText().replace(" ","").equals("")){
					userName.setText("�������û���...");
				}	
				if(String.valueOf(password.getPassword()).replace(" ","").equals("")){
					password.setText("����������...");
				    password.setEchoChar('\0');				    
				}
				if(String.valueOf(confirm.getPassword()).replace(" ","").equals("")){
					confirm.setText("�ٴ���������...");
					confirm.setEchoChar('\0');				    
				}
			}
		});
		
		reg = new MyButton("ע��");
		reg.setBounds(65, 150, 70, 20);
		reg.setFont(new Font("",0,12));
		frame.getContentPane().add(reg);
		
		cancel = new MyButton("ȡ��");
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
		
		MyButton yes = new MyButton("ȷ��");
		Font font=new Font("",0,14);
		yes.setFont(font);
		d.getContentPane().add(yes);
		
		MyButton no = new MyButton("ȡ��");
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
				showDialog("�˳�","ȷ��Ҫȡ��ע����?");
			}
		});
	}
	
	private void register(){
		if (this.userName.getText().equals("�������û���...")|| this.userName.getText().equals("")) {
			showDialog("����", "δ�����û���������д�û���");
			this.frame.setVisible(true);
		} else if (String.valueOf(this.password.getPassword()).equals("����������...") || String.valueOf(this.password.getPassword()).equals("")) {
			showDialog("����", "δ�������룬����д����");			 
			this.frame.setVisible(true);
		} else if (String.valueOf(this.confirm.getPassword()).equals("�ٴ���������...") || String.valueOf(this.confirm.getPassword()).equals("")) {
			showDialog("����", "δȷ�����룬������ȷ������");			
			this.frame.setVisible(true);
		} else if (!String.valueOf(this.password.getPassword()).equals(String.valueOf(this.confirm.getPassword()))) {
			showDialog("����", "�����ȷ�����벻һ�£�����������");						
			this.frame.setVisible(true);
		} else {
			if (updateProcessor.regist(Reg.this)) {
				this.frame.setVisible(false);
			};
					//���ݿ�д�룬������ʾ���û�����

		}
		
	}

	public MyTextField getUserName() {
		return userName;
	}

	public JPasswordField getPassword() {
		return password;
	}
	
	
	
}






