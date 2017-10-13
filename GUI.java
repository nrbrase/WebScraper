package webby;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends readAllLinks implements ActionListener {

	static JFrame _window;
	public PrintWriter outStream; 



	public GUI() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height * 1/8;
		int width = screenSize.width * 1/2;
		_window = new JFrame("Web Scraper");
		_window.setPreferredSize(new Dimension(width, height));
		_window.getContentPane().setLayout(new BoxLayout(_window.getContentPane(), BoxLayout.X_AXIS));
		
		_window.pack();
		
		
		JPanel panel = new JPanel();
		_window.add(panel);
			
		JLabel label1 = new JLabel("URL to Scrape: ");
	    panel.add(label1);
		
		JTextField textbox = new JTextField();
		textbox.setEditable(false);
		_window.add(textbox);
		panel.add(textbox);
		
		textbox.setHorizontalAlignment(JTextField.CENTER);
		textbox.setText("https://www.cvs.com/store-locator/cvs-pharmacy-locations");
		JButton button = new JButton("GO");
		button.addActionListener(this);
		button.setHorizontalAlignment(button.CENTER);
		panel.add(button);
		panel.setVisible(true);
		button.setVisible(true);
		_window.setVisible(true);
		
	
	}

		public void actionPerformed(ActionEvent e) {
		

			String filename = "gatheredData.txt";
		

			try {
				outStream = new PrintWriter(new FileWriter(filename, true));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

			readAllLinks obj = new readAllLinks();
			rootsite = "https://www.cvs.com/store-locator/cvs-pharmacy-locations/";
			obj.get_links("https://www.cvs.com/store-locator/cvs-pharmacy-locations");
			

			Iterator<String> it1 = addresses.iterator();
			Iterator<String> it2 = phoneAndStoreNumbers.iterator();

			while(it1.hasNext() && it2.hasNext()){
				outStream.println(it1.next());
				outStream.println(it2.next());
			}

			outStream.close();
			JFrame frame1 = new JFrame();
			frame1.setLayout(new GridBagLayout());
			JPanel panel1 = new JPanel();
			JLabel jlabel1 = new JLabel("CVS addresses, store numbers and phone numbers gathered from USA. You may now close this window.");
			jlabel1.setFont(new Font("Verdana",1,12));
			panel1.add(jlabel1);
			frame1.add(panel1, new GridBagConstraints());
			frame1.setSize(750, 200);
			frame1.setLocationRelativeTo(null);
			frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame1.setVisible(true);
		}



}



