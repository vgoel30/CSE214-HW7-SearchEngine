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
 * URLComparator
 * Compares two webpages according to their URL 
 * @author varungoel
 *
 */
public class URLComparator implements Comparator{

	
	public int compare(Object o1, Object o2) {
    	WebPage e1 = (WebPage) o1;
    	WebPage e2 = (WebPage) o2;
    	
    	return e1.getURL().compareTo(e2.getURL());
    	
    	
}
}
