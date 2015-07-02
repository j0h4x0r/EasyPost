package comm;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import javax.swing.JOptionPane;

public class MsgServer extends Thread {
	private ServerSocket serverSocket; // 服务端响应连接请求的ServerSocket
	private boolean isStarted = false; // 启动状态标志
	private int serverPort = CommConstant.DEFAULT_SERVER_PORT;

	public MsgServer() {
		try {
			Properties config = new Properties();
			config.load(new FileInputStream("server.properties"));
			String value = null;
			value = config.getProperty("SERVER_PORT");
			if (value != null)
				this.serverPort = Integer.parseInt(value);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "读取配置文件错误！", "错误",
					JOptionPane.ERROR_MESSAGE);
			ioe.printStackTrace();
		}
	}
	
	
	// 启动线程，开启服务
	public void run() {
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "打开端口失败！", "错误",
					JOptionPane.ERROR_MESSAGE);
			ioe.printStackTrace();
		}
		isStarted = true;
		while (isStarted == true) {
			try {
				Socket socket = serverSocket.accept();
				ResponseProcessor responser = new ResponseProcessor(socket);
				responser.start();
			} catch (IOException ioe) {
				//ioe.printStackTrace();
				//这里一定会抛出异常
			}
		}
	}
	
	// 停止服务
	public void stopService() {
		isStarted = false;
		try {
			serverSocket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
