package com.indus.training.ui.keywordscounteruserinterface;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import com.indus.training.persist.dao.KeyWordsCounterDao;
import com.indus.training.persist.impl.KeyWordsCounterImpl;

/**
 * The KeyWordsCounterUserInterface class provides a command-line interface for
 * managing keywords count files. It allows users to generate, retrieve, update,
 * and delete keyword count files for a given project path.
 */
public class KeyWordsCounterUserInterface {

	/**
	 * Displays the main menu of the application. The menu presents options to the
	 * user for various operations.
	 */
	private static void displayMenu() {
		System.out.println("*************************************************************");
		System.out.println("*         1. Generate KeyWords Count File                   *");
		System.out.println("*         2. Get KeyWords Count                             *");
		System.out.println("*         3. Update KeyWords Count(Rerun)                   *");
		System.out.println("*         4. Delete KeyWords Count File                     *");
		System.out.println("*         5. Exit                                           *");
		System.out.println("*************************************************************");

	}

	/**
	 * Prompts the user to enter their choice of operation.
	 * 
	 * @param scanner the Scanner object used for input
	 * @return the integer choice entered by the user
	 */
	private static int getUserChoice(Scanner scanner) {
		int choice = 0;
		while (true) {
			System.out.print("Enter your choice of Operation: ");
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character
				return choice;
			} else {
				System.out.println("Invalid input. Please enter a number.");
				scanner.next(); // Clear the invalid input
			}
		}
	}

	/**
	 * Saves the keywords count to a file for the specified project path. Prompts
	 * the user to enter a valid path
	 * 
	 * @param scanner    the Scanner object used for input
	 * @param keyWdCtObj the KeyWordsCounterDao object used for file operations
	 */
	private static void saveKeywordsCount(Scanner scanner, KeyWordsCounterDao keyWdCtObj) {
		String path = "";
		boolean validInput = false;
		while (!validInput) {
			System.out.println("Enter the Path of Project for which KeyWords Count File needs to be generated:");
			if (scanner.hasNext()) {
				path = scanner.nextLine();
				if (path != null && !path.trim().isEmpty()) {
					validInput = true;
				} else {
					System.out.println("Invalid path. Please enter a valid path.");
				}
			}
		}
		try {
			boolean isSaved = keyWdCtObj.saveKeyWordsToFile(path);
			if (isSaved) {
				System.out.println("Generated KeyWords Count File Successfully.");
			} else {
				System.out.println("The KeyWords Count File for the given Project Path has already been generated.");
			}
		} catch (IOException e) {
			System.err.println("An error occurred while generating the file: " + e.getMessage());
		}
	}

	/**
	 * Retrieves and displays the keywords count for the specified project path.
	 * Prompts the user to enter a valid path .
	 * 
	 * @param scanner    the Scanner object used for input
	 * @param keyWdCtObj the KeyWordsCounterDao object used for retrieving keyword
	 *                   counts
	 */
	private static void getKeyWordCount(Scanner scanner, KeyWordsCounterDao keyWdCtObj) {
		String path = "";
		boolean validInput = false;
		while (!validInput) {
			System.out.println("Enter the Path of Project to get the KeyWords count:");
			if (scanner.hasNext()) {
				path = scanner.nextLine();
				if (path != null && !path.trim().isEmpty()) {
					validInput = true;
				} else {
					System.out.println("Invalid path. Please enter a valid path.");
				}
			}
		}
		try {
			Map<String, Integer> keyWordsCount = keyWdCtObj.getKeyWords(path);
			for (Map.Entry<String, Integer> entry : keyWordsCount.entrySet()) {
				String keyword = entry.getKey();
				Integer count = entry.getValue();
				System.out.println("Keyword: " + keyword + ", Count: " + count);
			}

		} catch (Exception e) {
			System.err.println("An error occurred: " + e.getMessage());
		}

	}

	/**
	 * Updates the keywords count file for the specified project path. Prompts the
	 * user to enter a valid path.
	 * 
	 * @param scanner    the Scanner object used for input
	 * @param keyWdCtObj the KeyWordsCounterDao object used for updating keyword
	 *                   counts
	 */
	private static void updateKeywordsCount(Scanner scanner, KeyWordsCounterDao keyWdCtObj) {
		String path = "";
		boolean validInput = false;
		while (!validInput) {
			System.out.println("Enter the Path of Project for which KeyWords Count needs to be regenerated:");
			if (scanner.hasNext()) {
				path = scanner.nextLine();
				if (path != null && !path.trim().isEmpty()) {
					validInput = true;
				} else {
					System.out.println("Invalid path. Please enter a valid path.");
				}
			}
		}
		try {
			boolean isUpdated = keyWdCtObj.updateKeyWords(path);
			if (isUpdated) {
				System.out.println("Generated KeyWords Count File Successfully.");
			}
		} catch (IOException e) {
			System.err.println("An error occurred while regenerating: " + e.getMessage());
		}
	}

	/**
	 * Deletes the keywords count file for the specified project path. Prompts the
	 * user to enter a valid path.
	 * 
	 * @param scanner    the Scanner object used for input
	 * @param keyWdCtObj the KeyWordsCounterDao object used for deleting keyword
	 *                   counts
	 */
	private static void deleteKeywordsCount(Scanner scanner, KeyWordsCounterDao keyWdCtObj) {
		String path = "";
		boolean validInput = false;
		while (!validInput) {
			System.out.println("Enter the Path of Project for which KeyWords Count file need to be deleted:");
			if (scanner.hasNext()) {
				path = scanner.nextLine();
				if (path != null && !path.trim().isEmpty()) {
					validInput = true;
				} else {
					System.out.println("Invalid path. Please enter a valid path.");
				}
			}
		}
		try {
			boolean isDeleted = keyWdCtObj.deleteKeyWords(path);
			if (isDeleted) {
				System.out.println("KeyWords Count File for Given Project Deleted Successfully.");
			} else {
				System.out.println("KeyWords Count File for Given Project does not exist");
			}
		} catch (IOException e) {
			System.err.println("An error occurred while deleting: " + e.getMessage());
		}
	}

	/**
	 * Main method to run the KeyWordsCounterUserInterface application.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		KeyWordsCounterImpl keyWdCtObj = new KeyWordsCounterImpl();
		while (true) {
			displayMenu();
			int choice = getUserChoice(scanner);
			switch (choice) {
			case 1:
				saveKeywordsCount(scanner, keyWdCtObj);
				break;
			case 2:
				getKeyWordCount(scanner, keyWdCtObj);
				break;
			case 3:
				updateKeywordsCount(scanner, keyWdCtObj);
				break;
			case 4:
				deleteKeywordsCount(scanner, keyWdCtObj);
				break;
			case 5:
				System.out.println("Exiting");
				return;
			default:
				System.out.println("Invalid Choice. Plese Enter a Valid Choice between 1-5");
				break;

			}

		}

	}

}
