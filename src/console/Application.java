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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Shawyn Kane
 * Date Created: Mar 8, 2018
 */
public class Application {
	private static final String CANNCEL = "cancel";
	private static final String INITIAL_MESSAGE = "The answers for Yes/No and true/false questions/prompts are assumed to be \"No\" and \"false\" respectively, if the answers \"Yes\" and \"true\"," +
			"\r\nrespectively, are not provided. The answers to said questions/prompts are not case sensitive.\r\n";
	private static ArrayList<Patient> patients = new ArrayList<Patient>();
	private static Scanner scanConsole = null;
	
	/**
	 * Displays a prompt and returns the console input as a string.
	 * 
	 * 
	 * @param prompt
	 * @return
	 */
	private static String promptConsole(String prompt) {
		System.out.print(prompt + "\r\n >> ");
		return scanConsole.nextLine();
	} // promptConsole(String prompt)
	
	/**
	 * Displays a prompt to determine if and how the user wants to cancel input for patient data. 
	 * 
	 * 
	 * @param scan
	 * @return
	 */
	private static int promptForCancelCondition() {
		String prompt = "Enter a number to choose one of the following options:" + 
	"\r\n[1] Continue with prompts for current patient." + 
	"\r\n[2] Cancel/discard input data for the current patient only and continue inputing data for patient(s) through console." +
	"\r\n\t(Any and all data entered for the previous patient(s) will Not be discarded. Only the data currently being" +
	"\r\n\t entered for the current patient will be discarded.) ";
		if (patients.size() > 0) prompt += "\r\n[3] Cancel/discard input for current patient and send all the data from the previous patients to Watson.";
		
		
		boolean repeatPrompt = false;
		String answer = "";
		int optionChoosen = -1;
		do {
			answer = promptConsole(prompt);
			try {
				optionChoosen = Integer.parseInt(answer);
				if ((optionChoosen < 1) || (optionChoosen > 3)) throw new NumberFormatException();
				repeatPrompt = false;
			} catch (NumberFormatException e) {
				repeatPrompt = true;
			} // catch
		} while (repeatPrompt);
		return optionChoosen;
	} // promptForCancelCondition(Scanner scan)
	
	/**
	 * Determines if the string passed to it indicates "yes". 
	 * 
	 * 
	 * @param answer
	 * @return
	 */
	private static boolean answeredYes(String answer) { return (answer.equalsIgnoreCase("y")) || (answer.equalsIgnoreCase("yes")); }
	
	/**
	 * Displays the string "prompt" to the user on the console and returns true if the user indicated "yes" to the prompt on the console, false otherwise.
	 * 
	 * 
	 * @param prompt
	 * @return
	 */
	private static boolean yesNoQuestion(String prompt) { return answeredYes(promptConsole(prompt)); }
	
	/**
	 * Prompts the user for patient data and stores the entered data into the patients array to be sent to IBM's Watson.
	 * 
	 * 
	 * @param scan
	 */
	private static void parsePatientDataFromConsole() {
		String answer = "";
		String[] prompts = {"Body Temperature", "Heart Rate", "Respiratory Rate", "Urine Output (true/false)", "Platelet Count", "Difficulty Breathing (true/false)", "Abnormal Heart Pump (true/false)", "Age", "Abdominal Pain (true/false)", "Weakened Immune System (true/false)"};
		
		do {
			Patient p = new Patient();
			boolean cancelCurrentPatientData = false;
			
			for (int i = 0; i < 10; i++) {
				boolean repeatPrompt = false;
				do {
					answer = promptConsole(prompts[i]);
					try {
						switch (i) {
							case 0:
								p.bodyTemp = Float.parseFloat(answer);
								break;
							case 1:
								p.heartRate = Integer.parseInt(answer);
								break;
							case 2:
								p.respiratoryRate = Integer.parseInt(answer);
								break;
							case 3:
								p.urineOutput = Boolean.parseBoolean(answer);
								break;
							case 4:
								p.plateletCount = Double.parseDouble(answer);
								break;
							case 5:
								p.difficultyBreathing = Boolean.parseBoolean(answer);
								break;
							case 6:
								p.abnormalHeartPump = Boolean.parseBoolean(answer);
								break;
							case 7:
								p.age = Integer.parseInt(answer);
								break;
							case 8:
								p.abdominalPain = Boolean.parseBoolean(answer);
								break;
							case 9:
								p.weakenedImmuneSystem = Boolean.parseBoolean(answer);
								break;
						} // switch
						
						repeatPrompt = false;
					} catch (NumberFormatException e) {
						if (answer.equalsIgnoreCase(CANNCEL)) {
							switch (promptForCancelCondition()) {
							case 1: break;
							case 2: 
								cancelCurrentPatientData = true;
								break;
							case 3: return;
							} // switch
						} else repeatPrompt = true;
					} // catch
				} while (repeatPrompt);
				if (cancelCurrentPatientData) break;
			} // for
			if (!cancelCurrentPatientData) {
				
				if (yesNoQuestion("\r\n\r\nPlease review that the following data is correct:\r\n" + p.toStringForConsole() + "\r\n\r\nIs the above patient data correct (Yes/No)"))	{
					patients.add(p);
					System.out.println("The current patient data as listed/described above has been added to a list to be sent to IBM's Watson to be analyzed. The list will be sent" +
					"\r\nwhen you are done entering patient data here.\r\n");
				} else System.out.println("The current patient data as listed/described above has been discarded.\r\n");

			} // if (!cancelCurrentPatientData)
		} while (yesNoQuestion("Do you want to enter data for another patient? (Yes/No)"));
	} // parsePatientDataFromConsole(Scanner scan)
	
