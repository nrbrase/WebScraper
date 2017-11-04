package webby;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GUI extends readAllLinks implements ActionListener, ItemListener {

	static JFrame _window;
	public static HashMap<Integer, String> allInfo = new HashMap<Integer,String>();
	public static int _key;

	public PrintWriter outStream;
	readAllLinks obj = new readAllLinks();
	String ROOTSITE = "https://www.cvs.com/store-locator/cvs-pharmacy-locations";	//Static Rootsite var
	ArrayList<String> stateList = obj.getStates(ROOTSITE);							//State List
	String STATE = "Alabama";  										//initializing global var for state
	final JComboBox<String> listOfStates = new JComboBox<String>(new Vector<String>(stateList));

	 	/*
	 	final String[] targetList = {
	 		"CVS",
	 		"Rite Aid",
			"Wegman's"
	 	};
	 	final JComboBox<String> listOfTargets = new JComboBox<String>(targetList);
	 	*/
	 
	  	public GUI() throws IOException {
	  		_key = 0;
	  		//Frame
	  		JFrame frame = new JFrame("WebScraper");
	 		frame.setLayout(new FlowLayout());
	 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 		int height = 250, width = 500;
	 		frame.setSize(width, height);
	 		frame.setLocation(430, 100);
	 
	 		//panel
	 		JPanel panel = new JPanel();
	 		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	  		frame.add(panel);
	  
	  		//Labeling
	 		JLabel label = new JLabel("Store to target: CVS - Select state and click GO");
	 		//JLabel label = new JLabel("Select a store to target, then select state and click GO");
	  		label.setAlignmentX((Component.CENTER_ALIGNMENT));
	  		label.setVisible(true);
	  		panel.add(label);
	  
	  		/*
	 		//Dropdown box for targets
	 		listOfTargets.setVisible(true);
	 		listOfTargets.setSelectedIndex(0);
	 		listOfTargets.addItemListener(this);
	 	listOfTargets.setAlignmentX((Component.CENTER_ALIGNMENT));
	 		panel.add(listOfTargets);
	  		*/


		//Combo box, starts at index 0
		listOfStates.setVisible(true);
		listOfStates.setSelectedIndex(0);
		listOfStates.addItemListener(this);
		listOfStates.setAlignmentX((Component.CENTER_ALIGNMENT));
		panel.add(listOfStates);

		//Go button
		JButton button = new JButton("GO");
		button.addActionListener(this);
		button.setAlignmentX((Component.CENTER_ALIGNMENT));
		panel.add(button);


		//frame.pack();
		frame.setVisible(true);
		_window.add(frame);
		_window.add(panel);
		_window.pack();
	}

		public void itemStateChanged(ItemEvent e) {
			//gets state selected from dropdown menu
			JComboBox<?> box = (JComboBox<?>)e.getSource();
			STATE = (String)box.getSelectedItem();
		}

		public void getImportantInfo(String url) throws IOException{
			Document document = Jsoup.connect(url).get();
			Elements address = document.getElementsByClass("store-address");
			String addString = address.text();
			Elements phoneNumber = document.getElementsByClass("phone-number");
			String phoneString = phoneNumber.text();
			if(allInfo.isEmpty()){
				allInfo.put(_key, addString);
				allInfo.put(_key+1, phoneString);
			}
			else if(allInfo.get(_key).equals(addString)){
				allInfo.put(_key, addString);
				allInfo.put(_key+1, phoneString);
			}
			else{
				allInfo.put(_key+2, addString);
				allInfo.put(_key+3, phoneString);
				_key = _key+2;
			}
		}
		
		
		
		public void actionPerformed(ActionEvent e) {
			JFrame waitWin = new JFrame("Gathering addresses, store numbers and phone numbers from "+ STATE+", please wait.");
			waitWin.setLayout(new GridBagLayout());
			JPanel panel2 = new JPanel();
			waitWin.add(panel2, new GridBagConstraints());
			waitWin.setSize(760, 150);
			waitWin.setLocationRelativeTo(null);
			waitWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			waitWin.setVisible(true);

				String filename = "gatheredData.txt";
				try {
					outStream = new PrintWriter(new FileWriter(filename, true));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				readAllLinks obj = new readAllLinks();
				rootsite = "https://www.cvs.com/store-locator/cvs-pharmacy-locations/";
				ArrayList<String> stateALL;
				try {
					stateALL = obj.getSites(ROOTSITE+'/'+STATE);
					ArrayList<String> stateCities = new ArrayList<String>();
					for(String link : stateALL){
							if(link.contains(rootsite)){
								stateCities.add(link);
							}
					}
					for(String city : stateCities){
						try {
							getImportantInfo(city);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
					
				
				 for(Map.Entry<Integer, String> entry:allInfo.entrySet()){    
				        String info = entry.getValue();  
				        outStream.println(info); 
				 }

				outStream.close();
				JFrame frame1 = new JFrame();
				frame1.setLayout(new GridBagLayout());
				JPanel panel1 = new JPanel();
				JLabel jlabel1 = new JLabel("CVS addresses, store numbers and phone numbers gathered from "+ STATE +". You may now close this window.");
				jlabel1.setFont(new Font("Verdana",1,12));
				panel1.add(jlabel1);
				frame1.add(panel1, new GridBagConstraints());
				frame1.setSize(750, 150);
				frame1.setLocationRelativeTo(null);
				frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame1.setVisible(true);
				waitWin.setVisible(false);
			}
}



