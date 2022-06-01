import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board as
 * read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to a
 * GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 * @author Xavier Caracter
 */
public class CircuitTracer {

	/**
	 * launch the program
	 * 
	 * @param args three required arguments: first arg: -s for stack or -q for queue
	 *             second arg: -c for console output or -g for GUI output third arg:
	 *             input file name
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			printUsage();
			System.exit(1);
		}
		try {
			new CircuitTracer(args); // create this with args
		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}

	}

	/** Print instructions for running CircuitTracer from the command line. */
	private static void printUsage() {
		System.out.println("Usage: java CircuitTracer [-s | -q] [-c | -g] filename" + "\n\t"
				+ "Where in argument 1, -s represents"
				+ " a Stack for storage and -q represents using a Queue for storage" + "\n\t"
				+ "In argument 2, -c represents" + " a console output, and -g represents a GUI output (not supported)"
				+ "\n\t" + "Argument 3 must be the filename of the file you wish to use");
	}

	/**
	 * Set up the CircuitBoard and all other components based on command line
	 * arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {
		// TODO: parse and validate command line args - first validation provided
		boolean stackUse = false;
		boolean queueUse = false;
		boolean conUse = false;

		if (args[0].equals("-s")) {
			stackUse = true;
		} else if (args[0].equals("-q")) {
			queueUse = true;
		} else {
			System.out.println("Argument 1 is invalid, please refer to the usage.");
			printUsage();
			return;
		}

		if (args[1].equals("-c")) {
			conUse = true;
		} else if (args[0].equals("-g")) {
			System.out.println("GUI not supported.");
		} else {
			System.out.println("Argument 2 is invalid, please refer to the usage.");
			printUsage();
			return;
		}
		// TODO: initialize the Storage to use either a stack or queue
		Storage<TraceState> stateStore = null;
		if (stackUse) {
			stateStore = Storage.getStackInstance();
		} else if (queueUse) {
			stateStore = Storage.getQueueInstance();
		}
		try {
			CircuitBoard circuitboard = new CircuitBoard(args[2]);
			ArrayList<TraceState> bestPaths = new ArrayList<TraceState>(10);
			Point startingPoint = circuitboard.getStartingPoint();
			if (circuitboard.isOpen((int) startingPoint.getX(), (int) startingPoint.getY() + 1)) {
				stateStore.store(new TraceState(circuitboard, (int) startingPoint.getX(), (int) startingPoint.getY() + 1));
			}
			if (circuitboard.isOpen((int) startingPoint.getX(), (int) startingPoint.getY() - 1)) {
				stateStore.store(new TraceState(circuitboard, (int) startingPoint.getX(), (int) startingPoint.getY() - 1));
			}
			if (circuitboard.isOpen((int) startingPoint.getX() + 1, (int) startingPoint.getY())) {
				stateStore.store(new TraceState(circuitboard, (int) startingPoint.getX() + 1, (int) startingPoint.getY()));
			}
			if (circuitboard.isOpen((int) startingPoint.getX() - 1, (int) startingPoint.getY())) {
				stateStore.store(new TraceState(circuitboard, (int) startingPoint.getX() - 1, (int) startingPoint.getY()));
			}
			
			int bestPathLength = Integer.MAX_VALUE;
			while (!stateStore.isEmpty()) {
				TraceState t = stateStore.retrieve();
				if (t.isComplete()) {
					if (bestPaths.isEmpty()) {
						bestPathLength = t.pathLength();
						bestPaths.add(t);
					} else if (t.pathLength() <= bestPathLength) {
						if(t.pathLength() < bestPathLength) {
							bestPaths.clear();
						}
						bestPaths.add(t);
						bestPathLength = t.pathLength();
					}
				} else {
					if (t.isOpen(t.getRow(), t.getCol() + 1)) {
						stateStore.store(new TraceState(t, t.getRow(), t.getCol() + 1));
					}
					if (t.isOpen(t.getRow(), t.getCol() - 1)) {
						stateStore.store(new TraceState(t, t.getRow(), t.getCol() - 1));
					}
					if (t.isOpen(t.getRow() + 1, t.getCol())) {
						stateStore.store(new TraceState(t, t.getRow() + 1, t.getCol()));
					}
					if (t.isOpen(t.getRow() - 1, t.getCol())) {
						stateStore.store(new TraceState(t, t.getRow() - 1, t.getCol()));
					}
					
				}
			}
			if (conUse) {
				for (TraceState t : bestPaths) {
					String test = t.getBoard().toString();
					System.out.println(test);
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
			System.exit(1);
		}

	}
	// TODO: read in the CircuitBoard from the given file
	// TODO: run the search for best paths
	// TODO: output results to console or GUI, according to specified choice

} // class CircuitTracer
