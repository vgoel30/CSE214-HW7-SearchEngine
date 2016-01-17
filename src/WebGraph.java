import java.util.*;
import java.io.*;
/** @author varungoel
 * Name: Varun Goel
 *ID: 109991128
 * email: varun.goel@stonybrook.edu
 * CSE 214 HW 7
 * Recitation Section: 7
 * Recitation TA: Anthony Musco
 * Grading TA: Zhichuang Sun
 */

/**
 * WebGraph that organizes the WebPage objects as a directed graph
 * @author varungoel
 */

public class WebGraph {


	/**
	 * The maximum amount of pages allowed in a webgraph
	 */
	final static int MAX_PAGES = 40;

	/**
	 * Collection of WebPages
	 */
	ArrayList<WebPage> pages = new ArrayList<WebPage>();

	/**
	 * ArrayList of URLS to keep track of URL names
	 */
	ArrayList<String> URLS = new ArrayList<String>();

	/**
	 * Adjacency Matrix for the webgraph
	 */
	int[][] links = new int[MAX_PAGES][MAX_PAGES];

	/**
	 * Edge list for the webgraph
	 */
	ArrayList<ArrayList<Integer>> edges = new ArrayList<>();

	/**
	 * Helper variable
	 */
	int netPages = 0;

	/**
	 * Builds a WebGraph from the given text files
	 * @param pagesFile
	 * @param linksFile
	 * @return
	 * @throws IllegalArgumentException
	 * @throws FileNotFoundException
	 */
	public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException, FileNotFoundException{
		WebGraph graph = new WebGraph(); 

		File pageFile = new File(pagesFile);

		Scanner input = new Scanner(pageFile); //throws Exception if file wasn't found

		String thisLine = "";

		//scan through the file and keeps on adding pages till the maximum file isn't breached
		while(input.hasNext() && graph.URLS.size() < MAX_PAGES){
			thisLine = input.nextLine().trim(); //removes the leading whitespace

			String[] tokens = thisLine.split(" "); //splits all this spaces and puts the tokens into an array

			//create a new webpage with the given name
			WebPage toAdd = new WebPage(tokens[0]); //the name of the webpage is the first token

			graph.URLS.add(tokens[0]); //add the name of the URL to the ArrayList URLs

			//add keywords to the the webpage
			for(int i = 1; i < tokens.length; i++){
				toAdd.getKeywords().add(tokens[i]); //the array now consists of all the keywords. Traverse through the array and add the keywords
			} 

			//sets index to current size of pages
			toAdd.setIndex(graph.pages.size());

			graph.pages.add(toAdd); //adds the webpage to the collection of webpages

			graph.edges.add(new ArrayList<Integer>()); //creates new space in the edge list

			 //increment the accessible sizes for the adjacency matrix
			

		}
		//parsing for collection of webpages is done


		//parsing for of collection of links
		thisLine = "";

		File linkFile = new File(linksFile);
		input = new Scanner(linkFile); //scanner for scanning the file of links

		while(input.hasNext()){
			thisLine = input.nextLine().trim(); //remove leading white spaces

			String[] tokens = thisLine.split(" ");

			int source = graph.URLS.indexOf(tokens[0]); //source will be the index of the source link in the URLs
			int destination = graph.URLS.indexOf(tokens[1]); //destination will be the index of the source link in the URLs

			graph.links[source][destination] = 1; //fill the adjacency matrix

			graph.edges.get(source).add(destination); //adds the links to the edge list
			graph.pages.get(destination).setRank(graph.pages.get(destination).getRank()+1); //updates the ranks
		}	
		//fillings of the links and the adjacency matrix done now

		return graph; //return the graph

	}

