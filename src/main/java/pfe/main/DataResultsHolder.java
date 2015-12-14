package pfe.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.revwalk.RevCommit;

public class DataResultsHolder {

	private List<String> fieldcommits;
	private List<String> localcommits;
	private List<String> returncommits;
	private List<String> assignmentcommits;
	private String project;
	private String projectOwner;
	private File res_assign;
	private File res_local;
	private File res_return;
	private File res_field;

	public DataResultsHolder(String project, String projectOwner) {
		init();
		this.project = project;
		this.projectOwner = projectOwner;

		createFiles(project);
	}

	public void init() {
		fieldcommits = new ArrayList<String>();
		localcommits = new ArrayList<String>();
		returncommits = new ArrayList<String>();
		assignmentcommits = new ArrayList<String>();

		fieldcommits.add("\n");
		localcommits.add("\n");
		returncommits.add("\n");
		assignmentcommits.add("\n");
	}

	public void createFiles(String project) {
		res_assign = new File("results/" + project + "/assignments.md");
		res_local = new File("results/" + project + "/localvar.md");
		res_return = new File("results/" + project + "/return.md");
		res_field = new File("results/" + project + "/fieldread.md");
	}

	public void add(String action, RevCommit commit) {
		switch (action) {
		case ("FieldWrite"):
			fieldcommits.add("[" + commit.getName() + "](https://github.com/"
					+ projectOwner + "/" + project + "/commit/"
					+ commit.getName() + ")\n");
		break;

		case ("Assignment"):
			assignmentcommits.add("[" + commit.getName()
					+ "](https://github.com/" + projectOwner + "/" + project
					+ "/commit/" + commit.getName() + ")\n");
		break;

		case ("Return"):
			returncommits.add("[" + commit.getName() + "](https://github.com/"
					+ projectOwner + "/" + project + "/commit/"
					+ commit.getName() + ")\n");
		break;

		case ("LocalVariable"):
			localcommits.add("[" + commit.getName() + "](https://github.com/"
					+ projectOwner + "/" + project + "/commit/"
					+ commit.getName() + ")\n");
		break;

		default:
			System.out.println("Incorrect action, or not yet implemented");
			break;
		}
	}

	public void saveResults() throws IOException {
		FileUtils.writeStringToFile(res_assign, assignmentcommits.toString());
		FileUtils.writeStringToFile(res_local, localcommits.toString());
		FileUtils.writeStringToFile(res_return, returncommits.toString());
		FileUtils.writeStringToFile(res_field, fieldcommits.toString());
	}

	public File getRes_assign() {
		return res_assign;
	}

	public void setRes_assign(File res_assign) {
		this.res_assign = res_assign;
	}

	public File getRes_local() {
		return res_local;
	}

	public void setRes_local(File res_local) {
		this.res_local = res_local;
	}

	public File getRes_return() {
		return res_return;
	}

	public void setRes_return(File res_return) {
		this.res_return = res_return;
	}

	public File getRes_field() {
		return res_field;
	}

	public void setRes_field(File res_field) {
		this.res_field = res_field;
	}

	public List<String> getFieldcommits() {
		return fieldcommits;
	}

	public void setFieldcommits(List<String> fieldcommits) {
		this.fieldcommits = fieldcommits;
	}

	public List<String> getLocalcommits() {
		return localcommits;
	}

	public void setLocalcommits(List<String> localcommits) {
		this.localcommits = localcommits;
	}

	public List<String> getReturncommits() {
		return returncommits;
	}

	public void setReturncommits(List<String> returncommits) {
		this.returncommits = returncommits;
	}

	public List<String> getAssignmentcommits() {
		return assignmentcommits;
	}

	public void setAssignmentcommits(List<String> assignmentcommits) {
		this.assignmentcommits = assignmentcommits;
	}
}
