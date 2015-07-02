package server;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ui.MyButton;

public class ServerFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel servicePanel;
	private JPanel tabPanel1;
	private JPanel tabPanel2;
	private JPanel tabPanel3;
	private JPanel tabPanel4;
	private MyButton mBtnStartService;
	private MyButton mBtnAnnounce;
	private MyButton mBtnAgreeGroup;
	private MyButton mBtnDenyGroup;
	private MyButton mBtnUserMessage;
	private MyButton mBtnDelUser;
//	private MyButton mBtnSetTop;
//	private MyButton mBtnDelPost;
	private JLabel lbStateOn;
//	private List listGroup;
	private JPopupMenu menuGroup;
	private JPopupMenu menuPost;
	private int topCount;
//	private int selectedRow;

	private JTabbedPane tabbedPane;

	private Service mService;

	private boolean isServerStart;

	public ServerFrame() {
		getContentPane().setBackground(Color.WHITE);
		initial();
	}

	private void initial() {

		this.isServerStart = false;
		this.mService = new Service();

		this.setTitle("EasyPost");
		this.setSize(new Dimension(800, 600));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.width) / 2 - 463,
				(int) (screenSize.height) / 2 - 350);
		this.setResizable(false);
		this.getContentPane().setLayout(null);

		servicePanel = new JPanel();
		servicePanel.setBackground(Color.WHITE);
		servicePanel.setBounds(14, 13, 758, 55);
		servicePanel.setLayout(null);
		servicePanel.setVisible(true);

		this.getContentPane().add(servicePanel);

		JLabel lbServiceState = new JLabel("��ǰ��̳����״̬Ϊ��");
		lbServiceState.setBounds(14, 0, 218, 55);
		lbServiceState.setFont(new Font(Font.DIALOG, 0, 18));
		servicePanel.add(lbServiceState);

		lbStateOn = new JLabel("�����ѹر�");
		lbStateOn.setBounds(207, 16, 104, 18);
		lbStateOn.setFont(new Font(Font.DIALOG, 0, 20));
		lbStateOn.setForeground(Color.red);
		servicePanel.add(lbStateOn);

		mBtnStartService = new MyButton("������̳");
		mBtnStartService.setBounds(372, 13, 100, 30);
		servicePanel.add(mBtnStartService);
		loadTab1();
		mBtnStartService.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (isServerStart) {
					if (mService.stopService()) {
						JOptionPane.showMessageDialog(ServerFrame.this,
								"^_^�ɹ��ر���̳~");
						mBtnStartService.setText("������̳");
						lbStateOn.setText("�����ѹر�");
						lbStateOn.setForeground(Color.RED);
						isServerStart = !isServerStart;
					} else
						JOptionPane.showMessageDialog(ServerFrame.this,
								"-_-�ر�ʧ��");
				} else {
					if (mService.startService()) {
						JOptionPane.showMessageDialog(ServerFrame.this,
								"^_^�ɹ�������̳~");
						mBtnStartService.setText("�ر���̳");
						lbStateOn.setText("�����ѿ���");
						lbStateOn.setForeground(Color.GREEN);
						isServerStart = !isServerStart;
					} else
						JOptionPane.showMessageDialog(ServerFrame.this,
								"-_-����ʧ��");
				}
			}
		});

		loadTab2();
		loadTab3();
		loadTab4();

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(24, 81, 748, 450);
		tabbedPane.setBackground(Color.WHITE);
		this.getContentPane().add(tabbedPane);

		tabbedPane.addTab("��̳����", tabPanel1);
		tabbedPane.addTab("���ӹ���", tabPanel2);
		tabbedPane.addTab("�������", tabPanel3);
		tabbedPane.addTab("�û�����", tabPanel4);

		// ��tab��ǩ���
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				
				
				if (tabbedPane.getSelectedIndex() == 1) {
					loadTab2Group();
					jLGroup.setSelectedIndex(0);
					// loadPostList(jLGroup.getSelectedValue());
					String groupLeader = mService.getGroupLeader(jLGroup
							.getSelectedValue());
					jLabelGroupLeader.setText(groupLeader);
				}
				if(tabbedPane.getSelectedIndex() == 2){
					loadGroupList();
				}
				if(tabbedPane.getSelectedIndex() == 3){
					loadUserList();
				}
			}
		});

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * ���ر�ǩ1 ����̳�����ǩ
	 */
	JTextArea JTAnnounce;

	private void loadTab1() {
		tabPanel1 = new JPanel();
		tabPanel1.setBackground(Color.WHITE);
		tabPanel1.setLayout(null);

		// ��ӹ���� �Զ�����
		JTAnnounce = new JTextArea(mService.getAnnounce());
		JTAnnounce.setLineWrap(true);
		JTAnnounce.setFont(new Font(Font.DIALOG, 0, 20));
		JTAnnounce.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		JTAnnounce.setLocation(14, 13);
		JTAnnounce.setSize(711, 349);
		JScrollPane jScrollPaneAnnounce = new JScrollPane(JTAnnounce);
		jScrollPaneAnnounce.setBounds(0, 0, 743, 366);
		jScrollPaneAnnounce.getViewport().setBackground(Color.WHITE);
		jScrollPaneAnnounce.setVisible(true);

		tabPanel1.add(jScrollPaneAnnounce);
		// �޸Ĺ��水ť
		mBtnAnnounce = new MyButton("�޸Ĺ���");
		mBtnAnnounce.setBounds(581, 375, 100, 30);
		tabPanel1.add(mBtnAnnounce);
		mBtnAnnounce.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String announce = JTAnnounce.getText().toString();
				if (announce.equals("")) {
					announce = " ";
				}
				if (mService.postAnnounce(announce)) {
					JOptionPane.showMessageDialog(ServerFrame.this, "�����ɹ�!");
				} else
					JOptionPane.showConfirmDialog(ServerFrame.this, "����ʧ��");
			}
		});
	}

	/**
	 * ���ر�ǩ2 �����ӹ����ǩ
	 */
	Vector<Vector<String>> vDataPost;
	Vector<String> vNamePost;
	Vector<String> vRowPost;
	DefaultTableModel modelPost;
	JTable jTablePost;
	JScrollPane jScrollPanePost;
	JScrollPane jScrollAllGroup;
	JList<String> jLGroup;
	JLabel jLabelGroupLeader;
	boolean mouseFlag = false;
	Post editPost;

	private void loadTab2() {
		tabPanel2 = new JPanel();
		tabPanel2.setBackground(Color.WHITE);
		tabPanel2.setLayout(null);

		JLabel jLabelAllGroup = new JLabel("�鳤��");
		jLabelAllGroup.setFont(new Font(Font.DIALOG, 0, 18));
		jLabelAllGroup.setLocation(0, 0);
		jLabelAllGroup.setSize(59, 20);
		jLabelGroupLeader = new JLabel();
		jLabelGroupLeader.setLocation(58, 0);
		jLabelGroupLeader.setSize(89, 20);

		jLGroup = new JList<String>();
		jLGroup.setBounds(0, 0, 128, 336);
		jLGroup.setVisible(true);
		jLGroup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jLGroup.setFont(new Font(Font.DIALOG, 0, 15));
		jScrollAllGroup = new JScrollPane(jLGroup);
		jScrollAllGroup.setBounds(0, 27, 147, 391);
		jScrollAllGroup.getViewport().setBackground(Color.WHITE);
		jScrollAllGroup.setVisible(true);
		
		loadTab2Group();
		
		tabPanel2.add(jLabelAllGroup);
		tabPanel2.add(jLabelGroupLeader);
		tabPanel2.add(jScrollAllGroup);

		vDataPost = new Vector<Vector<String>>();
		vNamePost = new Vector<String>();
		vNamePost.add("��������");
		vNamePost.add("������");
		vNamePost.add("����ʱ��");
		vNamePost.add("������ʱ��");
		vRowPost = new Vector<String>();
		modelPost = new DefaultTableModel(vDataPost, vNamePost);
		jTablePost = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
			
		};
		jTablePost
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	//	jTablePost.setForeground(Color.BLACK);
		jTablePost.setModel(modelPost);
		jTablePost.setAutoCreateRowSorter(true);
		jTablePost.setFont(new Font(Font.DIALOG, 0, 18));
		jTablePost.setRowHeight(40);
		jTablePost.setSelectionBackground(Color.LIGHT_GRAY);
		jScrollPanePost = new JScrollPane(jTablePost);
		jScrollPanePost.setBounds(147, 0, 596, 418);
		jScrollPanePost.getViewport().setBackground(Color.WHITE);
		jScrollPanePost.setVisible(true);
		tabPanel2.add(jScrollPanePost);
		// ����������������
		jLGroup.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					loadPostList(jLGroup.getSelectedValue());
					
					
					String groupLeader = mService.getGroupLeader(jLGroup
							.getSelectedValue());
					jLabelGroupLeader.setText(groupLeader);
					mouseFlag = false;
				}
			}
		});
		jLGroup.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int index = jLGroup.locationToIndex(e.getPoint());
				jLGroup.setSelectedIndex(index);
			}

			public void mouseClicked(MouseEvent e) {
				// �һ�
				if (e.getButton() == 3) {
					menuGroup = new JPopupMenu();
					JMenuItem mItem1 = new JMenuItem("ɾ������");
					mItem1.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							deleteGroup(jLGroup.getSelectedValue());
						}
					});
					menuGroup.add(mItem1);
					menuGroup.show(jLGroup, e.getX(), e.getY());
				}
			}
		});

		jTablePost.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int index = jTablePost.rowAtPoint(e.getPoint());
				jTablePost.setRowSelectionInterval(index, index);
				if(index < topCount)
					jTablePost.setSelectionForeground(Color.RED);
				else
					jTablePost.setSelectionForeground(Color.BLACK);
			}

			public void mouseClicked(MouseEvent e) {
				int rowI = -1;
				rowI = jTablePost.getSelectedRow();
				
				if (rowI > -1 && e.getClickCount() == 2 && e.getButton() == 1) {
					new PostDetail(jTablePost
							.getModel()
							.getValueAt(
									jTablePost.convertRowIndexToModel(rowI), 2)
							.toString(), jTablePost
							.getModel()
							.getValueAt(
									jTablePost.convertRowIndexToModel(rowI), 0)
							.toString());
				} // ˫����
				if (rowI > -1 && e.getButton() == 3) {
					menuPost = new JPopupMenu();
					// editPost =
					// postList.get(jTablePost.convertRowIndexToModel(rowI));
					JMenuItem jMI1 = new JMenuItem("�ö�����");
					JMenuItem jMI2 = new JMenuItem("ɾ������");
					menuPost.add(jMI1);
					menuPost.add(jMI2);
					menuPost.show(jTablePost, e.getX(), e.getY());
					if (jTablePost.getSelectedRow() < topCount) {
						jMI1.setText("ȡ���ö�");
					}

					jMI1.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							setTop(jTablePost
									.getModel()
									.getValueAt(
											jTablePost
													.convertRowIndexToModel(jTablePost
															.getSelectedRow()),
											2).toString(),jTablePost
									.getModel()
									.getValueAt(
											jTablePost
													.convertRowIndexToModel(jTablePost
															.getSelectedRow()),
											0).toString());
						}
					});
					jMI2.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							delPost(jTablePost
									.getModel()
									.getValueAt(
											jTablePost
													.convertRowIndexToModel(jTablePost
															.getSelectedRow()),
											2).toString(),jTablePost
									.getModel()
									.getValueAt(
											jTablePost
													.convertRowIndexToModel(jTablePost
															.getSelectedRow()),
											0).toString());
						}
					});
				} // �һ��˵�

			}
		});

	}

	/**
	 * ���ر�ǩ3 ����������ǩ
	 */
	Vector<Vector<String>> vDataGroup;
	Vector<String> vNameGroup;
	Vector<String> vRowGroup;
	DefaultTableModel modelGroup;
	JTable jTableGroup;
	JScrollPane jScrollPaneGroup;

	private void loadTab3() {
		tabPanel3 = new JPanel();
		tabPanel3.setBackground(Color.WHITE);
		tabPanel3.setLayout(null);

		vDataGroup = new Vector<Vector<String>>();
		vNameGroup = new Vector<String>();
		vNameGroup.add("����׼����");
		vNameGroup.add("������");
		vRowGroup = new Vector<String>();
		modelGroup = new DefaultTableModel(vDataGroup, vNameGroup);
		jTableGroup = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		jTableGroup
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jTableGroup.setForeground(Color.BLACK);
		jTableGroup.setModel(modelGroup);
		jTableGroup.setAutoCreateRowSorter(true);
		jTableGroup.setFont(new Font(Font.DIALOG, 0, 18));
		jTableGroup.setRowHeight(40);
		jTableGroup.setSelectionBackground(Color.LIGHT_GRAY);
		jScrollPaneGroup = new JScrollPane(jTableGroup);
		jScrollPaneGroup.setBounds(0, 0, 743, 366);
		jScrollPaneGroup.getViewport().setBackground(Color.WHITE);
		jScrollPaneGroup.setVisible(true);
		tabPanel3.add(jScrollPaneGroup);

		loadGroupList();

		mBtnAgreeGroup = new MyButton("ͬ������");
		mBtnAgreeGroup.setBounds(93, 375, 100, 30);
		tabPanel3.add(mBtnAgreeGroup);
		mBtnAgreeGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowI = -1;
				rowI = jTableGroup.getSelectedRow();
				if (rowI > -1) {
					GroupandUser group = new GroupandUser(
							jTableGroup
									.getModel()
									.getValueAt(
											jTableGroup
													.convertRowIndexToModel(rowI),
											0).toString(),
							jTableGroup
									.getModel()
									.getValueAt(
											jTableGroup
													.convertRowIndexToModel(rowI),
											1).toString(), true);
					if (mService.agreeGroup(group)) {
						JOptionPane.showMessageDialog(ServerFrame.this,
								"��ͬ���������  ���� " + group.getGroupName() + " �鳤 "
										+ group.getGroupLeader());
						loadGroupList();
					} else
						JOptionPane.showMessageDialog(ServerFrame.this,
								"*_*����ʧ��");
					// System.out.println(group.getGroupName() +
					// " "+group.getGroupLeader());
				}

			}
		});

		mBtnDenyGroup = new MyButton("�ܾ�����");
		mBtnDenyGroup.setBounds(569, 375, 100, 30);
		tabPanel3.add(mBtnDenyGroup);
		mBtnDenyGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int rowI = -1;
				rowI = jTableGroup.getSelectedRow();
				if (rowI > -1) {
					GroupandUser group = new GroupandUser(
							jTableGroup
									.getModel()
									.getValueAt(
											jTableGroup
													.convertRowIndexToModel(rowI),
											0).toString(),
							jTableGroup
									.getModel()
									.getValueAt(
											jTableGroup
													.convertRowIndexToModel(rowI),
											1).toString(), true);
					if (mService.denyGroup(group)) {
						JOptionPane.showMessageDialog(ServerFrame.this,
								"�Ѿܾ���������  ���� " + group.getGroupName() + " �鳤 "
										+ group.getGroupLeader());
						loadGroupList();
					} else
						JOptionPane.showMessageDialog(ServerFrame.this,
								"*_*����ʧ��");
					// System.out.println(group.getGroupName() +
					// " "+group.getGroupLeader());
				}
			}
		});
	}

	/**
	 * ���ر�ǩ4 ���û������ǩ
	 */
	Vector<Vector<String>> vDataUser;
	Vector<String> vNameUser;
	Vector<String> vRowUser;
	DefaultTableModel modelUser;
	JTable jTableUser;
	JScrollPane jScrollPaneUser;

	private void loadTab4() {
		tabPanel4 = new JPanel();
		tabPanel4.setBackground(Color.WHITE);
		tabPanel4.setLayout(null);

		vDataUser = new Vector<Vector<String>>();
		vNameUser = new Vector<String>();
		vNameUser.add("�û���");
		vNameUser.add("��   ��");
		vRowUser = new Vector<String>();
		modelUser = new DefaultTableModel(vDataUser, vNameUser);
		jTableUser = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		jTableUser
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jTableUser.setForeground(Color.BLACK);
		jTableUser.setModel(modelUser);
		jTableUser.setAutoCreateRowSorter(true);
		jTableUser.setFont(new Font(Font.DIALOG, 0, 18));
		jTableUser.setRowHeight(40);
		jTableUser.setSelectionBackground(Color.LIGHT_GRAY);
		jScrollPaneUser = new JScrollPane(jTableUser);
		jScrollPaneUser.setBounds(0, 0, 743, 366);
		jScrollPaneUser.getViewport().setBackground(Color.WHITE);
		jScrollPaneUser.setVisible(true);
		tabPanel4.add(jScrollPaneUser);

		loadUserList();

		mBtnUserMessage = new MyButton("��վ����");
		mBtnUserMessage.setBounds(93, 375, 100, 30);
		tabPanel4.add(mBtnUserMessage);
		mBtnUserMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowI = -1;
				rowI = jTableUser.getSelectedRow();
				if (rowI > -1) {
					GroupandUser user = new GroupandUser(jTableUser
							.getModel()
							.getValueAt(
									jTableUser.convertRowIndexToModel(rowI), 0)
							.toString(), jTableUser
							.getModel()
							.getValueAt(
									jTableUser.convertRowIndexToModel(rowI), 1)
							.toString(), false);
					new SendMessage(user.getUserName());
				}

			}
		});

		mBtnDelUser = new MyButton("ɾ���û�");
		mBtnDelUser.setBounds(569, 375, 100, 30);
		tabPanel4.add(mBtnDelUser);
		mBtnDelUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int rowI = -1;
				rowI = jTableUser.getSelectedRow();
				if (rowI > -1) {
					GroupandUser user = new GroupandUser(jTableUser
							.getModel()
							.getValueAt(
									jTableUser.convertRowIndexToModel(rowI), 0)
							.toString(), jTableUser
							.getModel()
							.getValueAt(
									jTableUser.convertRowIndexToModel(rowI), 1)
							.toString(), false);
					if (mService.delUser(user)) {
						JOptionPane.showMessageDialog(ServerFrame.this,
								"��ɾ���û�   " + user.getUserName());
						loadUserList();
					} else
						JOptionPane.showMessageDialog(ServerFrame.this,
								"*_*����ʧ��");
					// System.out.println(group.getGroupName() +
					// " "+group.getGroupLeader());
				}
			}
		});
		jTableUser.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int rowI = -1;
				rowI = jTableUser.getSelectedRow();
				if (rowI > -1 && e.getClickCount() == 2 && e.getButton() == 1) {
					GroupandUser user = new GroupandUser(jTableUser
							.getModel()
							.getValueAt(
									jTableUser.convertRowIndexToModel(rowI), 0)
							.toString(), jTableUser
							.getModel()
							.getValueAt(
									jTableUser.convertRowIndexToModel(rowI), 1)
							.toString(), false);
					new SendMessage(user.getUserName());
				}

			}
		});
	}

	// ���ط��������JTable
	@SuppressWarnings("unchecked")
	private void loadGroupList() {
		ArrayList<GroupandUser> groupList = mService.getGroupInfo();
		vDataGroup = new Vector<Vector<String>>();
		vRowGroup = new Vector<String>();
		modelGroup = new DefaultTableModel(vDataGroup, vNameGroup);
		jTableGroup.setModel(modelGroup);
		for (GroupandUser group : groupList) {
			vRowGroup = new Vector<String>();
			vRowGroup.add(group.getGroupName());
			vRowGroup.add(group.getGroupLeader());
			vDataGroup.add((Vector<String>) vRowGroup.clone());
		}
		modelGroup = new DefaultTableModel(vDataGroup, vNameGroup);
		jTableGroup.setModel(modelGroup);
	}

	// �����û������JTable
	@SuppressWarnings("unchecked")
	private void loadUserList() {
		ArrayList<GroupandUser> userList = mService.getUserInfo();
		vDataUser = new Vector<Vector<String>>();
		vRowUser = new Vector<String>();
		modelUser = new DefaultTableModel(vDataUser, vNameUser);
		jTableUser.setModel(modelUser);
		for (GroupandUser user : userList) {
			vRowUser = new Vector<String>();
			vRowUser.add(user.getUserName());
			vRowUser.add(user.getUserPwd());
			vDataUser.add((Vector<String>) vRowUser.clone());
		}
		modelUser = new DefaultTableModel(vDataUser, vNameUser);
		jTableUser.setModel(modelUser);
	}

	private ArrayList<Post> postList;

	// �������������JTable
	@SuppressWarnings("unchecked")
	private void loadPostList(String groupName) {
		postList = mService.getPostByGroup(groupName);
		vDataPost = new Vector<Vector<String>>();
		vRowPost = new Vector<String>();
		modelPost = new DefaultTableModel(vDataPost, vNamePost);
		jTablePost.setModel(modelPost);
		if (postList.isEmpty()) {
		//	JOptionPane.showMessageDialog(ServerFrame.this, "�����ڻ�û������~");
			return;
		}
		int i = 0;
		for (Post post : postList) {
			vRowPost = new Vector<String>();
			vRowPost.add(post.getPostName());
			vRowPost.add(post.getPostAuthor());
			vRowPost.add(post.getPostTime());
			vRowPost.add(post.getEditTime());
			if (post.isTop)
				vDataPost.insertElementAt((Vector<String>) vRowPost.clone(),
						i++);
			else
				vDataPost.add((Vector<String>) vRowPost.clone());

		}
		modelPost = new DefaultTableModel(vDataPost, vNamePost);
		jTablePost.setModel(modelPost);
		topCount = i;
		// System.out.println(i);
		
		setTopColor();
	}
	
	private void loadTab2Group(){
		int i = 0;
		DefaultListModel<String> groupListModel = new DefaultListModel<String>();
		ArrayList<GroupandUser> allGroup = mService.getAllGroup();
		for (GroupandUser group : allGroup) {
			groupListModel.add(i++, group.getGroupName());
		}
		jLGroup.setModel(groupListModel);
	}

	// ɾ������
	private void deleteGroup(String groupName) {
		int res = JOptionPane.showConfirmDialog(ServerFrame.this, "ȷ��ɾ������ "
				+ groupName + " (�������ӽ�ȫ��ɾ����)");
		if (res == JOptionPane.OK_OPTION) {
			// System.out.println(groupName);
			if (mService.delGroup(groupName)) {
				JOptionPane.showMessageDialog(ServerFrame.this, "��ɾ���飺"
						+ groupName);
				int i = 0;
				DefaultListModel<String> groupListModel = new DefaultListModel<String>();
				ArrayList<GroupandUser> allGroup = mService.getAllGroup();
				for (GroupandUser group : allGroup) {
					groupListModel.add(i++, group.getGroupName());
				}
				jLGroup.setModel(groupListModel);
				jLGroup.setSelectedIndex(0);
			} else
				JOptionPane.showMessageDialog(ServerFrame.this, "ɾ��ʧ�ܣ�");

		}
	}

	// �����ö�
	private void setTop(String postTime,String postName) {
		mService.setTop(postTime, postName);
		loadPostList(jLGroup.getSelectedValue());
	}
	
	// ɾ������
	private void delPost(String postTime,String postName) {
		int res = JOptionPane.showConfirmDialog(ServerFrame.this, "ȷ��ɾ��������");
		if (res == JOptionPane.OK_OPTION) {
			if (mService.delPost(postTime, postName)) {
				JOptionPane.showMessageDialog(ServerFrame.this, "ɾ���ɹ�^_^");
				loadPostList(jLGroup.getSelectedValue());
			} else
				JOptionPane.showMessageDialog(ServerFrame.this, "ɾ��ʧ��*_*");

		}
	}

	// �����ö�����ɫ
	private void setTopColor() {
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if (row < topCount){
					setForeground(Color.RED); // �����ö���ǰ��ɫ
					setBackground(Color.WHITE);
			//		table.setSelectionForeground(Color.green);
					//System.out.println(topCount+" "+row);
				}
				else{
					setForeground(Color.BLACK);
					setBackground(Color.WHITE);
			//		table.setForeground(Color.yellow);
				}
				if(hasFocus)
	        		ServerFrame.this.requestFocus();
				return super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
			}
		};
		jTablePost.setDefaultRenderer(Object.class, tcr);
	//	for (int i = 0; i < jTablePost.getColumnCount(); i++) {
	//		jTablePost.getColumn(jTablePost.getColumnName(i)).setCellRenderer(tcr);
	//	}
		
	}

	public static void main(String[] args) {
		new ServerFrame();
	}

}
