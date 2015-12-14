package pfe.main;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import com.github.gumtreediff.actions.model.Action;

import fr.inria.sacha.spoon.diffSpoon.CtDiff;
import fr.inria.sacha.spoon.diffSpoon.DiffSpoon;

/**
 * 
 * @author Yassine BADACHE
 *
 *         This class is used to track files in commit, and using the Gumtree
 *         AST Diff library given by Spoon to get the difference between the
 *         current and previous version of a commit, then extracting value from
 *         the numbers given.
 * 
 */
public class BugfixTracker {

	private BugfixTrackerUtils bugfixUtils;

	private DataStatsHolder statsHolder;

	private DataResultsHolder resultsHolder;

	private String project;

	private String projectOwner;

	private String directoryPath;

	private Repository repository;

	private Git git;

	private RevWalk rw;

	public BugfixTracker(String[] args) throws Exception {
		projectOwner = args[0];
		project = args[1];
		directoryPath = "../bugfixRepoSamples/" + project + "/.git";

		bugfixUtils = new BugfixTrackerUtils();
		statsHolder = new DataStatsHolder();
		resultsHolder = new DataResultsHolder(project, projectOwner);

		repository = bugfixUtils.setRepository(directoryPath);
		git = new Git(repository);
		rw = new RevWalk(repository);
	}

	public void probeAllCommits() throws Exception {

		List<Ref> branches = bugfixUtils.getAllBranches(git);

		for (Ref branch : branches) {

			@SuppressWarnings("unused")
			String branchName = branch.getName();

			System.out.println("Commits of branch: " + branch.getName());
			System.out.println("-------------------------------------");

			Iterable<RevCommit> commits = bugfixUtils.getAllCommits(git);

			for (RevCommit commit : commits) {
				boolean assigned = false;
				boolean returned = false;
				boolean fielded = false;
				boolean localed = false;
				boolean faulty = false;

				statsHolder.increment("commit");
				System.out.println("\n-------------------------------------");
				System.out.println("--- Files of commit n°"
						+ statsHolder.getNbCommits() + " with ID : "
						+ commit.getName());
				System.out.println("-------------------------------------");

				if (commit.getParentCount() > 0) {
					RevCommit targetCommit = rw.parseCommit(repository
							.resolve(commit.getName()));

					RevCommit targetParent = rw.parseCommit(commit.getParent(0)
							.getId());

					DiffFormatter df = new DiffFormatter(
							DisabledOutputStream.INSTANCE);

					df.setRepository(repository);
					df.setDiffComparator(RawTextComparator.DEFAULT);
					df.setDetectRenames(true);

					List<DiffEntry> diffs = df.scan(targetParent.getTree(),
							targetCommit.getTree());

					for (DiffEntry diff : diffs) {
						String currentContent = bugfixUtils.getContent(
								repository, diff, commit)[0];
						String previousContent = bugfixUtils.getContent(
								repository, diff, commit)[1];

						if (diff.getNewPath().contains(".java")) {
							File f1 = bugfixUtils.writeContentInFile("c1.java",
									currentContent);
							File f2 = bugfixUtils.writeContentInFile("c2.java",
									previousContent);

							if (f1 != null && f2 != null) {
								try {
									DiffSpoon diffspoon = new DiffSpoon(true);

									CtDiff result = diffspoon.compare(f1, f2);

									f1.delete();
									f2.delete();

									List<Action> rootActions = result
											.getRootActions();

									System.out.println(result.toString());
									// update / insert
									if (diffspoon.containsAction(rootActions,
											"Insert", "FieldWrite")
											|| diffspoon.containsAction(
													rootActions, "Update",
													"FieldRead")) {
										if (!fielded) {
											resultsHolder.add("FieldWrite",
													commit);
											statsHolder.increment("Fieldwrite");
										}
										fielded = true;

									}

									if (diffspoon.containsAction(rootActions,
											"Insert", "Assignment")
											|| diffspoon.containsAction(
													rootActions, "Update",
													"Assignment")) {
										if (!assigned) {
											resultsHolder.add("Assignment",
													commit);
											statsHolder.increment("Assignment");
										}
										assigned = true;
									}

									if (diffspoon.containsAction(rootActions,
											"Insert", "Return")
											|| diffspoon.containsAction(
													rootActions, "Update",
													"Return")) {
										if (!returned) {
											resultsHolder.add("Return", commit);
											statsHolder.increment("Return");
										}
										returned = true;
									}

									if (diffspoon.containsAction(rootActions,
											"Insert", "LocalVariable")
											|| diffspoon.containsAction(
													rootActions, "Update",
													"LocalVariable")) {
										if (!localed) {
											resultsHolder.add("LocalVariable",
													commit);
											statsHolder
											.increment("LocalVariable");
										}
										localed = true;
									}

								}

								catch (NullPointerException e) {
									statsHolder.increment("file_error");
									faulty = true;
								} catch (org.eclipse.jdt.internal.compiler.problem.AbortCompilation e) {
									statsHolder.increment("file_error");
									faulty = true;
								} catch (IndexOutOfBoundsException e) {
									statsHolder.increment("file_error");
									faulty = true;
								} catch (spoon.support.reflect.reference.SpoonClassNotFoundException e) {
									statsHolder.increment("file_error");
									faulty = true;
								} catch (java.lang.RuntimeException e) {
									statsHolder.increment("file_error");
									faulty = true;
								} catch (java.lang.StackOverflowError e) {
									statsHolder.increment("file_error");
									faulty = true;
								}
							}
						}
					}
					if (faulty)
						statsHolder.increment("commit_error");
				}
			}

			statsHolder.printResults();
			resultsHolder.saveResults();
		}
	}
}
