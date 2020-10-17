/**
 * 
 */
package gui;

import generation.Order;

import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;


/**
 * This class is a wrapper class to startup the Maze game as a Java application
 * 
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 * 
 * TODO: use logger for output instead of Sys.out
 */
public class MazeApplication extends JFrame {

	// not used, just to make the compiler, static code checker happy
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public MazeApplication() {
		init(null);
	}

	/**
	 * Constructor that loads a maze from a given file or uses a particular method to generate a maze
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that stores an already generated maze that is then loaded, or can be null
	 */
	public MazeApplication(String parameter) {
		init(parameter);
	}
	
	public MazeApplication(String generation, String driver, String flrb) {
		Controller result = new Controller();
		result.setDeterministic(false);
		String msg = "";
		if (generation == null || "DFS".equalsIgnoreCase(generation)) {
			msg += "MazeApplication: maze will be generated with a randomized algorithm.\n"; 
		} else if ("Prim".equalsIgnoreCase(generation)){
		        msg += "MazeApplication: generating random maze with Prim's algorithm.\n";
		        result.setBuilder(Order.Builder.Prim);
	    } else if ("Kruskal".equalsIgnoreCase(generation)){
	        throw new RuntimeException("Don't know anybody named Kruskal ...");
	    } else if ("Eller".equalsIgnoreCase(generation)){
	    	msg += "MazeApplication: generating random maze with Eller's algorithm.\n";
	        result.setBuilder(Order.Builder.Eller);
	    }
		
		if (driver == null || "Manual".equalsIgnoreCase(driver)) {
			msg += "MazeApplication: maze will be solved manually.\n";
		}  else if ("Wizard".equalsIgnoreCase(driver)){
	    	msg += "MazeApplication: maze will be solved with Wizard.\n";
	    	ReliableRobot rob = new ReliableRobot();
	    	Wizard wiz = new Wizard();
	    	result.setRobotAndDriver(rob, wiz);
	    }
	    else if ("WallFollower".equalsIgnoreCase(driver)){
	    	msg += "MazeApplication: maze will be solved with MazeFollower.\n";
	    	UnreliableRobot rob = new UnreliableRobot(flrb);
	    	WallFollower wall_e = new WallFollower();
	    	result.setRobotAndDriver(rob, wall_e);
	    }
		System.out.println(msg);
		
		add(result.getPanel()) ;
		// instantiate a key listener that feeds keyboard input into the controller
		// and add it to the JFrame
		KeyListener kl = new SimpleKeyListener(this, result) ;
		addKeyListener(kl) ;
		// set the frame to a fixed size for its width and height and put it on display
		setSize(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT+22) ;
		setVisible(true) ;
		// focus should be on the JFrame of the MazeApplication and not on the maze panel
		// such that the SimpleKeyListener kl is used
		setFocusable(true) ;
		// start the game, hand over control to the game controller
		result.start();
	}

