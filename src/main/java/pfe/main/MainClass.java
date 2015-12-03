package pfe.main;


import java.io.File;
import java.util.ArrayList;
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
import org.gitective.core.BlobUtils;

import com.github.gumtreediff.actions.model.Action;

import fr.inria.sacha.spoon.diffSpoon.CtDiff;
import fr.inria.sacha.spoon.diffSpoon.DiffSpoon;



public class MainClass {
	
	static BugfixTrackerUtils bftUtils = new BugfixTrackerUtils();
	
	static String project = "openjpa";
	
	static String projectOwner = "apache";
	
	static String directoryPath = "../bugfixRepoSamples/" + project + "/.git";
	
	
	public static void main (String args[])	throws Exception {
		
		Repository repository = bftUtils.setRepository(directoryPath);
		
		diffspoonTry(repository);
	}
	
	
	public static void diffspoonTry (Repository repository) throws Exception	{
		Git git = new Git (repository);
		RevWalk rw = new RevWalk(repository);
		List<String> fieldcommits = new ArrayList<String>();
		List<String> localcommits = new ArrayList<String>();
		List<String> returncommits = new ArrayList<String>();
		List<String> assignmentcommits = new ArrayList<String>();
		fieldcommits.add("\n");
		localcommits.add("\n");
		returncommits.add("\n");
		assignmentcommits.add("\n");
		
		int totalcommit = 1;
		
		File res_assign = new File("results/" + project + "/assignments.md");       
		File res_local = new File("results/" + project + "/localvar.md");
		File res_return = new File("results/" + project + "/return.md");
		File res_field = new File("results/" + project + "/fieldread.md");
		
		
		List<Ref> branches = git.branchList().call();
		
		for (Ref branch : branches) {
			int nberrors = 0;
			int nbcommit = 0;
			int nbAssignment = 0;
			int nbLocalVar = 0;
			int nbReturn = 0;
			int nbFieldRead = 0;
			int nbchange = 0;
			int faulties = 0;
			
	        @SuppressWarnings("unused")
			String branchName = branch.getName();

	        System.out.println("Commits of branch: " + branch.getName());
	        System.out.println("-------------------------------------");

	        Iterable<RevCommit> commits = git.log().all().call();

	        for (RevCommit commit : commits) {
	        	boolean assigned = false;
	        	boolean returned = false;
	        	boolean fielded = false;
	        	boolean localed = false;
	        	boolean faulty = false;
	        	
	        	System.out.println("\n-------------------------------------");
	        	System.out.println("--- Files of commit n°" + totalcommit + " with ID : " + commit.getName());
		        System.out.println("-------------------------------------");
	        	nbcommit++;
	        	totalcommit++;
	        	
	        	if (commit.getParentCount() > 0)	{
	            RevCommit targetCommit = rw.parseCommit(repository.resolve(
	                    commit.getName()));
	            
            	RevCommit targetParent = rw.parseCommit(commit.getParent(0).getId());
            	
            	DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
	    		
	    		df.setRepository(repository);
	    		df.setDiffComparator(RawTextComparator.DEFAULT);
	    		df.setDetectRenames(true);
	    		
	    		List<DiffEntry> diffs = df.scan(targetParent.getTree(), targetCommit.getTree());
	    		
	    		
	    		for (DiffEntry diff : diffs) {
	    			String currentContent = BlobUtils.getContent(repository, commit.getId(), diff.getNewPath());
	    			String previousContent = BlobUtils.getContent(repository, commit.getParent(0).getId(), diff.getOldPath());
	    				
	    			
    				if (diff.getNewPath().contains(".java"))	{
    					File f1 = new File("c1.java");
    					File f2 = new File("c2.java");
    					
    					FileUtils.writeStringToFile(f1, currentContent);
    					FileUtils.writeStringToFile(f2, previousContent);
    					
    						if (f1 != null && f2 != null)	{
    							try	{
    							DiffSpoon diffspoon = new DiffSpoon(true);
    		    				
    							CtDiff result = diffspoon.compare(f1, f2);
    							
    							f1.delete();
    							f2.delete();
    							
    							List<Action> rootActions = result.getRootActions();
    							
    							// update / insert
    							if (diffspoon.containsAction(rootActions, "Insert", "FieldRead") || diffspoon.containsAction(rootActions, "Update", "FieldRead"))
    							{
    								if (!fielded)	{
    									fieldcommits.add("[" + commit.getName() + "](https://github.com/" + projectOwner + "/" + project + "/commit/" + commit.getName() + ")\n");
        								nbFieldRead++;
    								}
    								fielded = true;
    					
    							}
    							
    							if (diffspoon.containsAction(rootActions, "Insert", "Assignment") || diffspoon.containsAction(rootActions, "Update", "Assignment"))
    							{
    								if (!assigned)	{
    								assignmentcommits.add("[" + commit.getName() + "](https://github.com/" + projectOwner + "/" + project + "/commit/" + commit.getName() + ")\n");
    								nbAssignment++;
    								}
    								assigned = true;
    							}
    							
    							if (diffspoon.containsAction(rootActions, "Insert", "Return") || diffspoon.containsAction(rootActions, "Update", "Return"))
    							{
    								if (!returned)	{
    									returncommits.add("[" + commit.getName() + "](https://github.com/" + projectOwner + "/" + project + "/commit/" + commit.getName() + ")\n");
        								nbReturn++;
    								}
    								returned = true;
    							}
    							
    							if (diffspoon.containsAction(rootActions, "Insert", "LocalVariable") || diffspoon.containsAction(rootActions, "Update", "LocalVariable"))
    							{
    								if (!localed)	{
    									localcommits.add("[" + commit.getName() + "](https://github.com/" + projectOwner + "/" + project + "/commit/" + commit.getName() + ")\n");
    									nbLocalVar++;
    								}
    								localed = true;
    							}

    						}
	    							// Stocker commmit id tel que common ancestor
	    							// Liste actions, pour chaque action
	    							// Remonter au parent, si au moins un est vvv

    							catch (NullPointerException e)
    							{
    								String NPEFaultyFileCurrent = "errorfiles/" + project + "/faulty/npe/" + commit.getName() + "_currentVersion/" + diff.getNewPath();
    								String NPEFaultyFilePrevious = "errorfiles/" + project + "/faulty/npe/" + commit.getName() + "_previousVersion/" + diff.getOldPath();
    								File fault_new = new File(NPEFaultyFileCurrent);
    								File fault_old = new File(NPEFaultyFilePrevious);
    								FileUtils.writeStringToFile(fault_new, currentContent);
    								FileUtils.writeStringToFile(fault_old, previousContent);  								
    								nberrors++;
    								faulty = true;
    							}
    							catch (org.eclipse.jdt.internal.compiler.problem.AbortCompilation e)
    							{
    								String AbortExceptionFaultyFileCurrent = "errorfiles/" + project + "/faulty/abortCompilation/" + commit.getName() + "_currentVersion/" + diff.getNewPath();
    								String AbortExceptionFaultyFilePrevious = "errorfiles/" + project + "/faulty/abortCompilation/" + commit.getName() + "_previousVersion/" + diff.getNewPath();
    								File fault_new = new File(AbortExceptionFaultyFileCurrent);
    								File fault_old = new File(AbortExceptionFaultyFilePrevious);
    								FileUtils.writeStringToFile(fault_new, currentContent);
    								FileUtils.writeStringToFile(fault_old, currentContent);
    								nberrors++;
    								faulty = true;
    							}
    							catch (IndexOutOfBoundsException e)
    							{
    								String OOBExceptionFaultyFileCurrent = "errorfiles/" + project + "/faulty/outOfBounds/" + commit.getName() + "_currentVersion/" + diff.getNewPath();
    								String OOBExceptionFaultyFilePrevious = "errorfiles/" + project + "/faulty/outOfBounds/" + commit.getName() + "_previousVersion/" + diff.getNewPath();
    								File fault_new = new File(OOBExceptionFaultyFileCurrent);
    								File fault_old = new File(OOBExceptionFaultyFilePrevious);
    								FileUtils.writeStringToFile(fault_new, currentContent);
    								FileUtils.writeStringToFile(fault_old, currentContent);
    								nberrors++;
    								faulty = true;
    							}
    							catch (spoon.support.reflect.reference.SpoonClassNotFoundException e)
    							{
    								String SpoonCNFExceptionFaultyFileCurrent = "errorfiles/" + project + "/faulty/spoonCNFException/" + commit.getName() + "_currentVersion/" + diff.getNewPath();
    								String SpoonCNFExceptionFaultyFilePrevious = "errorfiles/" + project + "/faulty/spoonCNFException/" + commit.getName() + "_previousVersion/" + diff.getNewPath();
    								File fault_new = new File(SpoonCNFExceptionFaultyFileCurrent);
    								File fault_old = new File(SpoonCNFExceptionFaultyFilePrevious);
    								FileUtils.writeStringToFile(fault_new, currentContent);
    								FileUtils.writeStringToFile(fault_old, currentContent);
    								nberrors++;
    								faulty = true;
    							}
    							catch (java.lang.RuntimeException e)
    							{
    								String TypeBindingUnknowFaultyFileCurrent = "errorfiles/" + project + "/faulty/typeBindingException/" + commit.getName() + "_currentVersion/" + diff.getNewPath();
    								String TypeBindingUnknowFaultyFilePrevious = "errorfiles/" + project + "/faulty/typeBindingException/" + commit.getName() + "_previousVersion/" + diff.getNewPath();
    								File fault_new = new File(TypeBindingUnknowFaultyFileCurrent);
    								File fault_old = new File(TypeBindingUnknowFaultyFilePrevious);
    								FileUtils.writeStringToFile(fault_new, currentContent);
    								FileUtils.writeStringToFile(fault_old, currentContent);
    								nberrors++;
    								faulty = true;
    							}
    						} 				
    					}
    				}
	    		if (faulty)
	    			faulties++;
	        	}
	        }
		

        System.out.println(nberrors + " errors");
        System.out.println(nbcommit + " commits");
        System.out.println(faulties + " commits with errors");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-");
		System.out.println(nbAssignment + " updates or insert of assignments");
		System.out.println(nbLocalVar + " updates or insert of local variables ");
		System.out.println(nbReturn + " updates or insert of returns");
		System.out.println(nbFieldRead + " updates or insert of field reads");
		
		
		 FileUtils.writeStringToFile(res_assign, assignmentcommits.toString());
	     FileUtils.writeStringToFile(res_local, localcommits.toString());
	     FileUtils.writeStringToFile(res_return, returncommits.toString());
	     FileUtils.writeStringToFile(res_field, fieldcommits.toString());
       }            	
	}
}