	/**
	 * Adds a page to the WebGraph
	 * @param url
	 * @param keywords
	 * @throws IllegalArgumentException
	 */
	public void addPage(String url, ArrayList<String> keywords) throws IllegalArgumentException{

		//throws and Exception if the URL already exists or if one of the parameters is null
		if(URLS.indexOf(url) != -1 || url == null || keywords == null)
			throw new IllegalArgumentException();


		URLS.add(url); //add the name of the url to list of URLs

		WebPage toAdd = new WebPage(url);
		toAdd.setIndex(pages.size()); //sets index to the number of pages in pages ArrayList (0-indexing)

		toAdd.setKeywords(keywords); //sets the keywords

		this.pages.add(toAdd); //adds the newly created WebPage

		this.edges.add(new ArrayList<Integer>()); //creates new space in the edge list

		netPages++;
	}
	
	
	/**
	 * Adds a link between two pages
	 * @param source
	 * @param destination
	 * @throws IllegalArgumentException
	 */
	public void addLink(String source, String destination) throws IllegalArgumentException{

		//if destination or source are invalid, throw new IllegalArgumentException or if new link can't be added
		if(URLS.indexOf(source) == -1 || URLS.indexOf(destination) == -1 || source == null || destination == null )
			throw new IllegalArgumentException();

		int src = URLS.indexOf(source);
		int dest = URLS.indexOf(destination);


		if(!this.edges.get(src).contains(dest)){
			this.edges.get(src).add(dest); //adds the links to the edge list
			pages.get(dest).setRank(pages.get(dest).getRank()+1); //updates the rank
			System.out.println("Link successfully added");
		}
		else
			System.out.println("LINK EXISTS");

	}

	/**
	 * Removes the WebPage from the graph with the given URL.
	 * @param url
	 */
	public void removePage(String url){

		if(URLS.indexOf(url) == -1 || url == null)
			throw new IllegalArgumentException();

		int indexToRemove = URLS.indexOf(url); //to finally remove the row and column associated with the URL, we need it's index in the list of URLS

		for(int i = 0; i < edges.get(indexToRemove).size(); i++){
			//updates the rank
			int dest = edges.get(indexToRemove).get(i);
			pages.get(dest).setRank(pages.get(dest).getRank()-1);

		}

		//removes the link in the horizontal list

		edges.remove(indexToRemove);


		//removes the link in the vertical list
		for(int i = 0; i < edges.size(); i++){

			for(int j = 0; j < edges.get(i).size(); j++){
				if(edges.get(i).get(j) > indexToRemove){
					int replacement = edges.get(i).get(j) -1;
					edges.get(i).set(j, replacement);
				}
			}

			if(edges.get(i).contains(indexToRemove)){
				edges.get(i).remove(edges.get(i).indexOf(indexToRemove));
			}
		}

		URLS.remove(url); //removes the url from the list of URLS

		//resets the index to account for the removed page
		for(int i = indexToRemove + 1; i < pages.size(); i++){
			this.pages.get(i).setIndex(this.pages.get(i).getIndex() -1 );
		}

		pages.remove(indexToRemove); //removes the page from the collection of pages

		//decrement the accessible indexes
		netPages--;
	}

	/**
	 * Removes the link from WebPage with the URL indicated by source to the WebPage with the URL indicated by destination.
	 * @param source
	 * @param destination
	 * @throws IllegalArgumentException if the link doesn't exist
	 */
	public void removeLink(String source, String destination){
		int src = URLS.indexOf(source); //index of the source in URLS array
		int dest = URLS.indexOf(destination); //index of the dest

		if(edges.get(src).indexOf(dest) == -1)
			throw new IllegalArgumentException();

		//removes the link in the vertical list
		edges.get(src).remove(edges.get(src).indexOf(dest));

		//updates the rank
		pages.get(dest).setRank(pages.get(dest).getRank()-1);
	}

