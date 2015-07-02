package client;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import client.processor.GetProcessor;
import client.processor.UpdateProcessor;

import comm.MsgClient;

import server.Respond;
import ui.MyButton;
import ui.MyPasswordField;
import ui.MyTextField;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Checkbox;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Log {

	private JFrame frame;
	private Panel panel_top;
	private MyTextField userName;
	private MyPasswordField password;
	private Checkbox rempw;
	private Checkbox autolog;
	private MyButton regbt;
	private MyButton logbt;
	private Panel panel_bottom;
	private static Log window;
	private MsgClient msgClient;
	private UpdateProcessor updateProcessor; // 数据更新操作
	private GetProcessor getProcessor; // 数据获取操作
	private static FileReader fr;
	private static BufferedReader br;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fr = new FileReader("user.info");
					br = new BufferedReader(fr);

					if (br.readLine().equals("true")) {
						Respond respond = (Respond) Log.window.getProcessor
								.Login(br.readLine(), br.readLine());

						if (respond.getResult())
							new Main(br.readLine(), Log.window.msgClient,
									Log.window.getProcessor,
									Log.window.updateProcessor);

						else {
							JOptionPane.showMessageDialog(null, respond.getErrorMessage(), "提示",
									JOptionPane.INFORMATION_MESSAGE);
							fr.close();
							br.close();
						}
					} else {
						window = new Log("EasyPost");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Log(String title) {
		this.msgClient = new MsgClient();
		msgClient.start();
		initialize(title);
		setListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String title) {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setSize(new Dimension(400, 250));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) (screenSize.width) / 2 - 200,
				(int) (screenSize.height) / 2 - 275);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("EasyPost");
		frame.getContentPane().setLayout(null);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				Log.this.msgClient.disconnect();
			}
		});
		
		panel_top = new Panel();
		panel_top.setBounds(0, 0, 400, 190);
		panel_top.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(panel_top);
		panel_top.setLayout(null);

		userName = new MyTextField(5);
		userName.setBounds(200, 30, 160, 30);
		panel_top.add(userName);
		userName.setText("请输入用户名");
		userName.setFont(new Font("", 0, 13));

		password = new MyPasswordField(5);
		password.setBounds(200, 75, 160, 30);
		panel_top.add(password);
		password.setText("请输入密码");
		password.setEchoChar('\0');
		password.setFont(new Font("", 0, 13));

		userName.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (userName.getText().equals("请输入用户名")) {
					userName.setText("");
				}
				if (String.valueOf(password.getPassword()).replace(" ", "").equals("")) {
					password.setText("请输入密码");
					password.setEchoChar('\0');
				}
			}
		});

		password.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (String.valueOf(password.getPassword()).equals("请输入密码")) {
					password.setText("");
					password.setEchoChar('*');
				}
				if (userName.getText().replace(" ", "").equals("")) {
					userName.setText("请输入用户名");
				}
			}
		});

		panel_top.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (userName.getText().replace(" ", "").equals("")) {
					userName.setText("请输入用户名");
				}
				if (String.valueOf(password.getPassword()).replace(" ", "").equals("")) {
					password.setText("请输入密码");
					password.setEchoChar('\0');
				}
			}
		});

		rempw = new Checkbox("记住密码");
		rempw.setBounds(200, 116, 74, 18);
		rempw.setFont(new Font("", 0, 12));
		panel_top.add(rempw);

		autolog = new Checkbox("自动登录");
		autolog.setBounds(200, 145, 74, 18);
		autolog.setFont(new Font("", 0, 12));
		panel_top.add(autolog);

		regbt = new MyButton("注 册");
		regbt.setBounds(280, 116, 80, 18);
		regbt.setFont(new Font("", 0, 13));
		panel_top.add(regbt);

		logbt = new MyButton("登 录");
		logbt.setFont(new Font("", 0, 13));
		logbt.setBounds(280, 145, 80, 18);
		panel_top.add(logbt);

		panel_bottom = new Panel();
		panel_bottom.setBackground(new Color(204, 204, 204));
		panel_bottom.setBounds(0, 190, 400, 38);
		frame.getContentPane().add(panel_bottom);

		try {
			fr = new FileReader("user.info");
			br = new BufferedReader(fr);

			if (br.readLine().equals("true")) {
				autolog.setState(true);
			}
			if (br.readLine().equals("true")) {
				rempw.setState(true);
				userName.setText(br.readLine());
				password.setText(br.readLine());
				password.setEchoChar('*');
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void setListener() {
		regbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Log.this.updateProcessor = new UpdateProcessor("",
						Log.this.msgClient);
				new Reg(Log.this.updateProcessor);
			}
		});

		logbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Log.this.getProcessor = new GetProcessor(Log.this.userName
						.getText(), msgClient);
				Log.this.updateProcessor = new UpdateProcessor(
						Log.this.userName.getText(), Log.this.msgClient);
				Respond status = (Respond) getProcessor.Login(
						Log.this.userName.getText(),
						String.valueOf(Log.this.password.getPassword()));
				/*
				 * status为查询结果
				 */
				if (status.getResult() == true) {
					frame.dispose();
					try {
						FileWriter fw = new FileWriter("user.info");
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(autolog.getState() + "\n" + rempw.getState()
								+ "\n");
						if (rempw.getState())
							bw.write(userName.getText() + "\n"
									+ String.valueOf(password.getPassword()));
						else
							bw.write("\n\n");
						bw.close();
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					new Main(userName.getText(), Log.this.msgClient,
							Log.this.getProcessor, Log.this.updateProcessor);
				} else
					showDialog("错误", status.getErrorMessage());
			}
		});

	}

	private void showDialog(String title, String content) {
		final JDialog d = new JDialog(window.frame, title);
		d.setBackground(Color.WHITE);
		d.setModal(true);
		d.setSize(new Dimension(300, 150));
		d.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		d.setLocation((int) (screenSize.width) / 2 - 150,
				(int) (screenSize.height) / 2 - 150);
		SpringLayout d_layout = new SpringLayout();
		d.setLayout(d_layout);

		JLabel l = new JLabel("", SwingConstants.CENTER);
		// l.setIcon();
		l.setText(content);
		l.setFont(new Font("", 0, 18));
		d.add(l);

		MyButton yes = new MyButton("确认");
		Font font = new Font("", 0, 14);
		yes.setFont(font);
		d.add(yes);

		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				d.dispose();
			}
		});

		d_layout.putConstraint(SpringLayout.NORTH, l, 20, SpringLayout.NORTH,
				d.getContentPane());
		d_layout.putConstraint(SpringLayout.SOUTH, l, 70, SpringLayout.NORTH,
				d.getContentPane());
		d_layout.putConstraint(SpringLayout.WEST, l, 0, SpringLayout.WEST,
				d.getContentPane());
		d_layout.putConstraint(SpringLayout.EAST, l, 0, SpringLayout.EAST,
				d.getContentPane());

		d_layout.putConstraint(SpringLayout.SOUTH, yes, -20,
				SpringLayout.SOUTH, d.getContentPane());
		d_layout.putConstraint(SpringLayout.NORTH, yes, -50,
				SpringLayout.SOUTH, d.getContentPane());
		d_layout.putConstraint(SpringLayout.EAST, yes, -110, SpringLayout.EAST,
				d.getContentPane());
		d_layout.putConstraint(SpringLayout.WEST, yes, 110, SpringLayout.WEST,
				d.getContentPane());

		d.setVisible(true);
	}

}
