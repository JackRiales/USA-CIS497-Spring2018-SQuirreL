/**
 * File:
 * 		Author: Shawyn Kane
 *		Course: CSC 497-501 Senior Project
 *		Assignment: Course Project
 *		Team Name:	SQuirreLs
 *		Team Members:	Jack Riales (CS), Josh Richardson (IT), Shawyn Kane (CS), & Alec Macrae (IS)
 *		Project Name:	Sepsis Detection with IBM Watson
 * 		Date Created: Mar 8, 2018
 * 		Package: sepsisDetectionApplication
 */
package sepsisDetectionApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TODO Write description for Application class.
 * 
 * 
 * @author Shawyn Kane
 * @version 1
 * Date Created: Mar 8, 2018
 */
public class Application {
	
	private static final String username = "";
	private static final String password = "";
	private boolean manualEntry = true;
	private String oAuthKey = "";
	private static ArrayList<Patient> patients = new ArrayList<Patient>();
	
	private static void ParseFile(File f) {
		Scanner scanFile = null;
		try {
			scanFile = new Scanner(f);
			while (scanFile.hasNextLine()) patients.add(new Patient(scanFile.nextLine()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanFile.close();
		} // try catch finally
	} // ParseFile(File f) method
	
	/**
	 * TODO Write description for main method
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Do you want to input the patient data through the console? (Y/N): ");
		String answer = scan.nextLine().toLowerCase();
		if (answer == "y" || answer == "yes") {
			Patient p = new Patient();
			System.out.println("Body Temperature: ");
			p.bodyTemp = Float.parseFloat(scan.nextLine());
			
			System.out.println("Heart Rate:");
			p.heartRate = Integer.parseInt(scan.nextLine());
			
			System.out.println("Respiratory Rate: ");
			p.respiratoryRate = Integer.parseInt(scan.nextLine());
			
			System.out.println("Urine Output: ");
			p.urineOutput = Boolean.parseBoolean(scan.nextLine());
			
			System.out.println("Platelet Count: ");
			p.plateletCount = Double.parseDouble(scan.nextLine());
			
			System.out.println("Difficulty Breathing: ");
			p.difficultyBreathing = Boolean.parseBoolean(scan.nextLine());
			
			System.out.println("Abnormal Heart Pump: ");
			p.abnormalHeartPump = Boolean.parseBoolean(scan.nextLine());
			
			System.out.println("Age: ");
			p.age = Integer.parseInt(scan.nextLine());
			
			System.out.println("Abdominal Pain: ");
			p.abdominalPain = Boolean.parseBoolean(scan.nextLine());
			
			System.out.println("Weakened Immune System: ");
			p.weakenedImmuneSystem = Boolean.parseBoolean(scan.nextLine());
			
			// send patient information to IBM Watson
			
			// display results
			
		} else {
			boolean ask = true;
			File f;
			do {
				System.out.println("Absolute file path: ");
				f = new File(scan.nextLine());
				ask = !f.isAbsolute();
			} while (ask);
			
			// read in file and parse data
			ParseFile(f);
			
			// send patient data to IBM Watson
			
			// display results
			
		} // else
		
		scan.close();
	} // main method

} // Application class
