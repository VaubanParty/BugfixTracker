package pfe.resultHandling;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ReturnStats {

	private static String searchType;

	private static String project;

	private static String projectOwner;

	private File singleReturnActionFile;

	private File returnActionFile;

	private List<String> returnCommits = new ArrayList<String>();

	private List<String> singleActionReturnCommits = new ArrayList<String>();

	private static ReturnStats returnstats = new ReturnStats(searchType, getProject(), projectOwner);

	private ReturnStats(String searchType, String project, String projectOwner) {
		ReturnStats.setProject(project);
		ReturnStats.setProjectOwner(projectOwner);
		ReturnStats.setSearchType(projectOwner);
		returnActionFile = new File("results/" + searchType + "/by-projects/" + project + "/return/at_least_one/README.md");
		singleReturnActionFile = new File("results/" + searchType + "/by-projects/" + project + "/return/only_one/README.md");
	}

	@SuppressWarnings("unused")
	private ReturnStats getInstance() {
		return returnstats;
	}

	@SuppressWarnings("unused")
	private void addCommit(String commitID, int numberOfChanges) {
		if (numberOfChanges == 1) {
			singleActionReturnCommits.add("[" + commitID + "](https://github.com/" + projectOwner + "/" + getProject() + "/commit/" + commitID + ")\n");
		}

		else if (numberOfChanges != 0) {
			returnCommits.add("[" + commitID + "](https://github.com/" + projectOwner + "/" + getProject() + "/commit/" + commitID + ")\n");
		}
	}

	@SuppressWarnings("unused")
	private void writeResult(int numberOfChanges) throws IOException {
		if (numberOfChanges == 1)
			FileUtils.writeStringToFile(singleReturnActionFile, singleActionReturnCommits.toString());

		else if (numberOfChanges != 0) {
			FileUtils.writeStringToFile(returnActionFile, returnCommits.toString());
		}
	}

	public static String getProject() {
		return project;
	}

	public static void setProject(String project) {
		ReturnStats.project = project;
	}

	public static String getSearchType() {
		return searchType;
	}

	public static void setSearchType(String searchType) {
		ReturnStats.searchType = searchType;
	}

	public static String getProjectOwner() {
		return projectOwner;
	}

	public static void setProjectOwner(String projectOwner) {
		ReturnStats.projectOwner = projectOwner;
	}

}