	/**
	 * Instantiates a controller with settings according to the given parameter.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
	 * or a filename that contains a generated maze that is then loaded,
	 * or can be null
	 * @return the newly instantiated and configured controller
	 */
	 Controller createController(String parameter) {
	    // need to instantiate a controller to return as a result in any case
	    Controller result = new Controller() ;
	    // can decide if user repeatedly plays the same mazes or 
	    // if mazes are different each and every time
	    // set to true for testing purposes
	    // set to false for playing the game
	    result.setDeterministic(true);
	    String msg = null; // message for feedback
	    // Case 1: no input
	    if (parameter == null) {
	        msg = "MazeApplication: maze will be generated with a randomized algorithm."; 
	    }
	    // Case 2: Prim
	    else if ("Prim".equalsIgnoreCase(parameter))
	    {
	        msg = "MazeApplication: generating random maze with Prim's algorithm.";
	        result.setBuilder(Order.Builder.Prim);
	    }
	    // Case 3 a and b: Eller, Kruskal or some other generation algorithm
	    else if ("Kruskal".equalsIgnoreCase(parameter))
	    {
	    	// TODO: for P2 assignment, please add code to set the builder accordingly
	        throw new RuntimeException("Don't know anybody named Kruskal ...");
	    }
	    else if ("Eller".equalsIgnoreCase(parameter))
	    {
	    	msg = "MazeApplication: generating random maze with Eller's algorithm.";
	        result.setBuilder(Order.Builder.Eller);
	    }
	    else if ("Wizard".equalsIgnoreCase(parameter))
	    {
	    	msg = "MazeApplication: solving random maze with Wizard.";
	    	ReliableRobot rob = new ReliableRobot(); 
	    	Wizard wiz = new Wizard();
	    	result.setRobotAndDriver(rob, wiz); // links the controller to the robot and driver
	    }
	    else if ("WallFollower".equalsIgnoreCase(parameter))
	    {
	    	msg = "MazeApplication: solving random maze with MazeFollower.";
	    	String string = null;
	    	UnreliableRobot rob = new UnreliableRobot(string);
	    	WallFollower wall_e = new WallFollower();
	    	result.setRobotAndDriver(rob, wall_e);
	    }
	    // Case 4: a file
	    else {
	        File f = new File(parameter) ;
	        if (f.exists() && f.canRead())
	        {
	            msg = "MazeApplication: loading maze from file: " + parameter;
	            result.setFileName(parameter);
	            return result;
	        }
	        else {
	            // None of the predefined strings and not a filename either: 
	            msg = "MazeApplication: unknown parameter value: " + parameter + " ignored, operating in default mode.";
	        }
	    }
	    // controller instanted and attributes set according to given input parameter
	    // output message and return controller
	    System.out.println(msg);
	    return result;
	}

	/**
	 * Initializes some internals and puts the game on display.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that contains a generated maze that is then loaded, or can be null
	 */
	private void init(String parameter) {
	    // instantiate a game controller and add it to the JFrame
	    Controller controller = createController(parameter);
		add(controller.getPanel()) ;
		// instantiate a key listener that feeds keyboard input into the controller
		// and add it to the JFrame
		KeyListener kl = new SimpleKeyListener(this, controller) ;
		addKeyListener(kl) ;
		// set the frame to a fixed size for its width and height and put it on display
		setSize(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT+22) ;
		setVisible(true) ;
		// focus should be on the JFrame of the MazeApplication and not on the maze panel
		// such that the SimpleKeyListener kl is used
		setFocusable(true) ;
		// start the game, hand over control to the game controller
		controller.start();
	}
	
	
	/**
	 * Main method to launch Maze game as a java application.
	 * The application can be operated in three ways. 
	 * 1) The intended normal operation is to provide no parameters
	 * and the maze will be generated by a randomized DFS algorithm (default). 
	 * 2) If a filename is given that contains a maze stored in xml format. 
	 * The maze will be loaded from that file. 
	 * This option is useful during development to test with a particular maze.
	 * 3) A predefined constant string is given to select a maze
	 * generation algorithm, currently supported is "Prim".
	 * @param args is optional, first string can be a fixed constant like Prim or
	 * the name of a file that stores a maze in XML format
	 */
	public static void main(String[] args) {
	    JFrame app ; 
//	    for (int i = 0; i < args.length; i++) {
//	    	System.out.println(args[i]);
//	    }
	    
		switch (args.length) {
		case 6:
		case 5:
		case 4:
		case 3:
		case 2:
			String generation = null;
			String driver = null;
			String reliable = null;
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-g":
					switch (args[i+1]) {
					case "Prim":
					case "Kruskal":
					case "Eller":
					case "DFS":
						generation = args[i+1];
						break;
					default:
						File f = new File(args[i+1]) ;
				        if (f.exists() && f.canRead())
				        {
				        	generation = args[i+1];
				        }
				        else {
				        	System.err.println("Usage: MazeApplication [-g maze] [-d driver] [-r flrb]");
				        }				
						break;
					}
					break;
				case "-d":
					switch (args[i+1]) {
					case "Wizard":
					case "WallFollower":
						driver = args[i+1];
						break;
					default:
						System.err.println("Usage: MazeApplication [-g maze] [-d driver] [-r flrb]");
						break;
					}
					break;
				case "-r":
					reliable = args[i+1];
					break;
				default:
					break;
				}
			}
			app = new MazeApplication(generation, driver, reliable);
			break;
		case 1 : app = new MazeApplication(args[0]);
		break ;
		case 0 : 
		default : app = new MazeApplication() ;
		break ;
		}
		app.repaint() ;
	}

}
