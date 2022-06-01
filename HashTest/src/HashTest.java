import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

/**
 * Author: Xavier Caracter 
 * Date: 10/23/2021 
 * Description: This is the driver
 * class and will demonstrate the functionality of a hash table using 3
 * arguments the first with options 1-3 represented by: 1: A hash table of
 * randomly generated numbers, 2: System.currentTimeMillis(), 3: from the
 * word-list file), the second argument being load factor (0.5-0.99), and the
 * third being debug mode (0 - default, prints summary of experiment to console
 * 1 - prints the summary of experiment on the console and also print the hash
 * to source directory.
 */
public class HashTest {

	public static void showUsage() {
		System.out.println("Usage: java HashTest   <input type>   <load factor>   [<debug level>]");
		System.exit(1);
	}

	public static int getTwinPrimes(int low, int high) {
		if (high < low) {
			return -1;
		}
		int start = -1;
		if (low % 2 == 1) {
			start = low;
		} else if (low % 2 == 0 && low != 2) {
			start = low + 1;
		} else {
			start = low;
		}

		for (int i = start; i <= high; i++) {
			boolean isPrime1 = true;
			for (int j = 2; j < i; j++) {
				if (i % j == 0) {
					isPrime1 = false;
				}
			}
			if (isPrime1) {
				boolean isPrime2 = true;
				for (int j = 2; j < i + 2; j++) {
					if ((i + 2) % j == 0) {
						isPrime2 = false;
					}
				}
				if (isPrime2) {
					return (i + 2);
				}
			}
		}

		return -1;
	}

	public static void main(String[] args) {
		int tableSize = getTwinPrimes(95501, 96000);
		double loadFactor = Double.parseDouble(args[1]);
		if (args[0].equals("1")) {
			Random randN = new Random();
			HashTable<Integer> table1 = new HashTable<Integer>(tableSize, "Linear");
			HashTable<Integer> table2 = new HashTable<Integer>(tableSize, "Double Hashing");
			int i = 0;
			while (table1.getLoadFactor() < loadFactor) {
				int nextInt = randN.nextInt();
				table1.hashInsert(nextInt);
				table2.hashInsert(nextInt);
				i++;

			}
			System.out.println("A good table size is found: " + tableSize);
			System.out.println("Data source type: word-list\n");
			System.out.println("Using Linear Hashing....");
			System.out.println("Input " + i + " elements, of which " + table1.getDup() + " duplicates");
			System.out.println(
					"load factor = " + args[1] + ", Avg. no. of probes " + table1.getAvg() + "\n");

			System.out.println("Using Double Hashing....");
			System.out.println("Input " + i + " elements,  of which " + table2.getDup() + " duplicates");
			System.out
					.println("load factor = " + args[1] + ", Avg. no. of probes " + table2.getAvg());

			
			if (args[2].equals("1")) {
				try {
					table1.dump();
					table2.dump();
					System.out.println("A good table size is found: " + tableSize);
					System.out.println("Data source type: word-list\n");
					System.out.println("Using Linear Hashing....");
					System.out.println("Input " + i + " elements, of which " + table1.getDup() + " duplicates");
					System.out.println(
							"load factor = " + args[1] + ", Avg. no. of probes " + table1.getAvg() + "\n");

					System.out.println("Using Double Hashing....");
					System.out.println("Input " + i + " elements,  of which " + table2.getDup() + " duplicates");
					System.out
							.println("load factor = " + args[1] + ", Avg. no. of probes " + table2.getAvg());

					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} else if (args[0].equals("2")) {
			HashTable<Long> table1 = new HashTable<Long>(tableSize, "Linear");
			HashTable<Long> table2 = new HashTable<Long>(tableSize, "Double Hashing");
			int i = 0;
			while (table1.getLoadFactor() < loadFactor) {
				Long currentTime = System.currentTimeMillis();
				table1.hashInsert(currentTime);
				table2.hashInsert(currentTime);
				i++;

			}
			System.out.println("A good table size is found: " + tableSize);
			System.out.println("Data source type: word-list\n");
			System.out.println("Using Linear Hashing....");
			System.out.println("Input " + i + " elements, of which " + table1.getDup() + " duplicates");
			System.out.println(
					"load factor = " + args[1] + ", Avg. no. of probes " + table1.getAvg() + "\n");

			System.out.println("Using Double Hashing....");
			System.out.println("Input " + i + " elements,  of which " + table2.getDup() + " duplicates");
			System.out
					.println("load factor = " + args[1] + ", Avg. no. of probes " + table2.getAvg());

			
			if (args[2].equals("1")) {
				try {
					table1.dump();
					table2.dump();
					System.out.println("A good table size is found: " + tableSize);
					System.out.println("Data source type: word-list\n");
					System.out.println("Using Linear Hashing....");
					System.out.println("Input " + i + " elements, of which " + table1.getDup() + " duplicates");
					System.out.println(
							"load factor = " + args[1] + ", Avg. no. of probes " + table1.getAvg() + "\n");

					System.out.println("Using Double Hashing....");
					System.out.println("Input " + i + " elements,  of which " + table2.getDup() + " duplicates");
					System.out
							.println("load factor = " + args[1] + ", Avg. no. of probes " + table2.getAvg());

					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} else if (args[0].equals("3")) {
			HashTable<String> table1 = new HashTable<String>(tableSize, "Linear");
			HashTable<String> table2 = new HashTable<String>(tableSize, "Double Hashing");

			try {
				Scanner line = new Scanner(new File("word-list.txt"));
				int i = 0;
				while (line.hasNextLine() && table1.getLoadFactor() < loadFactor) {
					String word = line.nextLine();
					table1.hashInsert(word);
					table2.hashInsert(word);
					i++;
				}

				System.out.println("A good table size is found: " + tableSize);
				System.out.println("Data source type: word-list\n");
				System.out.println("Using Linear Hashing....");
				System.out.println("Input " + i + " elements, of which " + table1.getDup() + " duplicates");
				System.out.println(
						"load factor = " + args[1] + ", Avg. no. of probes " + table1.getAvg() + "\n");

				System.out.println("Using Double Hashing....");
				System.out.println("Input " + i + " elements,  of which " + table2.getDup() + " duplicates");
				System.out
						.println("load factor = " + args[1] + ", Avg. no. of probes " + table2.getAvg());

				if (args[2].equals("1")) {
					table1.dump();
					table2.dump();
					System.out.println("A good table size is found: " + tableSize);
					System.out.println("Data source type: word-list\n");
					System.out.println("Using Linear Hashing....");
					System.out.println("Input " + i + " elements, of which " + table1.getDup() + " duplicates");
					System.out.println("load factor = " + args[1] + ", Avg. no. of probes "
							+ table1.getAvg() + "\n");

					System.out.println("Using Double Hashing....");
					System.out.println("Input " + i + " elements,  of which " + table2.getDup() + " duplicates");
					System.out.println(
							"load factor = " + args[1] + ", Avg. no. of probes " + table2.getAvg());
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			showUsage();
		}

	}

}
