package pfe.main;


import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.gitective.core.BlobUtils;

import fr.inria.sacha.spoon.diffSpoon.DiffSpoon;



public class MainClass {
	
	static BugfixTrackerUtils bftUtils = new BugfixTrackerUtils();
	
	public static void main (String args[])	throws Exception {
		String directoryPath = "../bugfixRepoSamples/derby/.git";
		
		Repository repository = bftUtils.setRepository(directoryPath);

		diffspoonTry(repository);
	}
	
	

	public static void diffspoonTry (Repository repository) throws Exception	{
		DiffSpoon diffspoon = new DiffSpoon();
		Git git = new Git (repository);
		RevWalk rw = new RevWalk(repository);
		int nbcommit = 0;
		
		List<Ref> branches = git.branchList().call();
		
		for (Ref branch : branches) {
	        String branchName = branch.getName();

	        System.out.println("Commits of branch: " + branch.getName());
	        System.out.println("-------------------------------------");

	        Iterable<RevCommit> commits = git.log().all().call();

	        for (RevCommit commit : commits) {
	        	nbcommit++;
	        	System.out.println("Files of commit: " + commit.getName());
		        System.out.println("-------------------------------------");
	            // boolean foundInThisBranch = false;

	            RevCommit targetCommit = rw.parseCommit(repository.resolve(
	                    commit.getName()));
	            RevCommit targetParent = rw.parseCommit(commit.getParent(0).getId());
	            
	            DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
	    		
	    		df.setRepository(repository);
	    		df.setDiffComparator(RawTextComparator.DEFAULT);
	    		df.setDetectRenames(true);
	    		
	    		List<DiffEntry> diffs = df.scan(targetParent.getTree(), targetCommit.getTree());
	    		
	    		for (DiffEntry diff : diffs) {
	    			System.out.println(df.toFileHeader(diff));
	    			ObjectId current = BlobUtils.getId(repository, branchName, diff.getNewPath());
	    			ObjectId previous = BlobUtils.getId(repository, branchName + "~1", diff.getOldPath());
	    			
	    			if (previous != null)	{	    		
	    				String currentContent = BlobUtils.getContent(repository, current);
	    				String previousContent = BlobUtils.getContent(repository, previous);
	    				
	    				String currentContentPath = "resourcesNew/" + diff.getNewPath();
	    				String previousContentPath = "resourcesOld/" + diff.getOldPath();
	    				
	    				//System.out.println("Current : " + currentContentPath);
	    				//System.out.println("Previous : " + previousContentPath);
	    				
	    				if (currentContentPath.contains(".java"))	{
	    					File f1 = new File(currentContentPath);
	    					File f2 = new File(previousContentPath);
	    					
	    					FileUtils.writeStringToFile(f1, currentContent);
	    					FileUtils.writeStringToFile(f2, previousContent);
	    					
	    					// System.out.println("Current : \n" + currentContent);
	    					try	{
	    						if (f1 != null && f2 != null)
		    						System.out.println(diffspoon.compare(f1, f2).toString());
	    					}
	    					catch (Exception e)
	    					{
	    						System.out.println("Error occured here");
	    					}
	    					
	    				}
	    			}
	    		}
	    		
	    		if (nbcommit > 5)
	    			break;
	        }
		}
		
		System.out.println(nbcommit + " commits");
	}
}
	            
	            
	            /* for (Map.Entry<String, Ref> e : repository.getAllRefs().entrySet()) {
	                if (e.getKey().startsWith(Constants.R_HEADS)) {
	                    if (rw.isMergedInto(targetCommit, rw.parseCommit(
	                            e.getValue().getObjectId()))) {
	                        String foundInBranch = e.getValue().getName();
	                        if (branchName.equals(foundInBranch)) {
	                            foundInThisBranch = true;
	                            break;
	                        }
	                    }
	                }
	            }

	            if (foundInThisBranch) {
	                System.out.println(commit.getName());
	                System.out.println(commit.getAuthorIdent().getName());
	                System.out.println(new Date(commit.getCommitTime()));
	                System.out.println(commit.getFullMessage());
	            } */
