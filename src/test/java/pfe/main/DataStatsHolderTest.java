package pfe.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pfe.resultHandling.DataStatsHolder;

public class DataStatsHolderTest {

	@Test
	public void incrementTest() {
		DataStatsHolder dsh = new DataStatsHolder();
		assertEquals(0, dsh.getNbCommits());
		assertEquals(0, dsh.getCommitsWithError());
		assertEquals(0, dsh.getNbAssignment());
		assertEquals(0, dsh.getNbFieldWrite());
		assertEquals(0, dsh.getNbFileErrors());
		assertEquals(0, dsh.getNbLocalVar());
		assertEquals(0, dsh.getNbReturn());
		assertEquals("", dsh.getErrorString());

		dsh.increment("commit");
		assertEquals(1, dsh.getNbCommits());

		dsh.increment("file_error");
		assertEquals(1, dsh.getNbFileErrors());

		dsh.increment("Assignment");
		assertEquals(1, dsh.getNbAssignment());

		dsh.increment("LocalVariable");
		assertEquals(1, dsh.getNbLocalVar());

		dsh.increment("Return");
		assertEquals(1, dsh.getNbReturn());

		dsh.increment("FieldWrite");
		assertEquals(1, dsh.getNbFieldWrite());

		dsh.increment("commit_error");
		assertEquals(1, dsh.getCommitsWithError());

		dsh.increment("something");
		assertEquals("non-existent", dsh.getErrorString());
	}

}
