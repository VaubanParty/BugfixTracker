package pfe.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class BugfixTrackerTest {

	@Test
	public void bugfixTrackerTest() throws Exception {
		CommitAnalyzer bft = new CommitAnalyzer(new String[] { "ouap", "OPL_projet" });

		assertEquals("ouap", bft.getProjectOwner());
		assertEquals("OPL_projet", bft.getProject());
		assertEquals("../bugfixRepoSamples/OPL_projet/.git", bft.getDirectoryPath());

		assertNotNull(bft.getRepository());
		assertNotNull(bft.getBugfixUtils());
		assertNotNull(bft.getResultsHolder());
		assertNotNull(bft.getStatsHolder());
		assertNotNull(bft.getGit());
		assertNotNull(bft.getRw());
	}

	@Test
	public void probeAllCommitsTest() throws Exception {
		CommitAnalyzer bft = new CommitAnalyzer(new String[] { "pagsegura", "../java" });
		bft.probeAllCommits();

		assertEquals(13, bft.getStatsHolder().getNbAssignment());
		assertEquals(22, bft.getStatsHolder().getNbLocalVar());
		assertEquals(16, bft.getStatsHolder().getNbReturn());
		assertEquals(11, bft.getStatsHolder().getNbFieldWrite());

		assertEquals(126, bft.getStatsHolder().getNbCommits());
		assertEquals(22, bft.getStatsHolder().getNbCommitsWithError());
		assertEquals(113, bft.getStatsHolder().getNbFileErrors());
	}

}
