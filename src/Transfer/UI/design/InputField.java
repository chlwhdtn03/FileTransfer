package Transfer.UI.design;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;

public class InputField extends JTextField {
	
	private String hint;
	private boolean isRedLine;
	
	public InputField(String hint) {
		this.hint = hint;
		setBackground(new Color(0,0,0,0));
		setForeground(Color.white);
		setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		setBorder(null);
		
	}
	
	public void redline() {
		isRedLine = true;
	}
	
	@Override
	public void paint(Graphics graphic) {
        Graphics2D g = (Graphics2D) graphic;
		if(hasFocus()) {
			g.setColor(Color.white);
			isRedLine = false;
		} else {
			if(isRedLine)
				g.setColor(new Color(255,50,50));
			else
				g.setColor(new Color(175,175,175));
		}
		g.drawRect(0, getHeight()-1, getWidth(), getHeight());
		super.paint(g);

		if(getText().isEmpty()) {
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g.drawString(hint, 0, 19);
		}
	}

}
