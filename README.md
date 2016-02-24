BugfixTracker
=============

:rotating_light: WARNING :rotating_light:

This work is under heavy refactoring. The informations provided here -especially about software details-
aren't fully working, as I am working on a more elegant structure for this project. Please be gentle
while judging and criticism on this specific part of the program, as the software is still working
completely. Thank you !

			-- Yassine Badache

:rotating_light: WARNING :rotating_light:



### Individual research project checked by Martin Monperrus for the University of Lille 1

## Goal
-----
The main goal of this work is to provide a way to probe commits in a given repository, and analyze them
using the [Spoon](http://spoon.gforge.inria.fr/) library. Into those commits, we are comparing each
file (previous and current version), heading for AST changes into this file. The final goal is to
provide a data set in order to perform bug classification, and open a new way to fix bugs automatically.

## Libraries used
--------------
# [Spoon](http://spoon.gforge.inria.fr/)
------
Spoon is an open-source library for analyzing and transforming Java source code. External contributions as pull requests are welcome !
The mission of Spoon is to provide a high-quality library for analyzing and transforming Java source code.

- If you use Spoon for industrial purposes, please consider funding Spoon through a research contract with Inria (contact Martin Monperrus for this).

- If you use Spoon for academic purposes, please cite: Renaud Pawlak, Martin Monperrus, Nicolas Petitprez, Carlos Noguera, Lionel Seinturier. “Spoon: A Library for Implementing Analyses and Transformations of Java Source Code”. In Software: Practice and Experience, Wiley-Blackwell, 2015. Doi: 10.1002/spe.2346.

```
@article{pawlak:hal-01169705,
  TITLE = {{Spoon: A Library for Implementing Analyses and Transformations of Java Source Code}},
  AUTHOR = {Pawlak, Renaud and Monperrus, Martin and Petitprez, Nicolas and Noguera, Carlos and Seinturier, Lionel},
  JOURNAL = {{Software: Practice and Experience}},
  PUBLISHER = {{Wiley-Blackwell}},
  PAGES = {na},
  YEAR = {2015},
  doi = {10.1002/spe.2346},
}
```

# [Gumtree AST Diff](https://github.com/SpoonLabs/gumtree-spoon-ast-diff)
--------------------
The INRIA laboratory implemented the Gumtree algorith, which allows to compare two files as AST and not
as plain texts, giving structural details about changes and useful informations when analyzing code.

# [Gitective](https://github.com/kevinsawicki/gitective)
-------------
Even though it is not maintained, a few featurs of Gitective were used in this work, especially about
getting back content from files in commit versions. Commits are representend as Blob objects, and you
cannot directly grab the content of a file from a commit ID. That is where Gitective went in action.

# [JGit](https://eclipse.org/jgit/)
-------
[The Eclispe foundation](https://eclipse.org) implemented a lot of methods and features about managing
Git objects, and it is obviously used here in order to represent as an object a Repository, a Commit,
a Git instance or any object used in Git.

## Installation details
----------------------
# Required libraries
Even though the [pom.xml](https://github.com/ybadache/BugfixTracker/blob/master/pom.xml) file will provide you
every useful library you need, the Gumtree Java implementation isn't available on Maven. In order to use it,
you have two choices:
	- Clone the [Gumtree AST Diff](https://github.com/SpoonLabs/gumtree-spoon-ast-diff) repository, and use it as a reference through your IDE.
	- After cloning, turn the Gumtree into a .jar, and add it to your external depedencies.

Until the repository turns to public using Maven, those are the only ways to use Gumtree, thus this software.

## Software details
---------------
# Changes detected
At the moment, the software can detect four changes between two files, the others being classified as "Others":
   - Assignments
   - Return values
   - Field write
   - Local variables
If you want to add another change type, you just have to create another Singleton instance, following the ChangeSingleton
interface, and applying the name you want through the code. Then, add your condition to the StoreResult class. And that's it !
  
# Data set
---------
At today's date, the following repositories were chosen to being analyzed:
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

Two others data sets were given: one from [Odd Code](http://odd-code.github.io/), and one from a .xmls file
provided by the head teacher of this project, Martin Monperrus.

## Results
-------
Results are given in [this table.](https://github.com/VaubanParty/BugfixTracker/blob/master/results/table.md)

## Misc
----
Interesting observations have been made while running this algorithm on Elastic Search. Indeed, the algorithm
found no less than 30 025 commits, while both Git command and online Git repository show a number around 17 000.
It is not related to the branches, as the sum of all their commits is way more than 30 000, in addition to the
fact that this code only runs on local content. Strange.
More strange, this looks like it is a natural behavior: all the programs are showing more commits that the Git
command or remote repository. This is not natural, there is maybe a way that it also takes merges into account,
making this really hard to get.

If anyone find some reason for this behavior, with your knowledge of [JGit](https://github.com/eclipse/jgit), where I think the problem is, feel free to contact me; I would be glad to discuss about it:
	yassine.badache@gmail.com
	
	
And yes, I can't use Markdown correctly. Yet.
