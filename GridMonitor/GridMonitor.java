import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a grid monitor object that will be used for maintenance
 * of the Solar Array, primarily for preventing medowns.
 * 
 * @author Xavier Caracter
 *
 */
public class GridMonitor implements GridMonitorInterface {
	// FIELDS
	private File inputFile;
	private double arrayRows;
	private double arrayCols;
	private double gridArray[][];
	private ArrayList<Double> tempArrayList;
	private Object[] buffArray;
	private double sumGrid[][];
	private double avgGrid[][];
	private double[][] deltaGrid;
	private boolean[][] dangerGrid;

	// CONSTRUCTORS
	/**
	 * The main constructor for the grid monitor object, passes in a string filename
	 * and initializes values for grid monitor.
	 * 
	 * @param filename of the desired input file.
	 * @throws FileNotFoundException for when file is not found.
	 */
	public GridMonitor(String filename) throws FileNotFoundException {

		/*
		 * Initializes the inputFile using the filename passed into the constructor, the
		 * fileScanner Scanner object, and the tempArrayList.
		 */
		this.inputFile = new File(filename);
		Scanner fileScanner = new Scanner(inputFile);
		this.tempArrayList = new ArrayList<Double>();

		/*
		 * File scanner then adds the doubles from the input file to tempArrayList
		 */
		while (fileScanner.hasNextDouble()) {
			tempArrayList.add(fileScanner.nextDouble());
		}
		fileScanner.close();

		// Converts the array list object into a regular array object, buffArray (buffer
		// array)
		this.buffArray = tempArrayList.toArray();

		// Used the 1st two cells in the array for the dimensions of the grid monitor
		this.arrayRows = (double) buffArray[0];
		this.arrayCols = (double) buffArray[1];

		// Initialized the gridArray and created the value Index for the buffer array
		this.gridArray = new double[(int) arrayRows][(int) arrayCols];
		int valIndex = 2;

		// Creating the 2d array and putting the correct values into said array
		for (int rows = 0; rows < arrayRows; rows++) {
			for (int cols = 0; cols < arrayCols; cols++) {
				gridArray[rows][cols] = (double) buffArray[valIndex];
				valIndex++;
			}
		}

	}

	// METHODS

	@Override
	/**
	 * Returns a copy of the gridArray
	 * @return gridArrayC
	 */
	public double[][] getBaseGrid() {
		double[][] gridArrayC = new double[(int) arrayRows][(int) arrayCols];
		for (int row = 0; row < arrayRows; row++) {
			for (int col = 0; col < arrayCols; col++) {
				gridArrayC[row][col] = gridArray[row][col];
			}
		}
		return gridArrayC;
	}

	@Override
	/**
	 * Returns a copy of the Surrounding Sum Grid
	 * @return sumGridC
	 */
	public double[][] getSurroundingSumGrid() {
		//Initialized all my required values and arrays
		double[][] gridC = gridArray;
		double rowMax = arrayRows;
		double colMax = arrayCols;
		sumGrid = new double[(int) arrayRows][(int) arrayCols];
		
		//Variables that represent the surrounding array cells and the sum of them
		double upCell;
		double downCell;
		double leftCell;
		double rightCell;
		double cellSum;
		
		//Nested loop used for index checking and the correct array cell arithmetic
		for (int row = 0; row < rowMax; row++) {
			for (int col = 0; col < colMax; col++) {
				if (!(row == 0))
					upCell = gridC[row - 1][col];
				else
					upCell = gridC[row][col];

				if (!(row == rowMax - 1))
					downCell = gridC[row + 1][col];
				else
					downCell = gridC[row][col];

				if (!(col == 0))
					leftCell = gridC[row][col - 1];
				else
					leftCell = gridC[row][col];

				if (!(col == colMax - 1))
					rightCell = gridC[row][col + 1];
				else
					rightCell = gridC[row][col];

				cellSum = upCell + downCell + leftCell + rightCell;
				sumGrid[row][col] = cellSum;

			}
		}
		double[][] sumGridC = sumGrid;
		return sumGridC;
	}

