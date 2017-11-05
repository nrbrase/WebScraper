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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GUI extends readAllLinks implements ActionListener, ItemListener{
    
    public static HashMap<Integer, String> allInfo = new HashMap<Integer,String>();
    public static int _key;
    
    static JFrame _window = new JFrame("WebScraper");
    public PrintWriter outStream;
    readAllLinks obj = new readAllLinks();
    static String ROOTSITE = "https://www.cvs.com/store-locator/cvs-pharmacy-locations";	//Static Rootsite var
    ArrayList<String> stateList = obj.getStates(ROOTSITE);							//State List
    
    static String STATE = "All";  										//initializing global var for state
    final JComboBox<String> listOfStates = new JComboBox<String>(new Vector<String>(stateList));
    private final Collection<Runnable>cities = new ArrayList<Runnable>();
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
            _window.setLayout(new FlowLayout());
            _window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            
            //panel
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            _window.add(panel);
            
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
            
            
            //window stuff
            _window.setVisible(true);
            
            _window.add(panel);
            _window.pack();
            int height = 250, width = 500;
            _window.setSize(width, height);
            _window.setLocation(430, 100);
        }
    
    public void itemStateChanged(ItemEvent e) {
        //gets state selected from dropdown menu
        JComboBox<?> box = (JComboBox<?>)e.getSource();
        STATE = (String)box.getSelectedItem();
    }
    
    public void getImportantInfo(String url) throws IOException {
        try{
            //Runs as a user from mozilla coming from google
            Document document = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
            .referrer("http://www.google.com").ignoreHttpErrors(true)
            .timeout(1000*5) //it's in milliseconds, so this means 5 seconds.
            .get();
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
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HttpStatusException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    //When a city is sent, adds it to our outfile
    @SuppressWarnings("finally")
    public void run(ArrayList<String> stateCities) throws IOException {
        //extracts all important info from each city
        
        stateCities.parallelStream().forEach(link -> {
            try {
                getImportantInfo("http://cvs.com"+link);
                // System.out.println(ROOTSITE+'/'+link);
            }catch (IOException e) {
                e.printStackTrace();
            }
            
        });
        
		  }
    //When all states are chosen, sends each state to get all cities and calls run
    public void runAll(ArrayList<String> stateAll) throws IOException {
        //extracts all important info from each city
        stateAll.parallelStream().forEach(state -> {
            try {
                run(readAllLinks.getCities(ROOTSITE+'/'+state));
                System.out.println(ROOTSITE+'/'+state);
            }catch (IOException e) {
                e.printStackTrace();
            }
            
        });
        
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
        if(STATE == "All")
        {
            try {
                runAll(stateList);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        else{
            try {
                run(readAllLinks.getCities(ROOTSITE+'/'+STATE));
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
            
            for(Map.Entry<Integer, String> entry:allInfo.entrySet()){    
                String info = entry.getValue();  
                outStream.println(info); 
            }
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
    
    public Collection<Runnable> getCities() {
        return cities;
    }
}
