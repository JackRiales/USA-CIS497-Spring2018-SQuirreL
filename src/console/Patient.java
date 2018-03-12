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
 * TODO Write description for Patient class.
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
	
	Patient() {}

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
	
	Patient(String line) {
		String[] parameters = line.split(", ");
		
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
	}
}
