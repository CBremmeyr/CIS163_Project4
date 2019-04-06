package mixer;

import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;

public class Mix {

    /** Double linked list to hold users message in its current state */
    private DoubleLinkedList<Character> message;
    
    /** Inverses of commands performed */
    private String undoCommands;
    
    
    private Hashtable<Integer, DoubleLinkedList<Character>> clipBoards;

    /** Initial message from CLI input */
    private String userMessage;
    
    /** Scaneer for user input through CLI */
    private Scanner scan;

    
    public Mix() {
        scan = new Scanner(System.in);
        message = new DoubleLinkedList<Character>();
        clipBoards = new Hashtable<Integer, DoubleLinkedList<Character>>();

        undoCommands = "";
    }

    
    public static void main(String[] args) {

        Mix mix = new Mix();

		// Try to get initial user message
        try {
            mix.userMessage = args[0];
        }
        catch(ArrayIndexOutOfBoundsException e) {
            
			// Inform user of misusage problem and terminate
            System.out.println("Need starting message as command line argument.");
            System.exit(1);
        }

        // Init message list to inputed string
        for(int i=0; i < mix.userMessage.length(); ++i) {
           mix.message.add(mix.userMessage.charAt(i));
        }

		// Show initial message
        System.out.println("Message to mix: " + mix.userMessage);
        System.out.println();

		// Start mixing command prompt
        mix.mixture();
    }


    private void mixture() {

        while(true) {
            
            displayMessage();
            
			// Save state
            DoubleLinkedList<Character> currMessage =  new DoubleLinkedList<>();
            String currUndoCommands = undoCommands;
   
			// Get next full command
            System.out.print("Command: ");
			String input = scan.nextLine();
			
			// Parse command from input
			char command = input.charAt(0);

			// Flag to indecate an invalid input
			boolean invalidInput = false;

			// Split input around whitespace
			String[] parsedInput = input.split("\\s+");

			// Trim off whitespace from parsed strings
			for(int i=0; i < parsedInput.length; ++i) {
				parsedInput[i].trim();
			}

			// Test if command is valid
			boolean validCommand = true;
			String commandList = "Qbrcxphdz";

			// Check that command is correct length
			if(parsedInput[0].length() != 1) {
				validCommand = false;
			}

			// Check that the command argument is a valid command
			if(!commandList.contains(parsedInput[0])) {
				validCommand = false;
			}

			// Process command if it's valid
			if(validCommand) {

				int index1 = -1;
				int index2 = -1;
				int index3 = -1;
				String intRegx = "-?\\d+";

				// Parse and process command
    	        switch(parsedInput[0]) {

				// Q [FILENAME]
        	    case "Q":

					// Check that file name was given with command
					String fileName = "";
					if(input.length() > 1) {
						fileName = input.substring(1).trim();
					}
					else {
						System.out.println("Command Q requires a file name.");
						break;
					}

                	save(fileName);
					System.out.println("Saved to: " + fileName);
                    System.out.println ("Final mixed up message: \"" + message+"\"");
	                System.exit(0); 
    	            break;

				// b [STRING] [INDEX]
       	        case "b":
					
					// Check that command pramaters are valid
					if(parsedInput.length != 3) {
						
						// Too few arguments
						System.out.println("Too few arguments for command.");
						break;
					}
					else if(!parsedInput[2].matches(intRegx)) {
						
						// Index arg is not an int
						System.out.println("Second agrument must be an integer");
						break;
					}

					// Convert index arg to int
					index1 = Integer.parseInt(parsedInput[2]);

					// Check if index is valid
					if(message.validIndex(index1)) {
						System.out.println("Index argument is out of range: " + index1);
						break;
					}

					// Input is verrify so apply command
					insertbefore(parsedInput[1], index1);
                    break;

				// r [INDEX] [INDEX]
	            case "r":

					// Verify command arguments
					if(!parsedInput[1].matches(intRegx) || !parsedInput[2].matches(intRegx)) {

						// Argument(s) are not integers
						System.out.println("Arguments must both be integers.");
					}

					// Convert index input to integers
					index1 = Integer.parseInt(parsedInput[1]);
					index2 = Integer.parseInt(parsedInput[2]);

					// Check that indexes are vaild
					if(!message.validIndex(index1) || !message.validIndex(index2)) {

						// Index args are invalid
						System.out.println("Index arguments are invalid.");
					}

					// Apply command
					remove(index1, index2);
       	            break;

				// c [CLIPBOARD_INDEX] [START_INDEX] [END_INDEX]
           	    case "c":

					// Verrify command arguments
					if(parsedInput.length != 4) {
						
						// Incorrect amount of arguments
						System.out.println("Incorrect number of command arguments.");
						break;
					}

					// Check that all inputs are integers
					if(!parsedInput[1].matches(intRegx) || !parsedInput[2].matches(intRegx) || 
					   !parsedInput[3].matches(intRegx)) {
						
						// Not all inputs are integers
						System.out.println("All arguments must be integers.");
						break;
					}
					
					// Convert arguments to integers
					index1 = Integer.parseInt(parsedInput[1]);
					index2 = Integer.parseInt(parsedInput[2]);
					index3 = Integer.parseInt(parsedInput [3]);

					// Check that message indexes are in valid range
					if(!message.validIndex(index2) || !message.validIndex(index3)) {
						System.out.println("Second and/or third argument is an invalid index.");
					}

					// TODO: check if clipboard index is correct

					// Apply command
					copy(index1, index2, index3);
                   	break;

                case "x":
    	            cut(scan.nextInt(), scan.nextInt(), scan.nextInt());
        	        break;

                case "p":
               	    paste(scan.nextInt(), scan.nextInt());
                   	break;

	            case "h":
   	                helpPage();
       	            break;

            	case "d":
					String param = scan.next();	
	
					// Invalid parameter for command
					if(param.length() > 1) {
						System.out.println("Command parameter '" + param +
								 "' is too long to be valid, must be a char");
					}
	
					char toRemove = param.charAt(0);
	
					for(int i=0; i<message.size(); ++i) {
						char temp = message.get(i).toString().charAt(0);

						if(temp == toRemove) {

							message.deleteAt(i);
							// TODO: record inverse command
						}
					}

                   	break;

             	case "z":

    	            break;

                // Error case
           	    default:
               	    System.out.println("Invalid command: " + command);
                   	break;
               	}

				// Add inverse commands to list of undo commands
				addUndoCmd(input);

                scan.nextLine();   // should flush the buffer
                System.out.println("For demonstration purposes only:\n" + undoCommands);
			}
            else {

				// Inform user of invalid command argument
				System.out.println("Invalid command argument: " + parsedInput[0]);
				System.out.println("See help page with command \"h\" for more information.");

                // Ignore and clear erroneous input by flushing buffer
//                System.out.println ("Error on input, previous state restored.");
//                scan = new Scanner(System.in);

                // Restore state
//                undoCommands = currUndoCommands;
//                message = currMessage;
            }
        }
    }

