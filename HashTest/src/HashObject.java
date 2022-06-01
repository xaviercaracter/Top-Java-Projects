/**
 * Author: Xavier Caracter
 * Date: 10/23/2021
 * Description: This class represents a hashable object to be used in the
 * hash table in the HashTable.jave class.  Overridden the toString() and
 * the equals() methods.
 * 
 * */
public class HashObject<T> {
	// Fields
	private int duplicateCount;
	private int probeCount;
	private T value;
	
	

	// Constructor
	public HashObject(T value, int probeC) {
		this.value = value;
		this.duplicateCount = 0;
		this.probeCount = probeC;
	}

	// Methods
	public int getDuplicateCount() {
		return duplicateCount;
	}

	public void increaseDuplicateCount() {
		this.duplicateCount = duplicateCount + 1;
	}

	public void decreaseDuplicateCount() {
		this.duplicateCount = duplicateCount - 1;
	}

	public int getProbeCount() {
		return probeCount;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value) + " " + String.valueOf(duplicateCount) + " " + String.valueOf(probeCount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this.value.equals(obj)) {
			return true;
		} else {
			return false;
		}
	}
}
