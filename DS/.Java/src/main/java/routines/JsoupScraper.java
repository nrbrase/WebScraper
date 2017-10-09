package routines;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.nodes.Element;

/*
 * HTML parsing using jsoup
 */


/*
 * user specification: the function's comment should contain keys as follows: 1. write about the function's comment.but
 * it must be before the "{talendTypes}" key.
 * 
 * 2. {talendTypes} 's value must be talend Type, it is required . its value should be one of: String, char | Character,
 * long | Long, int | Integer, boolean | Boolean, byte | Byte, Date, double | Double, float | Float, Object, short |
 * Short
 * 
 * 3. {Category} define a category for the Function. it is required. its value is user-defined .
 * 
 * 4. {param} 's format is: {param} <type>[(<default value or closed list values>)] <name>[ : <comment>]
 * 
 * <type> 's value should be one of: string, int, list, double, object, boolean, long, char, date. <name>'s value is the
 * Function's parameter name. the {param} is optional. so if you the Function without the parameters. the {param} don't
 * added. you can have many parameters for the Function.
 * 
 * 5. {example} gives a example for the Function. it is optional.
 */
public class JsoupScraper {

    /**
     * {talendTypes} String
     * 
     * {Category} User Defined
     * 
     * {param} string("world") input: The string need to be printed.
     * 
     * {example} helloExemple("world") # hello world !.
     * @throws IOException 
     */

    public static ArrayList<String> getSites(String site) throws IOException {
    	Document doc = null;
    	ArrayList<String> linkCon = new ArrayList();		//Creates a list to store Links

    		doc = Jsoup.connect(site).get();
    		
    		String title = doc.title();
    		Elements links = doc.select("a[href]");
    		for (Element link : links) {
    	        // get the value from the href attribute
    			String a = link.attr("href");
    			
    	        System.out.println("\nlink: " + a);
    	        System.out.println("text: " + link.text());
    	        linkCon.add(a);
    	      }
    	
    	return linkCon;
    }
    public static ArrayList<String> update(ArrayList<String> oldList)
    {
    	ArrayList<String> newList = new ArrayList();
    	for (String x : oldList)
    	{
    		if(x.contains("Locations"))
    		{
    			newList.add(x);
    		}
    	}
    	return newList;
    }
    
    public static void print(String string) {
    	System.out.println(string);
    }	
}
        
    

