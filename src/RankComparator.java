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
 * RankComparator
 * Compares two webpages according to their rank 
 * @author varungoel
 *
 */
public class RankComparator implements Comparator{
	
	public int compare(Object o1, Object o2) {
    	WebPage e1 = (WebPage) o1;
    	WebPage e2 = (WebPage) o2;
    	
    	if(e1.getRank() > e2.getRank())
    		return -1;
    	else if(e1.getRank() < e2.getRank())
    		return 1;
    	return 0;
    	
}

}
