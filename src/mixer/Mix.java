package mixer;

import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;

public class Mix {

    
    private DoubleLinkedList<Character> message;
    
    
    private String undoCommands;
    
    
    private Hashtable<Integer, DoubleLinkedList<Character>> clipBoards;

    
    private String userMessage;
    
    
    private Scanner scan;

    
    public Mix() {
        scan = new Scanner(System.in);
        message = new DoubleLinkedList<Character>();
        clipBoards = new Hashtable<Integer, DoubleLinkedList<Character>>();

        undoCommands = "";
    }

    
    public static void main(String[] args) {

        Mix mix = new Mix();

        try {
            mix.userMessage = args[0];
        }
        catch(ArrayIndexOutOfBoundsException e) {
            
            System.out.println("Need starting message as command line argument.");
            System.exit(1);
        }

        // Init message list to inputed string
        for(int i=0; i < mix.userMessage.length(); ++i) {
           mix.message.add(mix.userMessage.charAt(i));
        }

        System.out.println("Message to mix: " + mix.userMessage);
        System.out.println();
        mix.mixture();
    }


    private void mixture() {

        while(true) {
            
            DisplayMessage();
            System.out.print("Command: ");

            // save state
            DoubleLinkedList<Character> currMessage =  new DoubleLinkedList<>();
            String currUndoCommands = undoCommands;

            try {
               
                String command = scan.next("[Qbrhdzpcx]");

                switch (command) {
                case "Q":
                    
                    System.out.println("In Q cmd");

                    if(scan.hasNext()) {
                        save(scan.next());
                        System.out.println ("Final mixed up message: \"" + message+"\"");
                        System.exit(0);
                    }
                    else {
                        System.out.println("Command Q requires a file name to save to.");
                    }
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

                    break;

                case "z":

                    break;

                // Error case
                default:
                    System.out.println("Invalid command: " + command);
                    break;
                }
                scan.nextLine();   // should flush the buffer
                System.out.println("For demonstration purposes only:\n" + undoCommands);
            }
            catch (Exception e ) {

                // Ignore and clear erroneous input by flushing buffer
                System.out.println ("Error on input, previous state restored.");
                scan = new Scanner(System.in);

                // Restore state
                undoCommands = currUndoCommands;
                message = currMessage ;
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
            message.insertAt(index+i, token.charAt(i));
        }
    }

    private void DisplayMessage() {
        
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
