package pfe.datas.singletons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import pfe.main.MainClass;

/**
 * Singleton for FieldWrite actions statistics and data saving
 * 
 * @author YBADACHE
 *
 */
public class FieldWriteSingleton {

	/** The type of search you perform (odd-code, from a given file, or all commits) */
	private static String searchType;

	/** File containing commits ID with a single action in them */
	private File singleFieldWriteActionFile;

	/** File containing commits ID with more than one action */
	private File fieldWriteActionFile;

	/** List of the commits with more than one action, completed over time */
	private List<String> fieldWriteCommits = new ArrayList<String>();

	/** List of the commits with only one action, completed over time */
	private List<String> singleActionFieldWriteCommits = new ArrayList<String>();

	/** Singleton instance */
	private static FieldWriteSingleton fieldWritestats = new FieldWriteSingleton(searchType);

	private FieldWriteSingleton(String searchType) {
		FieldWriteSingleton.setSearchType(searchType);
		fieldWriteActionFile = new File("results/" + searchType + "/by-projects/" + MainClass.project + "/fieldWrite/at_least_one/README.md");
		singleFieldWriteActionFile = new File("results/" + searchType + "/by-projects/" + MainClass.project + "/fieldWrite/only_one/README.md");
	}

	public static FieldWriteSingleton getInstance() {
		return fieldWritestats;
	}

	/** Adds a commit ID into the right file, performing the parsing in the same time */
	public void addCommit(String commitID, int numberOfChanges) {
		// Single action if there is only one file
		if (numberOfChanges == 1) {
			singleActionFieldWriteCommits.add("[" + commitID + "](https://github.com/" + MainClass.projectOwner + "/" + MainClass.project + "/commit/"
					+ commitID + ")\n");
		}

		// Otherwise, general file
		else if (numberOfChanges != 0) {
			fieldWriteCommits.add("[" + commitID + "](https://github.com/" + MainClass.projectOwner + "/" + MainClass.project + "/commit/" + commitID + ")\n");
		}
	}

	/** Saves the lists of commits, previously gathered, in the corresponding files */
	public void writeResult() throws IOException {
		FileUtils.writeStringToFile(singleFieldWriteActionFile, singleActionFieldWriteCommits.toString());
		FileUtils.writeStringToFile(fieldWriteActionFile, fieldWriteCommits.toString());
	}

	public static void setSearchType(String searchType) {
		FieldWriteSingleton.searchType = searchType;
	}

	public static String getSearchType() {
		return searchType;
	}
}
