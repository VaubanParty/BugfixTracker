package pfe.resultHandling;

public class FieldWriteStats {

	private static FieldWriteStats singleton = new FieldWriteStats();

	private FieldWriteStats() {

	}

	private FieldWriteStats getInstance() {
		return singleton;
	}
}
