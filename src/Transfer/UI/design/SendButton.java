package Transfer.UI.design;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.metal.MetalButtonUI;

public class SendButton extends JButton {
	
	public SendButton(String title) {
		
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		setText(title);
		setUI(new MetalButtonUI() {
			@Override
			protected Color getDisabledTextColor() {
				return Color.white;
			}
			@Override
			protected Color getSelectColor() {
				return Color.DARK_GRAY;
			}
		});
		setOpaque(true);
		setBackground(new Color(255,255,255,50));
		setFocusPainted(false);
		setForeground(Color.white);
	} 
}
