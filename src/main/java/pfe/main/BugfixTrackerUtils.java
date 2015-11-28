package pfe.main;

import java.io.File;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

public class BugfixTrackerUtils {

	public BugfixTrackerUtils ()	{
		
	}
	
	
	public Repository setRepository (String repo_path) throws Exception	{
		File gitDir = new File(repo_path);

		RepositoryBuilder builder = new RepositoryBuilder();
		Repository repository;
		repository = builder.setGitDir(gitDir).readEnvironment().findGitDir().build();
		
		return repository;
	}
}