	/**
	 * Remove elements in list from 'start' to 'stop', inclusive.
	 *
	 * @param start - first index to remove.
	 * @param stop - last index to remove.
	 * @return String of removed elements.
	 * @throws ArrayIndexOutOfBounds - if 'start' or 'stop' is outside 
	 *                                 valid index range.
	 */
    private String remove(int start, int stop) throws ArrayIndexOutOfBoundsException {

		if(start < 0 || stop < 0 || 
		   start >= message.size() ||  stop >= message.size()) {
			throw new ArrayIndexOutOfBoundsException();
		}

		String removed = "";

        for(int i=start; i <= stop; ++i) {
			char temp = message.deleteAt(i);
			removed = removed + temp;
		}

		return removed;
    }

    private void cut(int start, int stop, int clipNum) {

    }

    /**
     * Copy values in message from indices start to stop to the clipboard.
     *
     * @param start - index to start copying from.
     * @param stop - index to stop copying at.
     * @param clipNum - clipboard index to copy values to.
     */
    private void copy(int start, int stop, int clipNum) {

    }

    /**
     * Paste data from clip board to index.
     * 
     * @param index - location in message to insert message before.
     * @param clipNum - clipboard index to paste from.
     */
    private void paste( int index, int clipNum) {

    }
    

    /**
     * Insert string before the provided index.
     * 
     * @param token - String to be inserted.
     * @param index - Location to be inserted before.
     */
    private void insertbefore(String token, int index) {
        
        for(int i=0; i <token.length(); ++i) {
			
			this.displayMessage();

            message.insertAt(index+i, token.charAt(i));
        }
    }

	/**
	 * Display message to CLI with index over each character.
	 */
    private void displayMessage() {
        
        System.out.print ("Message:\n");
        userMessage = message.toString();

        for (int i = 0; i < userMessage.length(); i++) 
            System.out.format ("%3d", i);
        System.out.format ("\n");
        for (char c : userMessage.toCharArray()) 
            System.out.format("%3c",c);
        System.out.format ("\n");
    }

	
    public void save(String filename) {

        PrintWriter out = null;

        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println(undoCommands);
        out.close();
    }

	/**
	 * Find inverse of provided command and add to list of undo commands.
	 *
	 * @param command - command to find inverse of and record.
	 */
	private void addUndoCmd(String command) {

		
	}

    /**
     * Print the command help information to the console.
     */
    private void helpPage() {
        
        System.out.println("Commands:");
        System.out.println("\tQ [filename]\t\t\tquit and save to filename");         
        System.out.println("\t  ~ is used for a space character" );     
        System.out.println("\tb [STRING] [INDEX]\t\tinsert [STRING] " +
        "before [INDEX]");
        System.out.println("\tr [START] [STOP]\t\tremove all charecters" +
         " from index start to stop");
        System.out.println("\td [CHAR]\t\t\tremove all [CHAR] elements " +
         "in the list");
        System.out.println("\tr [CHAR1] [CHAR2]\t\treplace all " + 
        "[CHAR1]'s with [CHAR2]");
        System.out.println("\tz\t\t\t\trandomly run other commands several" +
         " times");
        System.out.println("\tp [INDEX] [CLIPBOARD]\t\tpaste from " +
         "clipboard number [CLIPBOARD] to message index [INDEX]");
        System.out.println("\tc [START] [STOP] [CLIPBOARD]\t" + 
        "copy message values from index [START] to [STOP] to " + 
        "clipboard [CLIPBOARD]");
        System.out.println("\tx [STOP] [START] [CLIPBOARD]\tcut from " +
         "message index [START] to [STOP] to clipboard [CLIPBOARD]");
        System.out.println("\th\t\t\t\tmeans to show this help page");
    }
}