	/**
	 * Parses a .csv file passed to it into patient data. This function expects a header and therefore skips the first line.
	 * 
	 * Ensure that the file is in the following format: A single line contains data for a single patient, with each data field for a single patient separated by comma (with no spaces around the comma).
	 * 
	 * 
	 * @param f
	 */
	private static void parseFile(File f) {
		Scanner scanFile = null;
		try {
			scanFile = new Scanner(f);
			scanFile.nextLine();
			while (scanFile.hasNextLine()) patients.add(new Patient(scanFile.nextLine()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanFile.close();
		} // try catch finally
	} // parseFile(File f)
	
	/**
	 * Converts the array of patient data "records" into a string to be concatenated to the string being sent to IBM's Watson.
	 * 
	 * 
	 * @return
	 */
	private static String patientArrayToString() {
		String str = "[ ";
		for (int i = 0; i < patients.size(); i++) {
			if (i < (patients.size()-1)) str += patients.get(i).toString() + ", ";
			else str += patients.get(i).toString() + " ]";
		} // for
		return str;
	} // patientArrayToString()
	
	/**
	 * This function is based off the code provided by IBM at the following URL:
	 * https://dataplatform.ibm.com/ml/deployments/339c171c-4e03-4751-bed9-1457cd1695c1/implementation?projectid=fb10e9a9-6c6e-4904-9418-fc487c8957ae&mlInstanceGuid=0af9cab8-de2f-430b-9591-2af04e51445a&context=analytics
	 * 
	 * This function sends the patient(s) data to IBM's Watson to detect if the patient(s) has/have sepsis.
	 * 
	 * @throws IOException 
	 * 
	 */
	private static void sendInformationAndDisplayScores() throws IOException {
				// NOTE: you must manually construct wml_credentials hash map below
				// using information retrieved from your IBM Cloud Watson Machine Learning Service instance.

				@SuppressWarnings("serial")
				Map<String, String> wml_credentials = new HashMap<String, String>()
					{{
						put("url", "https://ibm-watson-ml.mybluemix.net");
						put("username", "c27817b2-d8c5-45e7-8504-cd1b200929d6");
						put("password", "9d354c90-be1a-496d-bdd2-a3dbeb2e5115");
					}};

				String wml_auth_header = "Basic " +
						Base64.getEncoder().encodeToString((wml_credentials.get("username") + ":" +
							wml_credentials.get("password")).getBytes(StandardCharsets.UTF_8));
				String wml_url = wml_credentials.get("url") + "/v3/identity/token";
				HttpURLConnection tokenConnection = null;
				HttpURLConnection scoringConnection = null;
				BufferedReader tokenBuffer = null;
				BufferedReader scoringBuffer = null;
				try {
					// Getting WML token
					URL tokenUrl = new URL(wml_url);
					tokenConnection = (HttpURLConnection) tokenUrl.openConnection();
					tokenConnection.setDoInput(true);
					tokenConnection.setDoOutput(true);
					tokenConnection.setRequestMethod("GET");
					tokenConnection.setRequestProperty("Authorization", wml_auth_header);
					tokenBuffer = new BufferedReader(new InputStreamReader(tokenConnection.getInputStream()));
					StringBuffer jsonString = new StringBuffer();
					String line;
					while ((line = tokenBuffer.readLine()) != null) jsonString.append(line);
					// Scoring request
					URL scoringUrl = new URL("https://ibm-watson-ml.mybluemix.net/v3/wml_instances/0af9cab8-de2f-430b-9591-2af04e51445a/published_models/668b7b10-eb98-40a0-a791-673bd9188deb/deployments/339c171c-4e03-4751-bed9-1457cd1695c1/online");
					String wml_token = "Bearer " +
							jsonString.toString()
									.replace("\"","")
									.replace("}", "")
									.split(":")[1];
					scoringConnection = (HttpURLConnection) scoringUrl.openConnection();
					scoringConnection.setDoInput(true);
					scoringConnection.setDoOutput(true);
					scoringConnection.setRequestMethod("POST");
					scoringConnection.setRequestProperty("Accept", "application/json");
					scoringConnection.setRequestProperty("Authorization", wml_token);
					scoringConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
					OutputStreamWriter writer = new OutputStreamWriter(scoringConnection.getOutputStream(), "UTF-8");

					// NOTE: manually define and pass the array(s) of values to be scored in the next line
					String payload = "{\"fields\": [\"Body Temp\", \"Heart Rate\", \"Respiratory Rate\", \"Urine Output\", \"Platelet Count\", \"Difficulty Breathing\", \"Abnormal Heart Pump\", \"Age\", \"Abdominal Pain\", \"Weakened Immune System\"], \"values\": " + patientArrayToString() + " }";
					writer.write(payload);
					writer.close();

					scoringBuffer = new BufferedReader(new InputStreamReader(scoringConnection.getInputStream()));
					StringBuffer jsonStringScoring = new StringBuffer();
					String lineScoring;
					while ((lineScoring = scoringBuffer.readLine()) != null) jsonStringScoring.append(lineScoring);					
					
					boolean tryToWriteAgain = false;
					int numberOfOutputFiles = 1;
					do {
						File outputDirectory = new File(System.getProperty("user.dir") + "\\output\\");
						File outputFile = new File(outputDirectory.toString()  +"\\" + LocalDateTime.now().toString().replace(':', '-') + "ouput" + numberOfOutputFiles + ".csv");
						outputDirectory.mkdir();
						
						if (!outputFile.exists()) {
							tryToWriteAgain = false;
							outputFile.createNewFile();
							FileWriter fw = null;
							try {
								fw = new FileWriter(outputFile);
								
								String[] str = jsonStringScoring.toString().split("\"\\],  \"values\": \\[\\[");
								String fieldsStr = str[0].replaceFirst("\\{  \"fields\": \\[\"", "");
								String[] fields = fieldsStr.split("\", \"");
								String header = "";
								for (int i = 0; i < 10; i++) header += fields[i] + ",";
								header += fields[13];
								
								fw.write(header + "\r\n");
								
								String values = str[1].substring(0, str[1].lastIndexOf("]]}"));
								String[] oldRecords = values.split("\"\\]\\], \\[");
								
								for (int i = 0; i < oldRecords.length; i++) {
									int previousIndex = 0;
									String record = "";
									for (int j = 0; j < 10; j++) {
										int index = oldRecords[i].indexOf(", ", previousIndex)+1;
										if (previousIndex == 0) record += oldRecords[i].substring(previousIndex, index-1) + ",";
										else record += oldRecords[i].substring(previousIndex+1, index-1) + ",";
										previousIndex = index;
										
									} // inner for
									String prediction = oldRecords[i].split("\\]\\], \\[")[1].split("\\], ")[2].split(", ")[0];
									if (prediction.equals("1.0")) record += "\"true\"";
									else record +="\"false\"";
									fw.write(record + "\r\n");
								} // outer for
								
								System.out.println("The output is in the following file: \r\n" + outputFile.getAbsolutePath());
								tryToWriteAgain = false;
							} catch (IOException e) {
								numberOfOutputFiles++;
								tryToWriteAgain = true;
								//e.printStackTrace();
								System.out.println("Invalid File: " + e.getMessage());
							} finally {
								try {
									if (fw != null) fw.close();
								} catch (IOException e) {
									e.printStackTrace();
								} // catch
							} // finally
						} // if file exists
						else tryToWriteAgain = true;
					} while (tryToWriteAgain);
				} catch (IOException e) {
					System.out.println("The URL is not valid.");
					System.out.println(e.getMessage());
				} finally {
					if (tokenConnection != null) tokenConnection.disconnect();
					if (tokenBuffer != null) tokenBuffer.close();
					if (scoringConnection != null) scoringConnection.disconnect();
					if (scoringBuffer != null) scoringBuffer.close();
				} // finally
	} // sendInformationAndDisplayScores()
	
	/**
	 * Determines if the user wants to input data through the console or through a .csv file and then gets the data and then sends the data to IBM's Watson.
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Initial Setup for input through console
		scanConsole = new Scanner(System.in);
		System.out.println(INITIAL_MESSAGE);
		
		try {
			
			if (yesNoQuestion("Do you want to input the patient data through the console? (Yes/No)")) parsePatientDataFromConsole();
			else {
				
				boolean repeatPrompt = false;
				File dataFileToBeParsed = null;
				
				do {
					
					// get file from user
					dataFileToBeParsed = new File(promptConsole("Enter Absolute File Path to a \".csv\" file"));
					
					// check file
					try {
						if (!(dataFileToBeParsed.isFile() && dataFileToBeParsed.isAbsolute())) throw new FileNotFoundException("The path given is Not an Absolute File Path of an Existing file.");
						if (!(dataFileToBeParsed.getName().endsWith(".csv"))) throw new IOException("The file is not a \".csv\" file.");
						repeatPrompt = false;
					} catch (IOException e) {
						repeatPrompt = true;
						System.out.println("\r\n" + e.getMessage() + " Please enter the entire directory path with the file name.\r\n\tNOTE: Do NOT enclose the absolute file path in quotes (\"\").\r\n");
					} // catch
					
				} while (repeatPrompt);
				
				parseFile(dataFileToBeParsed);
				
			} // else
			
			sendInformationAndDisplayScores(); // send patient data to IBM Watson and display results
			
		} catch (IOException IOE) {
			IOE.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} // catch (Exception e)
		scanConsole.close();
	} // main(String[] args)
} // Application class
