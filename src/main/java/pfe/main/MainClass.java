package pfe.main;


public class MainClass {

	public static void main(String args[]) throws Exception {
		String pid;

		if (args.length != 2) {
			System.out.println("Bad use, please use as : ./bugfixtracker <owner of the project> <project>");
			System.out.println("Example: ./bugfixtracker apache derby");
		}

		else {
			BugfixTracker bugfix = new BugfixTracker(args);
			// bugfix.probeOddCodeCommit("facebook-android-sdk.pairs.txt");
			// bugfix.probeFileCommit("facebook-android-sdk.commitpaper.txt");
			bugfix.probeAllCommits();
		}

	}
}
