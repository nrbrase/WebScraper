package webby;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class readAllLinks{ 

	static JFrame _window;
	
	public static HashSet<String> addresses = new HashSet<String>();
	public static HashSet<String> phoneAndStoreNumbers = new HashSet<String>();
	public static Set<String> eachURL = new HashSet<String>();
	public static String rootsite;
	public static PrintWriter outStream;
	

	public static void main(String[] args) throws IOException {
		GUI gui = new GUI();
		
		}
	

	protected void get_links(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a");
			links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
				boolean add = eachURL.add(this_url);
				if (add && this_url.contains(rootsite)) {
					
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
		Elements phoneNumber = document.getElementsByClass("phone-number");
		String phoneString = phoneNumber.text();
		phoneAndStoreNumbers.add(phoneString);
		System.out.println(phoneString);
	
			
	}
	


}