	@Override
	/**
	 * Returns a copy of the Surrounding Average grid
	 * @return avgGridC
	 */
	public double[][] getSurroundingAvgGrid() {
		//Initialized all my required values and arrays
		avgGrid = new double[(int) arrayRows][(int) arrayCols];
		sumGrid = new double[(int) arrayRows][(int) arrayCols];
		double[][] gridC = gridArray;
		double rowMax = arrayRows;
		double colMax = arrayCols;
		
		//Variables that represent the surrounding array cells and the sum of them
		double upCell;
		double downCell;
		double leftCell;
		double rightCell;
		double cellSum;
		
		//Nested loop used for index checking and the correct array cell arithmetic
		for (int row = 0; row < rowMax; row++) {
			for (int col = 0; col < colMax; col++) {
				if (!(row == 0))
					upCell = gridC[row - 1][col];
				else
					upCell = gridC[row][col];

				if (!(row == rowMax - 1))
					downCell = gridC[row + 1][col];
				else
					downCell = gridC[row][col];

				if (!(col == 0))
					leftCell = gridC[row][col - 1];
				else
					leftCell = gridC[row][col];

				if (!(col == colMax - 1))
					rightCell = gridC[row][col + 1];
				else
					rightCell = gridC[row][col];

				cellSum = upCell + downCell + leftCell + rightCell;
				sumGrid[row][col] = cellSum;
				avgGrid[row][col] = (sumGrid[row][col]) / 4;

			}
		}

		double[][] avgGridC = avgGrid;
		return avgGridC;
	}

	@Override
	/**
	 * Returns a copy of the Delta Grid
	 * @return deltaGrid
	 */
	public double[][] getDeltaGrid() {
		//Initialized all my required values and arrays
		deltaGrid = new double[(int) arrayRows][(int) arrayCols];
		avgGrid = new double[(int) arrayRows][(int) arrayCols];
		sumGrid = new double[(int) arrayRows][(int) arrayCols];
		double[][] gridC = gridArray;
		double rowMax = arrayRows;
		double colMax = arrayCols;
		
		//Variables that represent the surrounding array cells and the sum of them
		double upCell;
		double downCell;
		double leftCell;
		double rightCell;
		double cellSum;
		
		//Nested loop used for index checking and the correct array cell arithmetic
		for (int row = 0; row < rowMax; row++) {
			for (int col = 0; col < colMax; col++) {
				if (!(row == 0))
					upCell = gridC[row - 1][col];
				else
					upCell = gridC[row][col];

				if (!(row == rowMax - 1))
					downCell = gridC[row + 1][col];
				else
					downCell = gridC[row][col];

				if (!(col == 0))
					leftCell = gridC[row][col - 1];
				else
					leftCell = gridC[row][col];

				if (!(col == colMax - 1))
					rightCell = gridC[row][col + 1];
				else
					rightCell = gridC[row][col];

				cellSum = upCell + downCell + leftCell + rightCell;
				sumGrid[row][col] = cellSum;
				avgGrid[row][col] = (sumGrid[row][col]) / 4;
				deltaGrid[row][col] = Math.abs((avgGrid[row][col]) * 0.5);

			}
		}
		double[][] deltaGridC = deltaGrid;
		return deltaGridC;
	}

	@Override
	/**
	 * Returns a copy of the Danger Grid
	 * @return dangerGrid
	 */
	public boolean[][] getDangerGrid() {
		//Initialized all my required values and arrays
		dangerGrid = new boolean[(int) arrayRows][(int) arrayCols];
		deltaGrid = new double[(int) arrayRows][(int) arrayCols];
		avgGrid = new double[(int) arrayRows][(int) arrayCols];
		sumGrid = new double[(int) arrayRows][(int) arrayCols];
		double[][] gridC = gridArray;
		double rowMax = arrayRows;
		double colMax = arrayCols;
		
		//Variables that represent the surrounding array cells and the sum of them
		double upCell;
		double downCell;
		double leftCell;
		double rightCell;
		double cellSum;
		
		//Nested loop used for index checking and the correct array cell arithmetic
		for (int row = 0; row < rowMax; row++) {
			for (int col = 0; col < colMax; col++) {
				if (!(row == 0))
					upCell = gridC[row - 1][col];
				else
					upCell = gridC[row][col];

				if (!(row == rowMax - 1))
					downCell = gridC[row + 1][col];
				else
					downCell = gridC[row][col];

				if (!(col == 0))
					leftCell = gridC[row][col - 1];
				else
					leftCell = gridC[row][col];

				if (!(col == colMax - 1))
					rightCell = gridC[row][col + 1];
				else
					rightCell = gridC[row][col];

				cellSum = upCell + downCell + leftCell + rightCell;
				sumGrid[row][col] = cellSum;
				avgGrid[row][col] = (sumGrid[row][col]) / 4;
				deltaGrid[row][col] = Math.abs((avgGrid[row][col]) * 0.5);
				
				//Boolean danger value created for danger grid
				boolean dangerVal = false;
				
				//Conditional used for the correct assignment of T/F values in the danger grid
				if ((gridArray[row][col] < avgGrid[row][col] - deltaGrid[row][col])
						|| (gridArray[row][col] > avgGrid[row][col] + deltaGrid[row][col]))
					dangerVal = true;
				dangerGrid[row][col] = dangerVal;

			}
		}
		boolean[][] dangerGridC = dangerGrid;
		return dangerGridC;
	}

}
