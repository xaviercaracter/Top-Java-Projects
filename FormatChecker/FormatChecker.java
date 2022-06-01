import java.io.FileNotFoundException;
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a format checker to be used for command line operation.
 * 
 * @author Xavier Caracter
 *
 */
public class FormatChecker {

	public static void main(String[] args) {
		// Conditional for bad user input
		if (args.length == 0)
			System.out.println("Usage: $ java FormatChecker file1 [file2 ... fileN]");
		// Loops through each input file and operates onto them.
		for (String s : args)
			checkIsValid(s);
	}

	// METHOD
	/**
	 * This method checks if the user's inout file is valid or not
	 * 
	 * @param filename of the input file
	 */
	@SuppressWarnings("resource")
	public static void checkIsValid(String filename) {
		// Initialized my row and column markers and their counters
		int row = 0, col = 0, rowC = 0, colC = 0;

		/*
		 * Try an catch statement to catch any errors thrown and to also parse through
		 * and operate on each of the files that the user input.
		 */
		try {
			System.out.println(filename);
			File file = new File(filename);
			
			//Scanners for my file and the integers on the line
			Scanner fileScanner = new Scanner(file);
			Scanner intScanner = new Scanner(fileScanner.nextLine());

			/*
			 * Conditionals for if the  first row contains two 
			 * white-spaced , positive integers
			 */
			if (!intScanner.hasNextInt()) {
				throw new InputMismatchException("The first value in file: " + filename + " is not an integer");
			}

			row = intScanner.nextInt();
			if (!intScanner.hasNextInt()) {
				throw new InputMismatchException("The second value in file: " + filename + " is not an integer");
			}

			col = intScanner.nextInt();
			if (intScanner.hasNextInt()) {
				throw new InputMismatchException("Extra values in file: " + filename);

			}
			
			
			/*
			 * Wile loop to read the rest of the file,
			 * and a nested while loop to read and analyze the contents on
			 * each line
			 */
			while (fileScanner.hasNextLine()) {

				Scanner lineScanner = new Scanner(fileScanner.nextLine());

				while (lineScanner.hasNext()) {

					Scanner currLine = new Scanner(lineScanner.next());
					
					//Conditional for if there is not a valid double on the current line
					if (!currLine.hasNextDouble()) {
						throw new InvalidFileFormatException(
								"Invalid data type detected, please check your matrix for the correct data types");
					}

					colC++;

				}
				//Conditional for if the number of columns is different than the number given
				if (!(col == colC)) {
					throw new InvalidFileFormatException(
							"Number of columns do not match the number of columns specified");
				}
				rowC++;
				colC = 0;
			}
			//Conditional for if the number of rows is different than the number given
			if (!(row == rowC)) {
				throw new InvalidFileFormatException("Number of rows do not match the number of columns specified");
			}

			System.out.println("VALID" + "\n");
			
		//Where the various exceptions are caught.
		} catch (FileNotFoundException e) {
			System.out.println("INVALID" + "\n" + e.toString() + "\n");
		} catch (InputMismatchException e) {
			System.out.println("INVALID" + "\n" + e.toString() + "\n");
		} catch (InvalidFileFormatException e) {
			System.out.println("INVALID" + "\n" + e.toString() + "\n");
		}

	}

}
