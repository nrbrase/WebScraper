package webby;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class readAllLinks{ 

	static JFrame _window;
	public static String rootsite;
	public static PrintWriter outStream;
	
	
	public static void main(String[] args) throws IOException {
			GUI gui = new GUI();
		}
	
	
	public static ArrayList<String> getSites(String site) throws IOException {
    	Document doc = null;
    	ArrayList<String> linkCon = new ArrayList<String>();		//Creates a list to store Links
    		doc = Jsoup.connect(site).get();
    		Elements links = doc.select("a[href]");
    		for (Element link : links) {
    			String a = link.attr("href");
    	        linkCon.add("https://www.cvs.com"+a);
    	      }
    	
    	return linkCon;
    }
	
	
	protected ArrayList<String> getStates(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");		//Selects "href" from html code on site
		ArrayList<String> linkCon = new ArrayList<String>(); //Container of links for dropdown menu
		for(Element link: links){
			String a = link.attr("href");		
			if(a.contains("locations"))			//finds everylink with a location 
			{
				linkCon.add(a.substring(38));	//cuts out everything besides state name
			}
		}
		return linkCon;
	}
	

}


