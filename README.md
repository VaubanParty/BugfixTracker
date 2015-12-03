BugfixTracker
=============

## Individual research project checked by Martin Monperrus for the University of Lille 



Goals
-----

* First, being able to probe for all commits in a given Github repository. The goal here is to use SpoonLabs'
tool "Diff Spoon" (1) to compare each .java file in a given revision on a syntaxic level, by comparing AST of both files.
* Then, filtering those commits to check only for introduced and fixed bugs, and working
  with the datas given by Diff Spoon to be able to classify each type of bug fix. 
* [NOT YET DEFINED]
        


Done
----
Bugfix Tracker is able to, on a given Github repository, probe on all commits and using the
AST difference between modified .java files to classify four types of changes, defined on
instructions from my project's teacher, Martin Monperrus. They only check for both update or
insertion, and a given commit can belong to more that one class:
   - Assignments
   - Return values
   - Field reads
   - Local variables
        
        
[02/12/2015] BugfixTracker is fully working on Cassandra, getting error files in order to correct
Spoon or detecting failures. Five errors are actually caught:
   - NullPointerException
   - AbortCompilation
   - IndexOutOfBounds
   - SpoonClassNotFound
   - RuntimeException
   		
[02/12/2015] Experimentations began.
    
    
    
Results
-------
Results are given in [this table.](https://github.com/VaubanParty/BugfixTracker/blob/master/results/table.md)
    
   
Data Set
--------
I am currently working on the following projects:

* [Aries](https://github.com/apache/aries)
* [Atmosphere](https://github.com/Atmosphere/atmosphere)
* [Cassandra](https://github.com/apache/cassandra)
* [Derby](https://github.com/apache/derby)
* [Elastic Search](https://github.com/elastic/elasticsearch)
* [Facebook Android SDK](https://github.com/facebook/facebook-android-sdk)
* [Lucene](https://github.com/apache/lucene-solr)
* [Mahout](https://github.com/apache/mahout)
* [Netty](https://github.com/netty/netty)
* [OpenJPA](https://github.com/apache/openjpa)
* [Presto](https://github.com/facebook/presto)
* [Qpid Proton](https://github.com/apache/qpid-proton)
* [Wicket](https://github.com/apache/wicket)
    

        
(*) DiffSpoon: Fine-grained and Accurate Source Code Differencing (Jean-Rémy Falleri, Floréal Morandat,
Xavier Blanc, Matias Martinez, Martin Monperrus), In Proceedings of the International Conference on
Automated Software Engineering, 2014.



Misc
----
Interesting observations have been made while running this algorithm on Elastic Search. Indeed, the algorithm
found no less than 30 025 commits, while both Git command and online Git repository show a number around 17 000.
It is not related to the branches, as the sum of all their commits is way more than 30 000, in addition to the
fact that this code only runs on local content. Strange.
More strange, this looks like it is a natural behavior: all the programs are showing more commits that the Git
command or remote repository. This is not natural, there is maybe a way that it also takes merges into account,
making this really hard to get.

If anyone find some reason for this behavior, with their knowledge of [JGit](https://github.com/eclipse/jgit),
feel free to contact me; I would be glad to discuss about it:
	yassine.badache@gmail.com
	
	
And yes, I can't use Markdown correctly. Yet.
