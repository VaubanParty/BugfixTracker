package pfe.datas.singletons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import pfe.main.MainClass;

/**
 * Singleton for Return actions statistics and data saving
 * 
 * @author YBADACHE
 *
 */
public class ReturnSingleton {

	/** The type of search you perform (odd-code, from a given file, or all commits) */
	private static String searchType;

	/** File containing commits ID with a single action in them */
	private File singleReturnActionFile;

	/** File containing commits ID with more than one action */
	private File returnActionFile;

	/** List of the commits with more than one action, completed over time */
	private List<String> returnCommits = new ArrayList<String>();

	/** List of the commits with only one action, completed over time */
	private List<String> singleActionReturnCommits = new ArrayList<String>();

	/** Singleton instance */
	private static ReturnSingleton returnstats = new ReturnSingleton(searchType);

	private ReturnSingleton(String searchType) {
		ReturnSingleton.setSearchType(searchType);
		returnActionFile = new File("results/" + searchType + "/by-projects/" + MainClass.project + "/return/at_least_one/README.md");
		singleReturnActionFile = new File("results/" + searchType + "/by-projects/" + MainClass.project + "/return/only_one/README.md");
	}

	public static ReturnSingleton getInstance() {
		return returnstats;
	}

	/** Adds a commit ID into the right file, performing the parsing in the same time */
	public void addCommit(String commitID, int numberOfChanges) {
		// Single action if there is only one file
		if (numberOfChanges == 1) {
			singleActionReturnCommits.add("[" + commitID + "](https://github.com/" + MainClass.projectOwner + "/" + MainClass.project + "/commit/" + commitID
					+ ")\n");
		}

		// Otherwise, general file
		else if (numberOfChanges != 0) {
			returnCommits.add("[" + commitID + "](https://github.com/" + MainClass.projectOwner + "/" + MainClass.project + "/commit/" + commitID + ")\n");
		}
	}

	/** Saves the lists of commits, previously gathered, in the corresponding files */
	public void writeResult() throws IOException {
		FileUtils.writeStringToFile(singleReturnActionFile, singleActionReturnCommits.toString());
		FileUtils.writeStringToFile(returnActionFile, returnCommits.toString());
	}

	public static void setSearchType(String searchType) {
		ReturnSingleton.searchType = searchType;
	}

	public static String getSearchType() {
		return searchType;
	}
}
