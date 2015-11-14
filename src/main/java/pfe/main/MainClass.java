package pfe.main;

import java.io.File;

import fr.inria.sacha.spoon.diffSpoon.DiffSpoon;



public class MainClass {

	public static void main (String args[])	throws Exception {
		
		File left = new File("sources/Left_test.java");
		File right = new File("sources/Right_test.java");
		
		DiffSpoon dfsp = new DiffSpoon();
		dfsp.compare(left, right);
	}
}