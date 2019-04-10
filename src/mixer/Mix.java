package mixer;

import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;

public class Mix {

    /** Double linked list to hold users message in its current state */
    private DoubleLinkedList<Character> message;
    
    /** Inverses of commands performed to unmix message */
    private String undoCommands;
    
    /**  */
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


    public Mix(String message) {
        scan = new Scanner(System.in);
        this.message = new DoubleLinkedList<Character>();
        clipBoards = new Hashtable<Integer, DoubleLinkedList<Character>>();


        for(int i=0; i<message.length(); ++i) {
            this.message.add(message.charAt(i));
        }

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
                    if(!message.validIndex(index1) && index1 != message.size()) {
                        System.out.println("Index argument is out of range.");
                        break;
                    }

                    // Input is verrify so apply command
                    insertbefore(parsedInput[1], index1);
                    break;

                // r [INDEX] [INDEX]
                // or
                // r [CHAR1] [CHAR2]
                case "r":

                    boolean removeFlag = false;

                    // Check for correct number of arguments
                    if(parsedInput.length != 3) {
                        System.out.println("Incorrect number of arguments for command.");
                        break;
                    }

                    // Check if arguments are both integers, then use remove command
                    if(parsedInput[1].matches(intRegx) && parsedInput[2].matches(intRegx)) {

                        // Arguments are not integers
                        removeFlag = true;
                    }
                    else {
                        removeFlag = false;
                    }

                    // Use remove command
                    if(removeFlag) {

                        // Convert index input to integers
                        index1 = Integer.parseInt(parsedInput[1]);
                        index2 = Integer.parseInt(parsedInput[2]);

                        // Check that indexes are vaild
                        if(!message.validIndex(index1) || !message.validIndex(index2)) {

                            // Index args are invalid
                            System.out.println("Index arguments are invalid.");
                            break;
                        }

                        if(index1 > index2) {
                            System.out.println("First index must be smaller than or equal to second index.");
                            break;
                        }

                        // Apply command
                        remove(index1, index2);
                        break;
                    }

                    // Use replace command
                    else {

                        // Check if arguments are both chars
                        if(parsedInput[1].length() != 1 || parsedInput[2].length() != 1) {
                            System.out.println("Arguments for replace command must be characters.");
                            break;
                        }

                        replace(parsedInput[1].charAt(0), parsedInput[2].charAt(0));
                        break;
                    }

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

                // x [CLIPBOARD_INDEX] [START_INDEX] [STOP_INDEX]
                case "x":

                    // Verrify that inputs are valid
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
                        System.out.println("Second and/or third argument is an invalid index for the message.");
                    }

                    // TODO: check if clipboard index is correct

                    // Apply command
                    cut(index1, index2, index3);
                    break;

                // p [CLIPBOARD_INDEX] [START_INDEX]
                case "p":


                    // Verrify that inputs are valid
                    if(parsedInput.length != 3) {

                        // Incorrect amount of arguments
                        System.out.println("Incorrect number of command arguments.");
                        break;
                    }

                    // Check that all inputs are integers
                    if(!parsedInput[1].matches(intRegx) || !parsedInput[2].matches(intRegx)) {
                        
                        // Not all inputs are integers
                        System.out.println("All arguments must be integers.");
                        break;
                    }

                    // Convert arguments to integers
                    index1 = Integer.parseInt(parsedInput[1]);
                    index2 = Integer.parseInt(parsedInput[2]);
                    
                    // Check that message indexes are in valid range
                    if(!message.validIndex(index2)) {
                        System.out.println("Second argument is an invalid index for the message.");
                    }

                    // TODO: check if clipboard index is correct

                    // Apply command
                    paste(index1, index2);
                    break;

                // h
                case "h":
                    helpPage();
                    break;

                // d [CHAR]
                case "d":

                    // Verrify parameters for command
                    if(parsedInput.length != 2) {
                        System.out.println("Incorrect number of arguments for command");
                    }

                    // Check that argument is a single character
                    if(parsedInput[1].length() != 1) {
                        System.out.println("Argument must be a single character.");
                    }

                    // Get character that is being deleted from the message
                    char toRemove = parsedInput[1].charAt(0);

                    // Delete all instances in the meesage
                    delete(toRemove);
                    break;

                // z
                case "z":

                    break;

                // Error case
                default:
                    System.out.println("ERROR - Invalid command: " + command);
                    break;
                }

            }
            else {

                // Inform user of invalid command argument
                System.out.println("Invalid command argument: " + parsedInput[0]);
                System.out.println("See help page with command \"h\" for more information.");
            }

            System.out.println("undo cmd: " + this.undoCommands);
        }
    }

    /**
     * Delete all instances of a char in the message list.
     *
     * @param toRomve - char that is to be removed from the list.
     */
    public void delete(char toRemove) {

        int i = 0;
        while(message.validIndex(i)) {

            char temp = message.get(i).toString().charAt(0);

            // If the current char should be deleted
            if(temp == toRemove) {

                message.deleteAt(i);
                --i;
                
                // Record inverse command
                int j = i + 1;
                this.undoCommands += "b " + toRemove + " " + j + "\n";
            }

            ++i;
        }
    }

    /**
     * Replace all instances of 'c1' with 'c2'.
     *
     * @param c1 - character being removed and replaced.
     * @param c2 - character being inserted.
     */
    protected void replace(char c1, char c2) {

        // Search for instances of 'c1'
        for(int i=0; i < message.size(); ++i) {

            // Check if match
            if(message.get(i) == c1) {
 
                // Replace data in node with new char
                message.deleteAt(i);
                message.insertAt(i, c2);
            }
        }

        // Record inverse command
        this.undoCommands += "r " + c2 + " " + c1 + "\n";
    }

    /**
     * Remove elements in list from 'start' to 'stop', inclusive.
     *
     * @param start - first index to remove.
     * @param stop - last index to remove.
     * @throws ArrayIndexOutOfBounds - if 'start' or 'stop' is outside 
     *                                 valid index range.
     */
    protected void remove(int start, int stop) throws ArrayIndexOutOfBoundsException {

        if(start < 0 || stop < 0 || 
           start >= message.size() ||  stop >= message.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        String removed = "";

        for(int i=stop; i >= start; --i) {
            char temp = message.deleteAt(i);
            removed = temp + removed;
        }

        this.undoCommands += "b " + removed + " " + start + "\n";
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
    protected void insertbefore(String token, int index) {

        int stop = token.length() + index - 1;

        for(int i=0; i < token.length(); ++i) {
            message.insertAt(index+i, token.charAt(i));
        }

        // Generate and store inverse commands
        this.undoCommands += "r " + index + " " + stop + "\n";
    }

    /**
     * Display message to CLI with index over each character.
     */
    protected void displayMessage() {
        
        System.out.print ("Message:\n");
        userMessage = message.toString();

        for(int i = 0; i < userMessage.length(); i++) 
            System.out.format ("%3d", i);
        System.out.format ("\n");
        for (char c : userMessage.toCharArray()) 
            System.out.format("%3c",c);
        System.out.format ("\n");
    }


    public String getMessage() {
        String temp = "";
        for(int i=0; i<message.size(); ++i) {
            try {
                temp += message.get(i);
            }
            catch(ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        return temp;
    }

    /**
     * Save commands to unmix message to file.
     *
     * @param filename - name of file to save key to.
     */
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
     * Print the command help information to the console.
     */
    protected void helpPage() {
        
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
