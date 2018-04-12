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

/**
 * This class is just to help process and temporarily store in memory the data of an individual patient.
 * 
 * 
 * @author Shawyn Kane
 * @version 1
 * Date Created: Mar 8, 2018
 */
public class Patient {
	public float bodyTemp;
	public int heartRate;
	public int respiratoryRate;
	public boolean urineOutput;
	public double plateletCount;
	public boolean difficultyBreathing;
	public boolean abnormalHeartPump;
	public int age;
	public boolean abdominalPain;
	public boolean weakenedImmuneSystem;
	
	/**
	 * Default Constructor.
	 */
	Patient() {}
	
	/**
	 * This constructor just takes each attribute as a parameter.
	 * 
	 * @param bodyTemp
	 * @param heartRate
	 * @param respiratoryRate
	 * @param urineOutput
	 * @param plateletCount
	 * @param difficultyBreathing
	 * @param abnormalHeartPump
	 * @param age
	 * @param abdominalPain
	 * @param weakenedImmuneSystem
	 */
	Patient(float bodyTemp, int heartRate, int respiratoryRate, boolean urineOutput, double plateletCount, boolean difficultyBreathing, boolean abnormalHeartPump, int age, boolean abdominalPain, boolean weakenedImmuneSystem) {
		this.bodyTemp = bodyTemp;
		this.heartRate = heartRate;
		this.respiratoryRate = respiratoryRate;
		this.urineOutput = urineOutput;
		this.plateletCount = plateletCount;
		this.difficultyBreathing = difficultyBreathing;
		this.abnormalHeartPump = abnormalHeartPump;
		this.age = age;
		this.abdominalPain = abdominalPain;
		this.weakenedImmuneSystem = weakenedImmuneSystem;
	} // Constructor
	
	/**
	 * This constructor parses a line from a .csv file as a string into the fields for this class.
	 * @param line
	 */
	Patient(String line) {
		String[] parameters = line.split(",");
		
		this.bodyTemp = Float.parseFloat(parameters[0]);
		this.heartRate = Integer.parseInt(parameters[1]);
		this.respiratoryRate = Integer.parseInt(parameters[2]);
		this.urineOutput = Boolean.parseBoolean(parameters[3]);
		this.plateletCount = Double.parseDouble(parameters[4]);
		this.difficultyBreathing = Boolean.parseBoolean(parameters[5]);
		this.abnormalHeartPump = Boolean.parseBoolean(parameters[6]);
		this.age = Integer.parseInt(parameters[7]);
		this.abdominalPain = Boolean.parseBoolean(parameters[8]);
		this.weakenedImmuneSystem = Boolean.parseBoolean(parameters[9]);
	} // second constructor
	
	/**
	 * Prints out the patient data in a readable format specifically for output to the console.
	 * 
	 * 
	 * @return
	 */
	public String toStringForConsole() { 
		return "\r\nBody Temperature: " + bodyTemp + "\r\nHeart Rate: " + heartRate + "\r\nRespiratory Rate: " + respiratoryRate +
				"\r\nUrine Output: " + urineOutput + "\r\nPlatelet Count: " + plateletCount + "\r\nDifficulty Breathing: " +
				difficultyBreathing + "\r\nAbnormal Heart Pump: " + abnormalHeartPump + "\r\nAge: " + age + "\r\nAbdominal Pain: " +
				abdominalPain + "\r\nWeakened Immune System: " + weakenedImmuneSystem;
	} // toStringForConsole()
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[ " + bodyTemp + ", " + heartRate + ", " + respiratoryRate + ", \"" + urineOutput + "\", " + plateletCount + ", \"" +
				difficultyBreathing + "\", \"" + abnormalHeartPump + "\", " + age + ", \"" + abdominalPain + "\", \"" + weakenedImmuneSystem + "\" ]";
	} // toString()
	
} // Patient Class
