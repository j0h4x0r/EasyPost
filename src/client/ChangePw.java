package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import client.processor.UpdateProcessor;

import ui.MyButton;
import ui.MyPasswordField;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChangePw {

	private JFrame frame;
	private MyPasswordField originPw;
	private MyPasswordField newPw;
	private MyPasswordField confirmPw;
	private MyButton confirm;
	
	private String userName;
	private UpdateProcessor updateProcessor;

	/**
	 * Create the application.
	 */
	public ChangePw(String userName, UpdateProcessor updateProcessor) {
		this.userName = userName;
		this.updateProcessor = updateProcessor;
		initialize();
		setListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("�޸�����");
		frame.setSize(new Dimension(300, 250));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-150,
						 (int)(screenSize.height)/2-250);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		originPw = new MyPasswordField(5);
		originPw.setBounds(50, 30, 200, 35);
		originPw.setText("������ԭʼ����...");
		originPw.setEchoChar('\0');
		frame.getContentPane().add(originPw);
		
		newPw = new MyPasswordField(5);
		newPw.setBounds(50, 80, 200, 35);
		newPw.setText("������������...");
		newPw.setEchoChar('\0');
		frame.getContentPane().add(newPw);
		
		confirmPw = new MyPasswordField(5);
		confirmPw.setBounds(50, 130, 200, 35);
		confirmPw.setText("�ٴ���������...");
		confirmPw.setEchoChar('\0');
		frame.getContentPane().add(confirmPw);
		
		confirm = new MyButton("ȷ���޸�");
		confirm.setBounds(100, 180, 100, 28);
		frame.getContentPane().add(confirm);
		
		originPw.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(String.valueOf(originPw.getPassword()).equals("������ԭʼ����...")){
					originPw.setText("");
					originPw.setEchoChar('*');
				}
				if(String.valueOf(newPw.getPassword()).replace(" ","").equals("")){
					newPw.setText("������������...");
				    newPw.setEchoChar('\0');				    
				}
				if(String.valueOf(confirmPw.getPassword()).replace(" ","").equals("")){
					confirmPw.setText("�ٴ���������...");
					confirmPw.setEchoChar('\0');				    
				}
			}
		});
		
		newPw.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(String.valueOf(newPw.getPassword()).equals("������������...")){
					newPw.setText("");
				    newPw.setEchoChar('*');
				}
				if(String.valueOf(originPw.getPassword()).replace(" ","").equals("")){
					originPw.setText("������ԭʼ����...");
					originPw.setEchoChar('\0');
				}
				if(String.valueOf(confirmPw.getPassword()).replace(" ","").equals("")){
					confirmPw.setText("�ٴ���������...");
					confirmPw.setEchoChar('\0');				    
				}
			}
		});
		
		confirmPw.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(String.valueOf(confirmPw.getPassword()).equals("�ٴ���������...")){
					confirmPw.setText("");
				    confirmPw.setEchoChar('*');
				}
				if(String.valueOf(originPw.getPassword()).replace(" ","").equals("")){
					originPw.setText("������ԭʼ����...");
					originPw.setEchoChar('\0');
				}
				if(String.valueOf(newPw.getPassword()).replace(" ","").equals("")){
					newPw.setText("������������...");
					newPw.setEchoChar('\0');				    
				}
			}
		});
		
		frame.getContentPane().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(String.valueOf(originPw.getPassword()).replace(" ","").equals("")){
					originPw.setText("������ԭʼ����...");
					originPw.setEchoChar('\0');
				}	
				if(String.valueOf(newPw.getPassword()).replace(" ","").equals("")){
					newPw.setText("������������...");
				    newPw.setEchoChar('\0');				    
				}
				if(String.valueOf(confirmPw.getPassword()).replace(" ","").equals("")){
					confirmPw.setText("�ٴ���������...");
					confirmPw.setEchoChar('\0');				    
				}
			}
		});
		
	}

	private void setListener(){
		confirm.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				/*
				 * �Ƚ���ԭʼ����ƥ���ж�
				 */
				
				
				if (updateProcessor.changePssword(ChangePw.this.userName, ChangePw.this.originPw.getText(), ChangePw.this.newPw.getText())) {
					ChangePw.this.frame.setVisible(false);
				} else {
					ChangePw.this.originPw.setText("");
					ChangePw.this.newPw.setText("");
					ChangePw.this.confirmPw.setText("");
				}
			}
		});
	}
}
