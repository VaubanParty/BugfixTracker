package pfe.resultHandling;

public class AssignmentStats {

	private static AssignmentStats singleton = new AssignmentStats();

	private AssignmentStats() {

	}

	private AssignmentStats getInstance() {
		return singleton;
	}
}
