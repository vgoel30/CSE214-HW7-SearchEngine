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
 * WebPage class which represents a hyperlinked document
 * @author varungoel
 */


public class WebPage {

	/**
	 * Default constructor with no parameters (Add parameters later)
	 */
	public WebPage(){
	}

	/**
	 * Constructor with the name of the webpage as the parameter
	 * @param name
	 */
	public WebPage(String name){
		url = name;
	}

	//member variables
	private String url;
	private int rank, index;

	/**
	 * Setter method for rank of the webpage
	 * @param rank
	 */
	public void setRank(int rank){
		this.rank = rank;
	}

	/**
	 * Setter method for index of the webpage
	 * @param index
	 */
	public void setIndex(int index){
		this.index = index;
	}

	/**
	 * Accessor method for rank
	 * @return integer rank
	 */
	public int getRank(){
		return this.rank;
	}

	/**
	 * Accessor method for index
	 * @return integer index
	 */
	public int getIndex(){
		return this.index;
	}

	/**
	 * Accessor method for url
	 * @return string url
	 */
	public String getURL(){
		return this.url;
	}

	/**
	 * Accessor method for list of keywords
	 * @return arraylist of keywords
	 */
	public ArrayList<String> getKeywords(){
		return this.keywords;
	}

	/**
	 * Mutator method for list of keywords
	 * @param arraylist of keywords
	 */
	public void setKeywords(ArrayList<String> keys){
		this.keywords = keys;
	}

	/**
	 * ArrayList of keywords associated with the webpage
	 */
	private ArrayList<String> keywords = new ArrayList<String>();

	public String toString(){
		return String.format("||| %-5d|%-5d|%-20s|",index,rank,url ) + keywords;

	}


	public static void main(String[] args) {
		System.out.println(String.format("%-5d|%-5d|%-30s|",5,5,"www.google.com" ));
	}

}
