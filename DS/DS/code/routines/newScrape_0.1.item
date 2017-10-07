package routines;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class newScrape{
	
	public static HashSet<String> addresses = new HashSet<String>();
	public static HashSet<String> psNumbers = new HashSet<String>();
	public static Set<String> eachURL = new HashSet<String>();
	public static String rootsite;

	public static void main(String[] args) {
		
		newScrape obj = new newScrape();
		rootsite = "cvs.com/store-locator/cvs-pharmacy-locations";
		obj.get_links("https://www.cvs.com/store-locator/cvs-pharmacy-locations");
	}

	private void get_links(String url) {
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
		Elements phoneAndStoreNumber = document.getElementsByClass("phone-number");
		String phoneString = phoneAndStoreNumber.text();
		psNumbers.add(phoneString);	
	}
	
	//As of right now the part that gets the phone numbers also gives the store numbers.
	//If you uncomment the print statements and run this in eclipse it'll print out the info we
	//want, but multiple times. By adding each string to the hash set it gets rid of the duplicate 
	//information.
	//Also you if you run it in eclipse, you'll find it runs very slow so give it 2 minutes to 
	//spit out the info to you.
	


}



