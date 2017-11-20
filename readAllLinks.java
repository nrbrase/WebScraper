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
    
    public static String rootsite;
    public static PrintWriter outStream;
    
    
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
    }
    
    //Returns /store-locator/cvs-pharmacy-locations/Alabama/CITY for all
    public static ArrayList<String> getCities(String site) throws IOException {
        ArrayList<String> linkCon = new ArrayList<String>();		//Creates a list to store Links
        Document doc = Jsoup.connect(site).get();
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String a = link.attr("href");
            if(a.contains("locations/")){
                linkCon.add(a);
            }
        }
        
        return linkCon;
    }
    
    //When sent URL returns substring of state name
    protected ArrayList<String> getStates(String url) throws IOException{
        ArrayList<String> linkCon = new ArrayList<String>(); //Container of links for dropdown menu
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");		//Selects "href" from html code on site
        for(Element link: links){
            String a = link.attr("href");
            if(a.contains("locations"))			//finds everylink with a location
            {
                linkCon.add(a.substring(38));	//cuts out everything besides state name
            }
        }
        linkCon.add("All");
        return linkCon;
    }
    
    
}


