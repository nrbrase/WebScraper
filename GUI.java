import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class GUI {

	static JFrame _window;


	public GUI() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height * 1/6;
		int width = screenSize.width * 1 / 3;
		_window = new JFrame("Web Scraper");
		_window.setPreferredSize(new Dimension(width, height));
		_window.getContentPane().setLayout(new BoxLayout(_window.getContentPane(), BoxLayout.X_AXIS));
		
		_window.pack();
		
		_window.setDefaultCloseOperation(_window.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		_window.add(panel);
				
		
		JTextField textbox = new JTextField();
		_window.add(textbox);
		panel.add(textbox);
		
		textbox.setHorizontalAlignment(JTextField.CENTER);
		textbox.setText("Enter your URL to be scraped here:");
		JButton button = new JButton("Click to gather info");
		button.setHorizontalAlignment(button.CENTER);
		panel.add(button);
		panel.setVisible(true);
		button.setVisible(true);
		_window.setVisible(true);
		
	
	}



}



