package pfe.datas.singletons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import pfe.main.MainClass;

/**
 * Singleton for Assignment actions statistics and data saving
 * 
 * @author YBADACHE
 *
 */
public class AssignmentSingleton {

	/** The type of search you perform (odd-code, from a given file, or all commits) */
	private static String searchType;

	/** File containing commits ID with a single action in them */
	private File singleAssignmentActionFile;

	/** File containing commits ID with more than one action */
	private File assignmentActionFile;

	/** List of the commits with more than one action, completed over time */
	private List<String> assignmentCommits = new ArrayList<String>();

	/** List of the commits with only one action, completed over time */
	private List<String> singleActionAssignmentCommits = new ArrayList<String>();

	/** Singleton instance */
	private static AssignmentSingleton assignmentstats = new AssignmentSingleton(searchType);

	private AssignmentSingleton(String searchType) {
		AssignmentSingleton.setSearchType(searchType);
		assignmentActionFile = new File("results/" + searchType + "/by-projects/" + MainClass.project + "/assignment/at_least_one/README.md");
		singleAssignmentActionFile = new File("results/" + searchType + "/by-projects/" + MainClass.project + "/assignment/only_one/README.md");
	}

	public static AssignmentSingleton getInstance() {
		return assignmentstats;
	}

	/** Adds a commit ID into the right file, performing the parsing in the same time */
	public void addCommit(String commitID, int numberOfChanges) {
		// Single action if there is only one file
		if (numberOfChanges == 1) {
			singleActionAssignmentCommits.add("[" + commitID + "](https://github.com/" + MainClass.projectOwner + "/" + MainClass.project + "/commit/"
					+ commitID + ")\n");
		}

		// Otherwise, general file
		else if (numberOfChanges != 0) {
			assignmentCommits.add("[" + commitID + "](https://github.com/" + MainClass.projectOwner + "/" + MainClass.project + "/commit/" + commitID + ")\n");
		}
	}

	/** Saves the lists of commits, previously gathered, in the corresponding files */
	public void writeResult() throws IOException {
		FileUtils.writeStringToFile(singleAssignmentActionFile, singleActionAssignmentCommits.toString());
		FileUtils.writeStringToFile(assignmentActionFile, assignmentCommits.toString());
	}

	public static void setSearchType(String searchType) {
		AssignmentSingleton.searchType = searchType;
	}

	public static String getSearchType() {
		return searchType;
	}
}
