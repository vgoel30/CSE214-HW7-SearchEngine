import java.util.*;

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
 * SearchEngine class
 * @author varungoel
 *
 */
public class SearchEngine {

	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";

	static private WebGraph web = new WebGraph();

	public static void main(String[] args){
		
		boolean proceed = true;

		try{
			web = WebGraph.buildFromFiles(PAGES_FILE,LINKS_FILE);
		}catch(Exception e){
			proceed = false;
			System.out.println("Invalid file(s)");
		}

		if(proceed){
		Scanner input = new Scanner(System.in);
		String option = "";
		printMenu();
		option = input.nextLine().toUpperCase();

		while(!option.equals("Q")){

			//Adding a page
			if(option.equals("AP")){
				System.out.println("Give the name of the webpage");
				String name = input.nextLine();

				System.out.println("Give the keywords(space separated)");
				String key = input.nextLine();
				String[] keys = key.split(" ");
				ArrayList<String> keywords = new ArrayList<String>();

				for(int i = 0; i < keys.length; i++)
					keywords.add(keys[i]);



				try{
					web.addPage(name,keywords);
				}catch(IllegalArgumentException e){
					System.out.println("Page already exists");
				}

				printMenu();
				option = input.nextLine().toUpperCase();
			}

			//removing a page
			else if(option.equals("RP")){
				System.out.println("Give the name of the webpage that you'd like removed");
				String name = input.nextLine();

				try{
					web.removePage(name);
					System.out.println("Page successfully removed");
				}
				catch(Exception e){
					System.out.println("Page doesn't exist\n");
				}

				printMenu();
				option = input.nextLine().toUpperCase();
			}

			//to add a link between pages
			else if(option.equals("AL")){
				System.out.println("Give the name of the source webpage");
				String source = input.nextLine();

				System.out.println("Give the name of the destination webpage");
				String destination = input.nextLine();

				try{
					web.addLink(source, destination);
					//System.out.println("Link successfully added");
				}catch(Exception e){
					System.out.println("Please check the URLs that you have entered");
				}

				printMenu();
				option = input.nextLine().toUpperCase();
			}

			//to remove a link between pages
			else if(option.equals("RL")){
				System.out.println("Give the name of the source webpage");
				String source = input.nextLine();

				System.out.println("Give the name of the destination webpage");
				String destination = input.nextLine();

				try{
					web.removeLink(source, destination);
					System.out.println("Link successfully removed");
				}catch(Exception e){
					System.out.println("Link doesn't exist");
				}

				printMenu();
				option = input.nextLine().toUpperCase();
			}

			//searching for a keyword
			else if(option.equals("S")){
				System.out.println("Which keyword would you like to look up?");
				String key = input.nextLine();

				web.searchKey(key);
				System.out.println();
				printMenu();
				option = input.nextLine().toUpperCase();
			}

			//print graph
			else if(option.equals("P")){
				printSortMenu();

				String sortingOption = input.nextLine().toUpperCase();

				//quit and return to previous menu
				if(sortingOption.equals("Q")){
					printMenu();
					option = input.nextLine().toUpperCase();
				}

				//print based on index
				else if(sortingOption.equals("I")){
					web.printTable(); //standard printing 
				}

				//sort based on URL
				else if(sortingOption.equals("U")){
					web.printURLTable();
				}

				//sort based on rank
				else if(sortingOption.equals("R")){
					web.printRankTable();
				}
				printMenu();
				option = input.nextLine().toUpperCase();
			}

			else{
				System.out.println("INVALID OPTION!");
				printMenu();
				option = input.nextLine().toUpperCase();
			}
		}
	}}

	/**
	 * Prints Menu for USER I/O
	 */
	public static void printMenu(){
		System.out.println("(AP) - Add a new page to the graph	(RP) - Remove a page from the graph.  (AL) - Add a link between pages in the graph.");
		System.out.println("(RL) - Remove a link between pages.	(P) - Print the graph	(S) - Search for pages with a keyword	(Q) - Quit.");
	}

	/**
	 * Prints the menu for sorting the webpages
	 */
	public static void printSortMenu(){
		System.out.println("I) Sort based on index (U) Sort based on URL	(R) Sort based on rank (DSC)	(Q) Quit (return to previous menu)");
	}


}
