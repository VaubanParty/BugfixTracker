package pfe.resultHandling;

public class LocalVariableStats {

	private static LocalVariableStats singleton = new LocalVariableStats();

	private LocalVariableStats() {

	}

	private LocalVariableStats getInstance() {
		return singleton;
	}
}
