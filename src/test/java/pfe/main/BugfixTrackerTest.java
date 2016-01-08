package pfe.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class BugfixTrackerTest {

	@Test
	public void bugfixTrackerTest() throws Exception {
		BugfixTracker bft = new BugfixTracker(new String[] { "ouap",
				"OPL_projet" });

		assertEquals("ouap", bft.getProjectOwner());
		assertEquals("OPL_projet", bft.getProject());
		assertEquals("../bugfixRepoSamples/OPL_projet/.git",
				bft.getDirectoryPath());

		assertNotNull(bft.getRepository());
		assertNotNull(bft.getBugfixUtils());
		assertNotNull(bft.getResultsHolder());
		assertNotNull(bft.getStatsHolder());
		assertNotNull(bft.getGit());
		assertNotNull(bft.getRw());
	}

	@Test
	public void probeAllCommitsTest() throws Exception {
		BugfixTracker bft = new BugfixTracker(new String[] { "pagsegura",
				"../java" });
		bft.probeAllCommits();

		assertEquals(13, bft.getStatsHolder().getNbAssignment());
		assertEquals(21, bft.getStatsHolder().getNbLocalVar());
		assertEquals(15, bft.getStatsHolder().getNbReturn());
		assertEquals(0, bft.getStatsHolder().getNbFieldWrite());

		assertEquals(126, bft.getStatsHolder().getNbCommits());
		assertEquals(25, bft.getStatsHolder().getNbCommitsWithError());
		assertEquals(133, bft.getStatsHolder().getNbFileErrors());
	}

}
