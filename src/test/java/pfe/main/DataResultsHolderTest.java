package pfe.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;

import pfe.commitAnalysis.CommitAnalyzingUtils;
import pfe.resultHandling.DataResultsHolder;

public class DataResultsHolderTest {

	@Test
	public void initTest() {
		DataResultsHolder drh = new DataResultsHolder("OPL_projet", "ouap", "all-commits");

		assertNotNull(drh.getFieldcommits());
		assertNotNull(drh.getLocalcommits());
		assertNotNull(drh.getAssignmentcommits());
		assertNotNull(drh.getReturncommits());

		assertEquals(1, drh.getFieldcommits().size());
		assertEquals(1, drh.getLocalcommits().size());
		assertEquals(1, drh.getAssignmentcommits().size());
		assertEquals(1, drh.getReturncommits().size());
	}

	@Test
	public void createFilesTest() throws IOException {
		DataResultsHolder drh = new DataResultsHolder("OPL_projet", "ouap", "all-commits");

		assertNotNull(drh.getRes_field());
		assertNotNull(drh.getRes_local());
		assertNotNull(drh.getRes_assign());
		assertNotNull(drh.getRes_return());
	}

	@Test
	public void addTest() throws Exception {
		CommitAnalyzingUtils bftu = new CommitAnalyzingUtils();
		DataResultsHolder drh = new DataResultsHolder("OPL_projet", "ouap", "all-commits");
		Repository repository = bftu.setRepository("../bugfixRepoSamples/OPL_projet/.git");
		RevWalk rw = new RevWalk(repository);
		RevCommit commit = rw.parseCommit(repository.resolve("b4475e94f3cc1f9d2c1755e83455a5175d37be65"));

		drh.add("FieldWrite", commit);
		assertNotNull(drh.getFieldcommits());
		assertEquals(2, drh.getFieldcommits().size());

		drh.add("Return", commit);
		assertNotNull(drh.getReturncommits());
		assertEquals(2, drh.getReturncommits().size());

		drh.add("Assignment", commit);
		assertNotNull(drh.getAssignmentcommits());
		assertEquals(2, drh.getAssignmentcommits().size());

		drh.add("LocalVariable", commit);
		assertNotNull(drh.getLocalcommits());
		assertEquals(2, drh.getLocalcommits().size());
	}
}
