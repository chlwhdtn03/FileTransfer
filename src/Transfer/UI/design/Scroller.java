package Transfer.UI.design;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Scroller extends JScrollPane {
	
	public Scroller(Component com) {
		super(com);
		LineBorder lb = new LineBorder(new Color(0,0,0,0));
		TitledBorder tb = new TitledBorder(lb,"전송할 파일", TitledBorder.CENTER, TitledBorder.CENTER);
		tb.setTitleColor(Color.white);
		tb.setTitleFont(new Font("맑은 고딕", Font.PLAIN, 16));
		setBorder(tb);
		setBackground(new Color(0,0,0,0));
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getVerticalScrollBar().setBackground(new Color(200, 200, 200, 100));
		getVerticalScrollBar().setUI(new BasicScrollBarUI() {

			@Override
			protected JButton createDecreaseButton(int orientation) {
				return createZeroButton();
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				return createZeroButton();
			}

			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(200, 200, 200, 50);
				this.minimumThumbSize = new Dimension(0, 50);
				this.maximumThumbSize = new Dimension(0, 50);
				this.thumbDarkShadowColor = new Color(200, 200, 200);
			}

			private JButton createZeroButton() {
				JButton jbutton = new JButton();
				jbutton.setPreferredSize(new Dimension(0, 0));
				jbutton.setMinimumSize(new Dimension(0, 0));
				jbutton.setMaximumSize(new Dimension(0, 0));
				return jbutton;
			}

		});
	}

}
