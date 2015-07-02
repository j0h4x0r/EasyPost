package comm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class MsgClient extends Thread {

	private String serverIPAddress = CommConstant.DEFAULT_SERVER_IP;
	private int serverPort = CommConstant.DEFAULT_SERVER_PORT;
	private Socket socket = null; // 客户端发送请求的socket
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private CommData responseData = null;
	private boolean isConnected = false;

	public MsgClient() {
		try {
			Properties config = new Properties();
			config.load(new FileInputStream("comm.properties"));
			String value = null;
			value = config.getProperty("SERVER_IP");
			if (value != null && MsgClient.isIpRight(value))
				this.serverIPAddress = value;
			value = null;
			value = config.getProperty("SERVER_PORT");
			if (value != null)
				this.serverPort = Integer.parseInt(value);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "读取配置文件错误！", "错误",
					JOptionPane.ERROR_MESSAGE);
			ioe.printStackTrace();
		}
		initMsgClient();
	}

	public void run() {
		isConnected = true;
		synchronized (this) {
			while (isConnected) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "线程通信错误！", "错误",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
	}
	
	public CommData sendRequest(CommData requestData) {
		try {
			out.writeObject(requestData);
			out.flush();
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "网络通信错误！", "错误",
					JOptionPane.ERROR_MESSAGE);
			ioe.printStackTrace();
		}
		responseData = null;
		try {
			responseData = (CommData) in.readObject();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "反序列化错误！", "错误",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "网络通信错误！", "错误",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return responseData;
	}
	
	private void initMsgClient() {
		try {
			socket = new Socket(serverIPAddress, serverPort);
		} catch (UnknownHostException uhe) {
			JOptionPane.showMessageDialog(null, "无法解析主机地址或IP地址有误！", "错误",
					JOptionPane.ERROR_MESSAGE);
			uhe.printStackTrace();
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "网络通信错误！", "错误",
					JOptionPane.ERROR_MESSAGE);
			ioe.printStackTrace();
		}
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "网络通信错误！", "错误",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void disconnect() {
		isConnected = false;
		DisconnData data = new DisconnData();
		data.setType(DataType.DISCONNECT);
		try {
			out.writeObject(data);
			out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized(this) {
			this.notify();
		}
	}
	
	public static boolean isIpRight(String ip) {
		boolean flag;
		String regex = CommConstant.REGEX_IP;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(ip);
		if (m.matches()) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

}