	/**
	 * Calculates and assigns the PageRank for every page in the WebGraph
	 * Note: This operation should be performed after ANY alteration of the graph structure (adding/removing a link, adding/removing a page).
	 */
	public void updatePageRanks(){
		//resets the ranks to zero because they had already been updated before.
		for(int i = 0; i < this.edges.size(); i++){
			for(int j = 0; j < this.edges.get(i).size() + netPages; j++){
				this.pages.get(this.edges.get(i).get(j)).setRank(0);
			}
		}

		//updates the ranks
		for(int i = 0; i < this.edges.size(); i++){
			for(int j = 0; j < this.edges.get(i).size() + netPages; j++){
				this.pages.get(this.edges.get(i).get(j)).setRank(this.pages.get(this.edges.get(i).get(j)).getRank()+1);

			}
		}	
	}

	/**
	 * Prints a formatted table of the webgraph
	 */
	public void printTable(){

		System.out.println(String.format("||| %-5s|%-5s|%-20s|%-10s","INDEX","RANK","URL","KEYWORDS AND LINKS" ) ) ;

		for(int i = 0; i < this.pages.size(); i++){
			//System.out.println(this.pages.get(i));

			System.out.println(String.format("||| %-5d|%-5d|%-20s|",this.pages.get(i).getIndex(),this.pages.get(i).getRank(),this.pages.get(i).getURL() ) 
					+ this.pages.get(i).getKeywords()) ;
			System.out.printf("%-37s","");
			System.out.println(this.edges.get(i).toString()+"\n");
		}
	}

	/**
	 * Helper method to print table based on URL sorting 
	 * Prints a formatted table of the webgraph
	 */
	public void printURLTable(){

		ArrayList<WebPage> copy = new ArrayList<WebPage>();

		for(int i = 0; i < pages.size(); i++){
			copy.add(pages.get(i));
		}

		Collections.sort(copy, new URLComparator());

		System.out.println(String.format("||| %-5s|%-5s|%-20s|%-10s","INDEX","RANK","URL","KEYWORDS AND LINKS" ) ) ;

		for(int i = 0; i < copy.size(); i++){

			System.out.println(String.format("||| %-5d|%-5d|%-20s|",copy.get(i).getIndex(),copy.get(i).getRank(),copy.get(i).getURL() ) 
					+ copy.get(i).getKeywords()) ;
			System.out.printf("%-37s","");

			System.out.println(edges.get(URLS.indexOf(copy.get(i).getURL())));
		}
	}

	/**
	 * Helper method to print table based on Rak sorting
	 * Prints a formatted table of the webgraph
	 */
	public void printRankTable(){

		ArrayList<WebPage> rankCopy = new ArrayList<WebPage>();

		for(int i = 0; i < pages.size(); i++){
			rankCopy.add(pages.get(i));
		}

		Collections.sort(rankCopy, new RankComparator());

		System.out.println(String.format("||| %-5s|%-5s|%-20s|%-10s","INDEX","RANK","URL","KEYWORDS AND LINKS" ) ) ;

		for(int i = 0; i < rankCopy.size(); i++){


			System.out.println(String.format("||| %-5d|%-5d|%-20s|",rankCopy.get(i).getIndex(),rankCopy.get(i).getRank(),rankCopy.get(i).getURL() ) 
					+ rankCopy.get(i).getKeywords()) ;
			System.out.printf("%-37s","");

			System.out.println(edges.get(URLS.indexOf(rankCopy.get(i).getURL())));
		}
	}

	/**
	 * Searches the web-graph for a specific keyword
	 * @param key
	 */
	public void searchKey(String key){
		int number = 0;
		System.out.println(String.format("||| %-5s|%-5s|%-20s|%s","INDEX","RANK","URL","KEYWORDS"));
		for(int i = 0; i < pages.size(); i++){
			if(pages.get(i).getKeywords().contains(key)){
				System.out.println(pages.get(i));
				number++;
			}
		}
		if(number == 0)
			System.out.println(String.format("||| %-5s|%-5s|%-20s|%s","-","-","-","-"));
	}

}
