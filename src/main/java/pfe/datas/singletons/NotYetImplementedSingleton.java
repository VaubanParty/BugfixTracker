package pfe.datas.singletons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import pfe.main.MainClass;

/**
 * Singleton for NotYetImplemented actions statistics and data saving
 * 
 * @author YBADACHE
 *
 */
public class NotYetImplementedSingleton {

	/** The type of search you perform (odd-code, from a given file, or all commits) */
	private static String searchType;

	/** File containing commits ID with a single action in them */
	private File singleNotYetImplementedActionFile;

	/** File containing commits ID with more than one action */
	private File notYetImplementedActionFile;

	/** List of the commits with more than one action, completed over time */
	private List<String> notYetImplementedCommits = new ArrayList<String>();

	/** List of the commits with only one action, completed over time */
	private List<String> singleActionNotYetImplementedCommits = new ArrayList<String>();

	/** Singleton instance */
	private static NotYetImplementedSingleton notYetImplementedstats = new NotYetImplementedSingleton(searchType);

	private NotYetImplementedSingleton(String searchType) {
		NotYetImplementedSingleton.setSearchType(searchType);
		notYetImplementedActionFile = new File("results/" + searchType + "/by-projects/" + MainClass.project + "/notYetImplemented/at_least_one/README.md");
		singleNotYetImplementedActionFile = new File("results/" + searchType + "/by-projects/" + MainClass.project + "/notYetImplemented/only_one/README.md");
	}

	@SuppressWarnings("unused")
	private NotYetImplementedSingleton getInstance() {
		return notYetImplementedstats;
	}

	/** Adds a commit ID into the right file, performing the parsing in the same time */
	@SuppressWarnings("unused")
	private void addCommit(String commitID, int numberOfChanges) {
		// Single action if there is only one file
		if (numberOfChanges == 1) {
			singleActionNotYetImplementedCommits.add("[" + commitID + "](https://github.com/" + MainClass.projectOwner + "/" + MainClass.project + "/commit/"
					+ commitID + ")\n");
		}

		// Otherwise, general file
		else if (numberOfChanges != 0) {
			notYetImplementedCommits.add("[" + commitID + "](https://github.com/" + MainClass.projectOwner + "/" + MainClass.project + "/commit/" + commitID
					+ ")\n");
		}
	}

	/** Saves the lists of commits, previously gathered, in the corresponding files */
	@SuppressWarnings("unused")
	private void writeResult() throws IOException {
		FileUtils.writeStringToFile(singleNotYetImplementedActionFile, singleActionNotYetImplementedCommits.toString());
		FileUtils.writeStringToFile(notYetImplementedActionFile, notYetImplementedCommits.toString());
	}

	public static void setSearchType(String searchType) {
		NotYetImplementedSingleton.searchType = searchType;
	}

	public static String getSearchType() {
		return searchType;
	}
}
