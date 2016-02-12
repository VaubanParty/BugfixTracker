package pfe.commitAnalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.gumtreediff.actions.model.Action;

import fr.inria.sacha.spoon.diffSpoon.CtDiff;
import fr.inria.sacha.spoon.diffSpoon.DiffSpoon;
import fr.inria.sacha.spoon.diffSpoon.DiffSpoonImpl;
import fr.inria.sacha.spoon.diffSpoon.SpoonGumTreeBuilder;

public class FileDiff {

	private List<Action> totalactions;

	public FileDiff() {
		totalactions = new ArrayList<Action>();
	}

	public void diffFiles(File f1, File f2) throws Exception {
		totalactions = new ArrayList<Action>();

		DiffSpoon diffspoon = new DiffSpoonImpl();

		CtDiff result = diffspoon.compare(f1, f2);

		List<Action> actions = result.getRootActions();

		for (Action a : actions) {
			totalactions.add(a);
			a.getNode().getMetadata(SpoonGumTreeBuilder.SPOON_OBJECT).getClass().getSimpleName();

			f1.delete();
			f2.delete();

			// FieldWrite Assignment Return LocalVar

		}
	}
}
