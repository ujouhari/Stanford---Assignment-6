/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	private String perName;
	private int[] rank = new int [NDECADES];

	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears
	 * in the data file.  Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each
	 * decade.
	 */
	public NameSurferEntry(String line) {
		parseLine(line);
	}

	private void parseLine(String line) {

		int endOfName=line.indexOf(" ");
		perName = line.substring(0,endOfName);
		endOfName++;

		//The popularity ranking of the name are placed in an array.
		String rankValue=line.substring(endOfName);
		StringTokenizer tokenizer = new StringTokenizer(rankValue);
		for(int i=0; tokenizer.hasMoreTokens(); i++){
			int popRank = Integer.parseInt(tokenizer.nextToken());
			rank[i]=popRank;
		}
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		return perName;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular
	 * decade.  The decade value is an integer indicating how many
	 * decades have passed since the first year in the database,
	 * which is given by the constant START_DECADE.  If a name does
	 * not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		return rank[decade];
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {

		String value = " ";
		for (int i = 0; i < rank.length; i++) {
			value += rank[i];

			if (i < rank.length - 1) {
				value += " ";
			}
		}
		return ("\"" + perName + " [" + value + "]\"");
	}
}

