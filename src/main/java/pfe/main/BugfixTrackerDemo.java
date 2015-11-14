package pfe.main;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitFinder;
import org.gitective.core.CommitUtils;
import org.gitective.core.stat.AuthorHistogramFilter;
import org.gitective.core.stat.CommitCalendar;
import org.gitective.core.stat.Month;
import org.gitective.core.stat.UserCommitActivity;
import org.gitective.core.stat.YearCommitActivity;

public class BugfixTrackerDemo {
	
	private String repoFullPath;
	
	private Repository repo;
	
	public BugfixTrackerDemo(String repoPath)	{
		String path = "C:\\Users\\YBADACHE\\workspace\\reposamples\\" + repoPath + "\\.git";
		repoFullPath = path.replace("\\", "/");
		
		try {
			repo = new FileRepository(repoFullPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getHead ()	{
		RevCommit latestCommit = CommitUtils.getHead(repo);
		System.out.println("HEAD commit ID for " + repoFullPath.substring(40, 48) + " is " + latestCommit.name());
	}
	
	public void getHeadContent (String file)
	{
		String content = BlobUtils.getHeadContent(repo, file);
		System.out.println("File content in HEAD commit : " + content);
	}
	
	public void generateHistogramCommit (int monthNumber)	{
		AuthorHistogramFilter filter = new AuthorHistogramFilter();
		CommitFinder finder = new CommitFinder(repoFullPath);
		finder.setFilter(filter).find();

		UserCommitActivity[] activity = filter.getHistogram().getUserActivity();
		CommitCalendar commits = new CommitCalendar(activity);

		for(YearCommitActivity year : commits.getYears())
		     System.out.println(year.getMonthCount(Month.month(monthNumber-1))
		                          + " commits this month in " + year.getYear());
	}
	
	public void diffBetweenFiles (String file, String branch)
	{
		String previousVersion = branch + "~1";
		ObjectId current = BlobUtils.getId(repo, branch, file);
		ObjectId previous = BlobUtils.getId(repo, previousVersion, file);

		Collection<Edit> edit = BlobUtils.diff(repo, previous, current);
		System.out.println(edit);
	}

}
