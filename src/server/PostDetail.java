package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import ui.MyButton;
import ui.TableCellTextAreaRenderer;


import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostDetail {

	private JFrame frame;
	private TextArea content;
	private MyButton replybt;
	private TextArea reply;
	private DefaultTableModel tm;
	private JTable commentList;
	private JScrollPane panel_right;
	private MyButton showReply;
	
	private String postTime;
	private String title;
	/**
	 * Create the application.
	 */
	public PostDetail(String postTime,String title) {
		this.postTime = postTime;
		this.title = title;
		initialize(title);
		setListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String title) {
		
		frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(new Dimension(1000, 427));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.width)/2-500,
						 (int)(screenSize.height)/2-275);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		content = new TextArea();
		content.setBounds(10, 10, 373, 318);
		frame.getContentPane().add(content);		

		showReply = new MyButton("收起回复");
		showReply.setBounds(274, 334, 109, 28);
		frame.getContentPane().add(showReply);
		
		replybt = new MyButton("回复");
		replybt.setBounds(274, 367, 109, 28);
		frame.getContentPane().add(replybt);
		
		
		reply = new TextArea();
		reply.setBounds(10, 334, 258, 61);
		frame.getContentPane().add(reply);

		loadReplyTable();
		
		
	}
	
	final Object[] columnNames = {"楼层","回复内容","回复人","回复时间"};
	//加载回复列表
	private void loadReplyTable(){	
		Service mService = new Service();
	    Object[][] rowData = mService.getPostReply(postTime, title);
	    content.setText(mService.getPostDetail(postTime, title));
	    
	    tm = new DefaultTableModel(rowData, columnNames);
				  
	    commentList = new JTable (){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row,int col){
	    		return false;
	    	}
	    };
	    commentList.setModel(tm);
	    commentList.setShowVerticalLines(false);
		commentList.setPreferredScrollableViewportSize(new Dimension(604,558));
		commentList.getTableHeader().setPreferredSize(new Dimension(commentList.getTableHeader().getWidth(),30));
		commentList.getTableHeader().setFont(new Font("",0,14));
		commentList.setFont(new Font("",0,13));
		commentList.setRowHeight (40);
		commentList.setRowMargin (0);
		commentList.setRowSelectionAllowed (true);
		commentList.setSelectionBackground (Color.white);
		commentList.setSelectionForeground (Color.red);
		commentList.setGridColor (Color.black);
		commentList.setDragEnabled (false);
		commentList.setShowGrid(false);
		commentList.getTableHeader().setReorderingAllowed(false);
		commentList.setShowHorizontalLines (true);
		commentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		commentList.setBackground (Color.white);
		commentList.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer()); 
		commentList.doLayout ();
		TableColumn Column = commentList.getColumnModel().getColumn(0);
		Column.setMaxWidth(45);
		Column.setMinWidth(45);
		Column = commentList.getColumnModel().getColumn(1);
		Column.setMaxWidth(290);
		Column.setMinWidth(290);
		Column = commentList.getColumnModel().getColumn(2);
		Column.setMaxWidth(90);
		Column.setMinWidth(90);
		Column = commentList.getColumnModel().getColumn(3);
		Column.setMaxWidth(180);
		Column.setMinWidth(180);			  
			
		panel_right = new JScrollPane (commentList);
		panel_right.getViewport().setBackground(Color.white);
		panel_right.setBounds(389, 10, 695, 385);
		frame.getContentPane().add(panel_right);
		
	}
	
	private void setListener(){
		showReply.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(showReply.getText().equals("收起回复")){
					frame.setSize(new Dimension(390, 427));
					frame.remove(panel_right);
					frame.validate();
					showReply.setText("展开回复");
				}
				else {
					frame.setSize(new Dimension(1000, 427));
					frame.getContentPane().add(panel_right);
					frame.validate();
					showReply.setText("收起回复");
				}
			}	
		});
		replybt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Service mService = new Service();
				String replyDetail = reply.getText().toString();
				if(replyDetail.equals("")){
					JOptionPane.showMessageDialog(frame, "回复不能为空~");
					return;
				}
				
				if(mService.addReply(postTime, title, replyDetail)){
					JOptionPane.showMessageDialog(frame, "回复成功~");
			//		reply.setText("");
			//		frame.getContentPane().remove(frame);
			//		loadReplyTable();
					new PostDetail(postTime, title);
					frame.dispose();
				}
				else
					JOptionPane.showMessageDialog(frame, "回复失败*_*");
				
			}
		});
	}
}
