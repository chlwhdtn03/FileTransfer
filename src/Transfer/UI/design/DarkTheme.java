package Transfer.UI.design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import Transfer.Bootstrap;

public class DarkTheme extends JFrame {

	private Point mouseDownCompCoords = null;
	
	private ImageIcon min_exit, min_enter;
	private ImageIcon close_exit, close_enter;
	
	private Image screenImage; // JFrame을 초기화
	private Graphics screenGraphic; // JFrame을 꾸밀 수 있게 초기화
	
	private JButton btnX = new JButton();
	private JButton btnM = new JButton();
	
	public DarkTheme() {

		setSize(300,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(100,100,100));
		setUndecorated(true);
		setLayout(null);
		setResizable(false);
		

		close_exit = new ImageIcon(Bootstrap.class.getClassLoader().getResource("Close_Exit.png"));
		close_enter = new ImageIcon(Bootstrap.class.getClassLoader().getResource("Close_Enter.png"));

		min_exit = new ImageIcon(Bootstrap.class.getClassLoader().getResource("Min_Exit.png"));
		min_enter = new ImageIcon(Bootstrap.class.getClassLoader().getResource("Min_Enter.png"));
		
		btnX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnX.setBorder(null);
		btnX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnX.setIcon(close_enter);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnX.setIcon(close_exit);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnX.setOpaque(false);
		btnX.setContentAreaFilled(false);
		btnX.setBorderPainted(false);
		btnX.setIcon(close_exit);
		btnX.setFocusable(false);
		btnX.setBounds(270, 0, 30, 30);

		btnM.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnM.setBorder(null);
		btnM.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnM.setIcon(min_enter);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnM.setIcon(min_exit);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				setState(JFrame.ICONIFIED);
			}
		});
		btnM.setOpaque(false);
		btnM.setContentAreaFilled(false);
		btnM.setBorderPainted(false);
		btnM.setIcon(min_exit);
		btnM.setFocusable(false);
		btnM.setBounds(240, 0, 30, 30);
		
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseDownCompCoords = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouseDownCompCoords = null;
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point currCoords = e.getLocationOnScreen();
				setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
			}
		});
		
		add(btnX);
		add(btnM);
		
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(getWidth(), getHeight());
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
		try {
			Bootstrap.delay.autoCompute();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void screenDraw(Graphics2D g) {
		paintComponents(g);
		this.repaint();	
	}

}
