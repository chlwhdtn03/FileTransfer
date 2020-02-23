package Transfer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Date;

public class ClientHandle extends Thread {
	
	private Socket socket;
	
	
	public ClientHandle(Socket client) {
		socket = client;
	}
	
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
			FileOutputStream out;
			DataInputStream din = new DataInputStream(in);
			File dir = new File("./Filebox/" + socket.getInetAddress() + "(" + socket.getPort() + ")" + "/");
			if(dir.isDirectory() == false)
				dir.mkdirs();
			System.out.println("[Client] " + "디렉터리 생성됨");
			Bootstrap.frame.title_label.setText(socket.getInetAddress() + " 접근");
			while(true) {
				dout.writeInt(15); // 전송자에게 파일을 받을 준비 됬다는 신호를 보냄
				long data = din.readLong(); // 파일 데이터가 몇번 와야되는지 받음
				String filename = din.readUTF(); // 파일 이름
				File file = new File(dir +"/"+ filename); // 파일을 저장할 위치
				System.out.println("[Client] " + filename + " 수신 준비중 :: " + data + "블럭");
				Bootstrap.frame.title_label.setText("파일 수신중...");
				out = new FileOutputStream(file);
				
				long datas = data;
				byte[] buffer = new byte[Bootstrap.BLOCK_SIZE]; // 데이터 규격
				int len =0;
				
				for(; data>0; data--) { // 파일 데이터 다 올때까지 반복
					System.out.println("[Client] " + filename + " 수신중 :: " + data + " 남음");
					len = in.read(buffer); // 파일 데이터 받음
					out.write(buffer,0,len); // 받은 파일 데이터로 파일 생성
				}
				System.out.println("[Client] " + filename + " 수신 완료");
				Bootstrap.frame.title_label.setText("파일 수신 완료");
				out.flush();
				out.close();
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
