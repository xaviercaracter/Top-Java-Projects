import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This java class file is designed to take two files through user input.
 * The first file is the source directory of files (fof in some cases as seen below),
 * the second file is a .csv file of destination file paths.
 * The program then copies the contents of the source file to the file paths
 * specified through the .csv file and verifies if the contents are present.
 * @author Xavier Carcater
 *
 */

public class FileDirChecker {

	public static void main(String[] args) {
		//ArrayList for the file paths
		ArrayList<String> filePaths = new ArrayList<String>();
		
		System.out.print("Please enter the file path to the directory of files you wish to transfer: ");
		
		/*User enters file path similar to below
		 * "C:\Users\xavie\Documents\FileDirChecker\FoF"
		 */
		Scanner kbd = new Scanner(System.in);
		String dirOfFiles = kbd.nextLine();
		//File path is now saved in the dirOfFiles var
		
		
		/*File object fofDir is created using the dirOfFiles var
		 * If the directory does not exist then create it
		 * otherwise it will be loaded up for use
		 */
		File fofDir = new File(dirOfFiles);
		if (!fofDir.exists())
		{
			if (fofDir.mkdir())
				System.out.println("Directory is created!");
		} 
		else
		{
			System.out.println("Directory loaded!");
		}

		
		/*User enters the file path to their desired .csv file
		 * that contains their file paths. 
		 * Input is saved using the destCSV var
		 * "C:\Users\xavie\Documents\FileDirChecker\filePaths.csv" for example
		 */
		System.out.print("Please enter the .csv file of destinations for your files: ");
		String destCSV = kbd.nextLine();
		
		
		/*File object for the destCSV var is created and parsed (using scanner objects)
		 * using the comma (",") delimiter.
		 */
		File csvFilePaths = new File(destCSV);
		try
		{
			Scanner fileScanner = new Scanner(csvFilePaths);
			while (fileScanner.hasNext())
			{
				fileScanner.useDelimiter(",");
				String destFilePath = fileScanner.next();
				filePaths.add(destFilePath);
			}
			System.out.println("Successfully loaded " + filePaths.size() + " directories!");
			fileScanner.close();
		}
		catch (FileNotFoundException fnf)
		{
			System.out.println("Unable to open file " + csvFilePaths.getPath() + ". Please try again.");
		}
		
		
		//Scanner object is closed
		kbd.close();
		
		/*The ArrayList for the file paths is converted into an Array.  The program then
		 * iterates on the array based on the size of it, creates or loads the directories,
		 * and copies the contents of the file of file directory into the newly specified 
		 * directories from the .csv file.
		 */
		Object[] fpArray = filePaths.toArray();
		int size = filePaths.size();
		System.out.println(fofDir.toPath().toString());
		for (int i = 0; i < size; i++)
		{
			System.out.println(fpArray[i]);
			String fpAtI = (String) fpArray[i];
			File fpDir = new File(fpAtI);
			if (!fpDir.exists())
			{
				if (fpDir.mkdirs())
					System.out.println("Directory is created!");
			} 
			else
			{
				System.out.println("Directory loaded!");
			}
			
			try {
				FileUtils.copyDirectory(fofDir, fpDir);
				System.out.println("Files copied to directory.");
			} catch (IOException e) {
				System.out.println("Files failed to copy.");
				e.printStackTrace();
			}
		}
		
		/*This section verifies if the contents of the source folder
		 * got copied to the file paths specified in the .csv file using the
		 * size of the directories themselves.
		 */
		System.out.println("Verifying if all files are present....");
		int verified = 0;
		for(int j = 0; j < size ; j++)
		{
			String fpAtI = (String) fpArray[j];
			File fpDir = new File(fpAtI);
			if (FileUtils.sizeOfDirectory(fofDir) == FileUtils.sizeOfDirectory(fpDir))
			{
				System.out.println("Directory " + (j+1) + " verified!");
				verified++;
			}
			else
			{
				System.out.println("Directory " + (j+1) + " not verified!");
			}
		}
		
		/*If the number of verified directories is equal to 
		 *  the size of the file path array, then we call it a success.
		 *  Otherwise it will tell you how many passed verification out of the
		 *  total number of file paths (size of the file path array).
		 */
		if (verified == size)
		{
			System.out.println("Successfully copied all files to each directory!");
		}
		else
		{
			System.out.println(verified + " out of " + size + " directories verified.");
		}
		
		
	}
}


