package client;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import client.databean.PostCommData;
import client.processor.GetProcessor;
import client.processor.UpdateProcessor;

import comm.MsgClient;
import comm.DataType;

import ui.MyButton;

public class Main {

	private JFrame frame;
	private String userName;
	private MyButton postPost;
	private MyButton applyGroup;
	private MyButton showFriend;
	private MyButton message;
	private MyButton changePw;
	private MyButton logout;
	private MyButton delPost;
	private MyButton stickyPost;
	private List groupList;
	private Panel noticePanel;
	private Panel noticeTitle;
	private Panel noticeContent;
	private JTable postList;
	private DefaultTableModel tm;
	private JScrollPane panel_right;
	private PopupMenu menu;
	private MenuItem mi1;
	private MenuItem mi2;
	private MenuItem mi3;
	
	
	private MsgClient msgClient;	//ͨ��
	private GetProcessor getProcessor;
	private UpdateProcessor updateProcessor;
	private Object[][] rowData;
	private JTextArea notice;
	
	/**
	 * Create the application.
	 */
	public Main(String userName, MsgClient msgClient, GetProcessor getProcessor, UpdateProcessor updateProcessor) {
		this.userName = userName;
		this.msgClient = msgClient;
		this.getProcessor = getProcessor;
		this.updateProcessor = updateProcessor;
		initialize();
		setListener();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				showDialog("�˳�","ȷ��Ҫ�˳���?");
			}
		});
		frame.setTitle("EasyPost ���� " + userName);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setSize(new Dimension(925,600));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-463,
						 (int)(screenSize.height)/2-350);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		postPost = new MyButton("��������");
		postPost.setBounds(32, 154, 100, 30);
		frame.getContentPane().add(postPost);
		
		applyGroup = new MyButton("�������");
		applyGroup.setBounds(170, 154, 100, 30);
		frame.getContentPane().add(applyGroup);
		
		showFriend = new MyButton("�鿴����");
		showFriend.setBounds(32, 196, 100, 30);
		frame.getContentPane().add(showFriend);
		
		message = new MyButton("վ����");
		message.setBounds(170, 196, 100, 30);
		frame.getContentPane().add(message);
		
		changePw = new MyButton("�޸�����");
		changePw.setBounds(32, 238, 100, 30);
		frame.getContentPane().add(changePw);
		
		logout = new MyButton("ע  ��");
		logout.setBounds(170, 238, 100, 30);
		frame.getContentPane().add(logout);
		
		delPost = new MyButton("ɾ������");
		delPost.setBounds(32, 280, 100, 30);
		frame.getContentPane().add(delPost);
			
		stickyPost = new MyButton("�ö�����");
		stickyPost.setBounds(170, 280, 100, 30);
		frame.getContentPane().add(stickyPost);
			
		groupList = new List();
		groupList.setBounds(10, 330, 299, 238);
		frame.getContentPane().add(groupList);
		
		ArrayList<String> aList = getProcessor.getGroupList(this.userName);		//�ӷ������˻�����б�
		
		if(aList != null) {
			for (String string : aList) {
				groupList.add(string);
			}
		}
		groupList.select(0);
		
		/*
		groupList.add("�ҵ�С��1");
		groupList.add("�ҵ�С��2");
		*/
		
		noticePanel = new Panel();
		noticePanel.setBounds(10, 10, 299, 127);
		frame.getContentPane().add(noticePanel);
		noticePanel.setLayout(null);
		
		noticeTitle = new Panel();
		noticeTitle.setBounds(0, 0, 25, 127);
		noticeTitle.setBackground(new Color(204, 204, 204));
		noticePanel.add(noticeTitle);
		noticeTitle.setLayout(null);
		
		noticeContent = new Panel();
		noticeContent.setBounds(25, 0, 274, 127);
		noticePanel.add(noticeContent);
		noticeContent.setLayout(null);
		
		JScrollPane jsp = new JScrollPane();
		jsp.setBounds(0,0, 274, 127);
		jsp.setLayout(null);
		notice = new JTextArea();
		notice.setBounds(0, 0, 274, 127);
		notice.setBackground(Color.white);
		notice.setEditable(false);
		notice.setFont(new Font("", 0, 16));
		notice.setLineWrap(true);
		notice.setLayout(null);
		jsp.add(notice);
		noticeContent.add(jsp);
		
		setNotice();
		setPostData();
		
	    postList = new JTable (){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row,int col){
	    		return false;
	    	}
	    };
	    
	    setPostList();
			
		panel_right = new JScrollPane (postList);
		panel_right.getViewport().setBackground(Color.white);
		panel_right.setBounds(315, 10, 604, 558);
		frame.getContentPane().add(panel_right);
					  
		menu = new PopupMenu();
		mi1 = new MenuItem("ɾ��������");
		mi1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				if (Main.this.getProcessor.getGroupAdmin(Main.this.groupList.getSelectedItem())
						.equals(Main.this.userName)) {
					if (updateProcessor.delPost(Main.this, DataType.DEL_POST)) {		//ɾ���ɹ���ɾ�������еĸ�����Ϣ
		//				Main.this.postList.remove(Main.this.postList.getSelectedRow());
						tm.removeRow(postList.getSelectedRow());
					}			
				} else {
					//showDialog("����", "�����Ǹ�����鳤�����ܽ���ɾ����");
					JOptionPane.showMessageDialog(null, "�����Ǹ������Ա���޷����иò�����", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				}					
			}		
		});
		mi2 = new MenuItem("�ö�������");
		mi2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {					
				stickyPost();
			}		
		});
		mi3 = new MenuItem("ȡ���ö�");
		mi3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {				
				unstickyPost();
			}		
		});
		postList.add(menu);
		
	}
	
	private void setPostList(){
		postList.setModel(tm);
		postList.setShowVerticalLines(false);
		postList.setPreferredScrollableViewportSize(new Dimension(600, 558));
		postList.getTableHeader().setPreferredSize(new Dimension(postList.getTableHeader().getWidth(),30));
		postList.getTableHeader().setFont(new Font("",0,14));
		postList.setFont(new Font("",0,13));
		postList.setRowHeight(40);
		postList.setRowMargin (0);
		postList.setRowSelectionAllowed (true);
		postList.setGridColor (Color.black);
		postList.setDragEnabled (false);
		postList.setShowGrid(false);
		postList.getTableHeader().setReorderingAllowed(false);
		postList.setShowHorizontalLines (false);
		postList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		postList.setDefaultRenderer(Object.class,new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
	                Object value, boolean isSelected, boolean hasFocus,
	                int row, int column) {
		        	if((Boolean) table.getValueAt(row, 4)){
		                setForeground(Color.black);
		                setBackground(new Color(210, 210, 210));
		            }
		        	else{
		        		setForeground(Color.black);
		        		setBackground(Color.white);
		        	}
		        	if(hasFocus)
		        		frame.requestFocus();
		        	return super.getTableCellRendererComponent(table, value,
	                  isSelected, hasFocus, row, column);
	            }
	          });
		postList.setSelectionForeground(Color.red);
		postList.setSelectionBackground(Color.white);
		postList.doLayout ();
		TableColumn Column = postList.getColumnModel().getColumn(0);
		Column.setMaxWidth(150);
		Column.setMinWidth(150);
		Column = postList.getColumnModel().getColumn(1);
		Column.setMaxWidth(90);
		Column.setMinWidth(90);
		Column = postList.getColumnModel().getColumn(2);
		Column.setMaxWidth(180);
		Column.setMinWidth(180);
		Column = postList.getColumnModel().getColumn(3);
		Column.setMaxWidth(180);
		Column.setMinWidth(180);
		Column = postList.getColumnModel().getColumn(4);
		Column.setMaxWidth(0);
		Column.setMinWidth(0);
	}
	
	/*
	 * ���������б�
	 */
	public void addPost(PostCommData postDetail) {
		boolean isTop = false;
		if(postDetail.getIsTop().equals("1"))
			isTop=true;
		Object[] newData = new Object[]{postDetail.getPostName(), postDetail.getUsername(), postDetail.getPostTime(),
				postDetail.getEditTime(), isTop};
		this.tm.addRow(newData);
		this.postList.setModel(tm);
		setPostList();
		
	}
	
	public void addPost(String postName, String userName, String postTime, String editTime, String top) {
		boolean isTop = false;
		if(top.equals("1"))
			isTop=true;
		Object[] newData = new Object[] {postName, userName, postTime, editTime, isTop};
		this.tm.addRow(newData);
		this.postList.setModel(tm);
		setPostList();
	}
	
	private void setListener(){
		
			
		/*
		 * ��������
		 */
		postPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Main.this.getGroupList().getSelectedIndex() >= 0)
					new PostPost(Main.this.groupList.getSelectedItem(), Main.this, Main.this.updateProcessor);
				else
					JOptionPane.showMessageDialog(null, "δѡ����飡", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		/*
		 * ����С��
		 */
		applyGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ApplyGroup(userName, Main.this, Main.this.updateProcessor);						//Ӧ�ô����û���Ϣ
			}
		});	
		
		/*
		 * �鿴�����б�
		 */
		showFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FriendList.friendList.setGetProcessor(Main.this.getProcessor);				
				FriendList.friendList.setUpdateProcessor(Main.this.updateProcessor);
				FriendList.friendList.setUserName(Main.this.userName);
				FriendList.friendList.addFriend();
				FriendList.friendList.setVisible(true);
				
			}
		});
		
		/*
		 * �鿴վ����
		 */
		message.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Message(Main.this.getProcessor, Main.this.updateProcessor);
			}
		});
		
		/*
		 * �޸�����
		 */
		changePw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ChangePw(Main.this.userName, updateProcessor);
			}
		});
		
		/*
		 * ע��
		 */
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = JOptionPane.showConfirmDialog(null, "ȷ��ע��", "��ʾ", JOptionPane.OK_CANCEL_OPTION);
				if (i == JOptionPane.OK_OPTION) {
					msgClient.disconnect();
					frame.dispose();
					new Log("EasyPost");
				}
				
			}
		});
		
		/*
		 *ɾ������ 
		 */
		delPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Main.this.postList.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "δѡ�����ӣ���ѡ��", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (Main.this.getProcessor.getGroupAdmin(Main.this.groupList.getSelectedItem())
						.equals(Main.this.userName)) {
					if (updateProcessor.delPost(Main.this, DataType.DEL_POST)) {		//ɾ���ɹ���ɾ�������еĸ�����Ϣ
						Main.this.postList.remove(Main.this.postList.getSelectedRow());
						tm.removeRow(postList.getSelectedRow());
					}			
				} else {
					JOptionPane.showMessageDialog(null, "�����Ǹ������Ա���޷����иò�����", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					//showDialog("����", "�����Ǹ�����鳤�����ܽ���ɾ����");
				}					
			}
		});
		
		stickyPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(stickyPost.getText().equals("�ö�����")) {
					stickyPost();		//��������������ʱ�޸Ŀͻ�����ʾ�б�
					Main.this.setPostData();
				} else {
					unstickyPost();		//��������������ʱ�޸Ŀͻ�����ʾ�б�
					Main.this.setPostData();
				}
			}
		});
		
		/*
		 * �������ˢ�����������б�
		 */
		groupList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setPostData();
				postList.setModel(tm);
				setPostList();
			}
		});
		
		/*
		 * ��������б����Ӧ
		 */
		postList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if(e.getButton()==1&&e.getClickCount()==2&&postList.getSelectedRow()>-1)
            		new PostDetail(postList.getValueAt(postList.getSelectedRow(), 0).toString(), Main.this, updateProcessor, getProcessor);
            	if(e.getButton()==3&&postList.getSelectedRow()>-1){
            		if((Boolean)postList.getValueAt(postList.getSelectedRow(), 4)==false){
            			menu.removeAll();
            			menu.add(mi1);
            			menu.add(mi2);
            			menu.show(postList, e.getX(), e.getY());
					}
            		else{
            			menu.removeAll();
            			menu.add(mi1);
            			menu.add(mi3);
            			menu.show(postList, e.getX(), e.getY());
            		}
            	}
            		
            }
            public void mousePressed(MouseEvent e){
            	if(e.getButton()==1&&postList.getSelectedRow()>-1&&(Boolean)postList.getValueAt(postList.getSelectedRow(), 4)==true){
            		postList.setSelectionBackground(new Color(210,210,210));
            		stickyPost.setText("ȡ���ö�");
            	}            		
            	if(e.getButton()==1&&postList.getSelectedRow()>-1&&(Boolean)postList.getValueAt(postList.getSelectedRow(), 4)==false){
            		postList.setSelectionBackground(Color.white);
            		stickyPost.setText("�ö�����");
            	}            		
            }
        });
		

	}
		
	public JTable getPostList() {
		return postList;
	}

	public void setPostList(JTable postList) {
		this.postList = postList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * �ö�����
	 */
	private void stickyPost(){
		if (Main.this.postList.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "δѡ�����ӣ���ѡ��", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!this.userName.equals(getProcessor.getGroupAdmin(this.groupList.getSelectedItem()))) {
			JOptionPane.showMessageDialog(null, "�����Ǹ������Ա���޷����иò�����", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (updateProcessor.changeIsTop(Main.this.postList.getValueAt(Main.this.postList.getSelectedRow(), 0).toString(),
				Main.this.postList.getValueAt(Main.this.postList.getSelectedRow(), 2).toString())) {	
			tm.moveRow(postList.getSelectedRow(), postList.getSelectedRow(), 0);
			postList.setRowSelectionInterval(0, 0);
			postList.setValueAt(true, postList.getSelectedRow(), 4);
			postList.setSelectionBackground(new Color(210,210,210));
			stickyPost.setText("ȡ���ö�");
		}
	}
	
	/*
	 * ȡ���ö�����
	 */
	private void unstickyPost(){
		if (Main.this.postList.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "δѡ�����ӣ���ѡ��", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!this.userName.equals(getProcessor.getGroupAdmin(this.groupList.getSelectedItem()))) {
			JOptionPane.showMessageDialog(null, "�����Ǹ������Ա���޷����иò�����", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (updateProcessor.changeIsTop(Main.this.postList.getValueAt(Main.this.postList.getSelectedRow(), 0).toString(),
				Main.this.postList.getValueAt(Main.this.postList.getSelectedRow(), 2).toString())) {
			int row=0;
			while(row<postList.getRowCount()&&(Boolean)postList.getValueAt(row, 4)==true)
				row++;
			row--;
			tm.moveRow(postList.getSelectedRow(), postList.getSelectedRow(), row);
			postList.setRowSelectionInterval(row, row);
			postList.setValueAt(false, postList.getSelectedRow(), 4);
			postList.setSelectionBackground(Color.white);
			stickyPost.setText("�ö�����");
		}
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
				msgClient.disconnect();
				frame.dispose();
				new Log("EasyPost");
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
	
	public boolean addGrouplistNember(String groupName) {
		this.groupList.add(groupName);
		return true;
	}

	public List getGroupList() {
		return groupList;
	}

	public void setGroupList(List groupList) {
		this.groupList = groupList;
	}
	
	private void setPostData() {
		
		Object[] columnNames = {"��������","������","����ʱ��","���ظ�ʱ��","�ö�"};
	/*
		Object[][] rowData = new Object[20][];
	    for(int i=0;i<20;i++)
	    	rowData[i] = new Object[]{"111111111"+i,""+i+""+i,"2012-01-01 00:00:00","2012-01-01 00:00:00",false};			  
	    */
		
		/*
		 * ��ʼ�������б�
		 */
		if(this.groupList.getItemCount() > 0)
			rowData = getProcessor.getRowData(this.userName, this.groupList.getSelectedItem());	//�ӷ������˻�������б�		
		tm = new DefaultTableModel(rowData, columnNames);
	}
	
	private void setNotice() {		
		this.notice.setText(getProcessor.getAnnounce());
	}
}
