package comm;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import javax.swing.JOptionPane;

public class MsgServer extends Thread {
	private ServerSocket serverSocket; // �������Ӧ���������ServerSocket
	private boolean isStarted = false; // ����״̬��־
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
			JOptionPane.showMessageDialog(null, "��ȡ�����ļ�����", "����",
					JOptionPane.ERROR_MESSAGE);
			ioe.printStackTrace();
		}
	}
	
	
	// �����̣߳���������
	public void run() {
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "�򿪶˿�ʧ�ܣ�", "����",
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
				//����һ�����׳��쳣
			}
		}
	}
	
	// ֹͣ����
	public void stopService() {
		isStarted = false;
		try {
			serverSocket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
