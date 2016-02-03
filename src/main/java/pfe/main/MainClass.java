package pfe.main;

import pfe.commitAnalysis.CommitAnalyzer;

public class MainClass {

	public static void main(String args[]) throws Exception {

		if (args.length != 2) {
			System.out.println("Bad use, please use as : ./bugfixtracker <owner of the project> <project>");
			System.out.println("Example: ./bugfixtracker apache derby");
		}

		else {
			CommitAnalyzer bugfix = new CommitAnalyzer(args);
			// bugfix.probeOddCodeCommit("facebook-android-sdk.pairs.txt");
			// bugfix.probeAllCommits();
			bugfix.commitSampleTry("faae1d6fd17db198e1da3736b8e152390c23a33b");
		}

	}
}
