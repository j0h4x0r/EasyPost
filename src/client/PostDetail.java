package client;

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

import client.databean.CommentCommData;
import client.databean.PostCommData;
import client.processor.GetProcessor;
import client.processor.UpdateProcessor;

import comm.DataType;

import ui.MyButton;
import ui.TableCellTextAreaRenderer;


import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostDetail {

	private JFrame frame;
	private TextArea content;
	private MyButton replybt;
	private TextArea reply;
	private DefaultTableModel tm;
	private JTable commentList;
	private JScrollPane panel_right;
	private MyButton showReply;
	
	private String title;
	private Main main;
	private PostCommData postCommData;
	private ArrayList<CommentCommData> commentCommDataArrayList;
	private UpdateProcessor updateProcessor;

	/**
	 * Create the application.
	 */
	public PostDetail(String title, Main main, UpdateProcessor updateProcessor, GetProcessor getProcessor) {
		this.title = title;
		this.main = main;
		this.updateProcessor = updateProcessor;
		postCommData = getProcessor.getPostCommData(title, main.getPostList().getValueAt(main.getPostList().getSelectedRow(), 2).toString());
		commentCommDataArrayList = getProcessor.getCommentCommDataArrayList(main.getUserName(), title, 
				main.getPostList().getValueAt(main.getPostList().getSelectedRow(), 2).toString());		//从服务器端获得评论列表
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
		
		
		/*
		 * 显示帖子内容
		 */
		content = new TextArea();
		content.setBounds(10, 10, 373, 318);
		content.setText(postCommData.getPostContent());
		frame.getContentPane().add(content);		

		showReply = new MyButton("收起回复");
		showReply.setBounds(274, 334, 109, 28);
		frame.getContentPane().add(showReply);
		
		replybt = new MyButton("回 复");
		replybt.setBounds(274, 367, 109, 28);
		frame.getContentPane().add(replybt);
		
		
		reply = new TextArea();
		reply.setBounds(10, 334, 258, 61);
		frame.getContentPane().add(reply);

		final Object[] columnNames = {"楼层","回复内容","回复人","回复时间"};
		Object[][] rowData = null;
	    if (commentCommDataArrayList != null) {
			rowData = new Object[commentCommDataArrayList.size()][];
			int i = 0;
			for (CommentCommData c : commentCommDataArrayList) {
				String s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getCommentTime());
				rowData[i] = new Object[]{i + 1, c.getCommentContent(), c.getUsername(),s1};
				i++;
			}								//初始化评论列表
		}
	    
	    /*
	    for(int i=0;i<20;i++){
	    	s="";
	    	for(int j=0;j<20+i;j++)
	    		s = s + "呵";
	    	rowData[i] = new Object[]{i,s,"admin","2012-01-01 00:00:00"};
	    }	
	    */
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
		/*
		 * 是否展开回复内容
		 */
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
		
		/*
		 * 回复按钮
		 */
		replybt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (PostDetail.this.reply.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "未填写评论，请填写后再次点击回复！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					Boolean b;
				//	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm;ss");
					
					Date df = new Date();
					b = updateProcessor.addComment(PostDetail.this.main.getUserName(), PostDetail.this.reply.getText(), 
							PostDetail.this.title, PostDetail.this.main.getPostList().getValueAt(PostDetail.this.main.getPostList().getSelectedRow(), 2).toString(), 
							df, DataType.NEW_COMMENT);
					String s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(df);
					
					if (b) {
						Object[] object = new Object[]{PostDetail.this.commentList.getRowCount() + 1, PostDetail.this.reply.getText(), PostDetail.this.main.getUserName(),  s1};
						PostDetail.this.tm.addRow(object);
						PostDetail.this.reply.setText("");
					}
				}
				
				
			}
		});
	}
}
