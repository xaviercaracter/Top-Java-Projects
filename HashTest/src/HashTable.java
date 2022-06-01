import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Author: Xavier Caracter
 * Date: 10/23/2021
 * Description: This HahTable class represents the Hash table object in the 
 * HashTest java class.  Functionality includes insertion and searching.
 * */
public class HashTable<T> {
	// Fields
	private HashObject<T>[] hashObjectArray;
	private int tableSize;
	String hashType;
	private final int defaultSize = 13;

	// Constructors
	@SuppressWarnings("unchecked")
	public HashTable() {
		this.hashObjectArray = new HashObject[defaultSize];
		this.tableSize = 0;
		this.hashType = "Linear";
	}

	@SuppressWarnings("unchecked")
	public HashTable(int tableSize, String hashType) {
		this.hashObjectArray = new HashObject[tableSize];
		this.tableSize = 0;
		this.hashType = hashType;
	}

	// Methods
	public int hashInsert(T obj) {
		for (int i = 0; i < hashObjectArray.length; i++) {
			int k = hashF(obj, i);
			if (hashObjectArray[k] == null || hashObjectArray[k].getValue() == null) {
				hashObjectArray[k] = new HashObject<T>(obj, i + 1);
				tableSize++;
				return k;
			} else if (hashObjectArray[k].equals(obj)) {
				hashObjectArray[k].increaseDuplicateCount();
				return k;
			}
		}
		return -1;
	}

	public int hashSearch(T obj) {
		int i = 0;
		boolean finish = false;
		while (finish == false) {
			int j = hashF(obj, i);
			if (hashObjectArray[j].equals(obj)) {
				return j;
			}
			i++;
			finish = ((hashObjectArray[j] == null) || (i == hashObjectArray.length));
		}
		return -1;
	}

	private int hashF(T object, int insertAttempts) {
		int key = object.hashCode();
		if (this.hashType.equals("Linear")) {
			return (posMod(key + insertAttempts, hashObjectArray.length));
		}
		if (this.hashType.equals("Double Hashing")) {
			return (posMod(key, hashObjectArray.length)
					+ insertAttempts * (1 + posMod(key, hashObjectArray.length - 2))) % hashObjectArray.length;
		}

		return -1;
	}

	private int posMod(int dividend, int divisor) {
		int result = dividend % divisor;
		if (result < 0) {
			result += divisor;
		}
		return result;

	}

	public double getLoadFactor() {
		return (double) tableSize / hashObjectArray.length;
	}

	public int getDup() {
		int dupVal = 0;
		for (int i = 0; i < hashObjectArray.length; i++) {
			if (hashObjectArray[i] != null) {
				dupVal += hashObjectArray[i].getDuplicateCount();
			}
		}
		return dupVal;
	}

	public double getAvg() {
		int avgVal = 0;
		for (int i = 0; i < hashObjectArray.length; i++) {
			if (hashObjectArray[i] != null) {
				avgVal += hashObjectArray[i].getProbeCount();
			}
		}
		return (double) avgVal / tableSize;
	}

	public void dump() throws FileNotFoundException {
		String fileN = "";
		if (hashType.equals("Linear")) {
			fileN = "linear-dump";
		} else {
			fileN = "double-dump";
		}
		PrintStream pStream = new PrintStream(new File(fileN));
		for (int i = 0; i < hashObjectArray.length; i++) {
			if (hashObjectArray[i] != null) {
				pStream.append("table[");
				pStream.append(String.valueOf(i));
				pStream.append("]: ");
				pStream.append(hashObjectArray[i].toString());
				pStream.append("\n");
			}
		}
		System.setOut(pStream);
		pStream.close();
	}

}
