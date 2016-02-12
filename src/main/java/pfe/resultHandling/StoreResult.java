package pfe.resultHandling;

import java.io.File;
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

	public File getFileForActionType(String actionType, String mode) {
		if (referencedActions.contains(actionType))
			return FileFactory.getFile(actionType, mode);

		else {
			String actionWithoutCt = actionType.replace("Ct", "");
			return new File("results/" + mode + "/by-projects/" + project + actionWithoutCt);
		}

	}
}
