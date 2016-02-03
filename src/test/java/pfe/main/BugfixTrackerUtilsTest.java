package pfe.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.junit.Test;

import pfe.commitAnalysis.CommitAnalyzingUtils;

import com.google.common.collect.Iterables;

public class BugfixTrackerUtilsTest {

	@Test
	public void setRepositoryTest() throws Exception {
		CommitAnalyzingUtils bftu = new CommitAnalyzingUtils();
		Repository repository = bftu
				.setRepository("../bugfixRepoSamples/OPL_projet/.git");

		assertNotNull(repository);
		assertEquals("master", repository.getBranch());
	}

	@Test
	public void getAllBranchesTest() throws Exception {
		CommitAnalyzingUtils bftu = new CommitAnalyzingUtils();
		Repository repository = bftu
				.setRepository("../bugfixRepoSamples/OPL_projet/.git");

		Git git = new Git(repository);
		List<Ref> list = bftu.getAllBranches(git);

		assertNotNull(list);
		assertEquals(1, list.size());
	}

	@Test
	public void getAllCommitsTest() throws Exception {
		CommitAnalyzingUtils bftu = new CommitAnalyzingUtils();
		Repository repository = bftu
				.setRepository("../bugfixRepoSamples/OPL_projet/.git");

		Git git = new Git(repository);
		Iterable<RevCommit> commits = bftu.getAllCommits(git);

		assertNotNull(commits);
		assertEquals(15, Iterables.size(commits));
	}

	@Test
	public void writeContentInFileTest() throws IOException {
		CommitAnalyzingUtils bftu = new CommitAnalyzingUtils();
		String name = "Hello.java";
		String content = "public static void main (String[] args)	{"
				+ "System.out.println(\"Hello world !\");" + "}	}";

		File f = bftu.writeContentInFile(name, content);

		assertNotNull(f);
		assertEquals(name, f.getName());
		assertEquals(content, FileUtils.readFileToString(f));
	}

	@Test
	public void getContent() throws Exception {
		CommitAnalyzingUtils bftu = new CommitAnalyzingUtils();
		Repository repository = bftu
				.setRepository("../bugfixRepoSamples/OPL_projet/.git");
		RevWalk rw = new RevWalk(repository);

		RevCommit targetCommit = rw.parseCommit(repository
				.resolve("b4475e94f3cc1f9d2c1755e83455a5175d37be65"));

		RevCommit targetParent = rw.parseCommit(repository
				.resolve("25fa9c071f65dbce215aed62b2f51b0c00f895f7"));

		DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);

		df.setRepository(repository);
		df.setDiffComparator(RawTextComparator.DEFAULT);
		df.setDetectRenames(false);

		List<DiffEntry> diffs = df.scan(targetParent.getTree(),
				targetCommit.getTree());

		DiffEntry diff = diffs.get(277);

		String currentContent = bftu.getContent(repository, diff, targetCommit)[0];
		String previousContent = bftu
				.getContent(repository, diff, targetCommit)[1];

		assertNotNull(currentContent);
		assertNotNull(previousContent);

		assertEquals(false, currentContent.equals(previousContent));
	}
}
