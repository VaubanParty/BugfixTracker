package pfe.datas.singletons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import pfe.main.MainClass;

/**
 * Singleton for LocalVariable actions statistics and data saving
 * 
 * @author YBADACHE
 *
 */
public class LocalVariableSingleton {

	/** The type of search you perform (odd-code, from a given file, or all commits) */
	private static String searchType;

	/** File containing commits ID with a single action in them */
	private File singleLocalVariableActionFile;

	/** File containing commits ID with more than one action */
	private File localVariableActionFile;

	/** List of the commits with more than one action, completed over time */
	private List<String> localVariableCommits = new ArrayList<String>();

	/** List of the commits with only one action, completed over time */
	private List<String> singleActionLocalVariableCommits = new ArrayList<String>();

	/** Singleton instance */
	private static LocalVariableSingleton localVariablestats = new LocalVariableSingleton(searchType);

	private LocalVariableSingleton(String searchType) {
		LocalVariableSingleton.setSearchType(searchType);
		localVariableActionFile = new File("results/" + searchType + "/by-projects/" + MainClass.project + "/localVariable/at_least_one/README.md");
		singleLocalVariableActionFile = new File("results/" + searchType + "/by-projects/" + MainClass.project + "/localVariable/only_one/README.md");
	}

	public static LocalVariableSingleton getInstance() {
		return localVariablestats;
	}

	/** Adds a commit ID into the right file, performing the parsing in the same time */
	public void addCommit(String commitID, int numberOfChanges) {
		// Single action if there is only one file
		if (numberOfChanges == 1) {
			singleActionLocalVariableCommits.add("[" + commitID + "](https://github.com/" + MainClass.projectOwner + "/" + MainClass.project + "/commit/"
					+ commitID + ")\n");
		}

		// Otherwise, general file
		else if (numberOfChanges != 0) {
			localVariableCommits.add("[" + commitID + "](https://github.com/" + MainClass.projectOwner + "/" + MainClass.project + "/commit/" + commitID
					+ ")\n");
		}
	}

	/** Saves the lists of commits, previously gathered, in the corresponding files */
	public void writeResult() throws IOException {
		FileUtils.writeStringToFile(singleLocalVariableActionFile, singleActionLocalVariableCommits.toString());
		FileUtils.writeStringToFile(localVariableActionFile, localVariableCommits.toString());
	}

	public static void setSearchType(String searchType) {
		LocalVariableSingleton.searchType = searchType;
	}

	public static String getSearchType() {
		return searchType;
	}
}
