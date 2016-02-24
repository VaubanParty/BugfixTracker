package pfe.resultHandling;

import java.util.ArrayList;
import java.util.List;

public class StoreResult {

	private List<String> referencedActions;

	private String project;

	private String projectOwner;

	public StoreResult(String project, String projectOwner) {
		referencedActions = new ArrayList<String>();
		this.project = project;
		this.projectOwner = projectOwner;
	}
}
