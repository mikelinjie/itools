package md5tool;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FileChooser extends JFrame implements ActionListener, Runnable {
	JButton open = null;
	JLabel filenameL;
	JLabel filesizeL;
	JLabel filemd5;
	String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	int ONE_SECOND = 1000;

	public static void main(String[] args) {
		Thread t = new Thread(new FileChooser());
	}

	public FileChooser() {
		setTitle("文件md5码");
		this.open = new JButton("open");
		this.filenameL = new JLabel("文件名：请选择文件");
		this.filesizeL = new JLabel("文件大小：");
		this.filemd5 = new JLabel("文件md5码：");
		GridLayout glayout = new GridLayout();
		glayout.setRows(4);
		setLayout(glayout);

		add(this.filenameL);
		add(this.filesizeL);
		add(this.filemd5);
		add(this.open);
		setBounds(400, 200, 330, 150);
		setVisible(true);
		setDefaultCloseOperation(3);
		this.open.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(0);
		jfc.showDialog(new JLabel(), "选择");
		File file = jfc.getSelectedFile();
		if (file != null) {
			String md5 = null;
			long startTime = System.currentTimeMillis();
			try {
				md5 = MD5Utils.file2md5(file);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime);
			this.filenameL.setText("文件名：" + file.getName());
			this.filesizeL.setText("文件大小：" + file.length() + "字节"
					+ "          用时:" + (endTime - startTime) + "ms");
			this.filemd5.setText("文件md5码：" + md5);
		}
	}

	public void run() {
		while (true) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(
					this.DEFAULT_TIME_FORMAT);
			this.filenameL.setText(dateFormatter.format(Calendar.getInstance()
					.getTime()));
			try {
				Thread.sleep(this.ONE_SECOND);
			} catch (Exception e) {
				this.filenameL.setText("Error!!!");
			}
		}
	}
}