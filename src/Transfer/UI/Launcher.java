package Transfer.UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.html.HTML.Attribute;

import Transfer.Bootstrap;
import Transfer.Delay;
import Transfer.FileDrop;
import Transfer.UI.design.DarkTheme;
import Transfer.UI.design.FilelistArea;
import Transfer.UI.design.InputField;
import Transfer.UI.design.Scroller;
import Transfer.UI.design.SendButton;
import Transfer.UI.design.Text;

public class Launcher extends DarkTheme {

	public JLabel title_label = new Text("파일 전송", Font.BOLD, 16, true);

	public List<File> filelist = new ArrayList<File>();
	public FilelistArea File_area = new FilelistArea();
	private Scroller File_panel = new Scroller(File_area);

	public InputField ID_input = new InputField("수신자 ID");
	public InputField PW_input = new InputField("수신자 PW");
	public Text info = new Text("7532 포트를 개방해야합니다", Font.PLAIN, 16, false);
	public SendButton btnSend = new SendButton("전송하기");

	public Launcher() {

		title_label.setBounds(0, 0, 300, 30);
		add(title_label);

		JPanel reciverPanel = new JPanel();
		TitledBorder tb = new TitledBorder("수신자 정보");
		tb.setTitleFont(new Font("맑은 고딕", Font.PLAIN, 16));
		tb.setTitleColor(Color.white);
		reciverPanel.setBorder(tb);
		reciverPanel.setLayout(new FlowLayout());
		reciverPanel.setBackground(new Color(0, 0, 0, 0));
		reciverPanel.setBounds(10, 40, 280, 120);
		try {
			reciverPanel.add(new Text("ID : " + Inet4Address.getLocalHost().getHostAddress() + " 또는 외부IP", Font.PLAIN,
					16, false));
			reciverPanel.add(new Text("PW : " + Bootstrap.password, Font.PLAIN, 16, false));
			reciverPanel.add(info);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(reciverPanel);

		File_panel.setBounds(10, 170, 280, 150);
		add(File_panel);

		new FileDrop(File_panel, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				for (File file : files) {
					if (file.isDirectory()) {
						filesDropped(file.listFiles());
						continue;
					}
					if (filelist.isEmpty()) {
						File_area.setText("");
						btnSend.setText("전송하기");
						btnSend.setEnabled(true);
						filelist.clear();
					}
					filelist.add(file);
					File_area.append(file.getName() + "\n");
				}
			}
		});

		ID_input.setBounds(15, 325, 180, 25);
		add(ID_input);
		PW_input.setBounds(15, 360, 180, 25);
		add(PW_input);

		btnSend.setBounds(210, 330, 75, 60);

		btnSend.addActionListener(new ActionListener() {

			long totalsize = 0,totalcount=0;
			@Override
			public void actionPerformed(ActionEvent e) {

				
				new Thread(new Runnable() {

					@Override
					public void run() {
						boolean checked = true;
						if (ID_input.getText().trim().isEmpty()) {
							ID_input.redline();
							checked= false;
						}
						if (filelist.isEmpty()) {
							File_area.redline();
							checked = false;
						} 
						if(!checked)
							return;
						btnSend.setText("연결중..");
						btnSend.setEnabled(false);
						try {
							Socket socket = new Socket(ID_input.getText(), 7532);
							btnSend.setText("초기화..");
							
							
							for(int i = 0; i < filelist.size(); i++) {
								long length = filelist.get(i).length();
								totalsize  += length / Bootstrap.BLOCK_SIZE + (length % Bootstrap.BLOCK_SIZE != 0 ? 1 : 0);
							}

							OutputStream out = socket.getOutputStream();
							DataInputStream din = new DataInputStream(socket.getInputStream());
							DataOutputStream dout = new DataOutputStream(out);
							FileInputStream fin;
							for (int i = 0; i < filelist.size(); i++) {
								String filename = filelist.get(i).getName(); 
								byte[] buffer = new byte[Bootstrap.BLOCK_SIZE];
								int data;
								long fileblock = 0;
								fin = new FileInputStream(filelist.get(i)); 
//								while((data = fin.read(buffer)) != -1) {
//									fileblock++;
//								}
								fileblock = filelist.get(i).length() / Bootstrap.BLOCK_SIZE;
								if(filelist.get(i).length() % Bootstrap.BLOCK_SIZE > 0)
									fileblock++;
								long datas = fileblock;
								din.readInt(); // 상대방이 준비가 됬음이 확인될때까지 대기
								dout.writeLong(fileblock); 
								dout.writeUTF(filename);
								data = 0;
								int cnt = 0;
								System.out.println("[Server] " + filename + " 전송 준비중 :: " + fileblock);
								
								for(; fileblock>0; fileblock--) {
									System.out.println("[Server] " + filename + " 전송중 :: " + fileblock + " 남음");
									data = fin.read(buffer);
									out.write(buffer, 0, data);
									cnt++;
									totalcount++;
									btnSend.setText((int)((float)totalcount / (float)totalsize * 100) + "%");
								}
								Element contentEl = File_area.getDocument().getDefaultRootElement().getElement(i);
								File_area.getDocument().remove(contentEl.getStartOffset() + filename.length(), contentEl.getEndOffset() - contentEl.getStartOffset() - filename.length() - 1);
								File_area.getDocument().insertString(contentEl.getStartOffset() + filename.length(), " [전송완료]", null);
								System.out.println("[Server] " + filename + " 전송완료");
							}
							socket.close();
							filelist.clear();
							System.out.println("모든 파일 전송완료");
							btnSend.setText("전송완료");
						} catch ( BadLocationException | IOException e1) {
							e1.printStackTrace();
							btnSend.setText("재전송하기");
							btnSend.setEnabled(true);
						}

					}
				}).start();

			}
		});

		add(btnSend);

	}

}
