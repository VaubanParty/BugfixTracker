# BugfixTracker
Individual research project checked by Martin Monperrus for the University of Lille 



This project has incremental goals:
    - First, being able to probe for all commits in a given Github repository. The goal
        here is to use SpoonLabs' tool "Diff Spoon"* to compare each .java file in a given
        revision on a syntaxic level, by comparing AST of both files.
        
    - Then, filtering those commits to check only for introduced and fixed bugs, and working
        with the datas given by Diff Spoon to be able to classify each type of bug fix.
        
    - [NOT YET DEFINED]
    
    
I'll give here the current progression of each goal.



[WHAT IS DONE]
    Bugfix Tracker is able to, on a given Github repository, probe on all commits and using the
    AST difference between modified .java files to classify four types of changes, defined on
    instructions from my project's teacher, Martin Monperrus. They only check for both update or
    insertion, and a given commit can belong to more that one class:
        - Assignments
        - Return values
        - Field reads
        - Local variables
        
    I am currently working on the classification on some projects. You can find the list below.
    
    * Aries
    * Atmosphere
    * Cassandra
    * Derby
    * Elastic Search
    * Facebook Android SDK
    * Lucene
    * Mahout
    * Netty
    * OpenJPA
    * Presto
    * Qpid Proton
    * Wicket
    

        
(*) DiffSpoon: Fine-grained and Accurate Source Code Differencing (Jean-Rémy Falleri, Floréal Morandat,
Xavier Blanc, Matias Martinez, Martin Monperrus), In Proceedings of the International Conference on
Automated Software Engineering, 2014.