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
            
            this.displayMessage();
            System.out.print("Command: ");

            // Save state
            DoubleLinkedList<Character> currMessage =  new DoubleLinkedList<>();
            String currUndoCommands = undoCommands;

            try {
               
                String command = scan.next("[Qbrhdzpcx]");

                switch (command) {
                case "Q":
                    
                    save(scan.next());
                    System.out.println ("Final mixed up message: \"" + message+"\"");
                    System.exit(0); 
                    break;

                case "b":
                    insertbefore(scan.next(), scan.nextInt());
                    break;

                case "r":
                    remove(scan.nextInt(), scan.nextInt());
                    break;

                case "c":
                    copy(scan.nextInt(), scan.nextInt(), scan.nextInt());
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
						char temp = message.get(i);
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
				addUndoCmd(command);

                scan.nextLine();   // should flush the buffer
                System.out.println("For demonstration purposes only:\n" + undoCommands);
            }
            catch (Exception e ) {

                // Ignore and clear erroneous input by flushing buffer
                System.out.println ("Error on input, previous state restored.");
                scan = new Scanner(System.in);

                // Restore state
                undoCommands = currUndoCommands;
                message = currMessage;
            }
        }
    }

    private void remove(int start, int stop) {

        
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
