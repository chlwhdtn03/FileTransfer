package Transfer.UI.design;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Text extends JLabel {
	
	public Text(String text, int fontType, int size, boolean isAlignCenter) {
		setForeground(Color.white);
		setFont(new Font("맑은 고딕", fontType, size));
		if(isAlignCenter)
			setHorizontalAlignment(SwingConstants.CENTER);
		setText(text);
	}

}
