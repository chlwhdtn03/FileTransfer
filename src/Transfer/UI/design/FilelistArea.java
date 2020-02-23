package Transfer.UI.design;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import Transfer.Bootstrap;

public class FilelistArea extends JTextArea {
	
	public boolean isRedLine;
	
	public FilelistArea() {
		setBackground(new Color(0, 0, 0, 50));
		setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		setForeground(Color.white);
		setEditable(false);
		setSelectionColor(new Color(0, 0, 0, 125));
		setSelectedTextColor(Color.WHITE);
	}


	@Override
	public void paint(Graphics graphic) {
        Graphics2D g = (Graphics2D) graphic;
		if(isRedLine) {
			setBorder(new LineBorder(new Color(255,50,50)));
		} else {
			setBorder(null);
		}
		if(getText().isEmpty()) {
			g.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			g.setColor(Color.LIGHT_GRAY);
			if(isRedLine)
				g.setColor(new Color(255,50,50));
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g.drawString("드래그 앤 드랍으로", getWidth()/2-5*12, getHeight()/2);
			g.drawString("전송할 파일을 정하세요", getWidth()/2-6*12, getHeight()/2+20);
		} else {
			if(isRedLine)
				isRedLine = false;
		}
		super.paint(g);
	}


	public void redline() {
		isRedLine = true;
		
	}

}
