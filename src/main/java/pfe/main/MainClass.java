package pfe.main;


import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.AndCommitFilter;
import org.gitective.core.filter.commit.CommitCountFilter;
import org.gitective.core.filter.commit.CommitMessageFindFilter;



public class MainClass {

	public static void main (String args[])	throws Exception {
		CommitCountFilter bugCommits = new CommitCountFilter();
		CommitCountFilter testCommits = new CommitCountFilter();
		CommitFinder finder = new CommitFinder("../gumtree-spoon-ast-diff/.git");
		
		// finder.setFilter(new AndCommitFilter(new CommitMessageFindFilter(":?(Test|test)"), testCommits));
		finder.setFilter(new AndCommitFilter(new CommitMessageFindFilter(":?(Bug|bug)"), bugCommits));
		finder.find();
		
		// System.out.println(testCommits.getCount() + " total test commits");
		System.out.println(bugCommits.getCount() + " total bug commits");
	}
}