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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class readAllLinks{ 

	static JFrame _window;
	
	public static HashSet<String> addresses = new HashSet<String>();
	public static HashSet<String> phoneNumbers = new HashSet<String>();
	public static Set<String> uniqueURL = new HashSet<String>();
	public static String my_site;
	public static PrintWriter outStream;
	

	public static void main(String[] args) throws IOException {
		GUI gui = new GUI();
		}
	

	protected void get_links(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a");
			links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
				boolean add = uniqueURL.add(this_url);
				if (add && this_url.contains(my_site)) {
					
					try {
						getImportantInfo(this_url);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					get_links(this_url);
				}
			});

		} catch (IOException ex) {

		}
	 
	}
	
	
	private void getImportantInfo(String url) throws IOException{
		Document document = Jsoup.connect(url).get();
		Elements address = document.getElementsByClass("store-address");
		String addString = address.text();
		addresses.add(addString);
		//System.out.println(count);
		Elements phoneNumber = document.getElementsByClass("phone-number");
		String phoneString = phoneNumber.text();
		phoneNumbers.add(phoneString);
		//count++;
		//System.out.println(phoneString);
	
			
	}
	


}


