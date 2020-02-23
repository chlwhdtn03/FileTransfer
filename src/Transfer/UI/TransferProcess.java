package Transfer.UI;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;

import Transfer.UI.design.SendButton;

public class TransferProcess extends Thread {
	
	public Launcher launcher;
	public String filename;
	public long cnt;
	public long datas;
	public long totalcount;
	public long totalsize;
	public int i;
	
	
	
	public TransferProcess(Launcher launcher, String filename, long cnt, long datas, long totalcount, long totalsize, int i) {
		this.launcher = launcher;
		this.filename = filename;
		this.cnt = cnt;
		this.datas = datas;
		this.totalcount = totalcount;
		this.totalsize = totalsize;
		this.i = i;
	}



	@Override
	public void run() {
			try {
				launcher.btnSend.setText((int)((float)totalcount / (float)totalsize * 100) + "%");
				if(filename != null) {
					Element contentEl = launcher.File_area.getDocument().getDefaultRootElement().getElement(i);
					launcher.File_area.getDocument().remove(contentEl.getStartOffset() + filename.length(), contentEl.getEndOffset() - contentEl.getStartOffset() - filename.length() - 1);
					launcher.File_area.getDocument().insertString(contentEl.getStartOffset() + filename.length(), " " + (int)(((float)cnt / (float)datas )*100) + "%...", null);
				}
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
